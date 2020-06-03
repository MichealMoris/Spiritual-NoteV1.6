package com.example.spiritualnote;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavVersesDao {

    @Query("SELECT * FROM favourite_verses")
    List<FavVerses> getAllVerses();

    @Insert
    void insert(FavVerses favVerses);

    @Delete
    void delete(FavVerses favVerses);

    @Update
    void update(FavVerses favVerses);

}
