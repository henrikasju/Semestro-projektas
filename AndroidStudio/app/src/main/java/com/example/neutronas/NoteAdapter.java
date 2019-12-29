package com.example.neutronas;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

class NoteAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<Note> notes;
    ArrayList<Bitmap> photos;
    public OnDeleteClickListener dListener;
    public OnEditClickListener eListener;
    private boolean isLoaderVisible = false;
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

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
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.note_load, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, final int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == notes.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    public void addItems(List<Note> newNotes, ArrayList<Bitmap> newPhotos) {
        notes.addAll(newNotes);
        photos.addAll(newPhotos);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        isLoaderVisible = true;
        notes.add(new Note());
        notifyItemInserted(notes.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        isLoaderVisible = false;
        int position = notes.size() - 1;
        Note item = getItem(position);
        if (item != null) {
            notes.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        notes.clear();
        notifyDataSetChanged();
    }

    Note getItem(int position) {
        return notes.get(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends BaseViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            note_name = itemView.findViewById(R.id.note_name);
            note_date = itemView.findViewById(R.id.note_date);
            note_description = itemView.findViewById(R.id.note_description);
            note_image = itemView.findViewById(R.id.note_image_preview);
            note_delete = itemView.findViewById(R.id.delete_button);
            note_edit = itemView.findViewById(R.id.edit_button);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            final Note note = notes.get(position);
            final Bitmap photo = photos.get(position);

            note_name.setText(note.getNoteName());
            note_date.setText(note.getNoteDate());
            note_description.setText(note.getNoteDescription());
            note_image.setImageBitmap(photo);

            note_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dListener != null)
                    {
                        dListener.onDeleteClick(note.getId());
                    }

                }
            });

            note_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eListener != null)
                    {
                        eListener.onEditClick(note.getId());
                    }
                }
            });
        }
    }

    public class ProgressHolder extends BaseViewHolder {
        ProgressHolder(View itemView) {
            super(itemView);
            note_load = itemView.findViewById(R.id.progressBar);
        }

        @Override
        protected void clear() {

        }
    }
}
