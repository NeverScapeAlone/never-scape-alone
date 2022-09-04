package com.neverscapealone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum EquipmentSlotEnum {
    HEAD(0, "head"),
    CAPE(1, "cape"),
    AMULET(2, "amulet"),
    WEAPON(3, "weapon"),
    BODY(4, "body"),
    SHIELD(5, "shield"),
    LEGS(7, "legs"),
    GLOVES(9, "gloves"),
    BOOTS(10, "boots"),
    RING(12, "ring"),
    AMMO(13, "ammo");

    private final int id;
    private final String name;
}

