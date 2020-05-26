package com.example.myhoroscopedaily.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class AstrologyModel {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("sunsign")
    @Expose
    private String sunsign;

    @SerializedName("horoscope")
    @Expose
    private String horoscope;

    @SerializedName("meta")
    @Expose
    private HashMap<String, String> meta;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSunsign() {
        return sunsign;
    }

    public void setSunsign(String sunsign) {
        this.sunsign = sunsign;
    }

    public String getHoroscope() {
        return horoscope;
    }

    public static void setHoroscope(Context context, String horoscope) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("prediction", horoscope);
        editor.apply();
    }

    public HashMap<String, String> getMeta() {
        return meta;
    }

    public void setMeta(HashMap<String, String> meta) {
        this.meta = meta;
    }
}
