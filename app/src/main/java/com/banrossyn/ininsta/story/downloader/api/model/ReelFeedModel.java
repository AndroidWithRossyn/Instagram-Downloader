package com.banrossyn.ininsta.story.downloader.api.model;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class ReelFeedModel implements Serializable {
    @SerializedName("expiring_atexpiring_at")
    private long expiringat;
    @SerializedName("id")
    private long id;
    @SerializedName(FirebaseAnalytics.Param.ITEMS)
    private ArrayList<ItemModel> items;
    @SerializedName("latest_reel_media")
    private long latestreelmedia;
    @SerializedName("media_count")
    private int mediacount;
    @SerializedName("reeltype")
    private String reeltype;
    @SerializedName("seen")
    private long seen;

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public long getLatestreelmedia() {
        return this.latestreelmedia;
    }

    public void setLatestreelmedia(long j) {
        this.latestreelmedia = j;
    }

    public long getExpiringat() {
        return this.expiringat;
    }

    public void setExpiringat(long j) {
        this.expiringat = j;
    }

    public long getSeen() {
        return this.seen;
    }

    public void setSeen(long j) {
        this.seen = j;
    }

    public String getReeltype() {
        return this.reeltype;
    }

    public void setReeltype(String str) {
        this.reeltype = str;
    }

    public ArrayList<ItemModel> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<ItemModel> arrayList) {
        this.items = arrayList;
    }

    public int getMediacount() {
        return this.mediacount;
    }

    public void setMediacount(int i) {
        this.mediacount = i;
    }
}