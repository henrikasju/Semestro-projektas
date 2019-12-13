package com.example.neutronas;

import android.support.v7.app.AppCompatDialogFragment;

public class ShowDialog extends AppCompatDialogFragment {

    public ShowDialog()
    {
        NoteDeleteDialog note_delete_dialog = new NoteDeleteDialog();
        note_delete_dialog.show(getFragmentManager(),"Note deletion");
    }

}
