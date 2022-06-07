package com.neverscapealone.enums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum worldTypeSelection {
    F2P("Free-to-Play", 0),
    P2P("Members", 1),
    BOTH("Both", 2);

    private final String name;
    private final int world_type;

    @Override
    public String toString()
    {
        return name;
    }
}