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

package com.neverscapealone.models.payload.matchdata.player.equipment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neverscapealone.models.payload.matchdata.player.equipment.ammo.Ammo;
import com.neverscapealone.models.payload.matchdata.player.equipment.amulet.Amulet;
import com.neverscapealone.models.payload.matchdata.player.equipment.body.Body;
import com.neverscapealone.models.payload.matchdata.player.equipment.boots.Boots;
import com.neverscapealone.models.payload.matchdata.player.equipment.cape.Cape;
import com.neverscapealone.models.payload.matchdata.player.equipment.gloves.Gloves;
import com.neverscapealone.models.payload.matchdata.player.equipment.head.Head;
import com.neverscapealone.models.payload.matchdata.player.equipment.legs.Legs;
import com.neverscapealone.models.payload.matchdata.player.equipment.ring.Ring;
import com.neverscapealone.models.payload.matchdata.player.equipment.shield.Shield;
import com.neverscapealone.models.payload.matchdata.player.equipment.weapon.Weapon;

public class Equipment {

    @SerializedName("head")
    @Expose
    private Head head;
    @SerializedName("cape")
    @Expose
    private Cape cape;
    @SerializedName("amulet")
    @Expose
    private Amulet amulet;
    @SerializedName("ammo")
    @Expose
    private Ammo ammo;
    @SerializedName("weapon")
    @Expose
    private Weapon weapon;
    @SerializedName("body")
    @Expose
    private Body body;
    @SerializedName("shield")
    @Expose
    private Shield shield;
    @SerializedName("legs")
    @Expose
    private Legs legs;
    @SerializedName("gloves")
    @Expose
    private Gloves gloves;
    @SerializedName("boots")
    @Expose
    private Boots boots;
    @SerializedName("ring")
    @Expose
    private Ring ring;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Cape getCape() {
        return cape;
    }

    public void setCape(Cape cape) {
        this.cape = cape;
    }

    public Amulet getAmulet() {
        return amulet;
    }

    public void setAmulet(Amulet amulet) {
        this.amulet = amulet;
    }

    public Ammo getAmmo() {
        return ammo;
    }

    public void setAmmo(Ammo ammo) {
        this.ammo = ammo;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Shield getShield() {
        return shield;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
    }

    public Legs getLegs() {
        return legs;
    }

    public void setLegs(Legs legs) {
        this.legs = legs;
    }

    public Gloves getGloves() {
        return gloves;
    }

    public void setGloves(Gloves gloves) {
        this.gloves = gloves;
    }

    public Boots getBoots() {
        return boots;
    }

    public void setBoots(Boots boots) {
        this.boots = boots;
    }

    public Ring getRing() {
        return ring;
    }

    public void setRing(Ring ring) {
        this.ring = ring;
    }

}