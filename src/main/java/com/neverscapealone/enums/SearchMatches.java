package com.neverscapealone.enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchMatches {

    @SerializedName("search_matches")
    @Expose
    private List<SearchMatchData> searchMatches = null;

    public List<SearchMatchData> getSearchMatches() {
        return searchMatches;
    }

    public void setSearchMatches(List<SearchMatchData> searchMatches) {
        this.searchMatches = searchMatches;
    }

    public SearchMatches withSearchMatches(List<SearchMatchData> searchMatches) {
        this.searchMatches = searchMatches;
        return this;
    }
}