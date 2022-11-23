package com.banrossyn.ininsta.story.downloader.api.model;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class StoryModel implements Serializable {
    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    private String status;
    @SerializedName("tray")
    private ArrayList<TrayModel> tray;

    public ArrayList<TrayModel> getTray() {
        return this.tray;
    }

    public void setTray(ArrayList<TrayModel> arrayList) {
        this.tray = arrayList;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}