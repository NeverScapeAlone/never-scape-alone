package com.neverscapealone.enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Status {

    @SerializedName("hp")
    @Expose
    private Integer hp;
    @SerializedName("prayer")
    @Expose
    private Integer prayer;
    @SerializedName("run_energy")
    @Expose
    private Integer runEnergy;
    @SerializedName("special_attack")
    @Expose
    private Integer specialAttack;
    @SerializedName("overhead_prayer")
    @Expose
    private String overheadPrayer;

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

    public Integer getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(Integer specialAttack) {
        this.specialAttack = specialAttack;
    }

    public Status withSpecialAttack(Integer specialAttack) {
        this.specialAttack = specialAttack;
        return this;
    }

    public String getOverheadPrayer() {
        return overheadPrayer;
    }

    public void setOverheadPrayer(String overheadPrayer) {
        this.overheadPrayer = overheadPrayer;
    }

    public Status withOverheadPrayer(String overheadPrayer) {
        this.overheadPrayer = overheadPrayer;
        return this;
    }

}