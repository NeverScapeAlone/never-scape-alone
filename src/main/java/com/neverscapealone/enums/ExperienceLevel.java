package com.neverscapealone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExperienceLevel {
    FLEXIBLE("Flexible", 0),
    BEGINNER("Beginner", 1),
    INTERMEDIATE("Intermediate", 2),
    ADVANCED("Advanced", 3);

    private final String name;
    private final int experienceRating;

    @Override
    public String toString() {
        return name;
    }
}