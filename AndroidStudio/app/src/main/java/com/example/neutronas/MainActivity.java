package com.example.neutronas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
//implements CameraBridgeViewBase.CvCameraViewListener2
    static {
        System.loadLibrary("opencv_java");
        System.loadLibrary("nonfree");
    }
    private ImageView imageView3;
    private Bitmap inputImage; // make bitmap from image resource
    private FeatureDetector detectorSift;// = FeatureDetector.create(FeatureDetector.SIFT);

    public void sift() {
        Mat rgba = new Mat();
        Mat rgbaGrey = new Mat();
        Utils.bitmapToMat(inputImage, rgba);
        MatOfKeyPoint keyPoints = new MatOfKeyPoint();
        Imgproc.cvtColor(rgba, rgbaGrey, Imgproc.COLOR_RGBA2GRAY);
        detectorSift.detect(rgbaGrey, keyPoints);
        Mat test = new Mat();
        Features2d.drawKeypoints(rgbaGrey, keyPoints, test);
        Utils.matToBitmap(test, inputImage);
        imageView3.setImageBitmap(inputImage);
    }

    public void initializeDependencies() throws IOException {
        detectorSift = FeatureDetector.create(FeatureDetector.SIFT);
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i("Logger", "OpenCV loaded successfully");
                    try {
                        initializeDependencies();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!OpenCVLoader.initDebug()) {
            Log.d("Logger", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        } else {
            Log.d("Logger", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        inputImage = BitmapFactory.decodeResource(getResources(), R.drawable.simple3);
        setContentView(R.layout.activity_main);
        imageView3 = this.findViewById(R.id.imageView3);
        sift();
        imageView3.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }







    /*private int w, h;
    private CameraBridgeViewBase mOpenCvCameraView;
    private Scalar RED = new Scalar(255, 0, 0);
    private Scalar GREEN = new Scalar(0, 255, 0);
    private FeatureDetector detector;
    private DescriptorExtractor descriptor;
    private DescriptorMatcher matcher;
    private Mat descriptors2, descriptors1, img1;
    private MatOfKeyPoint keypoints1, keypoints2;*/


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Logger", "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        mOpenCvCameraView = findViewById(R.id.cameraViewer);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }*/

    /*@Override
    public void onCameraViewStarted(int width, int height) {
        //Descriptor.turnGrey("Simple1");
        //Descriptor.saveKeyPointImg("Simple2");
        //Descriptor.detectKeyPoints("Simple3");
        //Descriptor.detectKeyPoints("Simple4");
        //Descriptor.turnGrey("Simple5");
        w = width;
        h = height;
    }*/

    /*@Override
    public void onCameraViewStopped() {
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return recognize(inputFrame.rgba());
    }*/

    /*@Override
    protected void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }*/

    /*@Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("Logger", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        } else {
            Log.d("Logger", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            //System.loadLibrary("nonfree");
        }
    }*/

    /*private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i("Logger", "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    try {
                        initializeOpenCVDependencies();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };*/

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }*/

    /*public void initializeOpenCVDependencies() throws IOException {
        mOpenCvCameraView.enableView();
        detector = FeatureDetector.create(FeatureDetector.ORB);
        descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);
        matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
        img1 = new Mat();*/

        /*String pathName = Environment.getExternalStorageDirectory()+"/"+imgName+".jpg";
        Mat srcImg = Imgcodecs.imread(pathName, Imgcodecs.IMREAD_UNCHANGED);
        img1 = srcImg.clone();
        Imgproc.cvtColor(srcImg, img1, Imgproc.COLOR_RGBA2GRAY);*/
       /* AssetManager assetManager = getAssets();
        InputStream istr = assetManager.open("simple3.jpg");
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        Utils.bitmapToMat(bitmap, img1);
        Mat dstImg = img1.clone();
        img1.release();
        Imgproc.cvtColor(dstImg, img1, Imgproc.COLOR_RGB2GRAY);
        //Imgcodecs.imwrite(Environment.getExternalStorageDirectory()+"/Neutronas/"+imgName+"_KPs.png", img1);

        img1.convertTo(img1, 0); //converting the image to match with the type of the cameras image
        descriptors1 = new Mat();
        keypoints1 = new MatOfKeyPoint();
        detector.detect(img1, keypoints1);
        descriptor.compute(img1, keypoints1, descriptors1);
    }*/

    /*public Mat recognize(Mat aInputFrame) {

        Imgproc.cvtColor(aInputFrame, aInputFrame, Imgproc.COLOR_RGB2GRAY);
        descriptors2 = new Mat();
        keypoints2 = new MatOfKeyPoint();
        detector.detect(aInputFrame, keypoints2);
        descriptor.compute(aInputFrame, keypoints2, descriptors2);

        // Matching
        MatOfDMatch matches = new MatOfDMatch();
        if (img1.type() == aInputFrame.type()) {
            matcher.match(descriptors1, descriptors2, matches);
        } else {
            return aInputFrame;
        }
        List<DMatch> matchesList = matches.toList();

        Double max_dist = 0.0;
        Double min_dist = 100.0;

        for (int i = 0; i < matchesList.size(); i++) {
            Double dist = (double) matchesList.get(i).distance;
            if (dist < min_dist)
                min_dist = dist;
            if (dist > max_dist)
                max_dist = dist;
        }

        LinkedList<DMatch> good_matches = new LinkedList<>();
        for (int i = 0; i < matchesList.size(); i++) {
            if (matchesList.get(i).distance <= (1.5 * min_dist))
                good_matches.addLast(matchesList.get(i));
        }

        MatOfDMatch goodMatches = new MatOfDMatch();
        goodMatches.fromList(good_matches);
        Mat outputImg = new Mat();
        MatOfByte drawnMatches = new MatOfByte();
        if (aInputFrame.empty() || aInputFrame.cols() < 1 || aInputFrame.rows() < 1) {
            return aInputFrame;
        }
        Features2d.drawMatches(img1, keypoints1, aInputFrame, keypoints2, goodMatches, outputImg, GREEN, RED, drawnMatches, Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS);
        Mat dstOutputImg = new Mat();
        mOpenCvCameraView.setUserRotation(90);
        Imgproc.resize(outputImg, dstOutputImg, aInputFrame.size());
        return dstOutputImg;
    }*/
}
