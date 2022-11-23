package com.banrossyn.ininsta.story.downloader.api.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class FullDetailModel implements Serializable {
    @SerializedName("reel_feed")
    private ReelFeedModel reelFeed;
    @SerializedName("user_detail")
    private UserDetailModel userDetail;

    public UserDetailModel getUserDetail() {
        return this.userDetail;
    }

    public void setUserDetail(UserDetailModel userDetailModel) {
        this.userDetail = userDetailModel;
    }

    public ReelFeedModel getReelFeed() {
        return this.reelFeed;
    }

    public void setReelFeed(ReelFeedModel reelFeedModel) {
        this.reelFeed = reelFeedModel;
    }
}