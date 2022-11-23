package com.banrossyn.ininsta.story.downloader.api.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DisplayResourceModel implements Serializable {

    @SerializedName("src")
    private String src;

    @SerializedName("config_width")
    private int config_width;

    @SerializedName("config_height")
    private int config_height;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getConfig_width() {
        return config_width;
    }

    public void setConfig_width(int config_width) {
        this.config_width = config_width;
    }

    public int getConfig_height() {
        return config_height;
    }

    public void setConfig_height(int config_height) {
        this.config_height = config_height;
    }
}
