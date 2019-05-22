package com.example.neutronas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class About extends AppCompatActivity {

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView textView = findViewById(R.id.textViewAbout);
        textView.setText("This application is designed to associate a symbol with some sort of note. Let us say that you will not remember what you placed inside a storage. Before placing anything, draw a distinct symbol and take a picture of it using the application. When you will decide that you want to check what did you place inside, just take a symbol using the applications scanning function and voila! All the notes you wrote about this symbol will be now shown! (well at least it should.. in theory..)");

        backButton = (Button) findViewById(R.id.back_button_about);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(About.this, MainActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }
}
