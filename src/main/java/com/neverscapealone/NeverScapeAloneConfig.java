package com.neverscapealone;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;
import com.neverscapealone.enums.ExperienceLevel;
import com.neverscapealone.enums.worldTypeSelection;

@ConfigGroup(NeverScapeAloneConfig.CONFIG_GROUP)
public interface NeverScapeAloneConfig extends Config
{
	String CONFIG_GROUP = "NeverScapeAlone";
	String AUTH_TOKEN_KEY = "authToken";
	String CONFIG_TRUE = "countConfigTrue";
	// SECTIONS
	@ConfigSection(
			position = 1,
			name = "Authentication",
			description = "Settings for user authentication."
	)
	String authSection = "authSection";

	@ConfigSection(
			position = 2,
			name = "Match Settings",
			description = "Select match up settings here."
	)
	String matchSection = "matchSection";

	@ConfigSection(
			position = 3,
			name = "Region Settings",
			description = "Select regions to search for partners here."
	)
	String regionSection = "regionSection";

	@ConfigSection(
			position = 4,
			name = "Other Settings",
			description = "Select misc. settings for the plugin."
	)
	String otherSection = "otherSection";


	/*
	Configuration items for each section
	 */

	@ConfigItem(
			position = 1,
			keyName = "discordUsername",
			name = "Discord Username",
			description = "Set your discord username here, this will allow you to be verified through the system.<br>You don't need to verify your discord,<br> however by doing so you will prevent scammers from using your ID.",
			section = authSection
	)
	default String discordUsername()
	{
		return "@UserName#1337";
	}

	@ConfigItem(
			position = 2,
			keyName = AUTH_TOKEN_KEY,
			name = "Authentication Token",
			description = "Your authentication token for the plugin. Length 32 characters - automatically generated if cleared and the plugin is restarted.",
			warning = "There are rare circumstances where you will need to change this field. If you are unsure about what you are doing, please click 'No'.",
			secret = true,
			section = authSection
	)
	default String authToken()
	{
		return "";
	}
	@ConfigItem(
			position = 1,
			keyName = "searchTime",
			name = "Maximum Search Time",
			description = "The maximum partner search time before the request is aborted.",
			section = matchSection
	)
	@Range(min = 1, max = 360)
	@Units(Units.MINUTES)
	default int searchTime()
	{
		return 15;
	}
	@ConfigItem(
			position = 2,
			keyName = "ignoreIgnores",
			name = "Ignore Ignores",
			description = "Ignore matchups with playes that are on your ignore list.",
			section = matchSection
	)
	default boolean ignoreIgnores()
	{
		return true;
	}
	@ConfigItem(
			position = 3,
			keyName = "prioritizeFriends",
			name = "Prioritize Friends",
			description = "Prioritize added friends when matching with other players.",
			section = matchSection
	)
	default boolean prioritizeFriends()
	{
		return true;
	}
	@ConfigItem(
			position = 4,
			keyName = "verifiedUsers",
			name = "Verified Users",
			description = "Allow strict matching with Verified users of the plugin.",
			section = matchSection
	)
	default boolean verifiedPartners()
	{
		return false;
	}
	@ConfigItem(
			position = 5,
			keyName = "worldTypeSelection",
			name = "World Type",
			description = "Select if you would like to match on free-to-play, members or both",
			section = matchSection
	)
	default worldTypeSelection worldTypeSelection()
	{
		return worldTypeSelection.BOTH;
	}
	@ConfigItem(
			position = 1,
			keyName = "usEast",
			name = "US East",
			description = "Allow for US East matches.",
			section = regionSection
	)
	default boolean usEast()
	{
		return true;
	}
	@ConfigItem(
			position = 2,
			keyName = "usWest",
			name = "US West",
			description = "Allow for US West matches.",
			section = regionSection
	)
	default boolean usWest()
	{
		return true;
	}
	@ConfigItem(
			position = 3,
			keyName = "euWest",
			name = "EU West",
			description = "Allow for West Europe matches.",
			section = regionSection
	)
	default boolean euWest()
	{
		return true;
	}
	@ConfigItem(
			position = 4,
			keyName = "euCentral",
			name = "EU Central",
			description = "Allow for Central Europe matches.",
			section = regionSection
	)
	default boolean euCentral()
	{
		return true;
	}
	@ConfigItem(
			position = 5,
			keyName = "oceania",
			name = "Oceania",
			description = "Allow for Oceania matches.",
			section = regionSection
	)
	default boolean oceania()
	{
		return true;
	}
	@ConfigItem(
			position = 1,
			keyName = "playFireworks",
			name = "Play Fireworks",
			description = "When a queue has been finished, play fireworks on the player.",
			section = otherSection
	)
	default boolean playFireworks()
	{
		return true;
	}
	@ConfigItem(
			position = 2,
			keyName = "playSound",
			name = "Play Sound",
			description = "When a queue has been finished, play a sound byte.",
			section = otherSection
	)
	default boolean playSound()
	{
		return true;
	}

	@ConfigItem(
			keyName = "countConfigTrue",
			name = "True Configs",
			description = "Number of Config Items which are True",
			hidden = true
	)
	default int countConfigTrue() {return 0;}

	//HIDDEN CONFIGS
	//ITEMS PRESENT AFTER THIS POINT RELATE TO THE PLUGIN PANEL ACTIVITY OPTIONS

	@ConfigItem(
			keyName = "config_attack",
			name = "ATTACK",
			description = "Config item for attack.",
			hidden = true
	)
	default boolean config_attack() {return false;}

	@ConfigItem(
			keyName = "config_strength",
			name = "STRENGTH",
			description = "Config item for strength.",
			hidden = true
	)
	default boolean config_strength() {return false;}

	@ConfigItem(
			keyName = "config_defence",
			name = "DEFENCE",
			description = "Config item for defence.",
			hidden = true
	)
	default boolean config_defence() {return false;}

	@ConfigItem(
			keyName = "config_hitpoints",
			name = "HITPOINTS",
			description = "Config item for hitpoints.",
			hidden = true
	)
	default boolean config_hitpoints() {return false;}

	@ConfigItem(
			keyName = "config_ranged",
			name = "RANGED",
			description = "Config item for ranged.",
			hidden = true
	)
	default boolean config_ranged() {return false;}

	@ConfigItem(
			keyName = "config_prayer",
			name = "PRAYER",
			description = "Config item for prayer.",
			hidden = true
	)
	default boolean config_prayer() {return false;}

	@ConfigItem(
			keyName = "config_magic",
			name = "MAGIC",
			description = "Config item for magic.",
			hidden = true
	)
	default boolean config_magic() {return false;}

	@ConfigItem(
			keyName = "config_cooking",
			name = "COOKING",
			description = "Config item for cooking.",
			hidden = true
	)
	default boolean config_cooking() {return false;}

	@ConfigItem(
			keyName = "config_woodcutting",
			name = "WOODCUTTING",
			description = "Config item for woodcutting.",
			hidden = true
	)
	default boolean config_woodcutting() {return false;}

	@ConfigItem(
			keyName = "config_fletching",
			name = "FLETCHING",
			description = "Config item for fletching.",
			hidden = true
	)
	default boolean config_fletching() {return false;}

	@ConfigItem(
			keyName = "config_fishing",
			name = "FISHING",
			description = "Config item for fishing.",
			hidden = true
	)
	default boolean config_fishing() {return false;}

	@ConfigItem(
			keyName = "config_firemaking",
			name = "FIREMAKING",
			description = "Config item for firemaking.",
			hidden = true
	)
	default boolean config_firemaking() {return false;}

	@ConfigItem(
			keyName = "config_crafting",
			name = "CRAFTING",
			description = "Config item for crafting.",
			hidden = true
	)
	default boolean config_crafting() {return false;}

	@ConfigItem(
			keyName = "config_smithing",
			name = "SMITHING",
			description = "Config item for smithing.",
			hidden = true
	)
	default boolean config_smithing() {return false;}

	@ConfigItem(
			keyName = "config_mining",
			name = "MINING",
			description = "Config item for mining.",
			hidden = true
	)
	default boolean config_mining() {return false;}

	@ConfigItem(
			keyName = "config_herblore",
			name = "HERBLORE",
			description = "Config item for herblore.",
			hidden = true
	)
	default boolean config_herblore() {return false;}

	@ConfigItem(
			keyName = "config_agility",
			name = "AGILITY",
			description = "Config item for agility.",
			hidden = true
	)
	default boolean config_agility() {return false;}

	@ConfigItem(
			keyName = "config_thieving",
			name = "THIEVING",
			description = "Config item for thieving.",
			hidden = true
	)
	default boolean config_thieving() {return false;}

	@ConfigItem(
			keyName = "config_slayer",
			name = "SLAYER",
			description = "Config item for slayer.",
			hidden = true
	)
	default boolean config_slayer() {return false;}

	@ConfigItem(
			keyName = "config_farming",
			name = "FARMING",
			description = "Config item for farming.",
			hidden = true
	)
	default boolean config_farming() {return false;}

	@ConfigItem(
			keyName = "config_runecraft",
			name = "RUNECRAFT",
			description = "Config item for runecraft.",
			hidden = true
	)
	default boolean config_runecraft() {return false;}

	@ConfigItem(
			keyName = "config_hunter",
			name = "HUNTER",
			description = "Config item for hunter.",
			hidden = true
	)
	default boolean config_hunter() {return false;}

	@ConfigItem(
			keyName = "config_construction",
			name = "CONSTRUCTION",
			description = "Config item for construction.",
			hidden = true
	)
	default boolean config_construction() {return false;}

	@ConfigItem(
			keyName = "config_all_skills",
			name = "ALL_SKILLS",
			description = "Config item for all_skills.",
			hidden = true
	)
	default boolean config_all_skills() {return false;}

	@ConfigItem(
			keyName = "config_abyssal_sire",
			name = "ABYSSAL_SIRE",
			description = "Config item for abyssal_sire.",
			hidden = true
	)
	default boolean config_abyssal_sire() {return false;}

	@ConfigItem(
			keyName = "config_alchemical_hydra",
			name = "ALCHEMICAL_HYDRA",
			description = "Config item for alchemical_hydra.",
			hidden = true
	)
	default boolean config_alchemical_hydra() {return false;}

	@ConfigItem(
			keyName = "config_bryophyta",
			name = "BRYOPHYTA",
			description = "Config item for bryophyta.",
			hidden = true
	)
	default boolean config_bryophyta() {return false;}

	@ConfigItem(
			keyName = "config_cerberus",
			name = "CERBERUS",
			description = "Config item for cerberus.",
			hidden = true
	)
	default boolean config_cerberus() {return false;}

	@ConfigItem(
			keyName = "config_grotesque_guardians",
			name = "GROTESQUE_GUARDIANS",
			description = "Config item for grotesque_guardians.",
			hidden = true
	)
	default boolean config_grotesque_guardians() {return false;}

	@ConfigItem(
			keyName = "config_hespori",
			name = "HESPORI",
			description = "Config item for hespori.",
			hidden = true
	)
	default boolean config_hespori() {return false;}

	@ConfigItem(
			keyName = "config_kraken",
			name = "KRAKEN",
			description = "Config item for kraken.",
			hidden = true
	)
	default boolean config_kraken() {return false;}

	@ConfigItem(
			keyName = "config_mimic",
			name = "MIMIC",
			description = "Config item for mimic.",
			hidden = true
	)
	default boolean config_mimic() {return false;}

	@ConfigItem(
			keyName = "config_obor",
			name = "OBOR",
			description = "Config item for obor.",
			hidden = true
	)
	default boolean config_obor() {return false;}

	@ConfigItem(
			keyName = "config_phosanis_nightmare",
			name = "PHOSANIS_NIGHTMARE",
			description = "Config item for phosanis_nightmare.",
			hidden = true
	)
	default boolean config_phosanis_nightmare() {return false;}

	@ConfigItem(
			keyName = "config_skotizo",
			name = "SKOTIZO",
			description = "Config item for skotizo.",
			hidden = true
	)
	default boolean config_skotizo() {return false;}

	@ConfigItem(
			keyName = "config_gauntlet",
			name = "GAUNTLET",
			description = "Config item for gauntlet.",
			hidden = true
	)
	default boolean config_gauntlet() {return false;}

	@ConfigItem(
			keyName = "config_gauntlet_corrupted",
			name = "GAUNTLET_CORRUPTED",
			description = "Config item for gauntlet_corrupted.",
			hidden = true
	)
	default boolean config_gauntlet_corrupted() {return false;}

	@ConfigItem(
			keyName = "config_thermonuclearsmokedevil",
			name = "THERMONUCLEARSMOKEDEVIL",
			description = "Config item for thermonuclearsmokedevil.",
			hidden = true
	)
	default boolean config_thermonuclearsmokedevil() {return false;}

	@ConfigItem(
			keyName = "config_tz_kal_zuk",
			name = "TZ_KAL_ZUK",
			description = "Config item for tz_kal_zuk.",
			hidden = true
	)
	default boolean config_tz_kal_zuk() {return false;}

	@ConfigItem(
			keyName = "config_tz_tok_jad",
			name = "TZ_TOK_JAD",
			description = "Config item for tz_tok_jad.",
			hidden = true
	)
	default boolean config_tz_tok_jad() {return false;}

	@ConfigItem(
			keyName = "config_vorkath",
			name = "VORKATH",
			description = "Config item for vorkath.",
			hidden = true
	)
	default boolean config_vorkath() {return false;}

	@ConfigItem(
			keyName = "config_zulrah",
			name = "ZULRAH",
			description = "Config item for zulrah.",
			hidden = true
	)
	default boolean config_zulrah() {return false;}

	@ConfigItem(
			keyName = "config_barrows",
			name = "BARROWS",
			description = "Config item for barrows.",
			hidden = true
	)
	default boolean config_barrows() {return false;}

	@ConfigItem(
			keyName = "config_callisto",
			name = "CALLISTO",
			description = "Config item for callisto.",
			hidden = true
	)
	default boolean config_callisto() {return false;}

	@ConfigItem(
			keyName = "config_chaos_elemental",
			name = "CHAOS_ELEMENTAL",
			description = "Config item for chaos_elemental.",
			hidden = true
	)
	default boolean config_chaos_elemental() {return false;}

	@ConfigItem(
			keyName = "config_chaos_fanatic",
			name = "CHAOS_FANATIC",
			description = "Config item for chaos_fanatic.",
			hidden = true
	)
	default boolean config_chaos_fanatic() {return false;}

	@ConfigItem(
			keyName = "config_commander_zilyana",
			name = "COMMANDER_ZILYANA",
			description = "Config item for commander_zilyana.",
			hidden = true
	)
	default boolean config_commander_zilyana() {return false;}

	@ConfigItem(
			keyName = "config_corporeal_beast",
			name = "CORPOREAL_BEAST",
			description = "Config item for corporeal_beast.",
			hidden = true
	)
	default boolean config_corporeal_beast() {return false;}

	@ConfigItem(
			keyName = "config_archaeologist_crazy",
			name = "ARCHAEOLOGIST_CRAZY",
			description = "Config item for archaeologist_crazy.",
			hidden = true
	)
	default boolean config_archaeologist_crazy() {return false;}

	@ConfigItem(
			keyName = "config_archaeologist_deranged",
			name = "ARCHAEOLOGIST_DERANGED",
			description = "Config item for archaeologist_deranged.",
			hidden = true
	)
	default boolean config_archaeologist_deranged() {return false;}

	@ConfigItem(
			keyName = "config_dagannoth_prime",
			name = "DAGANNOTH_PRIME",
			description = "Config item for dagannoth_prime.",
			hidden = true
	)
	default boolean config_dagannoth_prime() {return false;}

	@ConfigItem(
			keyName = "config_dagannoth_rex",
			name = "DAGANNOTH_REX",
			description = "Config item for dagannoth_rex.",
			hidden = true
	)
	default boolean config_dagannoth_rex() {return false;}

	@ConfigItem(
			keyName = "config_dagannoth_supreme",
			name = "DAGANNOTH_SUPREME",
			description = "Config item for dagannoth_supreme.",
			hidden = true
	)
	default boolean config_dagannoth_supreme() {return false;}

	@ConfigItem(
			keyName = "config_general_graardor",
			name = "GENERAL_GRAARDOR",
			description = "Config item for general_graardor.",
			hidden = true
	)
	default boolean config_general_graardor() {return false;}

	@ConfigItem(
			keyName = "config_giant_mole",
			name = "GIANT_MOLE",
			description = "Config item for giant_mole.",
			hidden = true
	)
	default boolean config_giant_mole() {return false;}

	@ConfigItem(
			keyName = "config_kalphite_queen",
			name = "KALPHITE_QUEEN",
			description = "Config item for kalphite_queen.",
			hidden = true
	)
	default boolean config_kalphite_queen() {return false;}

	@ConfigItem(
			keyName = "config_king_black_dragon",
			name = "KING_BLACK_DRAGON",
			description = "Config item for king_black_dragon.",
			hidden = true
	)
	default boolean config_king_black_dragon() {return false;}

	@ConfigItem(
			keyName = "config_kreearra",
			name = "KREEARRA",
			description = "Config item for kreearra.",
			hidden = true
	)
	default boolean config_kreearra() {return false;}

	@ConfigItem(
			keyName = "config_kril_tsutsaroth",
			name = "KRIL_TSUTSAROTH",
			description = "Config item for kril_tsutsaroth.",
			hidden = true
	)
	default boolean config_kril_tsutsaroth() {return false;}

	@ConfigItem(
			keyName = "config_nex",
			name = "NEX",
			description = "Config item for nex.",
			hidden = true
	)
	default boolean config_nex() {return false;}

	@ConfigItem(
			keyName = "config_nightmare",
			name = "NIGHTMARE",
			description = "Config item for nightmare.",
			hidden = true
	)
	default boolean config_nightmare() {return false;}

	@ConfigItem(
			keyName = "config_sarachnis",
			name = "SARACHNIS",
			description = "Config item for sarachnis.",
			hidden = true
	)
	default boolean config_sarachnis() {return false;}

	@ConfigItem(
			keyName = "config_scorpia",
			name = "SCORPIA",
			description = "Config item for scorpia.",
			hidden = true
	)
	default boolean config_scorpia() {return false;}

	@ConfigItem(
			keyName = "config_venenatis",
			name = "VENENATIS",
			description = "Config item for venenatis.",
			hidden = true
	)
	default boolean config_venenatis() {return false;}

	@ConfigItem(
			keyName = "config_vetion",
			name = "VETION",
			description = "Config item for vetion.",
			hidden = true
	)
	default boolean config_vetion() {return false;}

	@ConfigItem(
			keyName = "config_zalcano",
			name = "ZALCANO",
			description = "Config item for zalcano.",
			hidden = true
	)
	default boolean config_zalcano() {return false;}

	@ConfigItem(
			keyName = "config_barbarian_assault",
			name = "BARBARIAN_ASSAULT",
			description = "Config item for barbarian_assault.",
			hidden = true
	)
	default boolean config_barbarian_assault() {return false;}

	@ConfigItem(
			keyName = "config_blast_furnace",
			name = "BLAST_FURNACE",
			description = "Config item for blast_furnace.",
			hidden = true
	)
	default boolean config_blast_furnace() {return false;}

	@ConfigItem(
			keyName = "config_blast_mine",
			name = "BLAST_MINE",
			description = "Config item for blast_mine.",
			hidden = true
	)
	default boolean config_blast_mine() {return false;}

	@ConfigItem(
			keyName = "config_brimhaven_agility_arena",
			name = "BRIMHAVEN_AGILITY_ARENA",
			description = "Config item for brimhaven_agility_arena.",
			hidden = true
	)
	default boolean config_brimhaven_agility_arena() {return false;}

	@ConfigItem(
			keyName = "config_bounty_hunter_hunter",
			name = "BOUNTY_HUNTER_HUNTER",
			description = "Config item for bounty_hunter_hunter.",
			hidden = true
	)
	default boolean config_bounty_hunter_hunter() {return false;}

	@ConfigItem(
			keyName = "config_bounty_hunter_rogue",
			name = "BOUNTY_HUNTER_ROGUE",
			description = "Config item for bounty_hunter_rogue.",
			hidden = true
	)
	default boolean config_bounty_hunter_rogue() {return false;}

	@ConfigItem(
			keyName = "config_camdozaal_vault",
			name = "CAMDOZAAL_VAULT",
			description = "Config item for camdozaal_vault.",
			hidden = true
	)
	default boolean config_camdozaal_vault() {return false;}

	@ConfigItem(
			keyName = "config_castle_wars",
			name = "CASTLE_WARS",
			description = "Config item for castle_wars.",
			hidden = true
	)
	default boolean config_castle_wars() {return false;}

	@ConfigItem(
			keyName = "config_clan_wars",
			name = "CLAN_WARS",
			description = "Config item for clan_wars.",
			hidden = true
	)
	default boolean config_clan_wars() {return false;}

	@ConfigItem(
			keyName = "config_creature_creation",
			name = "CREATURE_CREATION",
			description = "Config item for creature_creation.",
			hidden = true
	)
	default boolean config_creature_creation() {return false;}

	@ConfigItem(
			keyName = "config_duel_arena",
			name = "DUEL_ARENA",
			description = "Config item for duel_arena.",
			hidden = true
	)
	default boolean config_duel_arena() {return false;}

	@ConfigItem(
			keyName = "config_fishing_trawler",
			name = "FISHING_TRAWLER",
			description = "Config item for fishing_trawler.",
			hidden = true
	)
	default boolean config_fishing_trawler() {return false;}

	@ConfigItem(
			keyName = "config_gnome_ball",
			name = "GNOME_BALL",
			description = "Config item for gnome_ball.",
			hidden = true
	)
	default boolean config_gnome_ball() {return false;}

	@ConfigItem(
			keyName = "config_gnome_restaurant",
			name = "GNOME_RESTAURANT",
			description = "Config item for gnome_restaurant.",
			hidden = true
	)
	default boolean config_gnome_restaurant() {return false;}

	@ConfigItem(
			keyName = "config_guardians_of_the_rift",
			name = "GUARDIANS_OF_THE_RIFT",
			description = "Config item for guardians_of_the_rift.",
			hidden = true
	)
	default boolean config_guardians_of_the_rift() {return false;}

	@ConfigItem(
			keyName = "config_hallowed_sepulchre",
			name = "HALLOWED_SEPULCHRE",
			description = "Config item for hallowed_sepulchre.",
			hidden = true
	)
	default boolean config_hallowed_sepulchre() {return false;}

	@ConfigItem(
			keyName = "config_puro_puro",
			name = "PURO_PURO",
			description = "Config item for puro_puro.",
			hidden = true
	)
	default boolean config_puro_puro() {return false;}

	@ConfigItem(
			keyName = "config_mage_arena",
			name = "MAGE_ARENA",
			description = "Config item for mage_arena.",
			hidden = true
	)
	default boolean config_mage_arena() {return false;}

	@ConfigItem(
			keyName = "config_mahogany_homes",
			name = "MAHOGANY_HOMES",
			description = "Config item for mahogany_homes.",
			hidden = true
	)
	default boolean config_mahogany_homes() {return false;}

	@ConfigItem(
			keyName = "config_mage_training_arena",
			name = "MAGE_TRAINING_ARENA",
			description = "Config item for mage_training_arena.",
			hidden = true
	)
	default boolean config_mage_training_arena() {return false;}

	@ConfigItem(
			keyName = "config_nightmare_zone",
			name = "NIGHTMARE_ZONE",
			description = "Config item for nightmare_zone.",
			hidden = true
	)
	default boolean config_nightmare_zone() {return false;}

	@ConfigItem(
			keyName = "config_organized_crime",
			name = "ORGANIZED_CRIME",
			description = "Config item for organized_crime.",
			hidden = true
	)
	default boolean config_organized_crime() {return false;}

	@ConfigItem(
			keyName = "config_pest_control",
			name = "PEST_CONTROL",
			description = "Config item for pest_control.",
			hidden = true
	)
	default boolean config_pest_control() {return false;}

	@ConfigItem(
			keyName = "config_pyramid_plunder",
			name = "PYRAMID_PLUNDER",
			description = "Config item for pyramid_plunder.",
			hidden = true
	)
	default boolean config_pyramid_plunder() {return false;}

	@ConfigItem(
			keyName = "config_rogues_den",
			name = "ROGUES_DEN",
			description = "Config item for rogues_den.",
			hidden = true
	)
	default boolean config_rogues_den() {return false;}

	@ConfigItem(
			keyName = "config_shades_of_morton",
			name = "SHADES_OF_MORTON",
			description = "Config item for shades_of_morton.",
			hidden = true
	)
	default boolean config_shades_of_morton() {return false;}

	@ConfigItem(
			keyName = "config_sorceress_garden",
			name = "SORCERESS_GARDEN",
			description = "Config item for sorceress_garden.",
			hidden = true
	)
	default boolean config_sorceress_garden() {return false;}

	@ConfigItem(
			keyName = "config_tai_bwo_wannai",
			name = "TAI_BWO_WANNAI",
			description = "Config item for tai_bwo_wannai.",
			hidden = true
	)
	default boolean config_tai_bwo_wannai() {return false;}

	@ConfigItem(
			keyName = "config_tithe_farm",
			name = "TITHE_FARM",
			description = "Config item for tithe_farm.",
			hidden = true
	)
	default boolean config_tithe_farm() {return false;}

	@ConfigItem(
			keyName = "config_trouble_brewing",
			name = "TROUBLE_BREWING",
			description = "Config item for trouble_brewing.",
			hidden = true
	)
	default boolean config_trouble_brewing() {return false;}

	@ConfigItem(
			keyName = "config_underwater_agility_and_thieving",
			name = "UNDERWATER_AGILITY_AND_THIEVING",
			description = "Config item for underwater_agility_and_thieving.",
			hidden = true
	)
	default boolean config_underwater_agility_and_thieving() {return false;}

	@ConfigItem(
			keyName = "config_volcanic_mine",
			name = "VOLCANIC_MINE",
			description = "Config item for volcanic_mine.",
			hidden = true
	)
	default boolean config_volcanic_mine() {return false;}

	@ConfigItem(
			keyName = "config_last_man_standing",
			name = "LAST_MAN_STANDING",
			description = "Config item for last_man_standing.",
			hidden = true
	)
	default boolean config_last_man_standing() {return false;}

	@ConfigItem(
			keyName = "config_soul_wars",
			name = "SOUL_WARS",
			description = "Config item for soul_wars.",
			hidden = true
	)
	default boolean config_soul_wars() {return false;}

	@ConfigItem(
			keyName = "config_tempoross",
			name = "TEMPOROSS",
			description = "Config item for tempoross.",
			hidden = true
	)
	default boolean config_tempoross() {return false;}

	@ConfigItem(
			keyName = "config_wintertodt",
			name = "WINTERTODT",
			description = "Config item for wintertodt.",
			hidden = true
	)
	default boolean config_wintertodt() {return false;}

	@ConfigItem(
			keyName = "config_cox",
			name = "COX",
			description = "Config item for cox.",
			hidden = true
	)
	default boolean config_cox() {return false;}

	@ConfigItem(
			keyName = "config_hard_cox",
			name = "HARD_COX",
			description = "Config item for hard_cox.",
			hidden = true
	)
	default boolean config_hard_cox() {return false;}

	@ConfigItem(
			keyName = "config_tob",
			name = "TOB",
			description = "Config item for tob.",
			hidden = true
	)
	default boolean config_tob() {return false;}

	@ConfigItem(
			keyName = "config_hard_tob",
			name = "HARD_TOB",
			description = "Config item for hard_tob.",
			hidden = true
	)
	default boolean config_hard_tob() {return false;}

	@ConfigItem(
			keyName = "config_clues",
			name = "CLUES",
			description = "Config item for clues.",
			hidden = true
	)
	default boolean config_clues() {return false;}

	@ConfigItem(
			keyName = "config_falador_party_room",
			name = "FALADOR_PARTY_ROOM",
			description = "Config item for falador_party_room.",
			hidden = true
	)
	default boolean config_falador_party_room() {return false;}

	@ConfigItem(
			keyName = "config_pvp_generic",
			name = "PVP_GENERIC",
			description = "Config item for pvp_generic.",
			hidden = true
	)
	default boolean config_pvp_generic() {return false;}


}

