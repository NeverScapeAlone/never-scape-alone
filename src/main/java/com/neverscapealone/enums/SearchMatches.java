package com.neverscapealone.enums;

import com.neverscapealone.enums.SearchMatchData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchMatches {
    @SerializedName("search_matches")
    ArrayList<SearchMatchData> search_matches;
}
