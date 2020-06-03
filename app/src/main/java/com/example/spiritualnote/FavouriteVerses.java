package com.example.spiritualnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

public class FavouriteVerses extends AppCompatActivity{

    ThemeSharedPrefes themeSharedPrefes;
    LinearLayout addNewFavVerseBar;
    LinearLayout editVerseBar;
    ImageView addNewFavVerseImage;
    ImageView backToHomeIconInFavVersesToolbar;
    RecyclerView notesRecyclerView;
    VersesRecyclerViewAdapter versesRecyclerViewAdapter;
    RecyclerView.LayoutManager layoutManager;
    EditText favVersesEdittext;
    EditText editVersesEdittext;
    Button addNewFavVerseButton;
    Button editVerseButton;
    TextView noVersesText;
    List<FavVerses> mVerses;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        themeSharedPrefes = new ThemeSharedPrefes(this);
        if(themeSharedPrefes.loadNightMode() == true){

            setTheme(R.style.AppThemeDark);

        }else {

            setTheme(R.style.AppTheme);

        }
        if(themeSharedPrefes.loadNightMode() == true){setTheme(R.style.AppThemeDark);}else{setTheme(R.style.AppTheme);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_verses);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewFavVerse);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        NotesDatabase db = Room.databaseBuilder(getApplicationContext(),
                NotesDatabase.class, "NotesDatabase").allowMainThreadQueries().build();
        mVerses = db.versesDao().getAllVerses();
        fillingRecyclerView();

        favVersesEdittext = findViewById(R.id.favVerseEdittext);
        addNewFavVerseBar = findViewById(R.id.addNewFavVerseBar);
        addNewFavVerseButton = findViewById(R.id.addVerseButton);
        addNewFavVerseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addVerses(favVersesEdittext.getText().toString());
                favVersesEdittext.setText("");
                addNewFavVerseBar.setVisibility(View.GONE);
                fillingRecyclerView();
            }
        });

        addNewFavVerseImage = findViewById(R.id.addNewVerseImage);
        addNewFavVerseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddNewFavVerseBar();

            }
        });


        backToHomeIconInFavVersesToolbar = findViewById(R.id.backToHomeIconInFavVersesToolbar);
        backToHomeIconInFavVersesToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backToHome();

            }
        });



    }


    public void addVerses(String verse){

        NotesDatabase db = Room.databaseBuilder(getApplicationContext(),
                NotesDatabase.class, "NotesDatabase").allowMainThreadQueries().build();
        db.versesDao().insert(new FavVerses(verse));

    }

    public void fillingRecyclerView(){

        noVersesText = findViewById(R.id.noVersesText);
        NotesDatabase db = Room.databaseBuilder(getApplicationContext(),
                NotesDatabase.class, "NotesDatabase").allowMainThreadQueries().build();
        List<FavVerses> verses = db.versesDao().getAllVerses();
        if(verses.size() == 0){

            noVersesText.setVisibility(View.VISIBLE);

        }else {

            layoutManager = new LinearLayoutManager(this);
            versesRecyclerViewAdapter = new VersesRecyclerViewAdapter(verses);
            notesRecyclerView = findViewById(R.id.versesRecyclerView);
            notesRecyclerView.setHasFixedSize(true);
            notesRecyclerView.setLayoutManager(layoutManager);
            notesRecyclerView.setAdapter(versesRecyclerViewAdapter);
            noVersesText.setVisibility(View.GONE);

        }

    }

    public void showAddNewFavVerseBar(){

        addNewFavVerseBar = findViewById(R.id.addNewFavVerseBar);
        if (addNewFavVerseBar.getVisibility() == View.VISIBLE){

            addNewFavVerseBar.setVisibility(View.GONE);

        }else {

            addNewFavVerseBar.setVisibility(View.VISIBLE);

        }

    }
    public void showEditVerseBar(){

        editVerseBar = findViewById(R.id.editVerseBar);
        editVerseBar.setVisibility(View.VISIBLE);

    }
    public void hideEditVerseBar(){

        editVerseBar = findViewById(R.id.editVerseBar);
        editVerseBar.setVisibility(View.GONE);
        editVersesEdittext = findViewById(R.id.editVerseEdittext);
        editVersesEdittext.setText("");
        fillingRecyclerView();

    }



    public void backToHome(){

        Intent intent  = new Intent(FavouriteVerses.this, Home.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {

        final NotesDatabase db = Room.databaseBuilder(getApplicationContext(),
                NotesDatabase.class, "NotesDatabase").allowMainThreadQueries().build();
        final List<FavVerses> verses = db.versesDao().getAllVerses();
        versesRecyclerViewAdapter = new VersesRecyclerViewAdapter(verses);

        switch (item.getItemId()){

            case 123:
                db.versesDao().delete(verses.get(item.getGroupId()));
                versesRecyclerViewAdapter.deleteVerse(item.getGroupId());
                restart();
                return true;
            case 124:
                showEditVerseBar();
                editVersesEdittext = findViewById(R.id.editVerseEdittext);
                editVerseButton = findViewById(R.id.editVerseButton);
                editVersesEdittext.setText(verses.get(item.getGroupId()).getFavVerse());
                editVerseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        verses.get(item.getGroupId()).setFavVerse(editVersesEdittext.getText().toString());
                        db.versesDao().update(verses.get(item.getGroupId()));
                        hideEditVerseBar();

                    }
                });
                return true;

            default:
                return super.onContextItemSelected(item);

        }
    }

    public void restart(){

        Intent intent = new Intent(this, FavouriteVerses.class);
        startActivity(intent);
        finish();

    }

}
