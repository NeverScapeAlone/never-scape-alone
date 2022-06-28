package com.neverscapealone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExperienceLevel
{
    Learner("Learner",0),
    Novice("Novice", 1),
    Apprentice("Apprentice", 2),
    Adept("Adept", 3),
    Expert("Expert", 4),
    Master("Master", 5);

    private final String name;
    private final int experienceRating;

    @Override
    public String toString()
    {
        return name;
    }
}