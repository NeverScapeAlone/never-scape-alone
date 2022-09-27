package com.neverscapealone.ui.search;

import com.neverscapealone.models.payload.searchmatches.SearchMatchData;

import javax.swing.*;

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