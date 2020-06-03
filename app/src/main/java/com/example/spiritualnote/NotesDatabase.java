package com.example.spiritualnote;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class, FavVerses.class}, version = 3, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();

    public abstract FavVersesDao versesDao();

}
