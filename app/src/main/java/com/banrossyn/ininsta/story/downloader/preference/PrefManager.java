package com.banrossyn.ininsta.story.downloader.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String VIDEODOWNLOADER = "InInsta";
    private static final String PREF_NAME = "AwesomeWallpapers";
    private static final String TAG = "PrefManager";
    private static String fileName = "fileName";
    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public PrefManager(Context context) {
        this._context = context;
        this.pref = context.getSharedPreferences(PREF_NAME, 0);
    }

    public int getFileName() {
        return this.pref.getInt(fileName, 0);
    }

    public void setFileName(int i) {
        SharedPreferences.Editor edit = this.pref.edit();
        this.editor = edit;
        edit.putInt(fileName, i);
        this.editor.commit();
    }

    public static void setRated(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(VIDEODOWNLOADER, 0).edit();
        edit.putBoolean("is_rated_2", z);
        edit.apply();
    }

}