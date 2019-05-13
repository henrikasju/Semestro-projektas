package com.example.neutronas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class About extends AppCompatActivity {

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        backButton = (Button) findViewById(R.id.back_button_about);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(About.this, MainActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });

        Button startButton = findViewById(R.id.pattern_camera_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cvIntent = new Intent(About.this, PatternCamera.class);
                startActivity(cvIntent);
            }
        });
        Button scannerButton = (Button)findViewById(R.id.matcher_button);
        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cvIntent = new Intent(About.this, Detector.class);
                startActivity(cvIntent);
            }
        });
    }
}
