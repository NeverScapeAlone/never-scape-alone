package com.neverscapealone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum experienceLevel
{
    NOVICE("Novice", 1),
    APPRENTICE("Apprentice", 2),
    ADEPT("Adept", 3),
    EXPERT("Expert", 4),
    MASTER("Master", 5);

    private final String name;
    private final int experienceRating;

    @Override
    public String toString()
    {
        return name;
    }
}