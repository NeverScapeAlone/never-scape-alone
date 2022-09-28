package com.neverscapealone.enums;
/*
 * Copyright (c) 2022, Ferrariic <ferrariictweet@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.neverscapealone.ui.utils.Icons;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

@Getter
@RequiredArgsConstructor
public enum PrayerTypeEnum {
    AUGURY(Icons.AUGURY),
    BURST_OF_STRENGTH(Icons.BURST_OF_STRENGTH),
    CHIVALRY(Icons.CHIVALRY),
    CLARITY_OF_THOUGHT(Icons.CLARITY_OF_THOUGHT),
    EAGLE_EYE(Icons.EAGLE_EYE),
    HAWK_EYE(Icons.HAWK_EYE),
    IMPROVED_REFLEXES(Icons.IMPROVED_REFLEXES),
    INCREDIBLE_REFLEXES(Icons.INCREDIBLE_REFLEXES),
    MYSTIC_LORE(Icons.MYSTIC_LORE),
    MYSTIC_MIGHT(Icons.MYSTIC_MIGHT),
    MYSTIC_WILL(Icons.MYSTIC_WILL),
    PIETY(Icons.PIETY),
    PRESERVE(Icons.PRESERVE),
    PROTECT_FROM_MAGIC(Icons.PROTECT_FROM_MAGIC),
    PROTECT_FROM_MELEE(Icons.PROTECT_FROM_MELEE),
    PROTECT_FROM_MISSILES(Icons.PROTECT_FROM_MISSILES),
    PROTECT_ITEM(Icons.PROTECT_ITEM),
    RAPID_HEAL(Icons.RAPID_HEAL),
    RAPID_RESTORE(Icons.RAPID_RESTORE),
    REDEMPTION(Icons.REDEMPTION),
    RETRIBUTION(Icons.RETRIBUTION),
    RIGOUR(Icons.RIGOUR),
    ROCK_SKIN(Icons.ROCK_SKIN),
    SHARP_EYE(Icons.SHARP_EYE),
    SMITE(Icons.SMITE),
    STEEL_SKIN(Icons.STEEL_SKIN),
    SUPERHUMAN_STRENGTH(Icons.SUPERHUMAN_STRENGTH),
    THICK_SKIN(Icons.THICK_SKIN),
    ULTIMATE_STRENGTH(Icons.ULTIMATE_STRENGTH);
    private final ImageIcon icon;
}
