package com.example.spiritualnote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Settings extends AppCompatActivity {

    ImageView backToHomeIconInSettingsToolbar;
    Switch changeAppThemeSwitch;
    Switch sendNotification;
    ThemeSharedPrefes themeSharedPrefes;
    MainSharedPrefes mainSharedPrefes;
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
        setContentView(R.layout.settings);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewSettings);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sendNotification =(Switch) findViewById(R.id.notfications);
        changeAppThemeSwitch = (Switch) findViewById(R.id.changeAppTheme);
        mainSharedPrefes = new MainSharedPrefes(this);
        if(themeSharedPrefes.loadNightMode() == true){changeAppThemeSwitch.setChecked(true);}

        sendNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mainSharedPrefes.sendNotification(true);
                }else {

                    mainSharedPrefes.sendNotification(false);

                }
            }
        });

        if(mainSharedPrefes.wantNotification() == true){sendNotification.setChecked(true);}
        changeAppThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    themeSharedPrefes.themeState(true);
                    restartApp();

                }else{

                    themeSharedPrefes.themeState(false);
                    restartApp();

                }

            }
        });

        backToHomeIconInSettingsToolbar = findViewById(R.id.backToHomeIconInSettingsToolbar);
        backToHomeIconInSettingsToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backToHome();

            }
        });



    }

    private void restartApp() {

        Intent intent = new Intent(getApplicationContext(), Splash.class);
        finishAffinity();
        startActivity(intent);

    }

    private void backToHome() {

        Intent intent = new Intent(Settings.this, Home.class);
        startActivity(intent);
        finish();

    }


    public void pleaseWaitCustomToast(){

        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        TextView text = (TextView) layout.findViewById(R.id.toast_text);
        text.setText("بـــرجــاء الانـتـظـار");

        Toast toast = new Toast(Settings.this);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }

}
