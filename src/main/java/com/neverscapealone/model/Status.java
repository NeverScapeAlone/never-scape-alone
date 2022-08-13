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

package com.neverscapealone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("hp")
    @Expose
    private Integer hp;
    @SerializedName("base_hp")
    @Expose
    private Integer baseHp;
    @SerializedName("prayer")
    @Expose
    private Integer prayer;
    @SerializedName("base_prayer")
    @Expose
    private Integer basePrayer;
    @SerializedName("run_energy")
    @Expose
    private Integer runEnergy;

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Status withHp(Integer hp) {
        this.hp = hp;
        return this;
    }

    public Integer getBaseHp() {
        return baseHp;
    }

    public void setBaseHp(Integer baseHp) {
        this.baseHp = baseHp;
    }

    public Status withBaseHp(Integer baseHp) {
        this.baseHp = baseHp;
        return this;
    }

    public Integer getPrayer() {
        return prayer;
    }

    public void setPrayer(Integer prayer) {
        this.prayer = prayer;
    }

    public Status withPrayer(Integer prayer) {
        this.prayer = prayer;
        return this;
    }

    public Integer getBasePrayer() {
        return basePrayer;
    }

    public void setBasePrayer(Integer basePrayer) {
        this.basePrayer = basePrayer;
    }

    public Status withBasePrayer(Integer basePrayer) {
        this.basePrayer = basePrayer;
        return this;
    }

    public Integer getRunEnergy() {
        return runEnergy;
    }

    public void setRunEnergy(Integer runEnergy) {
        this.runEnergy = runEnergy;
    }

    public Status withRunEnergy(Integer runEnergy) {
        this.runEnergy = runEnergy;
        return this;
    }

}