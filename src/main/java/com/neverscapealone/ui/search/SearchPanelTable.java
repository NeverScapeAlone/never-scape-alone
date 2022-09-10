package com.neverscapealone.ui.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neverscapealone.models.payload.searchmatches.SearchMatchData;
import com.neverscapealone.models.payload.searchmatches.SearchMatches;

//1 private String activity; // activity image
//2 private String partyLeader; // show string
//private Boolean isPrivate; // change match color to notifier or highlight
//3 private Boolean RuneGuard; runeguard green/red symbol
//private String id; // on hover
//4 private String playerCount;
//private String partyMembers; // just show playercount/partymembers

//5 private String experience; // special icon -- click to cycle (?)
//6 private String splitType; // special icon
//7 private String accounts; // special icon
//8 private String regions; // special icon

//private String matchVersion; // change match color to red
//private String notes; // click to open

import javax.swing.*;

import java.awt.*;

import static com.neverscapealone.ui.NeverScapeAlonePanel.ACCENT_COLOR;

public class SearchPanelTable{
    int ACTIVITY_WIDTH = 16;
    int ARROW_WIDTH = 5;

    public JPanel buildSearchTable(){
        JPanel searchTable = new JPanel();
        return searchTable;
    }

    public JPanel searchTableHeader(){
        JPanel searchTableHeader = new JPanel();
        return searchTableHeader;
    }

    public JPanel drawRow(SearchMatchData searchMatchData, int rowID){
        JPanel row = new JPanel();
        if (rowID % 2 == 0){
            row.setBackground(ACCENT_COLOR);
        } else {
            row.setBackground(ACCENT_COLOR.darker());
        }

        return row;
    }
}