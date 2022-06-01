package com.neverscapealone.enums;

import com.neverscapealone.ui.Icons;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

@Getter
@RequiredArgsConstructor
public enum ActivityReference
// "skill", "boss", "minigame", "misc", "solo", "raid"
{
    // skill
    ATTACK(Icons.ATTACK, "skill","Attack"),
    STRENGTH(Icons.STRENGTH,"skill","Strength"),
    DEFENCE(Icons.DEFENCE, "skill","Defence"),
    HITPOINTS(Icons.HITPOINTS, "skill","Hitpoints"),
    RANGED(Icons.RANGED, "skill","Ranged"),
    PRAYER(Icons.PRAYER, "skill","Prayer"),
    MAGIC(Icons.MAGIC, "skill","Magic"),
    COOKING(Icons.COOKING, "skill","Cooking"),
    WOODCUTTING(Icons.WOODCUTTING, "skill","Woodcutting"),
    FLETCHING(Icons.FLETCHING, "skill","Fletching"),
    FISHING(Icons.FISHING, "skill","Fishing"),
    FIREMAKING(Icons.FIREMAKING, "skill","Firemaking"),
    CRAFTING(Icons.CRAFTING, "skill","Crafting"),
    SMITHING(Icons.SMITHING, "skill","Smithing"),
    MINING(Icons.MINING, "skill","Mining"),
    HERBLORE(Icons.HERBLORE, "skill","Herblore"),
    AGILITY(Icons.AGILITY, "skill","Agility"),
    THIEVING(Icons.THIEVING, "skill","Thieving"),
    SLAYER(Icons.SLAYER, "skill","Slayer"),
    FARMING(Icons.FARMING, "skill","Farming"),
    RUNECRAFTING(Icons.RUNECRAFTING, "skill","Runecraft"),
    HUNTER(Icons.HUNTER, "skill","Hunter"),
    CONSTRUCTION(Icons.CONSTRUCTION, "skill","Construction"),
    ALL_SKILLS(Icons.ALL_SKILLS,"skill","All Skills"),

    // solo
    ABYSSAL_SIRE(Icons.ABYSSAL_SIRE, "solo","Abyssal Sire"),
    ALCHEMICAL_HYDRA(Icons.ALCHEMICAL_HYDRA, "solo","Alchemical Hydra"),
    BRYOPHYTA(Icons.BRYOPHYTA, "solo","Bryophyta"),
    CERBERUS(Icons.CERBERUS, "solo","Cerberus"),
    GROTESQUE_GUARDIANS(Icons.GROTESQUE_GUARDIANS, "solo","Grotesque Guardians"),
    HESPORI(Icons.HESPORI, "solo","Hespori"),
    KRAKEN(Icons.KRAKEN, "solo","Kraken"),
    MIMIC(Icons.MIMIC, "solo","Mimic"),
    OBOR(Icons.OBOR, "solo","Obor"),
    PHOSANIS_NIGHTMARE(Icons.PHOSANIS_NIGHTMARE, "solo","Phosani's Nightmare"),
    SKOTIZO(Icons.SKOTIZO, "solo","Skotizo"),
    GAUNTLET(Icons.GAUNTLET, "solo","Gauntlet"),
    GAUNTLET_CORRUPTED(Icons.GAUNTLET_CORRUPTED, "solo","The Corrupted Gauntlet"),
    THERMONUCLEARSMOKEDEVIL(Icons.THERMONUCLEARSMOKEDEVIL, "solo","Thermonuclearsmokedevil"),
    TZ_KAL_ZUK(Icons.TZ_KAL_ZUK, "solo","The Inferno"),
    TZ_TOK_JAD(Icons.TZ_TOK_JAD, "solo","The Fight Caves"),
    VORKATH(Icons.VORKATH, "solo","Vorkath"),
    ZULRAH(Icons.ZULRAH, "solo","Zulrah"),

    // boss
    BARROWS(Icons.BARROWS, "boss","Barrows Brothers"),
    CALLISTO(Icons.CALLISTO, "boss","Callisto"),
    CHAOS_ELEMENTAL(Icons.CHAOS_ELEMENTAL, "boss","Chaos Elemental"),
    CHAOS_FANATIC(Icons.CHAOS_FANATIC, "boss","Chaos Fanatic"),
    COMMANDER_ZILYANA(Icons.COMMANDER_ZILYANA, "boss","Commander Zilyana"),
    CORPOREAL_BEAST(Icons.CORPOREAL_BEAST,"boss","Corporeal Beast"),
    ARCHAEOLOGIST_CRAZY(Icons.ARCHAEOLOGIST_CRAZY,"boss", "Crazy Archaeologist"),
    ARCHAEOLOGIST_DERANGED(Icons.ARCHAEOLOGIST_DERANGED, "boss","Deranged Archaeologist"),
    DAGANNOTH_PRIME(Icons.DAGANNOTH_PRIME,"boss","Dagannoth Prime"),
    DAGANNOTH_REX(Icons.DAGANNOTH_REX,"boss","Dagannoth Rex"),
    DAGANNOTH_SUPREME(Icons.DAGANNOTH_SUPREME,"boss","Dagannoth Supreme"),
    GENERAL_GRAARDOR(Icons.GENERAL_GRAARDOR, "boss","General Graardor"),
    GIANT_MOLE(Icons.GIANT_MOLE,"boss","Giant Mole"),
    KALPHITE_QUEEN(Icons.KALPHITE_QUEEN,"boss","Kalphite Queen"),
    KING_BLACK_DRAGON(Icons.KING_BLACK_DRAGON, "boss","King Black Dragon"),
    KREEARRA(Icons.KREEARRA, "boss","Kreearra"),
    KRIL_TSUTSAROTH(Icons.KRIL_TSUTSAROTH,"boss","Kril Tsutsaroth"),
    NEX(Icons.NEX,"boss","Nex"),
    NIGHTMARE(Icons.NIGHTMARE,"boss","Nightmare"),
    SARACHNIS(Icons.SARACHNIS, "boss","Sarachnis"),
    SCORPIA(Icons.SCORPIA,"boss","Scorpia"),
    VENENATIS(Icons.VENENATIS,"boss","Venenatis"),
    VETION(Icons.VETION,"boss","Vet'ion"),
    ZALCANO(Icons.ZALCANO,"boss","Zalcano"),

    // minigame
    BARBARIAN_ASSAULT(Icons.BARBARIAN_ASSAULT,"minigame","Barbarian Assault"),
    BLAST_FURNACE(Icons.BLAST_FURNACE, "minigame","Blast Furnace"),
    BLAST_MINE(Icons.BLAST_MINE, "minigame","Blast Mine"),
    BRIMHAVEN_AGILITY_ARENA(Icons.BRIMHAVEN_AGILITY_ARENA, "minigame","Brimhaven Agility Arena"),
    BOUNTY_HUNTER_HUNTER(Icons.BOUNTY_HUNTER_HUNTER,"minigame","Bounty Hunter (Hunter)"),
    BOUNTY_HUNTER_ROGUE(Icons.BOUNTY_HUNTER_ROGUE,"minigame","Bounty Hunter (Rogue)"),
    CAMDOZAAL_VAULT(Icons.CAMDOZAAL_VAULT,"minigame","Camdozaal Vault"),
    CASTLE_WARS(Icons.CASTLE_WARS,"minigame","Castle Wars"),
    CLAN_WARS(Icons.CLAN_WARS, "minigame","Clan Wars"),
    CREATURE_CREATION(Icons.CREATURE_CREATION,"minigame","Creature Creation"),
    DUEL_ARENA(Icons.DUEL_ARENA,"minigame","Duel Arena"),
    FISHING_TRAWLER(Icons.FISHING_TRAWLER,"minigame","Fishing Trawler"),
    GNOME_BALL(Icons.GNOME_BALL,"minigame","Gnome Ball"),
    GNOME_RESTAURANT(Icons.GNOME_RESTAURANT,"minigame","Gnome Restaurant"),
    GUARDIANS_OF_THE_RIFT(Icons.GUARDIANS_OF_THE_RIFT,"minigame","Guardians of the Rift"),
    HALLOWED_SEPULCHRE(Icons.HALLOWED_SEPULCHRE, "minigame","Hallowed Sepulchre"),
    PURO_PURO(Icons.PURO_PURO,"minigame","Puro Puro"),
    MAGE_ARENA(Icons.MAGE_ARENA,"minigame","Mage Arena"),
    MAHOGANY_HOMES(Icons.MAHOGANY_HOMES,"minigame","Mahogany Homes"),
    MAGE_TRAINING_ARENA(Icons.MAGE_TRAINING_ARENA,"minigame","Mage Training Arena"),
    NIGHTMARE_ZONE(Icons.NIGHTMARE_ZONE,"minigame","Nightmare Zone"),
    ORGANIZED_CRIME(Icons.ORGANIZED_CRIME,"minigame","Organized Crime"),
    PEST_CONTROL(Icons.PEST_CONTROL, "minigame","Pest Control"),
    PYRAMID_PLUNDER(Icons.PYRAMID_PLUNDER,"minigame","Pyramid Plunder"),
    ROGUES_DEN(Icons.ROGUES_DEN,"minigame","Rogues Den"),
    SHADES_OF_MORTON(Icons.SHADES_OF_MORTON,"minigame","Shades of Morton"),
    SORCERESS_GARDEN(Icons.SORCERESS_GARDEN,"minigame","Sorceress's Garden"),
    TAI_BWO_WANNAI(Icons.TAI_BWO_WANNAI,"minigame","Tai Bwo Wannai"),
    TITHE_FARM(Icons.TITHE_FARM,"minigame","Tithe Farm"),
    TROUBLE_BREWING(Icons.TROUBLE_BREWING,"minigame","Trouble Brewing"),
    UNDERWATER_AGILITY_AND_THIEVING(Icons.UNDERWATER_AGILITY_AND_THIEVING, "minigame","Underwater Agility and Thieving"),
    VOLCANIC_MINE(Icons.VOLCANIC_MINE,"minigame","Volcanic Mine"),
    LAST_MAN_STANDING(Icons.LAST_MAN_STANDING,"minigame","Last Man Standing"),
    SOUL_WARS(Icons.SOUL_WARS,"minigame","Soul Wars"),
    TEMPOROSS(Icons.TEMPOROSS, "minigame","Tempoross"),
    WINTERTODT(Icons.WINTERTODT,"minigame","Wintertodt"),

    // raid
    COX(Icons.COX, "raid","Chambers of Xeric"),
    HARD_COX(Icons.HARD_COX,"raid","Chambers of Xeric Challenge Mode"),
    TOB(Icons.TOB,"raid","Theatre of Blood"),
    HARD_TOB(Icons.HARD_TOB,"raid","Theatre of Blood Hard Mode"),

    // misc
    CLUES(Icons.CLUES,"misc","Clues"),
    FALADOR_PARTY_ROOM(Icons.FALADOR_PARTY_ROOM,"misc","Falador Party Room"),
    PVP_GENERIC(Icons.PVP_GENERIC,"misc","PKing"),

    ;
    private final ImageIcon icon;
    private final String activity;
    private final String name;
}