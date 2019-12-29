package com.example.neutronas;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.example.neutronas.PaginationScrollListener.PAGE_START;

public class Gallery extends AppCompatActivity{

    Button backButton;
    RecyclerView recyclerView;
    NoteAdapter adapter;

    private AppDatabase db;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        backButton = (Button) findViewById(R.id.back_button_gallery);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "note").allowMainThreadQueries().build();

        final List<Note> notes = new ArrayList<>();
        ArrayList<Bitmap> photos = new ArrayList<>();
        /*for (int i = 0; i < notes.size(); i++) {
            Bitmap bitmap = BitmapFactory.decodeFile(notes.get(i).getNotePhotoPath());

            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 200,200, true);
            photos.add(scaled);
        }*/


        adapter = new NoteAdapter(notes, photos);

        recyclerView.setAdapter(adapter);
        doApiCall(db);

        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                doApiCall(db);
            }
            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
            @Override
            public boolean isLoading() {
                return isLoading;
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
        NoteDeleteDialog note_delete_dialog = new NoteDeleteDialog();
        note_delete_dialog.show(getSupportFragmentManager(),"Tag");
    }

    @Override
    public void onBackPressed() {
        Intent intentLoadNewActivity = new Intent(Gallery.this, MainActivity.class);
        startActivity(intentLoadNewActivity);
    }

    private void doApiCall(final AppDatabase dataBase) {
        final List<Note> notes = dataBase.noteDao().getAllNotes();
        final List<Note> newNotes = new ArrayList<>();
        final ArrayList<Bitmap> photos = new ArrayList<>();
        final ArrayList<Bitmap> newPhotos = new ArrayList<>();
        totalPage = (int)Math.round(notes.size() / 5 + 0.5);
        for (int i = 0; i < newNotes.size(); i++) {
            Bitmap bitmap = BitmapFactory.decodeFile(newNotes.get(i).getNotePhotoPath());

            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 200,200, true);
            photos.add(scaled);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    if (itemCount >= notes.size()) {
                        break;
                    }
                    Note note = notes.get(itemCount);
                    Bitmap bitmap = BitmapFactory.decodeFile(notes.get(itemCount).getNotePhotoPath());
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 200,200, true);

                    adapter.setOnDeleteClickListener(new NoteAdapter.OnDeleteClickListener() {
                        @Override
                        public void onDeleteClick(int position) {
                            Note clickedNote = db.noteDao().getDataById(position);
                            String noteName = clickedNote.getNoteName();
                            NoteDeleteDialog newFragment = NoteDeleteDialog.newInstance(noteName,position);
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
                    newNotes.add(note);
                    newPhotos.add(scaled);
                    itemCount++;
                    if (itemCount >= notes.size()) {
                        break;
                    }
                }
                if (currentPage != PAGE_START) adapter.removeLoading();
                adapter.addItems(newNotes, newPhotos);
                // check weather is last page or not
                if (currentPage < totalPage) {
                    adapter.addLoading();
                } else {
                    isLastPage = true;
                }
                isLoading = false;
            }
        }, 1000);
    }
}
