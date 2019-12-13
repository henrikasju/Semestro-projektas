package com.example.neutronas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatDialogFragment;

public class Note_delete_dialog extends AppCompatDialogFragment {

    public static Note_delete_dialog newInstance(String noteName, int noteId)
    {
        Note_delete_dialog frag = new Note_delete_dialog();
        Bundle args = new Bundle();
        args.putString("noteName", noteName);
        args.putInt("noteId", noteId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String noteName = getArguments().getString("noteName");
        final int noteId = getArguments().getInt("noteId");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Note Deletion").setMessage("Do you really want to delete \"" + noteName +"\" note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                         AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "note")
                                .allowMainThreadQueries()
                                .build();

                         db.noteDao().deleteByNoteId(noteId);

                        Intent intentLoadNewActivity = new Intent(getActivity(), Gallery.class);
                        startActivity(intentLoadNewActivity);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }
}
