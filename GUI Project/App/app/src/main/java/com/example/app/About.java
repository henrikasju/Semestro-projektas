package com.example.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class About extends AppCompatActivity {

    Button backButton;
    Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about2);

        backButton = (Button) findViewById(R.id.back_button_about);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(About.this, MainActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });

        testButton = (Button) findViewById(R.id.testButton);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToFillNote = new Intent(About.this, NoteFill.class);
                Bundle transfer = new Bundle();
                transfer.putString("filePath", "hehe");
                goToFillNote.putExtras(transfer);
                startActivity(goToFillNote);
                finish();
            }
        });
    }
}
