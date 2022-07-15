package com.neverscapealone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExperienceLevel {
    Flexible("Flexible", 0),
    Beginner("Beginner", 1),
    Average("Average", 2),
    Advanced("Advanced", 3);

    private final String name;
    private final int experienceRating;

    @Override
    public String toString() {
        return name;
    }
}