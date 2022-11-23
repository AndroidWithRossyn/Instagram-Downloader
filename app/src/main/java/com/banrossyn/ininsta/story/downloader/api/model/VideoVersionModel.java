package com.banrossyn.ininsta.story.downloader.api.model;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class VideoVersionModel implements Serializable {
    @SerializedName("height")
    private int height;
    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private int type;
    @SerializedName(ImagesContract.URL)
    private String url;
    @SerializedName("width")
    private int width;

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }
}