package com.example.spiritualnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    ThemeSharedPrefes themeSharedPrefes;

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
        setContentView(R.layout.splash);


        //Function To Move To Home Activity
        moveToHomeActivity();

    }

    public void moveToHomeActivity(){

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this, Home.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, 2000);


    }

}
