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

package com.neverscapealone.enums;

import com.neverscapealone.ui.Icons;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

@Getter
@RequiredArgsConstructor
public enum ActivityReference
// "random", "skill", "boss", "minigame", "misc", "raid"
{
    // random
    RANDOM(Icons.RANDOM, "random", "Random Activity", "RANDOM"),
    // skill
    ATTACK(Icons.ATTACK, "skill", "Attack", "ATTACK"),
    STRENGTH(Icons.STRENGTH, "skill", "Strength", "STRENGTH"),
    DEFENCE(Icons.DEFENCE, "skill", "Defence", "DEFENCE"),
    HITPOINTS(Icons.HITPOINTS, "skill", "Hitpoints", "HITPOINTS"),
    RANGED(Icons.RANGED, "skill", "Ranged", "RANGED"),
    PRAYER(Icons.PRAYER, "skill", "Prayer", "PRAYER"),
    MAGIC(Icons.MAGIC, "skill", "Magic", "MAGIC"),
    COOKING(Icons.COOKING, "skill", "Cooking", "COOKING"),
    WOODCUTTING(Icons.WOODCUTTING, "skill", "Woodcutting", "WOODCUTTING"),
    FLETCHING(Icons.FLETCHING, "skill", "Fletching", "FLETCHING"),
    FISHING(Icons.FISHING, "skill", "Fishing", "FISHING"),
    FIREMAKING(Icons.FIREMAKING, "skill", "Firemaking", "FIREMAKING"),
    CRAFTING(Icons.CRAFTING, "skill", "Crafting", "CRAFTING"),
    SMITHING(Icons.SMITHING, "skill", "Smithing", "SMITHING"),
    MINING(Icons.MINING, "skill", "Mining", "MINING"),
    HERBLORE(Icons.HERBLORE, "skill", "Herblore", "HERBLORE"),
    AGILITY(Icons.AGILITY, "skill", "Agility", "AGILITY"),
    THIEVING(Icons.THIEVING, "skill", "Thieving", "THIEVING"),
    SLAYER(Icons.SLAYER, "skill", "Slayer", "SLAYER"),
    FARMING(Icons.FARMING, "skill", "Farming", "FARMING"),
    RUNECRAFT(Icons.RUNECRAFTING, "skill", "Runecraft", "RUNECRAFT"),
    HUNTER(Icons.HUNTER, "skill", "Hunter", "HUNTER"),
    CONSTRUCTION(Icons.CONSTRUCTION, "skill", "Construction", "CONSTRUCTION"),
    ALL_SKILLS(Icons.ALL_SKILLS, "skill", "All Skills", "ALL_SKILLS"),

    // boss
    ABYSSAL_SIRE(Icons.ABYSSAL_SIRE, "boss", "Abyssal Sire", "ABYSSAL_SIRE"),
    ALCHEMICAL_HYDRA(Icons.ALCHEMICAL_HYDRA, "boss", "Alchemical Hydra", "ALCHEMICAL_HYDRA"),
    ARCHAEOLOGIST_CRAZY(Icons.ARCHAEOLOGIST_CRAZY, "boss", "Crazy Archaeologist", "ARCHAEOLOGIST_CRAZY"),
    ARCHAEOLOGIST_DERANGED(Icons.ARCHAEOLOGIST_DERANGED, "boss", "Deranged Archaeologist", "ARCHAEOLOGIST_DERANGED"),
    BRYOPHYTA(Icons.BRYOPHYTA, "boss", "Bryophyta", "BRYOPHYTA"),
    BARROWS(Icons.BARROWS, "boss", "Barrows Brothers", "BARROWS"),
    CALLISTO(Icons.CALLISTO, "boss", "Callisto", "CALLISTO"),
    CERBERUS(Icons.CERBERUS, "boss", "Cerberus", "CERBERUS"),
    CHAOS_ELEMENTAL(Icons.CHAOS_ELEMENTAL, "boss", "Chaos Elemental", "CHAOS_ELEMENTAL"),
    CHAOS_FANATIC(Icons.CHAOS_FANATIC, "boss", "Chaos Fanatic", "CHAOS_FANATIC"),
    COMMANDER_ZILYANA(Icons.COMMANDER_ZILYANA, "boss", "Commander Zilyana", "COMMANDER_ZILYANA"),
    CORPOREAL_BEAST(Icons.CORPOREAL_BEAST, "boss", "Corporeal Beast", "CORPOREAL_BEAST"),
    DAGANNOTH_PRIME(Icons.DAGANNOTH_PRIME, "boss", "Dagannoth Prime", "DAGANNOTH_PRIME"),
    DAGANNOTH_REX(Icons.DAGANNOTH_REX, "boss", "Dagannoth Rex", "DAGANNOTH_REX"),
    DAGANNOTH_SUPREME(Icons.DAGANNOTH_SUPREME, "boss", "Dagannoth Supreme", "DAGANNOTH_SUPREME"),
    GENERAL_GRAARDOR(Icons.GENERAL_GRAARDOR, "boss", "General Graardor", "GENERAL_GRAARDOR"),
    GIANT_MOLE(Icons.GIANT_MOLE, "boss", "Giant Mole", "GIANT_MOLE"),
    GROTESQUE_GUARDIANS(Icons.GROTESQUE_GUARDIANS, "boss", "Grotesque Guardians", "GROTESQUE_GUARDIANS"),
    HESPORI(Icons.HESPORI, "boss", "Hespori", "HESPORI"),
    KALPHITE_QUEEN(Icons.KALPHITE_QUEEN, "boss", "Kalphite Queen", "KALPHITE_QUEEN"),
    KING_BLACK_DRAGON(Icons.KING_BLACK_DRAGON, "boss", "King Black Dragon", "KING_BLACK_DRAGON"),
    KRAKEN(Icons.KRAKEN, "boss", "Kraken", "KRAKEN"),
    KREEARRA(Icons.KREEARRA, "boss", "Kreearra", "KREEARRA"),
    KRIL_TSUTSAROTH(Icons.KRIL_TSUTSAROTH, "boss", "Kril Tsutsaroth", "KRIL_TSUTSAROTH"),
    MIMIC(Icons.MIMIC, "boss", "Mimic", "MIMIC"),
    NEX(Icons.NEX, "boss", "Nex", "NEX"),
    NIGHTMARE(Icons.NIGHTMARE, "boss", "Nightmare", "NIGHTMARE"),
    OBOR(Icons.OBOR, "boss", "Obor", "OBOR"),
    PHOSANIS_NIGHTMARE(Icons.PHOSANIS_NIGHTMARE, "boss", "Phosani's Nightmare", "PHOSANIS_NIGHTMARE"),
    SARACHNIS(Icons.SARACHNIS, "boss", "Sarachnis", "SARACHNIS"),
    SCORPIA(Icons.SCORPIA, "boss", "Scorpia", "SCORPIA"),
    SKOTIZO(Icons.SKOTIZO, "boss", "Skotizo", "SKOTIZO"),
    GAUNTLET(Icons.GAUNTLET, "boss", "Gauntlet", "GAUNTLET"),
    GAUNTLET_CORRUPTED(Icons.GAUNTLET_CORRUPTED, "boss", "The Corrupted Gauntlet", "GAUNTLET_CORRUPTED"),
    THERMONUCLEARSMOKEDEVIL(Icons.THERMONUCLEARSMOKEDEVIL, "boss", "Thermonuclearsmokedevil", "THERMONUCLEARSMOKEDEVIL"),
    TZ_KAL_ZUK(Icons.TZ_KAL_ZUK, "boss", "The Inferno", "TZ_KAL_ZUK"),
    TZ_TOK_JAD(Icons.TZ_TOK_JAD, "boss", "The Fight Caves", "TZ_TOK_JAD"),
    VENENATIS(Icons.VENENATIS, "boss", "Venenatis", "VENENATIS"),
    VETION(Icons.VETION, "boss", "Vet'ion", "VETION"),
    VORKATH(Icons.VORKATH, "boss", "Vorkath", "VORKATH"),
    ZALCANO(Icons.ZALCANO, "boss", "Zalcano", "ZALCANO"),
    ZULRAH(Icons.ZULRAH, "boss", "Zulrah", "ZULRAH"),

    // minigame
    BARBARIAN_ASSAULT(Icons.BARBARIAN_ASSAULT, "minigame", "Barbarian Assault", "BARBARIAN_ASSAULT"),
    BLAST_FURNACE(Icons.BLAST_FURNACE, "minigame", "Blast Furnace", "BLAST_FURNACE"),
    BLAST_MINE(Icons.BLAST_MINE, "minigame", "Blast Mine", "BLAST_MINE"),
    BRIMHAVEN_AGILITY_ARENA(Icons.BRIMHAVEN_AGILITY_ARENA, "minigame", "Brimhaven Agility Arena", "BRIMHAVEN_AGILITY_ARENA"),
    BOUNTY_HUNTER_HUNTER(Icons.BOUNTY_HUNTER_HUNTER, "minigame", "Bounty Hunter (Hunter)", "BOUNTY_HUNTER_HUNTER"),
    BOUNTY_HUNTER_ROGUE(Icons.BOUNTY_HUNTER_ROGUE, "minigame", "Bounty Hunter (Rogue)", "BOUNTY_HUNTER_ROGUE"),
    CAMDOZAAL_VAULT(Icons.CAMDOZAAL_VAULT, "minigame", "Camdozaal Vault", "CAMDOZAAL_VAULT"),
    CASTLE_WARS(Icons.CASTLE_WARS, "minigame", "Castle Wars", "CASTLE_WARS"),
    CLAN_WARS(Icons.CLAN_WARS, "minigame", "Clan Wars", "CLAN_WARS"),
    CREATURE_CREATION(Icons.CREATURE_CREATION, "minigame", "Creature Creation", "CREATURE_CREATION"),
    DUEL_ARENA(Icons.DUEL_ARENA, "minigame", "Duel Arena", "DUEL_ARENA"),
    FISHING_TRAWLER(Icons.FISHING_TRAWLER, "minigame", "Fishing Trawler", "FISHING_TRAWLER"),
    GNOME_BALL(Icons.GNOME_BALL, "minigame", "Gnome Ball", "GNOME_BALL"),
    GNOME_RESTAURANT(Icons.GNOME_RESTAURANT, "minigame", "Gnome Restaurant", "GNOME_RESTAURANT"),
    GUARDIANS_OF_THE_RIFT(Icons.GUARDIANS_OF_THE_RIFT, "minigame", "Guardians of the Rift", "GUARDIANS_OF_THE_RIFT"),
    HALLOWED_SEPULCHRE(Icons.HALLOWED_SEPULCHRE, "minigame", "Hallowed Sepulchre", "HALLOWED_SEPULCHRE"),
    PURO_PURO(Icons.PURO_PURO, "minigame", "Puro Puro", "PURO_PURO"),
    MAGE_ARENA(Icons.MAGE_ARENA, "minigame", "Mage Arena", "MAGE_ARENA"),
    MAHOGANY_HOMES(Icons.MAHOGANY_HOMES, "minigame", "Mahogany Homes", "MAHOGANY_HOMES"),
    MAGE_TRAINING_ARENA(Icons.MAGE_TRAINING_ARENA, "minigame", "Mage Training Arena", "MAGE_TRAINING_ARENA"),
    NIGHTMARE_ZONE(Icons.NIGHTMARE_ZONE, "minigame", "Nightmare Zone", "NIGHTMARE_ZONE"),
    ORGANIZED_CRIME(Icons.ORGANIZED_CRIME, "minigame", "Organized Crime", "ORGANIZED_CRIME"),
    PEST_CONTROL(Icons.PEST_CONTROL, "minigame", "Pest Control", "PEST_CONTROL"),
    PYRAMID_PLUNDER(Icons.PYRAMID_PLUNDER, "minigame", "Pyramid Plunder", "PYRAMID_PLUNDER"),
    ROGUES_DEN(Icons.ROGUES_DEN, "minigame", "Rogues Den", "ROGUES_DEN"),
    SHADES_OF_MORTON(Icons.SHADES_OF_MORTON, "minigame", "Shades of Morton", "SHADES_OF_MORTON"),
    SORCERESS_GARDEN(Icons.SORCERESS_GARDEN, "minigame", "Sorceress's Garden", "SORCERESS_GARDEN"),
    TAI_BWO_WANNAI(Icons.TAI_BWO_WANNAI, "minigame", "Tai Bwo Wannai", "TAI_BWO_WANNAI"),
    TITHE_FARM(Icons.TITHE_FARM, "minigame", "Tithe Farm", "TITHE_FARM"),
    TROUBLE_BREWING(Icons.TROUBLE_BREWING, "minigame", "Trouble Brewing", "TROUBLE_BREWING"),
    UNDERWATER_AGILITY_AND_THIEVING(Icons.UNDERWATER_AGILITY_AND_THIEVING, "minigame", "Underwater Agility and Thieving", "UNDERWATER_AGILITY_AND_THIEVING"),
    VOLCANIC_MINE(Icons.VOLCANIC_MINE, "minigame", "Volcanic Mine", "VOLCANIC_MINE"),
    LAST_MAN_STANDING(Icons.LAST_MAN_STANDING, "minigame", "Last Man Standing", "LAST_MAN_STANDING"),
    SOUL_WARS(Icons.SOUL_WARS, "minigame", "Soul Wars", "SOUL_WARS"),
    TEMPOROSS(Icons.TEMPOROSS, "minigame", "Tempoross", "TEMPOROSS"),
    WINTERTODT(Icons.WINTERTODT, "minigame", "Wintertodt", "WINTERTODT"),

    // raid
    COX(Icons.COX, "raid", "Chambers of Xeric", "COX"),
    HARD_COX(Icons.HARD_COX, "raid", "Chambers of Xeric Challenge Mode", "HARD_COX"),
    TOB(Icons.TOB, "raid", "Theatre of Blood", "TOB"),
    HARD_TOB(Icons.HARD_TOB, "raid", "Theatre of Blood Hard Mode", "HARD_TOB"),

    // misc
    CLUES(Icons.CLUES, "misc", "Clues", "CLUES"),
    FALADOR_PARTY_ROOM(Icons.FALADOR_PARTY_ROOM, "misc", "Falador Party Room", "FALADOR_PARTY_ROOM"),
    PVP_GENERIC(Icons.PVP_GENERIC, "misc", "PKing", "PVP_GENERIC"),
    CHAT(Icons.CHAT, "misc", "Chat and Relax", "CHAT"),
    QUEST(Icons.QUEST, "misc", "All Quests", "QUEST"),
    DIARY(Icons.DIARY, "misc", "All Diaries", "DIARY"),
    FAVOR(Icons.FAVOR, "misc", "All Kourend Favors", "FAVOR"),
    CA1(Icons.CA1, "misc", "Combat Achievements (Easy)", "CA1"),
    CA2(Icons.CA2, "misc", "Combat Achievements (Medium)", "CA2"),
    CA3(Icons.CA3, "misc", "Combat Achievements (Hard)", "CA3"),
    CA4(Icons.CA4, "misc", "Combat Achievements (Elite)", "CA4"),
    CA5(Icons.CA5, "misc", "Combat Achievements (Master)", "CA5"),
    CA6(Icons.CA6, "misc", "Combat Achievements (Grandmaster)", "CA6"),
    ALL_COMBAT_ACHIEVEMENTS(Icons.ALL_COMBAT_ACHIEVEMENTS, "misc", "All Combat Achievements", "ALL_COMBAT_ACHIEVEMENTS"),
    F2P(Icons.F2P, "misc", "Free-to-Play", "F2P"),
    ;

    private final ImageIcon icon;
    private final String activity;
    private final String tooltip;
    private final String label;
}