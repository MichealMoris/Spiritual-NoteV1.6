package com.example.spiritualnote;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeSharedPrefes {

    SharedPreferences myPrefes;

    public ThemeSharedPrefes(Context context) {

        myPrefes = context.getSharedPreferences("filename", Context.MODE_PRIVATE);

    }

    public void themeState(Boolean state){

        SharedPreferences.Editor editor = myPrefes.edit();
        editor.putBoolean("nightMode", state);
        editor.commit();

    }

    public Boolean loadNightMode(){

        Boolean state = myPrefes.getBoolean("nightMode", false);
        return state;

    }

}
