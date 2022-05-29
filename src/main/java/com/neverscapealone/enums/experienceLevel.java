package com.neverscapealone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum experienceLevel
{
    LEARNER("Learner",1),
    NOVICE("Novice", 2),
    APPRENTICE("Apprentice", 3),
    ADEPT("Adept", 4),
    EXPERT("Expert", 5),
    MASTER("Master", 6);

    private final String name;
    private final int experienceRating;

    @Override
    public String toString()
    {
        return name;
    }
}