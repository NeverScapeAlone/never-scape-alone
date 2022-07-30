package com.neverscapealone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum SoundPingEnum {

    MATCH_JOIN(0),
    MATCH_LEAVE(1),
    NORMAL_PING(2),
    HEAVY_PING(3),
    PLAYER_JOIN(4),
    PLAYER_LEAVE(5);

    private final Integer ID;
}
