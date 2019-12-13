package com.example.neutronas;

import android.support.v7.app.AppCompatDialogFragment;

public class showDialog extends AppCompatDialogFragment {

    public showDialog()
    {
        Note_delete_dialog note_delete_dialog = new Note_delete_dialog();
        note_delete_dialog.show(getFragmentManager(),"Note deletion");
    }

}
