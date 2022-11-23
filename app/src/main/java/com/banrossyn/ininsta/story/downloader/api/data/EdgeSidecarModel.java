package com.banrossyn.ininsta.story.downloader.api.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EdgeSidecarModel implements Serializable {
    @SerializedName("edges")
    private List<EdgeModel> edges;

    public List<EdgeModel> getEdges() {
        return this.edges;
    }

    public void setEdges(List<EdgeModel> list) {
        this.edges = list;
    }

}
