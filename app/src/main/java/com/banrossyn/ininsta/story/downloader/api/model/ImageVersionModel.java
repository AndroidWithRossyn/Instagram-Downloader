package com.banrossyn.ininsta.story.downloader.api.model;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ImageVersionModel implements Serializable {
    @SerializedName("candidates")
    private List<CandidatesModel> candidates;

    public List<CandidatesModel> getCandidates() {
        return this.candidates;
    }

    public void setCandidates(List<CandidatesModel> list) {
        this.candidates = list;
    }
}