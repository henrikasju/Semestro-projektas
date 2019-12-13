package com.example.neutronas;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{

    List<Note> notes;
    ArrayList<Bitmap> photos;
    public OnDeleteClickListener dListener;
    public OnEditClickListener eListener;

    public interface OnDeleteClickListener{
        void onDeleteClick(int position);
    }

    public interface OnEditClickListener{
        void onEditClick(int position);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener)
    {
        dListener = listener;
    }

    public void setOnEditClickListener(OnEditClickListener listener)
    {
        eListener = listener;
    }


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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.note_name.setText(notes.get(position).getNoteName());
        holder.note_date.setText(notes.get(position).getNoteDate());
        holder.note_description.setText(notes.get(position).getNoteDescription());

//        Bitmap bitmap = BitmapFactory.decodeFile(notes.get(position).getNotePhotoPath());
//
//        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 110,100, true);

        holder.note_image.setImageBitmap(photos.get(position));
        holder.note_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dListener != null)
                {
                    dListener.onDeleteClick(notes.get(position).getId());
                }

            }
        });

        holder.note_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eListener != null)
                {
                    eListener.onEditClick(notes.get(position).getId());
                }
            }
        });

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
        public ImageButton note_delete;
        public ImageButton note_edit;

        public ViewHolder(View itemView) {
            super(itemView);
            note_name = itemView.findViewById(R.id.note_name);
            note_date = itemView.findViewById(R.id.note_date);
            note_description = itemView.findViewById(R.id.note_description);
            note_image = itemView.findViewById(R.id.note_image_preview);
            note_delete = itemView.findViewById(R.id.delete_button);
            note_edit = itemView.findViewById(R.id.edit_button);
        }
    }

}
