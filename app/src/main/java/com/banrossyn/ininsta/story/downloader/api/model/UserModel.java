package com.banrossyn.ininsta.story.downloader.api.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class UserModel implements Serializable {
    @SerializedName("full_name")
    private String fullname;
    int isFav;
    @SerializedName("is_private")
    private boolean isprivate;
    @SerializedName("is_verified")
    private boolean isverified;
    @SerializedName("pk")
    private long pk;
    @SerializedName("profile_pic_id")
    private String profilepicid;
    @SerializedName("profile_pic_url")
    private String profilepicurl;
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

    public int getIsFav() {
        return this.isFav;
    }

    public void setIsFav(int i) {
        this.isFav = i;
    }
}