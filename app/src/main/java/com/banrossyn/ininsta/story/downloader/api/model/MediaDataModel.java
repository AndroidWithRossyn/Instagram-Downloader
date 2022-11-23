package com.banrossyn.ininsta.story.downloader.api.model;


import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class MediaDataModel implements Serializable {
    @SerializedName("__typename")
    private String __typename;
    @SerializedName("id")
    private String id;
    @SerializedName("playable_url_quality_hd")
    private String playable_url_quality_hd;
    @SerializedName("previewImage")
    private JsonObject previewImage;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String get__typename() {
        return this.__typename;
    }

    public void set__typename(String str) {
        this.__typename = str;
    }

    public String getPlayable_url_quality_hd() {
        return this.playable_url_quality_hd;
    }

    public void setPlayable_url_quality_hd(String str) {
        this.playable_url_quality_hd = str;
    }

    public JsonObject getPreviewImage() {
        return this.previewImage;
    }

    public void setPreviewImage(JsonObject jsonObject) {
        this.previewImage = jsonObject;
    }
}