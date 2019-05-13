package com.example.neutronas;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    public static String NAME;

    private Utilities(){

    }

    public static String generateFilename(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return NAME = "PATTERN-" + sdf.format(new Date()) + ".jpeg";
    }
}
