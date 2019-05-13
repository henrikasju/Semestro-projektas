package com.example.neutronas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.AKAZE;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.neutronas.PatternCamera.currentPhotoPath;

public class Detector extends AppCompatActivity {

    private static AKAZE detector = MainActivity.detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detector);
        // Comment line 46 and uncomment 47, to read existing photo, instead of taking a new one.
        File inFile = new File(currentPhotoPath);
        //File inFile = new File(Environment.getExternalStorageDirectory() + "/imagetempl.jpg");
        System.out.println(currentPhotoPath);
        File templ = new File(Environment.getExternalStorageDirectory() + "/Untitled.png");
        if(inFile.exists()) {
            run(inFile, templ, Environment.getExternalStorageDirectory() + "/matched.jpg");
        }
    }

    private Bitmap setPic() {
        // Get the dimensions of the View
        int targetW = 1680;
        int targetH = 945;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        return bitmap;
    }

    public void run(File inFile, File templateFile, String outFile) {
        // Uncomment line 79 and comment line 82,83 to read existing scene file instead of taking a picture.
        //Mat scene = Imgcodecs.imread(inFile.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);

        // Resize captured picture to not overload memory
        Mat scene = new Mat();
        Utils.bitmapToMat(setPic(), scene);
        Mat pattern = Imgcodecs.imread(templateFile.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
        Mat matchOutput = new Mat();

        // Use AKAZE method for picture and pattern matching
        AKAZE akaze = AKAZE.create();
        MatOfKeyPoint patternKeypts = new MatOfKeyPoint(), sceneKeypts = new MatOfKeyPoint();
        Mat patternDescriptors = new Mat(), sceneDescriptors = new Mat();

        // Detect and compute keypoints of both images
        akaze.detectAndCompute(pattern, new Mat(), patternKeypts, patternDescriptors);
        akaze.detectAndCompute(scene, new Mat(), sceneKeypts, sceneDescriptors);

        // Match said keypoints
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
        List<MatOfDMatch> knnMatches = new ArrayList<>();
        matcher.knnMatch(patternDescriptors, sceneDescriptors, knnMatches, 2);

        // Nearest neighbor matching ratio
        float ratioThreshold = 0.8f;

        List<KeyPoint> listOfMatched1 = new ArrayList<>();
        List<KeyPoint> listOfMatched2 = new ArrayList<>();
        List<KeyPoint> listOfKeypoints1 = patternKeypts.toList();
        List<KeyPoint> listOfKeypoints2 = sceneKeypts.toList();
        LinkedList<Point> objList = new LinkedList<Point>();
        LinkedList<Point> sceneList = new LinkedList<Point>();

        // Add matches to a list
        for (int i = 0; i < knnMatches.size(); i++) {
            DMatch[] matches = knnMatches.get(i).toArray();
            float dist1 = matches[0].distance;
            float dist2 = matches[1].distance;
            if (dist1 < ratioThreshold * dist2) {
                listOfMatched1.add(listOfKeypoints1.get(matches[0].queryIdx));
                listOfMatched2.add(listOfKeypoints2.get(matches[0].trainIdx));
                objList.add(listOfKeypoints1.get(matches[0].queryIdx).pt);
                sceneList.add(listOfKeypoints2.get(matches[0].trainIdx).pt);
            }
        }

        MatOfPoint2f points1 = new MatOfPoint2f(), points2 = new MatOfPoint2f();
        points1.fromList(objList);
        points2.fromList(sceneList);

        // Get homography of the picture
        Mat homo = Calib3d.findHomography(points1, points2, Calib3d.RANSAC);

        // Distance threshold to identify inliers with homography check
        double inlierThreshold = 2.5;
        List<KeyPoint> listOfInliers1 = new ArrayList<>();
        List<KeyPoint> listOfInliers2 = new ArrayList<>();
        List<DMatch> listOfGoodMatches = new ArrayList<>();

        // Filter out only good keypoints
        for (int i = 0; i < listOfMatched1.size(); i++) {
            Mat col = new Mat(3, 1, CvType.CV_64F);
            double[] colData = new double[(int) (col.total() * col.channels())];
            colData[0] = listOfMatched1.get(i).pt.x;
            colData[1] = listOfMatched1.get(i).pt.y;
            colData[2] = 1.0;
            col.put(0, 0, colData);
            Mat colRes = new Mat();
            Core.gemm(homo, col, 1.0, new Mat(), 0.0, colRes);
            colRes.get(0, 0, colData);
            Core.multiply(colRes, new Scalar(1.0 / colData[2]), col);
            col.get(0, 0, colData);
            double dist = Math.sqrt(Math.pow(colData[0] - listOfMatched2.get(i).pt.x, 2) +
                    Math.pow(colData[1] - listOfMatched2.get(i).pt.y, 2));
            if (dist < inlierThreshold) {
                listOfGoodMatches.add(new DMatch(listOfInliers1.size(), listOfInliers2.size(), 0));
                listOfInliers1.add(listOfMatched1.get(i));
                listOfInliers2.add(listOfMatched2.get(i));
            }
        }

        // Draw good matches
        MatOfKeyPoint patternKeypoints = new MatOfKeyPoint(listOfInliers1.toArray(new KeyPoint[listOfInliers1.size()]));
        MatOfKeyPoint sceneKeypoints = new MatOfKeyPoint(listOfInliers2.toArray(new KeyPoint[listOfInliers2.size()]));
        MatOfDMatch goodMatches = new MatOfDMatch(listOfGoodMatches.toArray(new DMatch[listOfGoodMatches.size()]));
        Features2d.drawMatches(pattern, patternKeypoints, scene, sceneKeypoints, goodMatches, matchOutput);

        Bitmap test = Bitmap.createBitmap(scene.cols(), scene.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(scene, test);
        ImageView image = this.findViewById(R.id.pattern_picture);
        image.setImageBitmap(test);

        System.out.println("Amount of good matches:" + listOfGoodMatches.size());
        TextView template = this.findViewById(R.id.textView);

        double inlierRatio = listOfInliers1.size() / (double) listOfMatched1.size();
        if (listOfGoodMatches.size() > 20) {
            String text = "<font color=#00ee59>" +
                    "\nIt's A MATCH!<br>" +
                    "\nA-KAZE Matching Results<br>" +
                    "\n===============================<br>" +
                    "\n# Keypoints 1:                        \t" + listOfKeypoints1.size() + "<br>" +
                    "\n# Keypoints 2:                        \t" + listOfKeypoints2.size() + "<br>" +
                    "\n# Matches:                            \t" + listOfMatched1.size() + "<br>" +
                    "\n# Inliers:                            \t" + listOfInliers1.size() + "<br>" +
                    "\n# Inliers Ratio:                      \t" + inlierRatio + "<br></font>";
            template.setText(Html.fromHtml(text));

            Mat objCorners = new Mat(4, 1, CvType.CV_32FC2), sceneCorners = new Mat();
            float[] objCornersData = new float[(int) (objCorners.total() * objCorners.channels())];
            objCorners.get(0, 0, objCornersData);
            objCornersData[0] = 0;
            objCornersData[1] = 0;
            objCornersData[2] = pattern.cols();
            objCornersData[3] = 0;
            objCornersData[4] = pattern.cols();
            objCornersData[5] = pattern.rows();
            objCornersData[6] = 0;
            objCornersData[7] = pattern.rows();
            objCorners.put(0, 0, objCornersData);

            Core.perspectiveTransform(objCorners, sceneCorners, homo);

            float[] sceneCornersData = new float[(int) (sceneCorners.total() * sceneCorners.channels())];
            sceneCorners.get(0, 0, sceneCornersData);

            Imgproc.line(matchOutput, new Point(sceneCornersData[0] + pattern.cols(), sceneCornersData[1]), new Point(sceneCornersData[2] + pattern.cols(), sceneCornersData[3]), new Scalar(255, 255, 255), 4);
            Imgproc.line(matchOutput, new Point(sceneCornersData[2] + pattern.cols(), sceneCornersData[3]), new Point(sceneCornersData[4] + pattern.cols(), sceneCornersData[5]), new Scalar(255, 255, 255), 4);
            Imgproc.line(matchOutput, new Point(sceneCornersData[4] + pattern.cols(), sceneCornersData[5]), new Point(sceneCornersData[6] + pattern.cols(), sceneCornersData[7]), new Scalar(255, 255, 255), 4);
            Imgproc.line(matchOutput, new Point(sceneCornersData[6] + pattern.cols(), sceneCornersData[7]), new Point(sceneCornersData[0] + pattern.cols(), sceneCornersData[1]), new Scalar(255, 255, 255), 4);
        }
        else {
            String text = "<font color=#ee4511>" +
                    "\nNo pattern found!<br>"+
                    "\nA-KAZE Matching Results<br>" +
                    "\n===============================<br>" +
                    "\n# Keypoints 1:                        \t" + listOfKeypoints1.size() + "<br>" +
                    "\n# Keypoints 2:                        \t" + listOfKeypoints2.size() + "<br>" +
                    "\n# Matches:                            \t" + listOfMatched1.size() + "<br>" +
                    "\n# Inliers:                            \t" + listOfInliers1.size() + "<br>" +
                    "\n# Inliers Ratio:                      \t" + inlierRatio + "<br></font>";
            template.setText(Html.fromHtml(text));
        }

        ImageView matched = this.findViewById(R.id.display_keypoints);
        Bitmap matchedBitmap = Bitmap.createBitmap(matchOutput.cols(), matchOutput.rows(), Bitmap.Config.ARGB_8888);



        // Convert matrix to bitmap
        Utils.matToBitmap(matchOutput, matchedBitmap);

        // Show matched image in app
        matched.setImageBitmap(matchedBitmap);

        // Print out AKAZE information
        Imgcodecs.imwrite(outFile, matchOutput);
        System.out.println("A-KAZE Matching Results");
        System.out.println("*******************************");
        System.out.println("# Keypoints 1:                        \t" + listOfKeypoints1.size());
        System.out.println("# Keypoints 2:                        \t" + listOfKeypoints2.size());
        System.out.println("# Matches:                            \t" + listOfMatched1.size());
        System.out.println("# Inliers:                            \t" + listOfInliers1.size());
        System.out.println("# Inliers Ratio:                      \t" + inlierRatio);
    }
}
