package com.example.neutronas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.resize;

public class KeyPointDetector extends AppCompatActivity {

    static {
        System.loadLibrary("opencv_java");
        System.loadLibrary("nonfree");
    }

    private ImageView keypoints;
    private ImageView picture;
    private Bitmap outputBitmap;
    private Bitmap inputImage;
    private Bitmap pattern;
    private Bitmap scaledTakenImage;
    private Bitmap scaledPatternImage;
    private MatOfKeyPoint patternKeyPoints = new MatOfKeyPoint();
    private MatOfKeyPoint pictureKeyPoints = new MatOfKeyPoint();
    private Mat outputMat = new Mat();
    private Mat patternMat = new Mat();
    private Mat pictureMat = new Mat();
    private Mat dstPatternMat = new Mat();
    private Mat dstPictureMat = new Mat();
    private MatOfKeyPoint patternDescriptors = new MatOfKeyPoint();
    private MatOfKeyPoint pictureDescriptors = new MatOfKeyPoint();
    private float nndrRatio = 0.7f;
    private Scalar matchestColor = new Scalar(0, 255, 0);
    private Scalar newKeypointColor = new Scalar(255, 0, 0);
    private List<MatOfDMatch> matches;
    private LinkedList<DMatch> goodMatchesList;
    private String PATH = "\\Users\\henrikasj\\Documents\\GitHub\\Semestro-projektas\\AndroidStudio\\app\\src\\main\\res\\Simple1.png";
    private int scaleSize = 240;


    private DescriptorMatcher matcher = MainActivity.matcher;
    private DescriptorExtractor extractor = MainActivity.extractor;
    private FeatureDetector detectorSift = MainActivity.detectorSift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_keypoints);
        File file = new File(OpenCVCamera.PATH);
        if(file.exists()) {
            //inputImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            Mat pictureMat = Highgui.imread(file.getAbsolutePath(), Highgui.CV_LOAD_IMAGE_COLOR);
            //scaledTakenImage = Bitmap.createScaledBitmap(inputImage, scaleSize, scaleSize, false);
            System.out.println("--------------OK---------------");
            keypoints = this.findViewById(R.id.taken_picture);

            Size sz = new Size(scaleSize,scaleSize);
            resize(pictureMat, dstPictureMat, sz, 0, 0, Imgproc.INTER_CUBIC);
            detectorSift.detect(dstPictureMat, pictureKeyPoints);
            //pictureMat = Detector.siftColMat(inputImage, keypoints);
            //pictureKeyPoints = Detector.siftColKeyPoints(inputImage, keypoints);
        }
        if(file.exists()) {
            pattern = BitmapFactory.decodeResource(getResources(), R.drawable.simple3);
            System.out.println(Environment.getExternalStorageDirectory()+"/simple3.jpg");
            Mat patternMat = Highgui.imread(Environment.getExternalStorageDirectory()+"/simple3.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
            //scaledPatternImage = Bitmap.createScaledBitmap(pattern, 120, 120, false);
            picture = this.findViewById(R.id.display_keypoints);

            Size sz = new Size(scaleSize,scaleSize);
            resize(patternMat, dstPatternMat, sz, 0, 0, Imgproc.INTER_CUBIC);
            detectorSift.detect(dstPatternMat, patternKeyPoints);
            Detector.siftMat(pattern, keypoints);
            //patternMat = Detector.siftMat(pattern, picture);
            //patternKeyPoints = Detector.siftKeyPoints(pattern, picture);
        }
        //patternMat.convertTo(patternMat, 0);
        //pictureMat.convertTo(pictureMat, 0);

        extractor.compute(dstPatternMat, patternKeyPoints, patternDescriptors);
        extractor.compute(dstPictureMat, pictureKeyPoints, pictureDescriptors);

        matches = new LinkedList<MatOfDMatch>();
        goodMatchesList = new LinkedList<DMatch>();

        System.out.println("Matching pattern and picture images..");
        //matcher.knnMatch(patternDescriptors, pictureDescriptors, matches, 2);

        Scalar RED = new Scalar(255, 0, 0);
        Scalar GREEN = new Scalar(0, 255, 0);
        MatOfDMatch DMatches = new MatOfDMatch();
        MatOfByte drawnMatches = new MatOfByte();
        matcher.match(patternDescriptors, pictureDescriptors, DMatches);

        //Features2d.drawMatchesKnn(patternMat, patternKeyPoints, pictureMat, pictureKeyPoints, matches, outputMat);
        Features2d.drawMatches(dstPatternMat, patternKeyPoints, dstPictureMat, pictureKeyPoints, DMatches, outputMat, GREEN, RED, drawnMatches, Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS);
        outputBitmap = Bitmap.createBitmap(outputMat.cols(), outputMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(outputMat, outputBitmap);
        picture.setImageBitmap(Bitmap.createScaledBitmap(outputBitmap, scaleSize, scaleSize, false));


    }
}
