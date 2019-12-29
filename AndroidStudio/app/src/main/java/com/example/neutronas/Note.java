package com.example.neutronas;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "photo_path")
    private String notePhotoPath;

    @ColumnInfo(name = "date")
    private String noteDate;

    @ColumnInfo(name = "name")
    private String noteName;

    @ColumnInfo(name = "description")
    private String noteDescription;

    public void setId(int id) {
        this.id = id;
    }

    public void setNotePhotoPath(String notePhotoPath) {
        this.notePhotoPath = notePhotoPath;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Note(String notePhotoPath, String noteDate, String noteName, String noteDescription) {
        this.notePhotoPath = notePhotoPath;
        this.noteDate = noteDate;
        this.noteName = noteName;
        this.noteDescription = noteDescription;
    }

    public Note() {

    }

    public int getId() {
        return id;
    }

    public String getNotePhotoPath() {
        return notePhotoPath;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteDescription() {
        return noteDescription;
    }


}
