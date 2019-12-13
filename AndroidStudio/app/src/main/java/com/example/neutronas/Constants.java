package com.example.neutronas;

import android.os.Environment;

public class Constants {


    private Constants(){
        //cannot be instantiated
    }

    public static final String SCAN_IMAGE_LOCATION = Environment.getExternalStorageDirectory() + "/Android/data/com.example.neutronas/files/Pictures";
    public static final int TAKE_GALLERY_PICTURE = 1;
    public static final int TAKE_PATTERN_PICTURE = 2;
}
