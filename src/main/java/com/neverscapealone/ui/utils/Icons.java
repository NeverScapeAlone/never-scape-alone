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

package com.neverscapealone.ui.utils;

import com.neverscapealone.NeverScapeAlonePlugin;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;

public class Icons {
    private static final Class<?> PLUGIN_CLASS = NeverScapeAlonePlugin.class;

    // web links and references
    public static final ImageIcon COORDINATE_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/coordinate.png"));
    public static final ImageIcon WORLD_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/world.png"));
    public static final ImageIcon NOTES_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/notes.png"));
    public static final ImageIcon SAFETY_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/safety.png"));
    public static final ImageIcon PROFILE_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/profile.png"));
    public static final ImageIcon HELP_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/help.png"));
    public static final ImageIcon ACCEPT_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/Checkmark.png"));
    public static final ImageIcon MINIMIZED_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/minimized.png"));
    public static final ImageIcon MAXIMIZED_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/maximized.png"));
    public static final ImageIcon LOGOUT_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/logout.png"));
    public static final ImageIcon CANCEL_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/Xmark.png"));
    public static final ImageIcon UNDO_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/undo.png"));
    public static final ImageIcon RESET_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/reset.png"));
    public static final ImageIcon GITHUB_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/github.png"));
    public static final ImageIcon DISCORD_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/discord.png"));
    public static final ImageIcon WEB_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/web.png"));
    public static final ImageIcon PATREON_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/patreon.png"));
    public static final ImageIcon TWITTER_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/twitter.png"));
    public static final ImageIcon ETH_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/eth.png"));
    public static final ImageIcon BTC_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/btc.png"));
    public static final ImageIcon PAYPAL_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/paypal.png"));
    public static final ImageIcon UNVERIFIED_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/unverified.png"));
    public static final ImageIcon VERIFIED_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/generic_player.png"));
    public static final ImageIcon NSA_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/NeverScapeAloneVersion.png"));
    public static final ImageIcon KICK_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/kick.png"));
    public static final ImageIcon RATING_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/rating.png"));
    public static final ImageIcon LIKE_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/like.png"));
    public static final ImageIcon CROWN_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/crown.png"));
    public static final ImageIcon DISLIKE_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/dislike.png"));

    public static final ImageIcon BUG_REPORT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/bug_report.png"));
    public static final ImageIcon RUNEWATCH_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/runewatch.png"));
    public static final ImageIcon WDR_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/wdr_symbol.png"));

    // account flags
    public static final ImageIcon IM_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/IM.png"));
    public static final ImageIcon HCIM_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/HCIM.png"));
    public static final ImageIcon UIM_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/UIM.png"));
    public static final ImageIcon GIM_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/GIM.png"));
    public static final ImageIcon HCGIM_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/HCGIM.png"));
    public static final ImageIcon UGIM_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/UGIM.png"));


    // region flags

    public static final ImageIcon US_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/us.png"));
    public static final ImageIcon OCEANIA_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/oceania.png"));
    public static final ImageIcon EU_WEST_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/eu_west.png"));
    public static final ImageIcon EU_CENTRAL_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/eu_central.png"));
    public static final ImageIcon PLAYERS_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/players.png"));

    // status icons
    public static final ImageIcon WARNING_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/warning.png"));
    public static final ImageIcon ERROR_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/error.png"));
    public static final ImageIcon WAITING_ICON = new ImageIcon("/other/waiting.png");

    // other

    public static final ImageIcon FILTER_FULL_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/filter_full.png"));
    public static final ImageIcon FILTER_EMPTY_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/filter_empty.png"));
    public static final ImageIcon PRIVATE_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/private.png"));
    public static final ImageIcon PUBLIC_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/public.png"));
    public static final ImageIcon EXPERIENCE_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/experience.png"));
    public static final ImageIcon LIGHTBULB_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/lightbulb.png"));
    public static final ImageIcon LOOTBAG_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/lootbag.png"));
    public static final ImageIcon YELLOW_PARTYHAT_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/yellow_partyhat.png"));

    // "skill", "boss", "minigame", "misc", "solo", "raids"
    public static final ImageIcon RANDOM = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/other/random.png"));
    // SKILLS
    public static final ImageIcon ATTACK = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/attack.png"));
    public static final ImageIcon DEFENCE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/defence.png"));
    public static final ImageIcon STRENGTH = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/strength.png"));
    public static final ImageIcon HITPOINTS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/hitpoints.png"));
    public static final ImageIcon RANGED = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/ranged.png"));
    public static final ImageIcon PRAYER = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/prayer.png"));
    public static final ImageIcon MAGIC = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/magic.png"));
    public static final ImageIcon COOKING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/cooking.png"));
    public static final ImageIcon WOODCUTTING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/woodcutting.png"));
    public static final ImageIcon FLETCHING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/fletching.png"));
    public static final ImageIcon FISHING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/fishing.png"));
    public static final ImageIcon FIREMAKING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/firemaking.png"));
    public static final ImageIcon CRAFTING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/crafting.png"));
    public static final ImageIcon SMITHING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/smithing.png"));
    public static final ImageIcon MINING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/mining.png"));
    public static final ImageIcon HERBLORE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/herblore.png"));
    public static final ImageIcon AGILITY = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/agility.png"));
    public static final ImageIcon THIEVING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/thieving.png"));
    public static final ImageIcon SLAYER = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/slayer.png"));
    public static final ImageIcon FARMING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/farming.png"));
    public static final ImageIcon RUNECRAFTING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/runecrafting.png"));
    public static final ImageIcon HUNTER = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/hunter.png"));
    public static final ImageIcon CONSTRUCTION = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/construction.png"));
    public static final ImageIcon ALL_SKILLS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/skills/all_skills.png"));

    // SOLO
    public static final ImageIcon ABYSSAL_SIRE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/abyssalsire.png"));
    public static final ImageIcon ALCHEMICAL_HYDRA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/alchemicalhydra.png"));
    public static final ImageIcon BRYOPHYTA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/bryophyta.png"));
    public static final ImageIcon CERBERUS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/cerberus.png"));
    public static final ImageIcon GROTESQUE_GUARDIANS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/grotesqueguardians.png"));
    public static final ImageIcon HESPORI = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/hespori.png"));
    public static final ImageIcon KRAKEN = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/kraken.png"));
    public static final ImageIcon MIMIC = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/mimic.png"));
    public static final ImageIcon OBOR = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/obor.png"));
    public static final ImageIcon PHOSANIS_NIGHTMARE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/phosanisnightmare.png"));
    public static final ImageIcon SKOTIZO = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/skotizo.png"));
    public static final ImageIcon GAUNTLET = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/thegauntlet.png"));
    public static final ImageIcon GAUNTLET_CORRUPTED = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/thecorruptedgauntlet.png"));
    public static final ImageIcon THERMONUCLEARSMOKEDEVIL = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/thermonuclearsmokedevil.png"));
    public static final ImageIcon TZ_KAL_ZUK = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/tzkalzuk.png"));
    public static final ImageIcon TZ_TOK_JAD = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/tztokjad.png"));
    public static final ImageIcon VORKATH = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/vorkath.png"));
    public static final ImageIcon ZULRAH = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/zulrah.png"));

    // BOSS
    public static final ImageIcon BARROWS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/barrowschests.png"));
    public static final ImageIcon CALLISTO = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/callisto.png"));
    public static final ImageIcon CHAOS_ELEMENTAL = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/chaoselemental.png"));
    public static final ImageIcon CHAOS_FANATIC = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/chaosfanatic.png"));
    public static final ImageIcon COMMANDER_ZILYANA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/commanderzilyana.png"));
    public static final ImageIcon CORPOREAL_BEAST = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/corporealbeast.png"));
    public static final ImageIcon ARCHAEOLOGIST_CRAZY = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/crazyarchaeologist.png"));
    public static final ImageIcon ARCHAEOLOGIST_DERANGED = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/derangedarchaeologist.png"));
    public static final ImageIcon DAGANNOTH_PRIME = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/dagannothprime.png"));
    public static final ImageIcon DAGANNOTH_REX = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/dagannothrex.png"));
    public static final ImageIcon DAGANNOTH_SUPREME = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/dagannothsupreme.png"));
    public static final ImageIcon GENERAL_GRAARDOR = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/generalgraardor.png"));
    public static final ImageIcon GIANT_MOLE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/giantmole.png"));
    public static final ImageIcon KALPHITE_QUEEN = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/kalphitequeen.png"));
    public static final ImageIcon KING_BLACK_DRAGON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/kingblackdragon.png"));
    public static final ImageIcon KREEARRA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/kreearra.png"));
    public static final ImageIcon KRIL_TSUTSAROTH = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/kriltsutsaroth.png"));
    public static final ImageIcon NEX = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/nex.png"));
    public static final ImageIcon NIGHTMARE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/nightmare.png"));
    public static final ImageIcon SARACHNIS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/sarachnis.png"));
    public static final ImageIcon SCORPIA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/scorpia.png"));
    public static final ImageIcon VENENATIS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/venenatis.png"));
    public static final ImageIcon VETION = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/vetion.png"));
    public static final ImageIcon ZALCANO = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/bosses/zalcano.png"));

    // MINIGAME
    public static final ImageIcon BARBARIAN_ASSAULT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/barbarian_assault.png"));
    public static final ImageIcon BLAST_FURNACE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/blast_furnace.png"));
    public static final ImageIcon BLAST_MINE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/blast_mine.png"));
    public static final ImageIcon BRIMHAVEN_AGILITY_ARENA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/brimhaven_agility_arena.png"));
    public static final ImageIcon BOUNTY_HUNTER = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/bountyhunterhunter.png"));
    public static final ImageIcon CAMDOZAAL_VAULT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/camdozaal_vault.png"));
    public static final ImageIcon CASTLE_WARS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/castle_wars.png"));
    public static final ImageIcon CLAN_WARS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/clan_wars.png"));
    public static final ImageIcon CREATURE_CREATION = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/creature_creation.png"));
    public static final ImageIcon PVP_ARENA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/duel_arena.png"));
    public static final ImageIcon FISHING_TRAWLER = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/fishing_trawler.png"));
    public static final ImageIcon GIANTS_FOUNDRY = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/giants_foundry.png"));
    public static final ImageIcon GNOME_BALL = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/gnome_ball.png"));
    public static final ImageIcon GNOME_RESTAURANT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/gnome_restaurant.png"));
    public static final ImageIcon GUARDIANS_OF_THE_RIFT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/gotr.png"));
    public static final ImageIcon HALLOWED_SEPULCHRE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/hallowed_sepulchre.png"));
    public static final ImageIcon HERBIBOAR = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/herbiboar.png"));
    public static final ImageIcon PURO_PURO = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/puro_puro.png"));
    public static final ImageIcon MAGE_ARENA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/ma.png"));
    public static final ImageIcon MAHOGANY_HOMES = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/mahogany_homes.png"));
    public static final ImageIcon MAGE_TRAINING_ARENA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/mta.png"));
    public static final ImageIcon MOTHERLODE_MINE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/motherlode_mine.png"));
    public static final ImageIcon NIGHTMARE_ZONE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/nmz.png"));
    public static final ImageIcon ORGANIZED_CRIME = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/organized_crime.png"));
    public static final ImageIcon PEST_CONTROL = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/pest_control.png"));
    public static final ImageIcon PYRAMID_PLUNDER = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/pyramid_plunder.png"));
    public static final ImageIcon ROGUES_DEN = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/rogues_den.png"));
    public static final ImageIcon SHADES_OF_MORTON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/shades_of_morton.png"));
    public static final ImageIcon SORCERESS_GARDEN = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/sorceress_garden.png"));
    public static final ImageIcon TAI_BWO_WANNAI = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/tai_bwo_wannai.png"));
    public static final ImageIcon TITHE_FARM = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/tithe_farm.png"));
    public static final ImageIcon TROUBLE_BREWING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/trouble_brewing.png"));
    public static final ImageIcon UNDERWATER_AGILITY_AND_THIEVING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/underwater_agility.png"));
    public static final ImageIcon VOLCANIC_MINE = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/volcanic_mine.png"));
    public static final ImageIcon LAST_MAN_STANDING = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/lms.png"));
    public static final ImageIcon SOUL_WARS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/soulwars.png"));
    public static final ImageIcon TEMPOROSS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/tempoross.png"));
    public static final ImageIcon WINTERTODT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/minigames/wintertodt.png"));

    // RAIDS
    public static final ImageIcon COX = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/raids/chambersofxeric.png"));
    public static final ImageIcon HARD_COX = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/raids/chambersofxericchallengemode.png"));
    public static final ImageIcon ENTRY_TOB = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/raids/theatreofbloodeasy.png"));
    public static final ImageIcon TOB = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/raids/theatreofblood.png"));
    public static final ImageIcon HARD_TOB = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/raids/theatreofbloodhardmode.png"));
    public static final ImageIcon ENTRY_TOA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/raids/toa_entry.png"));
    public static final ImageIcon NORMAL_TOA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/raids/toa_normal.png"));
    public static final ImageIcon EXPERT_TOA = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/raids/toa_expert.png"));

    // MISC
    public static final ImageIcon CLUES = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/cluescrollsall.png"));
    public static final ImageIcon FALADOR_PARTY_ROOM = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/faladorpartyroom.png"));
    public static final ImageIcon PVP_GENERIC = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/wilderness.png"));
    public static final ImageIcon CHAT = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/chat.png"));
    public static final ImageIcon QUEST = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/quest.png"));
    public static final ImageIcon DIARY = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/diary.png"));
    public static final ImageIcon FAVOR = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/favor.png"));
    public static final ImageIcon CA1 = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/ca1.png"));
    public static final ImageIcon CA2 = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/ca2.png"));
    public static final ImageIcon CA3 = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/ca3.png"));
    public static final ImageIcon CA4 = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/ca4.png"));
    public static final ImageIcon CA5 = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/ca5.png"));
    public static final ImageIcon CA6 = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/ca6.png"));
    public static final ImageIcon ALL_COMBAT_ACHIEVEMENTS = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/combat_achievements.png"));
    public static final ImageIcon COIN = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/coin.png"));
    public static final ImageIcon F2P = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/misc/f2p.png"));

}