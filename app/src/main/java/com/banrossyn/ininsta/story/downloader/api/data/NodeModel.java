package com.banrossyn.ininsta.story.downloader.api.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NodeModel implements Serializable {
    @SerializedName("display_resources")
    private List<DisplayResourceModel> displayResources;
    @SerializedName("is_video")
    private boolean isVideo;
    @SerializedName("video_url")
    private String videoUrl;

    public List<DisplayResourceModel> getDisplayResources() {
        return this.displayResources;
    }

    public void setDisplayResources(List<DisplayResourceModel> list) {
        this.displayResources = list;
    }

    public boolean isVideo() {
        return this.isVideo;
    }

    public void setVideo(boolean z) {
        this.isVideo = z;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String str) {
        this.videoUrl = str;
    }

}
