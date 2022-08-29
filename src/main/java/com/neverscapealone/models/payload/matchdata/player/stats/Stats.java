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

package com.neverscapealone.models.payload.matchdata.player.stats;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {

    @SerializedName("attack")
    @Expose
    private Integer attack;
    @SerializedName("strength")
    @Expose
    private Integer strength;
    @SerializedName("defense")
    @Expose
    private Integer defense;
    @SerializedName("ranged")
    @Expose
    private Integer ranged;
    @SerializedName("prayer")
    @Expose
    private Integer prayer;
    @SerializedName("magic")
    @Expose
    private Integer magic;
    @SerializedName("runecraft")
    @Expose
    private Integer runecraft;
    @SerializedName("construction")
    @Expose
    private Integer construction;
    @SerializedName("hitpoints")
    @Expose
    private Integer hitpoints;
    @SerializedName("agility")
    @Expose
    private Integer agility;
    @SerializedName("herblore")
    @Expose
    private Integer herblore;
    @SerializedName("thieving")
    @Expose
    private Integer thieving;
    @SerializedName("crafting")
    @Expose
    private Integer crafting;
    @SerializedName("fletching")
    @Expose
    private Integer fletching;
    @SerializedName("slayer")
    @Expose
    private Integer slayer;
    @SerializedName("hunter")
    @Expose
    private Integer hunter;
    @SerializedName("mining")
    @Expose
    private Integer mining;
    @SerializedName("smithing")
    @Expose
    private Integer smithing;
    @SerializedName("fishing")
    @Expose
    private Integer fishing;
    @SerializedName("cooking")
    @Expose
    private Integer cooking;
    @SerializedName("firemaking")
    @Expose
    private Integer firemaking;
    @SerializedName("woodcutting")
    @Expose
    private Integer woodcutting;
    @SerializedName("farming")
    @Expose
    private Integer farming;

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Stats withAttack(Integer attack) {
        this.attack = attack;
        return this;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Stats withStrength(Integer strength) {
        this.strength = strength;
        return this;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Stats withDefense(Integer defense) {
        this.defense = defense;
        return this;
    }

    public Integer getRanged() {
        return ranged;
    }

    public void setRanged(Integer ranged) {
        this.ranged = ranged;
    }

    public Stats withRanged(Integer ranged) {
        this.ranged = ranged;
        return this;
    }

    public Integer getPrayer() {
        return prayer;
    }

    public void setPrayer(Integer prayer) {
        this.prayer = prayer;
    }

    public Stats withPrayer(Integer prayer) {
        this.prayer = prayer;
        return this;
    }

    public Integer getMagic() {
        return magic;
    }

    public void setMagic(Integer magic) {
        this.magic = magic;
    }

    public Stats withMagic(Integer magic) {
        this.magic = magic;
        return this;
    }

    public Integer getRunecraft() {
        return runecraft;
    }

    public void setRunecraft(Integer runecraft) {
        this.runecraft = runecraft;
    }

    public Stats withRunecraft(Integer runecraft) {
        this.runecraft = runecraft;
        return this;
    }

    public Integer getConstruction() {
        return construction;
    }

    public void setConstruction(Integer construction) {
        this.construction = construction;
    }

    public Stats withConstruction(Integer construction) {
        this.construction = construction;
        return this;
    }

    public Integer getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(Integer hitpoints) {
        this.hitpoints = hitpoints;
    }

    public Stats withHitpoints(Integer hitpoints) {
        this.hitpoints = hitpoints;
        return this;
    }

    public Integer getAgility() {
        return agility;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    public Stats withAgility(Integer agility) {
        this.agility = agility;
        return this;
    }

    public Integer getHerblore() {
        return herblore;
    }

    public void setHerblore(Integer herblore) {
        this.herblore = herblore;
    }

    public Stats withHerblore(Integer herblore) {
        this.herblore = herblore;
        return this;
    }

    public Integer getThieving() {
        return thieving;
    }

    public void setThieving(Integer thieving) {
        this.thieving = thieving;
    }

    public Stats withThieving(Integer thieving) {
        this.thieving = thieving;
        return this;
    }

    public Integer getCrafting() {
        return crafting;
    }

    public void setCrafting(Integer crafting) {
        this.crafting = crafting;
    }

    public Stats withCrafting(Integer crafting) {
        this.crafting = crafting;
        return this;
    }

    public Integer getFletching() {
        return fletching;
    }

    public void setFletching(Integer fletching) {
        this.fletching = fletching;
    }

    public Stats withFletching(Integer fletching) {
        this.fletching = fletching;
        return this;
    }

    public Integer getSlayer() {
        return slayer;
    }

    public void setSlayer(Integer slayer) {
        this.slayer = slayer;
    }

    public Stats withSlayer(Integer slayer) {
        this.slayer = slayer;
        return this;
    }

    public Integer getHunter() {
        return hunter;
    }

    public void setHunter(Integer hunter) {
        this.hunter = hunter;
    }

    public Stats withHunter(Integer hunter) {
        this.hunter = hunter;
        return this;
    }

    public Integer getMining() {
        return mining;
    }

    public void setMining(Integer mining) {
        this.mining = mining;
    }

    public Stats withMining(Integer mining) {
        this.mining = mining;
        return this;
    }

    public Integer getSmithing() {
        return smithing;
    }

    public void setSmithing(Integer smithing) {
        this.smithing = smithing;
    }

    public Stats withSmithing(Integer smithing) {
        this.smithing = smithing;
        return this;
    }

    public Integer getFishing() {
        return fishing;
    }

    public void setFishing(Integer fishing) {
        this.fishing = fishing;
    }

    public Stats withFishing(Integer fishing) {
        this.fishing = fishing;
        return this;
    }

    public Integer getCooking() {
        return cooking;
    }

    public void setCooking(Integer cooking) {
        this.cooking = cooking;
    }

    public Stats withCooking(Integer cooking) {
        this.cooking = cooking;
        return this;
    }

    public Integer getFiremaking() {
        return firemaking;
    }

    public void setFiremaking(Integer firemaking) {
        this.firemaking = firemaking;
    }

    public Stats withFiremaking(Integer firemaking) {
        this.firemaking = firemaking;
        return this;
    }

    public Integer getWoodcutting() {
        return woodcutting;
    }

    public void setWoodcutting(Integer woodcutting) {
        this.woodcutting = woodcutting;
    }

    public Stats withWoodcutting(Integer woodcutting) {
        this.woodcutting = woodcutting;
        return this;
    }

    public Integer getFarming() {
        return farming;
    }

    public void setFarming(Integer farming) {
        this.farming = farming;
    }

    public Stats withFarming(Integer farming) {
        this.farming = farming;
        return this;
    }

}