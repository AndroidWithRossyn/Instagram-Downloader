package com.banrossyn.ininsta.story.downloader.api.model;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ItemModel implements Serializable {
    @SerializedName("can_reply")
    private boolean canreply;
    @SerializedName("can_reshare")
    private boolean canreshare;
    @SerializedName("caption_is_edited")
    private boolean captionisedited;
    @SerializedName("caption_position")
    private int captionposition;
    @SerializedName("client_cache_key")
    private String clientcachekey;
    @SerializedName("code")
    private String code;
    @SerializedName("device_timestamp")
    private long devicetimestamp;
    @SerializedName("expiring_at")
    private long expiringat;
    @SerializedName("filter_type")
    private int filtertype;
    @SerializedName("has_audio")
    private boolean hasaudio;
    @SerializedName("id")
    private String id;
    @SerializedName("image_versions2")
    private ImageVersionModel imageversions2;
    @SerializedName("is_reel_media")
    private boolean isreelmedia;
    @SerializedName("media_type")
    private int mediatype;
    @SerializedName("organic_tracking_token")
    private String organictrackingtoken;
    @SerializedName("original_height")
    private int originalheight;
    @SerializedName("original_width")
    private int originalwidth;
    @SerializedName("photo_of_you")
    private boolean photoofyou;
    @SerializedName("pk")
    private long pk;
    @SerializedName("taken_at")
    private long takenat;
    @SerializedName("video_duration")
    private double videoduration;
    @SerializedName("video_versions")
    private List<VideoVersionModel> videoversions;

    public long getTakenat() {
        return this.takenat;
    }

    public void setTakenat(long j) {
        this.takenat = j;
    }

    public long getPk() {
        return this.pk;
    }

    public void setPk(long j) {
        this.pk = j;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public long getDevicetimestamp() {
        return this.devicetimestamp;
    }

    public void setDevicetimestamp(long j) {
        this.devicetimestamp = j;
    }

    public int getMediatype() {
        return this.mediatype;
    }

    public void setMediatype(int i) {
        this.mediatype = i;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getClientcachekey() {
        return this.clientcachekey;
    }

    public void setClientcachekey(String str) {
        this.clientcachekey = str;
    }

    public int getFiltertype() {
        return this.filtertype;
    }

    public void setFiltertype(int i) {
        this.filtertype = i;
    }

    public ImageVersionModel getImageversions2() {
        return this.imageversions2;
    }

    public void setImageversions2(ImageVersionModel imageVersionModel) {
        this.imageversions2 = imageVersionModel;
    }

    public int getOriginalwidth() {
        return this.originalwidth;
    }

    public void setOriginalwidth(int i) {
        this.originalwidth = i;
    }

    public int getOriginalheight() {
        return this.originalheight;
    }

    public void setOriginalheight(int i) {
        this.originalheight = i;
    }

    public List<VideoVersionModel> getVideoversions() {
        return this.videoversions;
    }

    public void setVideoversions(List<VideoVersionModel> list) {
        this.videoversions = list;
    }

    public boolean isHasaudio() {
        return this.hasaudio;
    }

    public void setHasaudio(boolean z) {
        this.hasaudio = z;
    }

    public double getVideoduration() {
        return this.videoduration;
    }

    public void setVideoduration(double d) {
        this.videoduration = d;
    }

    public boolean isCaptionisedited() {
        return this.captionisedited;
    }

    public void setCaptionisedited(boolean z) {
        this.captionisedited = z;
    }

    public int getCaptionposition() {
        return this.captionposition;
    }

    public void setCaptionposition(int i) {
        this.captionposition = i;
    }

    public boolean isIsreelmedia() {
        return this.isreelmedia;
    }

    public void setIsreelmedia(boolean z) {
        this.isreelmedia = z;
    }

    public boolean isPhotoofyou() {
        return this.photoofyou;
    }

    public void setPhotoofyou(boolean z) {
        this.photoofyou = z;
    }

    public String getOrganictrackingtoken() {
        return this.organictrackingtoken;
    }

    public void setOrganictrackingtoken(String str) {
        this.organictrackingtoken = str;
    }

    public long getExpiringat() {
        return this.expiringat;
    }

    public void setExpiringat(long j) {
        this.expiringat = j;
    }

    public boolean isCanreshare() {
        return this.canreshare;
    }

    public void setCanreshare(boolean z) {
        this.canreshare = z;
    }

    public boolean isCanreply() {
        return this.canreply;
    }

    public void setCanreply(boolean z) {
        this.canreply = z;
    }
}