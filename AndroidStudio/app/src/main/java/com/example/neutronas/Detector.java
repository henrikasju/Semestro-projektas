package com.example.neutronas;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

public class Detector {
    static {
        System.loadLibrary("opencv_java");
        System.loadLibrary("nonfree");
    }

    private static Mat rgba = new Mat();
    private static Mat rgbaGrey = new Mat();
    private static Mat outImage = new Mat();
    private static FeatureDetector detectorSift = MainActivity.detectorSift;// = FeatureDetector.create(FeatureDetector.SIFT);

    public static Mat siftMat(Bitmap inputImage, ImageView imageView) {
        Utils.bitmapToMat(inputImage, rgba);
        MatOfKeyPoint keyPoints = new MatOfKeyPoint();
        Imgproc.cvtColor(rgba, rgbaGrey, Imgproc.COLOR_RGBA2GRAY);
        detectorSift.detect(rgbaGrey, keyPoints);
        Features2d.drawKeypoints(rgbaGrey, keyPoints, outImage);
        Utils.matToBitmap(outImage, inputImage);
        imageView.setImageBitmap(Bitmap.createScaledBitmap(inputImage, 120, 120, false));
        return outImage;
    }

    public static MatOfKeyPoint siftKeyPoints(Bitmap inputImage, ImageView imageView) {
        Utils.bitmapToMat(inputImage, rgba);
        MatOfKeyPoint keyPoints = new MatOfKeyPoint();
        Imgproc.cvtColor(rgba, rgbaGrey, Imgproc.COLOR_RGBA2GRAY);
        detectorSift.detect(rgbaGrey, keyPoints);
        Features2d.drawKeypoints(rgbaGrey, keyPoints, outImage);
        return keyPoints;
    }
}
