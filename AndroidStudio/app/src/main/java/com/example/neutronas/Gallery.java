package com.example.neutronas;

import android.app.DialogFragment;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends AppCompatActivity{

    Button backButton;
    RecyclerView recyclerView;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        backButton = (Button) findViewById(R.id.back_button_gallery);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
//        ArrayList<Note> notes = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            Note note = new Note("path", "2019-05-11", "NoteName [" + i + "]", "Description");
//            notes.add(note);
//
//        }

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "note")
                .allowMainThreadQueries()
                .build();

        List<Note> notes = db.noteDao().getAllNotes();

        ArrayList<Bitmap> photos = new ArrayList<>();
        for (int i = 0; i < notes.size(); i++) {
            Bitmap bitmap = BitmapFactory.decodeFile(notes.get(i).getNotePhotoPath());

            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 500,500, true);
            photos.add(scaled);
        }


        adapter = new NoteAdapter(notes, photos);

        recyclerView.setAdapter(adapter);

        adapter.setOnDeleteClickListener(new NoteAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                Note clickedNote = db.noteDao().getDatalById(position);
                String noteName = clickedNote.getNoteName();
                Note_delete_dialog newFragment = Note_delete_dialog.newInstance(noteName,position);
                newFragment.show(getSupportFragmentManager(), "dialog");
            }
        });

        adapter.setOnEditClickListener(new NoteAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int position) {
                Intent goToFillNote = new Intent(Gallery.this, NoteFill.class);
                Bundle transfer = new Bundle();
                transfer.putInt("noteId", position);
                goToFillNote.putExtras(transfer);
                startActivity(goToFillNote);
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Gallery.this, MainActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }

    public void showPrompt()
    {
        Note_delete_dialog note_delete_dialog = new Note_delete_dialog();
        note_delete_dialog.show(getSupportFragmentManager(),"Tag");
    }

}
