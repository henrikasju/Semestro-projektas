package com.example.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton scanButton;
    ImageButton cameraButton;
    ImageButton galleryButton;
    ImageButton newsButton;
    Button aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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





}
