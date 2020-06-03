package com.example.spiritualnote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class RecordedNotes extends AppCompatActivity {

    ImageView backToHomeIconInRecordedNotesToolbar;
    ThemeSharedPrefes themeSharedPrefes;
    RecyclerView notesRecyclerView;
    NotesRecyclerViewAdapter notesRecyclerViewAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView noNotesText;
    EditText searchForNote;
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
        setContentView(R.layout.recorded_notes);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewRecordedNotes);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        MainSharedPrefes mainSharedPrefes = new MainSharedPrefes(this);
        if(mainSharedPrefes.isFillingRecyclerView()){

            fillingRecyclerView();

        }


        //Click On Back Icon In Toolbar To Go Home
        backToHomeIconInRecordedNotesToolbar = findViewById(R.id.backToHomeIconInRecordedNotesToolbar);
        backToHomeIconInRecordedNotesToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backToHome();

            }
        });

    }

    public void fillingRecyclerView(){

        noNotesText = findViewById(R.id.noNotesText);
        NotesDatabase db = Room.databaseBuilder(getApplicationContext(),
                NotesDatabase.class, "NotesDatabase").allowMainThreadQueries().build();
        final List<Note> note = db.notesDao().getAllNotes();
        if(note.size() == 0){

            noNotesText.setVisibility(View.VISIBLE);

        }else {

            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
            notesRecyclerViewAdapter = new NotesRecyclerViewAdapter(note);
            notesRecyclerView = findViewById(R.id.notesRecyclerView);
            notesRecyclerView.setHasFixedSize(true);
            notesRecyclerView.setLayoutManager(layoutManager);
            notesRecyclerView.setAdapter(notesRecyclerViewAdapter);
            db.notesDao().deleteDuplicates();
            noNotesText.setVisibility(View.GONE);
            searchForNote = findViewById(R.id.searchForNote);
            searchForNote.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {

                    ArrayList<Note> filteredNoteList = new ArrayList<>();

                    for (Note note1 : note){

                        if (note1.getDay().contains(s.toString())){

                            filteredNoteList.add(note1);

                        }

                    }

                    notesRecyclerViewAdapter.filterList(filteredNoteList);

                }
            });

        }

    }

    public void backToHome(){

        Intent intent = new Intent(RecordedNotes.this, Home.class);
        startActivity(intent);
        finish();

    }

    public void finishThis(){

        RecordedNotes.this.finish();

    }


}

