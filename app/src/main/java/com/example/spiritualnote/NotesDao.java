package com.example.spiritualnote;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY DATE, DAY")
    List<Note> getAllNotes();

    @Insert
    void insert(Note note);

    @Query("DELETE FROM notes WHERE id NOT IN (SELECT MIN(id) FROM NOTES GROUP BY Day, Date)")
    void deleteDuplicates();

}
