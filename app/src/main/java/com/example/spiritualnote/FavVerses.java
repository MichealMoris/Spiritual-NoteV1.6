package com.example.spiritualnote;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_verses")
public class FavVerses {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "fav_verse")
    String favVerse;

    public FavVerses(String favVerse) {
        this.favVerse = favVerse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFavVerse() {
        return favVerse;
    }

    public void setFavVerse(String favVerse) {
        this.favVerse = favVerse;
    }
}
