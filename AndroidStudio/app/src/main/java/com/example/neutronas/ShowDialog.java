package com.example.neutronas;

import android.support.v7.app.AppCompatDialogFragment;

public class ShowDialog extends AppCompatDialogFragment {

    public ShowDialog()
    {
        NoteDeleteDialog noteDeleteDialog = new NoteDeleteDialog();
        noteDeleteDialog.show(getFragmentManager(),"Note deletion");
    }

}
