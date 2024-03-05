package com.banrossyn.ininsta.story.downloader.api.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class ReelFeedModel implements Serializable {
    @SerializedName("user")
    private User user;
    @SerializedName("items")
    private ArrayList<ItemModel> items;
    @SerializedName("media_count")
    private int media_count;

    @SerializedName("media_ids")
    private ArrayList<String> media_ids;

    public ArrayList<String> getMedia_ids() {
        return media_ids;
    }

    public void setMedia_ids(ArrayList<String> media_ids) {
        this.media_ids = media_ids;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<ItemModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemModel> items) {
        this.items = items;
    }

    public int getMedia_count() {
        return media_count;
    }

    public void setMedia_count(int media_count) {
        this.media_count = media_count;
    }
}