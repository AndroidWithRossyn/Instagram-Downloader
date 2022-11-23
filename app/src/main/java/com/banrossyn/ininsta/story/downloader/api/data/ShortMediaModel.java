package com.banrossyn.ininsta.story.downloader.api.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ShortMediaModel implements Serializable {

    @SerializedName("accessibility_caption")
    private String accessibilityCaption;
    @SerializedName("display_resources")
    private List<DisplayResourceModel> displayResources;
    @SerializedName("display_url")
    private String displayUrl;
    @SerializedName("edge_sidecar_to_children")
    private EdgeSidecarModel edgeSidecarToChildren;
    @SerializedName("is_video")
    private boolean isVideo;
    @SerializedName("video_url")
    private String videoUrl;

    public String getDisplayUrl() {
        return this.displayUrl;
    }

    public void setDisplayUrl(String str) {
        this.displayUrl = str;
    }

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

    public EdgeSidecarModel getEdgeSidecarToChildren() {
        return this.edgeSidecarToChildren;
    }

    public void setEdgeSidecarToChildren(EdgeSidecarModel edgeSidecarToChildren2) {
        this.edgeSidecarToChildren = edgeSidecarToChildren2;
    }

    public String getAccessibilityCaption() {
        return this.accessibilityCaption;
    }

    public void setAccessibilityCaption(String str) {
        this.accessibilityCaption = str;
    }

}
