package com.example.neutronas;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAllNotes();

    @Insert
    void insertAll(Note... notes);

    @Query("SELECT * FROM note WHERE id = :noteId")
    Note getDatalById(int noteId);

    @Query("DELETE FROM note WHERE id = :noteId")
    abstract void deleteByNoteId(int noteId);

    @Query("Update note SET name = :noteName, description = :noteDescription WHERE id = :noteId")
    void updateByNoteId(int noteId, String noteName, String noteDescription);



}
