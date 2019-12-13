package com.example.neutronas;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NoteFill extends AppCompatActivity {

    Button backButton;
    ImageButton saveButton;
    ImageButton shareButton;
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
        shareButton = (ImageButton) findViewById(R.id.share_button);
        dateText = (EditText) findViewById(R.id.dateText);
        noteNameText = (EditText) findViewById(R.id.noteNameText);
        noteDescriptionText = (EditText) findViewById(R.id.noteDescriptionText);
        symbolView = findViewById(R.id.symbol_circle);

        Bundle transfer = getIntent().getExtras();

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "note")
                .allowMainThreadQueries()
                .build();

        if (transfer != null && getIntent().hasExtra("noteId"))
        {
            final int noteId = transfer.getInt("noteId");

            Note note = db.noteDao().getDatalById(noteId);

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentLoadNewActivity = new Intent(NoteFill.this, Gallery.class);
                    startActivity(intentLoadNewActivity);
                }
            });

            imagePath = note.getNotePhotoPath();

            String date = imagePath.substring(imagePath.indexOf("JPEG_") + 5, imagePath.indexOf("JPEG_") + 8 + 5);
            date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
            dateText.setText(date);
            dateText.setEnabled(false);

            noteNameText.setText(note.getNoteName());
            noteDescriptionText.setText(note.getNoteDescription());

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.noteDao().updateByNoteId(noteId, noteNameText.getText().toString(), noteDescriptionText.getText().toString());
                    startActivity(new Intent(NoteFill.this, Gallery.class));
                }
            });

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Drawable mDrawable = symbolView.getDrawable();
                    Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                    String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
                    Uri uri = Uri.parse(path);

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(intent, "Share Image"));
                }
            });

        } else {

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentLoadNewActivity = new Intent(NoteFill.this, Camera.class);
                    startActivity(intentLoadNewActivity);
                }
            });

            //Bundle transfer = getIntent().getExtras();
            if (transfer != null)
                imagePath = transfer.getString("filePath");

            if (imagePath != null) {
                System.out.println("Photo path : " + imagePath);
                //setPic(imagePath);
            }

            String date = imagePath.substring(imagePath.indexOf("JPEG_") + 5, imagePath.indexOf("JPEG_") + 8 + 5);
            date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);


            dateText.setText(date);
            dateText.setEnabled(false);


            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Note note = new Note(imagePath, dateText.getText().toString(), noteNameText.getText().toString(), noteDescriptionText.getText().toString());
                    db.noteDao().insertAll(note);
                    startActivity(new Intent(NoteFill.this, Gallery.class));
                }
            });

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Drawable mDrawable = symbolView.getDrawable();
                    Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                    String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
                    Uri uri = Uri.parse(path);

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(intent, "Share Image"));
                }
            });
        }
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
