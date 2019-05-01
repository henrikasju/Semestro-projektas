package com.example.neutronas;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton scanButton;
    ImageButton cameraButton;
    ImageButton galleryButton;
    ImageButton newsButton;
    Button aboutButton;

    public static FeatureDetector detectorSift;
    public static DescriptorMatcher matcher;
    public static DescriptorExtractor extractor;

    public void initializeDependencies() throws IOException {
        detectorSift = FeatureDetector.create(FeatureDetector.SIFT);
        matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
        extractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        System.loadLibrary("opencv_java");
//        System.loadLibrary("nonfree");
//
//        if (!OpenCVLoader.initDebug()) {
//            Log.d("Logger", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
//        } else {
//            Log.d("Logger", "OpenCV library found inside package. Using it!");
//            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
//        }
//        Dexter.withActivity(this)
//                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if(!report.areAllPermissionsGranted()){
//                            Toast.makeText(MainActivity.this, "You need to grant all permission to use this app features", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//
//                    }
//                })
//                .check();



        scanButton = (ImageButton) findViewById(R.id.scan_view);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, Scan.class);
                startActivity(intentLoadNewActivity);
            }
        });

        cameraButton = (ImageButton) findViewById(R.id.cameraView);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, Camera.class);
                startActivity(intentLoadNewActivity);
            }
        });

        galleryButton = (ImageButton) findViewById(R.id.galleryView);

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, Gallery.class);
                startActivity(intentLoadNewActivity);
            }
        });

        newsButton = (ImageButton) findViewById(R.id.newsView);

        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, News.class);
                startActivity(intentLoadNewActivity);
            }
        });

        aboutButton = (Button) findViewById(R.id.aboutButton);

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, About.class);
                startActivity(intentLoadNewActivity);
            }
        });
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






}
