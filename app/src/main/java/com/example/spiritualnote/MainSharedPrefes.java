package com.example.spiritualnote;

import android.content.Context;
import android.content.SharedPreferences;

public class MainSharedPrefes {

    SharedPreferences mainSharedPrefes;
    SharedPreferences.Editor editor;

    public MainSharedPrefes(Context context) {

        mainSharedPrefes = context.getSharedPreferences("mainPrefes", Context.MODE_PRIVATE);

    }

    public void setMorningState(Boolean morningState){

        editor = mainSharedPrefes.edit();
        editor.putBoolean("morningState", morningState);
        editor.commit();

    }

    public Boolean getMorningState(){

        Boolean morningState = mainSharedPrefes.getBoolean("morningState", false);
        return  morningState;

    }

    public void setEveningState(Boolean eveningState){

        editor = mainSharedPrefes.edit();
        editor.putBoolean("eveningState", eveningState);
        editor.commit();

    }

    public Boolean getEveningState(){

        Boolean eveningState = mainSharedPrefes.getBoolean("eveningState", false);
        return  eveningState;

    }


    public void setNightState(Boolean nightState){

        editor = mainSharedPrefes.edit();
        editor.putBoolean("nightState", nightState);
        editor.commit();

    }

    public Boolean getNightState(){

        Boolean nightState = mainSharedPrefes.getBoolean("nightState", false);
        return  nightState;

    }


    public void setEchauristState(Boolean echauristState){

        editor = mainSharedPrefes.edit();
        editor.putBoolean("echauristState", echauristState);
        editor.commit();

    }

    public Boolean getEchauristState(){

        Boolean echauristState = mainSharedPrefes.getBoolean("echauristState", false);
        return  echauristState;

    }


    public void setConfessState(Boolean confessState){

        editor = mainSharedPrefes.edit();
        editor.putBoolean("confessState", confessState);
        editor.commit();

    }

    public Boolean getConfessState(){

        Boolean confessState = mainSharedPrefes.getBoolean("confessState", false);
        return  confessState;

    }


    public void setBibleState(Boolean bibleState){

        editor = mainSharedPrefes.edit();
        editor.putBoolean("bibleState", bibleState);
        editor.commit();

    }

    public Boolean getBibleState(){

        Boolean bibleState = mainSharedPrefes.getBoolean("bibleState", false);
        return  bibleState;

    }


    public void setWritedNotes(String writedNotes){

        editor = mainSharedPrefes.edit();
        editor.putString("writedNotes", writedNotes);
        editor.commit();

    }

    public String getWritedNotes(){

        String writedNotes = mainSharedPrefes.getString("writedNotes", "");
        return  writedNotes;

    }

    public void setCurrentTime(long currentTime){

        editor = mainSharedPrefes.edit();
        editor.putLong("currentTime", currentTime);
        editor.commit();

    }

    public long getCurrentTime(){

        long currentTime = mainSharedPrefes.getLong("currentTime", 0);
        return currentTime;
    }

    public void setIsFillingRecyclerView(Boolean isFillingRecyclerView){

        editor = mainSharedPrefes.edit();
        editor.putBoolean("isFillingRecyclerView", isFillingRecyclerView);
        editor.commit();

    }

    public Boolean isFillingRecyclerView(){

        boolean isNoteAdded =  mainSharedPrefes.getBoolean("isFillingRecyclerView", false);
        return isNoteAdded;

    }

    public void setIsNoteAdd(Boolean isNoteAdded){

        editor = mainSharedPrefes.edit();
        editor.putBoolean("isNoteAdd", isNoteAdded);
        editor.commit();

    }

    public Boolean isNoteAdd(){

        boolean isNoteAdded =  mainSharedPrefes.getBoolean("isNoteAdd", false);
        return isNoteAdded;

    }

    public void setSaintImage(Integer saintImage){

        editor = mainSharedPrefes.edit();
        editor.putInt("saintImage", saintImage);
        editor.commit();

    }

    public Integer getSaintImage(){

        Integer saintImage = mainSharedPrefes.getInt("saintImage", R.drawable.saint_micheal);
        return saintImage;

    }


    public void sendNotification(Boolean state){

        editor = mainSharedPrefes.edit();
        editor.putBoolean("sendNotification", state);
        editor.commit();

    }


    public boolean wantNotification(){

        boolean wantNotification = mainSharedPrefes.getBoolean("sendNotification", true);
        return wantNotification;

    }

    public void setLastTime(long lastTime){

        editor = mainSharedPrefes.edit();
        editor.putLong("lastTime", lastTime);
        editor.commit();
    }

    public long getLastTime(){

        long lastTime = mainSharedPrefes.getLong("lastTime", System.currentTimeMillis());
        return lastTime;

    }

}
