package com.neverscapealone.ui;

import com.neverscapealone.NeverScapeAlonePlugin;
import javax.swing.ImageIcon;
import net.runelite.client.util.ImageUtil;

import java.util.Objects;

public class Icons
{
    private static final Class<?> PLUGIN_CLASS = NeverScapeAlonePlugin.class;

    // web links and references

    public static final ImageIcon ACCEPT_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/Checkmark.png"));
    public static final ImageIcon CANCEL_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/Xmark.png"));
    public static final ImageIcon UNDO_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/undo.png"));
    public static final ImageIcon RESET_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/reset.png"));
    public static final ImageIcon GITHUB_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/github.png"));
    public static final ImageIcon DISCORD_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/discord.png"));
    public static final ImageIcon WEB_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/web.png"));
    public static final ImageIcon PATREON_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/patreon.png"));
    public static final ImageIcon TWITTER_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/twitter.png"));
    public static final ImageIcon ETH_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/eth.png"));
    public static final ImageIcon BTC_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/btc.png"));
    public static final ImageIcon PAYPAL_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/paypal.png"));
    public static final ImageIcon NSA_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/NeverScapeAloneVersion.png"));
    public static final ImageIcon BUG_REPORT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bug_report.png"));
    public static final ImageIcon RUNEWATCH_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/runewatch.png"));
    public static final ImageIcon WDR_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/wdr_symbol.png"));

    // region flags

    public static final ImageIcon US_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/us.png"));
    public static final ImageIcon OCEANIA_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/oceania.png"));
    public static final ImageIcon EU_WEST_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/eu_west.png"));
    public static final ImageIcon EU_CENTRAL_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/eu_central.png"));
    public static final ImageIcon PLAYERS_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/players.png"));
    public static final ImageIcon GENERIC_PLAYER_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/generic_player.png"));

    // status icons
    public static final ImageIcon WARNING_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/warning.png"));
    public static final ImageIcon ERROR_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/error.png"));
    public static final ImageIcon SPINNER_GIF = new ImageIcon("/loading_spinner_darker.gif");
    public static final ImageIcon WAITING_ICON = new ImageIcon("/waiting.png");

    // "skill", "boss", "minigame", "misc", "solo", "raids"
    // SKILLS
    public static final ImageIcon ATTACK = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/attack.png"));
    public static final ImageIcon DEFENCE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/defence.png"));
    public static final ImageIcon STRENGTH = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/strength.png"));
    public static final ImageIcon HITPOINTS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/hitpoints.png"));
    public static final ImageIcon RANGED = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/ranged.png"));
    public static final ImageIcon PRAYER = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/prayer.png"));
    public static final ImageIcon MAGIC = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/magic.png"));
    public static final ImageIcon COOKING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/cooking.png"));
    public static final ImageIcon WOODCUTTING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/woodcutting.png"));
    public static final ImageIcon FLETCHING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/fletching.png"));
    public static final ImageIcon FISHING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/fishing.png"));
    public static final ImageIcon FIREMAKING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/firemaking.png"));
    public static final ImageIcon CRAFTING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/crafting.png"));
    public static final ImageIcon SMITHING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/smithing.png"));
    public static final ImageIcon MINING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/mining.png"));
    public static final ImageIcon HERBLORE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/herblore.png"));
    public static final ImageIcon AGILITY = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/agility.png"));
    public static final ImageIcon THIEVING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/thieving.png"));
    public static final ImageIcon SLAYER = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/slayer.png"));
    public static final ImageIcon FARMING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/farming.png"));
    public static final ImageIcon RUNECRAFTING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/runecrafting.png"));
    public static final ImageIcon HUNTER = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/hunter.png"));
    public static final ImageIcon CONSTRUCTION = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/construction.png"));
    public static final ImageIcon ALL_SKILLS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS,"/all_skills.png"));

    // SOLO
    public static final ImageIcon ABYSSAL_SIRE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/abyssalsire.png"));
    public static final ImageIcon ALCHEMICAL_HYDRA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/alchemicalhydra.png"));
    public static final ImageIcon BRYOPHYTA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bryophyta.png"));
    public static final ImageIcon CERBERUS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/cerberus.png"));
    public static final ImageIcon GROTESQUE_GUARDIANS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/grotesqueguardians.png"));
    public static final ImageIcon HESPORI = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/hespori.png"));
    public static final ImageIcon KRAKEN = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/kraken.png"));
    public static final ImageIcon MIMIC = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/mimic.png"));
    public static final ImageIcon OBOR = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/obor.png"));
    public static final ImageIcon PHOSANIS_NIGHTMARE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/phosanisnightmare.png"));
    public static final ImageIcon SKOTIZO = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skotizo.png"));
    public static final ImageIcon GAUNTLET = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/thegauntlet.png"));
    public static final ImageIcon GAUNTLET_CORRUPTED = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/thecorruptedgauntlet.png"));
    public static final ImageIcon THERMONUCLEARSMOKEDEVIL = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/thermonuclearsmokedevil.png"));
    public static final ImageIcon TZ_KAL_ZUK = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/tzkalzuk.png"));
    public static final ImageIcon TZ_TOK_JAD = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/tztokjad.png"));
    public static final ImageIcon VORKATH = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/vorkath.png"));
    public static final ImageIcon ZULRAH = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/zulrah.png"));

    // BOSS
    public static final ImageIcon BARROWS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/barrowschests.png"));
    public static final ImageIcon CALLISTO = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/callisto.png"));
    public static final ImageIcon CHAOS_ELEMENTAL = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/chaoselemental.png"));
    public static final ImageIcon CHAOS_FANATIC = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/chaosfanatic.png"));
    public static final ImageIcon COMMANDER_ZILYANA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/commanderzilyana.png"));
    public static final ImageIcon CORPOREAL_BEAST = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/corporealbeast.png"));
    public static final ImageIcon ARCHAEOLOGIST_CRAZY = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/crazyarchaeologist.png"));
    public static final ImageIcon ARCHAEOLOGIST_DERANGED = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/derangedarchaeologist.png"));
    public static final ImageIcon DAGANNOTH_PRIME = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/dagannothprime.png"));
    public static final ImageIcon DAGANNOTH_REX = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/dagannothrex.png"));
    public static final ImageIcon DAGANNOTH_SUPREME = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/dagannothsupreme.png"));
    public static final ImageIcon GENERAL_GRAARDOR = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/generalgraardor.png"));
    public static final ImageIcon GIANT_MOLE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/giantmole.png"));
    public static final ImageIcon KALPHITE_QUEEN = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/kalphitequeen.png"));
    public static final ImageIcon KING_BLACK_DRAGON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/kingblackdragon.png"));
    public static final ImageIcon KREEARRA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/kreearra.png"));
    public static final ImageIcon KRIL_TSUTSAROTH = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/kriltsutsaroth.png"));
    public static final ImageIcon NEX = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/nex.png"));
    public static final ImageIcon NIGHTMARE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/nightmare.png"));
    public static final ImageIcon SARACHNIS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/sarachnis.png"));
    public static final ImageIcon SCORPIA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/scorpia.png"));
    public static final ImageIcon VENENATIS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/venenatis.png"));
    public static final ImageIcon VETION = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/vetion.png"));
    public static final ImageIcon ZALCANO = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/zalcano.png"));

    // MINIGAME
    public static final ImageIcon BARBARIAN_ASSAULT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/barbarian_assault.png"));
    public static final ImageIcon BLAST_FURNACE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/blast_furnace.png"));
    public static final ImageIcon BLAST_MINE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/blast_mine.png"));
    public static final ImageIcon BRIMHAVEN_AGILITY_ARENA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/brimhaven_agility_arena.png"));
    public static final ImageIcon BOUNTY_HUNTER_HUNTER = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bountyhunterhunter.png"));
    public static final ImageIcon BOUNTY_HUNTER_ROGUE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bountyhunterrogue.png"));
    public static final ImageIcon CAMDOZAAL_VAULT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/camdozaal_vault.png"));
    public static final ImageIcon CASTLE_WARS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/castle_wars.png"));
    public static final ImageIcon CLAN_WARS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/clan_wars.png"));
    public static final ImageIcon CREATURE_CREATION = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/creature_creation.png"));
    public static final ImageIcon DUEL_ARENA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/duel_arena.png"));
    public static final ImageIcon FISHING_TRAWLER = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/fishing_trawler.png"));
    public static final ImageIcon GNOME_BALL = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/gnome_ball.png"));
    public static final ImageIcon GNOME_RESTAURANT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/gnome_restaurant.png"));
    public static final ImageIcon GUARDIANS_OF_THE_RIFT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/gotr.png"));
    public static final ImageIcon HALLOWED_SEPULCHRE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/hallowed_sepulchre.png"));
    public static final ImageIcon PURO_PURO = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/puro_puro.png"));
    public static final ImageIcon MAGE_ARENA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/ma.png"));
    public static final ImageIcon MAHOGANY_HOMES = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/mahogany_homes.png"));
    public static final ImageIcon MAGE_TRAINING_ARENA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/mta.png"));
    public static final ImageIcon NIGHTMARE_ZONE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/nmz.png"));
    public static final ImageIcon ORGANIZED_CRIME = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/organized_crime.png"));
    public static final ImageIcon PEST_CONTROL = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/pest_control.png"));
    public static final ImageIcon PYRAMID_PLUNDER = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/pyramid_plunder.png"));
    public static final ImageIcon ROGUES_DEN = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/rogues_den.png"));
    public static final ImageIcon SHADES_OF_MORTON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/shades_of_morton.png"));
    public static final ImageIcon SORCERESS_GARDEN = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/sorceress_garden.png"));
    public static final ImageIcon TAI_BWO_WANNAI = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/tai_bwo_wannai.png"));
    public static final ImageIcon TITHE_FARM = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/tithe_farm.png"));
    public static final ImageIcon TROUBLE_BREWING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/trouble_brewing.png"));
    public static final ImageIcon UNDERWATER_AGILITY_AND_THIEVING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/underwater_agility.png"));
    public static final ImageIcon VOLCANIC_MINE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/volcanic_mine.png"));
    public static final ImageIcon LAST_MAN_STANDING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/lms.png"));
    public static final ImageIcon SOUL_WARS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/soulwars.png"));
    public static final ImageIcon TEMPOROSS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/tempoross.png"));
    public static final ImageIcon WINTERTODT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/wintertodt.png"));

    // RAIDS
    public static final ImageIcon COX = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/chambersofxeric.png"));
    public static final ImageIcon HARD_COX = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/chambersofxericchallengemode.png"));
    public static final ImageIcon TOB = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/theatreofblood.png"));
    public static final ImageIcon HARD_TOB = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/theatreofbloodhardmode.png"));

    // MISC
    public static final ImageIcon CLUES = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/cluescrollsall.png"));
    public static final ImageIcon FALADOR_PARTY_ROOM = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/faladorpartyroom.png"));
    public static final ImageIcon PVP_GENERIC = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/wilderness.png"));
}