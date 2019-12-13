package com.example.neutronas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class News extends AppCompatActivity {

    Button backButton;
    ImageView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        backButton = findViewById(R.id.back_button_news);

        list = findViewById(R.id.list);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(News.this, MainActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intentLoadNewActivity = new Intent(News.this, MainActivity.class);
        startActivity(intentLoadNewActivity);
    }
}
