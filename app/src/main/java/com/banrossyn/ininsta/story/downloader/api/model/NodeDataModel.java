package com.banrossyn.ininsta.story.downloader.api.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class NodeDataModel implements Serializable {
    @SerializedName("attachments")
    private ArrayList<MediaModel> attachmentsList;
    @SerializedName("id")
    private String id;
    @SerializedName("owner")
    private JsonObject owner;
    @SerializedName("story_bucket_owner")
    private JsonObject story_bucket_owner;

    public JsonObject getOwner() {
        return this.owner;
    }

    public void setOwner(JsonObject jsonObject) {
        this.owner = jsonObject;
    }

    public JsonObject getStory_bucket_owner() {
        return this.story_bucket_owner;
    }

    public void setStory_bucket_owner(JsonObject jsonObject) {
        this.story_bucket_owner = jsonObject;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public ArrayList<MediaModel> getAttachmentsList() {
        return this.attachmentsList;
    }

    public void setAttachmentsList(ArrayList<MediaModel> arrayList) {
        this.attachmentsList = arrayList;
    }
}