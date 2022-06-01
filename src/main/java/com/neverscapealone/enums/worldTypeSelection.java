package com.neverscapealone.enums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum worldTypeSelection {
    F2P("free-to-play", 0),
    P2P("members", 1),
    BOTH("both", 2);

    private final String name;
    private final int experienceRating;

    @Override
    public String toString()
    {
        return name;
    }
}