package com.neverscapealone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountTypeSelection {
    NORMAL("Normal", 0),
    IM("IM", 1),
    HCIM("HCIM", 2),
    UIM("UIM", 3),
    GIM("GIM", 4),
    HCGIM("HCGIM", 5),
    MAINS("PVP Mains", 6),
    PURES("PVP Pures",7),
    ALL("All Accounts",8);

    private final String name;
    private final int account_type;

    @Override
    public String toString()
    {
        return name;
    }
}