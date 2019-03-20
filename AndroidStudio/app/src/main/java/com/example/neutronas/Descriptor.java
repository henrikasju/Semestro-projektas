package com.example.neutronas;

import android.os.Environment;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Descriptor {

    private static FeatureDetector detector = FeatureDetector.create(FeatureDetector.SIFT);

    public static void detectKeyPoints(String imgName)
    {
        Mat dstImg = turnGrey(imgName);

        MatOfKeyPoint keyPoints = new MatOfKeyPoint();
        detector.detect(dstImg, keyPoints);
        Mat dstImg2 = new Mat();
        Features2d.drawKeypoints(dstImg, keyPoints, dstImg2);
        Imgcodecs.imwrite(Environment.getExternalStorageDirectory()+"/Neutronas/"+imgName+"_KPs.png", dstImg2);
    }

    public static Mat turnGrey(String imgName)
    {
        String pathName = Environment.getExternalStorageDirectory()+"/"+imgName+".png";
        Mat srcImg = Imgcodecs.imread(pathName, Imgcodecs.IMREAD_UNCHANGED);
        Mat dstImg = srcImg.clone();
        Imgproc.cvtColor(srcImg, dstImg, Imgproc.COLOR_RGBA2GRAY);
        Imgcodecs.imwrite(Environment.getExternalStorageDirectory()+"/Neutronas/"+imgName+"_Grey.png", dstImg);
        return dstImg;
    }
}
