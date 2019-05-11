package com.example.neutronas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<Note> notes;
    ArrayList<Bitmap> photos;

    public NoteAdapter(List<Note> notes, ArrayList<Bitmap> photos) {
        this.notes = notes;
        this.photos = photos;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false);

        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.note_name.setText(notes.get(position).getNoteName());
        holder.note_date.setText(notes.get(position).getNoteDate());
        holder.note_description.setText(notes.get(position).getNoteDescription());

//        Bitmap bitmap = BitmapFactory.decodeFile(notes.get(position).getNotePhotoPath());
//
//        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 110,100, true);

        holder.note_image.setImageBitmap(photos.get(position));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView note_name;
        public TextView note_date;
        public TextView note_description;
        public ImageView note_image;
        public ViewHolder(View itemView) {
            super(itemView);
            note_name = itemView.findViewById(R.id.note_name);
            note_date = itemView.findViewById(R.id.note_date);
            note_description = itemView.findViewById(R.id.note_description);
            note_image = itemView.findViewById(R.id.note_image_preview);
        }
    }

}
