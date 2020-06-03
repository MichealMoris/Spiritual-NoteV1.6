package com.example.spiritualnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class Home extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("E", new Locale("ar"));
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", new Locale("ar"));
    final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private SharedPreferences sharedpreferences;
    private ImageView goToRecordedNotesIconInHomeToolbar;
    private ImageView goToSettingsIconInHomeToolbar;
    private Button saveChangesButton;
    private Button favVerses;
    private TextView dayInEmptyNewNote;
    private CheckBox morningCheckBox;
    private CheckBox eveningCheckBox;
    private CheckBox nightCheckBox;
    private CheckBox echuaristCheckBox;
    private CheckBox confessCheckBox;
    private CheckBox bibleCheckBox;
    private EditText writeNotesEdittext;
    private AdView mAdView;
    ImageView saintImage;
    ThemeSharedPrefes themeSharedPrefes;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private static final String PREF_PAUSE_TIME_KEY = "exit_time";

    long lastTime = System.currentTimeMillis();
    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler2 = new Handler();

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                // TODO: do what you need here
                // ............
                // ............
                addEdittextValue();
            }
        }
    };

    private static final Long MILLIS_IN_DAY = 86400000L;

    private static final int TRIGGER_HOUR = 0;
    private static final int TRIGGER_MIN = 0;
    private static final int TRIGGER_SEC = 0;


    private final Handler handler = new Handler();
    private SharedPreferences prefs;

    private final Calendar calendar = Calendar.getInstance();

    private final Runnable addItemRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(addItemRunnable, MILLIS_IN_DAY);
            addNotes();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // Add missing events since onPause.
        saintImage = findViewById(R.id.saintImage);
        morningCheckBox = findViewById(R.id.morningCheckBox);
        eveningCheckBox = findViewById(R.id.eveningCheckBox);
        nightCheckBox = findViewById(R.id.nightCheckbox);
        echuaristCheckBox = findViewById(R.id.echuraistCheckBox);
        confessCheckBox = findViewById(R.id.confessCheckBox);
        bibleCheckBox = findViewById(R.id.bibleCheckBox);
        writeNotesEdittext = findViewById(R.id.notesInHome);
        NotesDatabase db = Room.databaseBuilder(getApplicationContext(),
                NotesDatabase.class, "NotesDatabase").allowMainThreadQueries().build();
        final MainSharedPrefes mainSharedPrefes = new MainSharedPrefes(this);
        final long resumeTime = mainSharedPrefes.getCurrentTime();
        Log.e("resumeTime", String.valueOf(resumeTime));
        long pauseTime = prefs.getLong(PREF_PAUSE_TIME_KEY, resumeTime);

        // Set calendar to trigger time on the day the app was paused.
        calendar.setTimeInMillis(pauseTime);
        calendar.set(Calendar.HOUR_OF_DAY, TRIGGER_HOUR);
        calendar.set(Calendar.MINUTE, TRIGGER_MIN);
        calendar.set(Calendar.SECOND, TRIGGER_SEC);

        long time;
        while (true) {
            // If calendar time is during the time that app was on pause, add item.
            time = calendar.getTimeInMillis();
            if (time > resumeTime) {
                // Past current time, all items were added.
                mainSharedPrefes.setIsFillingRecyclerView(true);
                break;
            } else if (time >= pauseTime) {
                // This time happened when app was on pause, add item.
                if( db.notesDao().getAllNotes().size() == 0){
                    mainSharedPrefes.setLastTime(lastTime - 86400000);
                    addNotes();
                    morningCheckBox.setChecked(false);
                    eveningCheckBox.setChecked(false);
                    nightCheckBox.setChecked(false);
                    echuaristCheckBox.setChecked(false);
                    confessCheckBox.setChecked(false);
                    bibleCheckBox.setChecked(false);
                    writeNotesEdittext.setText("");
                    mainSharedPrefes.setMorningState(false);
                    mainSharedPrefes.setEveningState(false);
                    mainSharedPrefes.setNightState(false);
                    mainSharedPrefes.setEchauristState(false);
                    mainSharedPrefes.setConfessState(false);
                    mainSharedPrefes.setBibleState(false);
                    mainSharedPrefes.setWritedNotes("");
                    saintOfTheDay();
                    saintImage.setImageResource(mainSharedPrefes.getSaintImage());
                    //mainSharedPrefes.setLastTime(lastTime  + 86400000);

                }else{
                    lastTime = lastTime - 86400000;
                    mainSharedPrefes.setLastTime(lastTime);
                    addNotes();
                    morningCheckBox.setChecked(false);
                    eveningCheckBox.setChecked(false);
                    nightCheckBox.setChecked(false);
                    echuaristCheckBox.setChecked(false);
                    confessCheckBox.setChecked(false);
                    bibleCheckBox.setChecked(false);
                    writeNotesEdittext.setText("");
                    mainSharedPrefes.setMorningState(false);
                    mainSharedPrefes.setEveningState(false);
                    mainSharedPrefes.setNightState(false);
                    mainSharedPrefes.setEchauristState(false);
                    mainSharedPrefes.setConfessState(false);
                    mainSharedPrefes.setBibleState(false);
                    mainSharedPrefes.setWritedNotes("");
                    saintOfTheDay();
                    saintImage.setImageResource(mainSharedPrefes.getSaintImage());

                }
            }else if(resumeTime - time <= 3600000 && db.notesDao().getAllNotes().size() == 0){
                        mainSharedPrefes.setLastTime(lastTime - 86400000);
                        Log.e("timeDiff", String.valueOf(time - resumeTime));
                        addNotes();
                        morningCheckBox.setChecked(false);
                        eveningCheckBox.setChecked(false);
                        nightCheckBox.setChecked(false);
                        echuaristCheckBox.setChecked(false);
                        confessCheckBox.setChecked(false);
                        bibleCheckBox.setChecked(false);
                        writeNotesEdittext.setText("");
                        mainSharedPrefes.setMorningState(false);
                        mainSharedPrefes.setEveningState(false);
                        mainSharedPrefes.setNightState(false);
                        mainSharedPrefes.setEchauristState(false);
                        mainSharedPrefes.setConfessState(false);
                        mainSharedPrefes.setBibleState(false);
                        mainSharedPrefes.setWritedNotes("");
                        saintOfTheDay();
                        saintImage.setImageResource(mainSharedPrefes.getSaintImage());

            }

            // Set calendar time to same hour on next day.
            calendar.add(Calendar.DATE, 1);
        }

        // Set handler to add item on trigger time.
        handler.postDelayed(addItemRunnable, time - resumeTime);
        Log.e("timeDiff", String.valueOf(time - resumeTime));
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save pause time so items can be added on resume.
        MainSharedPrefes mainSharedPrefes = new MainSharedPrefes(this);
        prefs.edit().putLong(PREF_PAUSE_TIME_KEY, mainSharedPrefes.getCurrentTime()).apply();

        // Cancel handler callback to add item.
        handler.removeCallbacks(addItemRunnable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        themeSharedPrefes = new ThemeSharedPrefes(this);
        if(themeSharedPrefes.loadNightMode() == true){setTheme(R.style.AppThemeDark);}else{setTheme(R.style.AppTheme);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        writeNotesEdittext = findViewById(R.id.notesInHome);
        writeNotesEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                writeNotesEdittext.setHint("");
                writeNotesEdittext.setFocusableInTouchMode(true);
            }
        });

        MainSharedPrefes mainSharedPrefes = new MainSharedPrefes(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        saintImage = findViewById(R.id.saintImage);
        saintImage.setImageResource(mainSharedPrefes.getSaintImage());

        if(mainSharedPrefes.wantNotification()){
            setNotification();
        }

        checkboxesState();

        //Click On Menu Icon In Toolbar To Go Recorded Notes
        goToRecordedNotesIconInHomeToolbar = findViewById(R.id.goToRecordedNotesIconInHomeToolbar);
        goToRecordedNotesIconInHomeToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveToRecordedNotes();

            }
        });

        goToSettingsIconInHomeToolbar = findViewById(R.id.settings);
        goToSettingsIconInHomeToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveToSettings();

            }
        });

        //Get Current Day To Set It To Day In Empty New Note
        getCurrentDay();

        checkStates();

        favVerses = findViewById(R.id.favVerses);
        favVerses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveToFavVerses();

            }
        });


    }


    public void setNotification(){

        Intent intentAlarm = new Intent(Home.this, NotificationReciever.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(Home.this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);

    }
    public void moveToFavVerses(){

        Intent intent = new Intent(Home.this, FavouriteVerses.class);
        startActivity(intent);

    }
    public void addNotes(){

        String[] states = new String[6];
        MainSharedPrefes mainSharedPrefes = new MainSharedPrefes(this);
        if (mainSharedPrefes.getMorningState()){

            states[0] = "+";

        }else {

            states[0] = "-";

        }
        if(mainSharedPrefes.getEveningState()){

            states[1] = "+";

        }else {

            states[1] = "-";

        }
        if (mainSharedPrefes.getNightState()){

            states[2] = "+";

        }else {

            states[2] = "-";

        }
        if (mainSharedPrefes.getEchauristState()){

            states[3] = "+";

        }else {

            states[3] = "-";

        }
        if (mainSharedPrefes.getConfessState()){

            states[4] = "+";

        }else {

            states[4] = "-";

        }
        if (mainSharedPrefes.getBibleState()){

            states[5] = "+";

        }else {

            states[5] = "-";

        }
        Note addNewNote = new Note(sdf.format(mainSharedPrefes.getLastTime()),sdf2.format(mainSharedPrefes.getLastTime()),states[0],states[1],states[2],states[3],states[4],states[5],mainSharedPrefes.getWritedNotes());
        NotesDatabase db = Room.databaseBuilder(getApplicationContext(),
                NotesDatabase.class, "NotesDatabase").allowMainThreadQueries().build();
        db.notesDao().insert(addNewNote);



    }
    public void saintOfTheDay(){

        MainSharedPrefes mainSharedPrefes = new MainSharedPrefes(this);
        Random randomImage = new Random();
        int randomImageNumber = randomImage.nextInt(15 - 1) - 1;
        Integer[] saintsImages = new Integer[20];
        saintsImages[0] = R.drawable.saint_micheal;
        saintsImages[1] = R.drawable.saint_abo_sefen;
        saintsImages[2] = R.drawable.saint_abraam;
        saintsImages[3] = R.drawable.saint_antonious;
        saintsImages[4] = R.drawable.saint_demyana;
        saintsImages[5] = R.drawable.saint_faltaos;
        saintsImages[6] = R.drawable.saint_goerge;
        saintsImages[7] = R.drawable.saint_karas;
        saintsImages[8] = R.drawable.saint_marina;
        saintsImages[9] = R.drawable.saint_mary;
        saintsImages[10] = R.drawable.saint_mina;
        saintsImages[11] = R.drawable.saint_philomina;
        saintsImages[12] = R.drawable.saint_pola;
        saintsImages[13] = R.drawable.saint_wannas;
        saintsImages[14] = R.drawable.pop_shenoda;
        saintsImages[15] = R.drawable.pop_kerolose;
        saintsImages[16] = R.drawable.saint_beshoy;
        saintsImages[17] = R.drawable.saint_nofer;
        if(randomImageNumber == -1){

            mainSharedPrefes.setSaintImage(saintsImages[1]);

        }else{
            mainSharedPrefes.setSaintImage(saintsImages[randomImageNumber]);
        }

    }
    public void moveToRecordedNotes(){

        Intent intent = new Intent(Home.this, RecordedNotes.class);
        startActivity(intent);
    }
    public void moveToSettings(){

        Intent intent = new Intent(Home.this, Settings.class);
        startActivity(intent);

    }
    public void getCurrentDay(){

        SimpleDateFormat sdf = new SimpleDateFormat("E", new Locale("ar"));
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dayInEmptyNewNote = findViewById(R.id.dayInEmptyNewNote);
        dayInEmptyNewNote.setText(sdf.format(timestamp));

    }
    public void checkStates(){

        final MainSharedPrefes mainSharedPrefes = new MainSharedPrefes(getApplicationContext());
        morningCheckBox = findViewById(R.id.morningCheckBox);
        eveningCheckBox = findViewById(R.id.eveningCheckBox);
        nightCheckBox = findViewById(R.id.nightCheckbox);
        echuaristCheckBox = findViewById(R.id.echuraistCheckBox);
        confessCheckBox = findViewById(R.id.confessCheckBox);
        bibleCheckBox = findViewById(R.id.bibleCheckBox);
        writeNotesEdittext = findViewById(R.id.notesInHome);

        morningCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(morningCheckBox.isChecked()){
                    mainSharedPrefes.setMorningState(true);
                }else {

                    mainSharedPrefes.setMorningState(false);

                }

                saveChangesCustomToast();

            }
        });

        eveningCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(eveningCheckBox.isChecked()){
                    mainSharedPrefes.setEveningState(true);
                }else{

                    mainSharedPrefes.setEveningState(false);

                }

                saveChangesCustomToast();

            }
        });

        nightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(nightCheckBox.isChecked()){
                    mainSharedPrefes.setNightState(true);
                }else {

                    mainSharedPrefes.setNightState(false);

                }

                saveChangesCustomToast();

            }
        });

        echuaristCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(echuaristCheckBox.isChecked()){
                    mainSharedPrefes.setEchauristState(true);
                }else{

                    mainSharedPrefes.setEchauristState(false);

                }

                saveChangesCustomToast();

            }
        });

        confessCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(confessCheckBox.isChecked()){
                    mainSharedPrefes.setConfessState(true);
                }else{

                    mainSharedPrefes.setConfessState(false);

                }

                saveChangesCustomToast();

            }
        });

        bibleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(bibleCheckBox.isChecked()){
                    mainSharedPrefes.setBibleState(true);
                }else {

                    mainSharedPrefes.setBibleState(false);

                }

                saveChangesCustomToast();

            }
        });

        writeNotesEdittext.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged (CharSequence s,int start, int count,
                                             int after){
              }
              @Override
              public void onTextChanged ( final CharSequence s, int start, int before,
                                          int count){
                  //You need to remove this to run only once
                  handler2.removeCallbacks(input_finish_checker);

              }
              @Override
              public void afterTextChanged ( final Editable s){
                  //avoid triggering event when text is empty
                  if (s.length() > 0) {
                      last_text_edit = System.currentTimeMillis();
                      handler2.postDelayed(input_finish_checker, delay);
                  } else {

                  }
              }
          }

        );

    }
    public void addEdittextValue(){

        writeNotesEdittext = findViewById(R.id.notesInHome);
        MainSharedPrefes mainSharedPrefes = new MainSharedPrefes(this);
        mainSharedPrefes.setWritedNotes(writeNotesEdittext.getText().toString());
        writeNotesEdittext.setFocusable(false);
        saveChangesCustomToast();

    }
    public void saveChangesCustomToast(){

        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        TextView text = (TextView) layout.findViewById(R.id.toast_text);
        text.setText("تم حفظ التعديلات");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();


    }
    public void checkboxesState() {

        MainSharedPrefes mainSharedPrefes = new MainSharedPrefes(getApplicationContext());
        morningCheckBox = findViewById(R.id.morningCheckBox);
        eveningCheckBox = findViewById(R.id.eveningCheckBox);
        nightCheckBox = findViewById(R.id.nightCheckbox);
        echuaristCheckBox = findViewById(R.id.echuraistCheckBox);
        confessCheckBox = findViewById(R.id.confessCheckBox);
        bibleCheckBox = findViewById(R.id.bibleCheckBox);
        writeNotesEdittext = findViewById(R.id.notesInHome);

        if(mainSharedPrefes.getMorningState() == true){morningCheckBox.setChecked(true);}
        else{morningCheckBox.setChecked(false);}
        if(mainSharedPrefes.getEveningState() == true){eveningCheckBox.setChecked(true);}
        else{eveningCheckBox.setChecked(false);}
        if(mainSharedPrefes.getNightState() == true){nightCheckBox.setChecked(true);}
        else{nightCheckBox.setChecked(false);}
        if(mainSharedPrefes.getEchauristState() == true){echuaristCheckBox.setChecked(true);}
        else{echuaristCheckBox.setChecked(false);}
        if(mainSharedPrefes.getConfessState() == true){confessCheckBox.setChecked(true);}
        else{confessCheckBox.setChecked(false);}
        if(mainSharedPrefes.getBibleState() == true){bibleCheckBox.setChecked(true);}
        else{bibleCheckBox.setChecked(false);}
        if(mainSharedPrefes.getWritedNotes().isEmpty()){mainSharedPrefes.setWritedNotes("");}
        else{writeNotesEdittext.setText(mainSharedPrefes.getWritedNotes());}
        mainSharedPrefes.setCurrentTime(System.currentTimeMillis());
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.arrow_back)
                    .setTitle("تاكيد!")
                    .setMessage("هل تريد الخروج؟")
                    .setPositiveButton("نعم", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Stop the activity
                            finishAffinity();
                        }

                    })
                    .setNegativeButton("لا", null)
                    .show();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
