package com.banrossyn.ininsta.story.downloader.api.model;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class TrayModel implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName(FirebaseAnalytics.Param.ITEMS)
    private List<ItemModel> items;
    @SerializedName("media_count")
    private int mediacount;
    @SerializedName("user")
    private UserModel user;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public UserModel getUser() {
        return this.user;
    }

    public void setUser(UserModel userModel) {
        this.user = userModel;
    }

    public int getMediacount() {
        return this.mediacount;
    }

    public void setMediacount(int i) {
        this.mediacount = i;
    }

    public List<ItemModel> getItems() {
        return this.items;
    }

    public void setItems(List<ItemModel> list) {
        this.items = list;
    }
}