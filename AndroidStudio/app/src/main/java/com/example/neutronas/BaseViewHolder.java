package com.example.neutronas;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    private int mCurrentPosition;
    public TextView note_name;
    public TextView note_date;
    public TextView note_description;
    public ImageView note_image;
    public ImageButton note_delete;
    public ImageButton note_edit;
    public ProgressBar note_load;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int position) {
        mCurrentPosition = position;
        clear();
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}
