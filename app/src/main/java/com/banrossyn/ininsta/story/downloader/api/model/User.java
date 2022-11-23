package com.banrossyn.ininsta.story.downloader.api.model;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("biography")
    private String biography;
    @SerializedName("follower_count")
    private int followercount;
    @SerializedName("following_count")
    private int followingcount;
    @SerializedName("full_name")
    private String fullname;
    @SerializedName("hd_profile_pic_url_info")
    private HDProfileModel hdProfileModel;
    @SerializedName("is_private")
    private boolean isprivate;
    @SerializedName("is_verified")
    private boolean isverified;
    @SerializedName("media_count")
    private int mediacount;
    @SerializedName("mutual_followers_count")
    private int mutualfollowerscount;
    @SerializedName("pk")
    private long pk;
    @SerializedName("profile_context")
    private String profilecontext;
    @SerializedName("profile_pic_id")
    private String profilepicid;
    @SerializedName("profile_pic_url")
    private String profilepicurl;
    @SerializedName("total_igtv_videos")
    private String totaligtvvideos;
    @SerializedName("username")
    private String username;

    public long getPk() {
        return this.pk;
    }

    public void setPk(long j) {
        this.pk = j;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String str) {
        this.fullname = str;
    }

    public boolean isIsprivate() {
        return this.isprivate;
    }

    public void setIsprivate(boolean z) {
        this.isprivate = z;
    }

    public String getProfilepicurl() {
        return this.profilepicurl;
    }

    public void setProfilepicurl(String str) {
        this.profilepicurl = str;
    }

    public String getProfilepicid() {
        return this.profilepicid;
    }

    public void setProfilepicid(String str) {
        this.profilepicid = str;
    }

    public boolean isIsverified() {
        return this.isverified;
    }

    public void setIsverified(boolean z) {
        this.isverified = z;
    }

    public int getMediacount() {
        return this.mediacount;
    }

    public void setMediacount(int i) {
        this.mediacount = i;
    }

    public int getFollowercount() {
        return this.followercount;
    }

    public void setFollowercount(int i) {
        this.followercount = i;
    }

    public int getFollowingcount() {
        return this.followingcount;
    }

    public void setFollowingcount(int i) {
        this.followingcount = i;
    }

    public String getBiography() {
        return this.biography;
    }

    public void setBiography(String str) {
        this.biography = str;
    }

    public String getTotaligtvvideos() {
        return this.totaligtvvideos;
    }

    public void setTotaligtvvideos(String str) {
        this.totaligtvvideos = str;
    }

    public HDProfileModel getHdProfileModel() {
        return this.hdProfileModel;
    }

    public void setHdProfileModel(HDProfileModel hDProfileModel) {
        this.hdProfileModel = hDProfileModel;
    }

    public int getMutualfollowerscount() {
        return this.mutualfollowerscount;
    }

    public void setMutualfollowerscount(int i) {
        this.mutualfollowerscount = i;
    }

    public String getProfilecontext() {
        return this.profilecontext;
    }

    public void setProfilecontext(String str) {
        this.profilecontext = str;
    }
}