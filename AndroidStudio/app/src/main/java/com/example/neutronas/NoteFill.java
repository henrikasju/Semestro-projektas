package com.example.neutronas;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mikhaellopez.circularimageview.CircularImageView;

public class NoteFill extends AppCompatActivity {

    Button backButton;
    ImageButton saveButton;
    //ImageView symbolView;
    String imagePath = null;
    CircularImageView symbolView;

    EditText dateText;
    EditText noteNameText;
    EditText noteDescriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_fill);

        backButton = (Button) findViewById(R.id.back_button_note);
        saveButton = (ImageButton) findViewById(R.id.save_button);
        dateText = (EditText) findViewById(R.id.dateText);
        noteNameText = (EditText) findViewById(R.id.noteNameText);
        noteDescriptionText = (EditText) findViewById(R.id.noteDescriptionText);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(NoteFill.this, Camera.class);
                startActivity(intentLoadNewActivity);
            }
        });

        Bundle transfer = getIntent().getExtras();
        if (transfer != null)
            imagePath = transfer.getString("filePath");

        symbolView = findViewById(R.id.symbol_circle);
        if (imagePath != null) {
            System.out.println("Photo path : " + imagePath);
            //setPic(imagePath);
        }

        String date = imagePath.substring(imagePath.indexOf("JPEG_") + 5,imagePath.indexOf("JPEG_") + 8 + 5);
        date = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8);


        dateText.setText(date);
        dateText.setEnabled(false);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2019-05-11 Save to database
                AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "note")
                        .allowMainThreadQueries()
                        .build();

                Note note = new Note(imagePath, dateText.getText().toString(), noteNameText.getText().toString(), noteDescriptionText.getText().toString());
                db.noteDao().insertAll(note);
                startActivity(new Intent(NoteFill.this, Gallery.class));
            }
        });
    }

    public void onWindowFocusChanged(boolean hasFocus)
    {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            setPic(imagePath);
        }
    }

    private void setPic(String filePath) {
        // Get the dimensions of the View
        int targetW = symbolView.getWidth();
        int targetH = symbolView.getHeight();

        //    Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        symbolView.setImageBitmap(bitmap);
    }


}
