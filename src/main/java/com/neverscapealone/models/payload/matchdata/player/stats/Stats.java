package com.neverscapealone.models.payload.matchdata.player.stats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neverscapealone.models.payload.matchdata.player.stats.agility.Agility;
import com.neverscapealone.models.payload.matchdata.player.stats.attack.Attack;
import com.neverscapealone.models.payload.matchdata.player.stats.construction.Construction;
import com.neverscapealone.models.payload.matchdata.player.stats.cooking.Cooking;
import com.neverscapealone.models.payload.matchdata.player.stats.crafting.Crafting;
import com.neverscapealone.models.payload.matchdata.player.stats.defence.Defence;
import com.neverscapealone.models.payload.matchdata.player.stats.farming.Farming;
import com.neverscapealone.models.payload.matchdata.player.stats.firemaking.Firemaking;
import com.neverscapealone.models.payload.matchdata.player.stats.fishing.Fishing;
import com.neverscapealone.models.payload.matchdata.player.stats.fletching.Fletching;
import com.neverscapealone.models.payload.matchdata.player.stats.herblore.Herblore;
import com.neverscapealone.models.payload.matchdata.player.stats.hitpoints.Hitpoints;
import com.neverscapealone.models.payload.matchdata.player.stats.hunter.Hunter;
import com.neverscapealone.models.payload.matchdata.player.stats.magic.Magic;
import com.neverscapealone.models.payload.matchdata.player.stats.mining.Mining;
import com.neverscapealone.models.payload.matchdata.player.stats.overall.Overall;
import com.neverscapealone.models.payload.matchdata.player.stats.prayer.Prayer;
import com.neverscapealone.models.payload.matchdata.player.stats.ranged.Ranged;
import com.neverscapealone.models.payload.matchdata.player.stats.runecraft.Runecraft;
import com.neverscapealone.models.payload.matchdata.player.stats.slayer.Slayer;
import com.neverscapealone.models.payload.matchdata.player.stats.smithing.Smithing;
import com.neverscapealone.models.payload.matchdata.player.stats.strength.Strength;
import com.neverscapealone.models.payload.matchdata.player.stats.thieving.Thieving;
import com.neverscapealone.models.payload.matchdata.player.stats.woodcutting.Woodcutting;

public class Stats {

    @SerializedName("Attack")
    @Expose
    private Attack attack;
    @SerializedName("Strength")
    @Expose
    private Strength strength;
    @SerializedName("Defence")
    @Expose
    private Defence defence;
    @SerializedName("Ranged")
    @Expose
    private Ranged ranged;
    @SerializedName("Prayer")
    @Expose
    private Prayer prayer;
    @SerializedName("Magic")
    @Expose
    private Magic magic;
    @SerializedName("Runecraft")
    @Expose
    private Runecraft runecraft;
    @SerializedName("Construction")
    @Expose
    private Construction construction;
    @SerializedName("Hitpoints")
    @Expose
    private Hitpoints hitpoints;
    @SerializedName("Agility")
    @Expose
    private Agility agility;
    @SerializedName("Herblore")
    @Expose
    private Herblore herblore;
    @SerializedName("Thieving")
    @Expose
    private Thieving thieving;
    @SerializedName("Crafting")
    @Expose
    private Crafting crafting;
    @SerializedName("Fletching")
    @Expose
    private Fletching fletching;
    @SerializedName("Slayer")
    @Expose
    private Slayer slayer;
    @SerializedName("Hunter")
    @Expose
    private Hunter hunter;
    @SerializedName("Mining")
    @Expose
    private Mining mining;
    @SerializedName("Smithing")
    @Expose
    private Smithing smithing;
    @SerializedName("Fishing")
    @Expose
    private Fishing fishing;
    @SerializedName("Cooking")
    @Expose
    private Cooking cooking;
    @SerializedName("Firemaking")
    @Expose
    private Firemaking firemaking;
    @SerializedName("Woodcutting")
    @Expose
    private Woodcutting woodcutting;
    @SerializedName("Farming")
    @Expose
    private Farming farming;
    @SerializedName("Overall")
    @Expose
    private Overall overall;

    public Attack getAttack() {
        return attack;
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
    }

    public Strength getStrength() {
        return strength;
    }

    public void setStrength(Strength strength) {
        this.strength = strength;
    }

    public Defence getDefence() {
        return defence;
    }

    public void setDefence(Defence defence) {
        this.defence = defence;
    }

    public Ranged getRanged() {
        return ranged;
    }

    public void setRanged(Ranged ranged) {
        this.ranged = ranged;
    }

    public Prayer getPrayer() {
        return prayer;
    }

    public void setPrayer(Prayer prayer) {
        this.prayer = prayer;
    }

    public Magic getMagic() {
        return magic;
    }

    public void setMagic(Magic magic) {
        this.magic = magic;
    }

    public Runecraft getRunecraft() {
        return runecraft;
    }

    public void setRunecraft(Runecraft runecraft) {
        this.runecraft = runecraft;
    }

    public Construction getConstruction() {
        return construction;
    }

    public void setConstruction(Construction construction) {
        this.construction = construction;
    }

    public Hitpoints getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(Hitpoints hitpoints) {
        this.hitpoints = hitpoints;
    }

    public Agility getAgility() {
        return agility;
    }

    public void setAgility(Agility agility) {
        this.agility = agility;
    }

    public Herblore getHerblore() {
        return herblore;
    }

    public void setHerblore(Herblore herblore) {
        this.herblore = herblore;
    }

    public Thieving getThieving() {
        return thieving;
    }

    public void setThieving(Thieving thieving) {
        this.thieving = thieving;
    }

    public Crafting getCrafting() {
        return crafting;
    }

    public void setCrafting(Crafting crafting) {
        this.crafting = crafting;
    }

    public Fletching getFletching() {
        return fletching;
    }

    public void setFletching(Fletching fletching) {
        this.fletching = fletching;
    }

    public Slayer getSlayer() {
        return slayer;
    }

    public void setSlayer(Slayer slayer) {
        this.slayer = slayer;
    }

    public Hunter getHunter() {
        return hunter;
    }

    public void setHunter(Hunter hunter) {
        this.hunter = hunter;
    }

    public Mining getMining() {
        return mining;
    }

    public void setMining(Mining mining) {
        this.mining = mining;
    }

    public Smithing getSmithing() {
        return smithing;
    }

    public void setSmithing(Smithing smithing) {
        this.smithing = smithing;
    }

    public Fishing getFishing() {
        return fishing;
    }

    public void setFishing(Fishing fishing) {
        this.fishing = fishing;
    }

    public Cooking getCooking() {
        return cooking;
    }

    public void setCooking(Cooking cooking) {
        this.cooking = cooking;
    }

    public Firemaking getFiremaking() {
        return firemaking;
    }

    public void setFiremaking(Firemaking firemaking) {
        this.firemaking = firemaking;
    }

    public Woodcutting getWoodcutting() {
        return woodcutting;
    }

    public void setWoodcutting(Woodcutting woodcutting) {
        this.woodcutting = woodcutting;
    }

    public Farming getFarming() {
        return farming;
    }

    public void setFarming(Farming farming) {
        this.farming = farming;
    }

    public Overall getOverall() {
        return overall;
    }

    public void setOverall(Overall overall) {
        this.overall = overall;
    }

}