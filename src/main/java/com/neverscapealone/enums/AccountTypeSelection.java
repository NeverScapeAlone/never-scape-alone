package com.neverscapealone.enums;

import com.neverscapealone.ui.Icons;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

@Getter
@RequiredArgsConstructor
public enum AccountTypeSelection {
    NORMAL("Normal", Icons.NSA_ICON),
    IM("IM", Icons.IM_ICON),
    HCIM("HCIM", Icons.HCGIM_ICON),
    UIM("UIM", Icons.UIM_ICON),
    GIM("GIM", Icons.GIM_ICON),
    HCGIM("HCGIM", Icons.HCGIM_ICON),
    UGIM("UGIM", Icons.UGIM_ICON),
    ANY("Any", Icons.NSA_ICON);

    private final String name;
    private final ImageIcon image;

    @Override
    public String toString() {
        return name;
    }
}