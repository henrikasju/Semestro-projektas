package com.example.neutronas;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class Camera extends AppCompatActivity {

    Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        backButton = (Button) findViewById(R.id.back_button_camera);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Camera.this, MainActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
        dispatchTakePictureIntent();
//        final Button captureButton  = findViewById(R.id.camera_button);
//        captureButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dispatchTakePictureIntent();
//            }
//        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try{
                photoFile = createImageFile();
                if (photoFile != null)
                {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }catch (Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Button captureButton = (Button) findViewById(R.id.camera_button);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            System.out.println("req = " + requestCode + ", res = " + requestCode);
            captureButton.setText("Picture is captured");

            Intent goToFillNote = new Intent(Camera.this, NoteFill.class);
            Bundle transfer = new Bundle();
            transfer.putString("filePath", currentPhotoPath);
            goToFillNote.putExtras(transfer);
            startActivity(goToFillNote);
            finish();
        }else{
            File file = new File(currentPhotoPath);
            long length = file.length();
            if (length == 0) {
                System.out.println("No picture was taken");
                if (file.delete())
                {
                    System.out.println("Deleted empty picture!");
                    captureButton.setText("Picture was not captured");
                }
            }

        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    public void onBackPressed() {
        Intent intentLoadNewActivity = new Intent(Camera.this, MainActivity.class);
        startActivity(intentLoadNewActivity);
    }
}
