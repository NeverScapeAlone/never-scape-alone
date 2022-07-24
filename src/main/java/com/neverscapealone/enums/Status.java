package com.neverscapealone.enums;

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