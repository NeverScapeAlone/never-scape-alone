package com.neverscapealone.models.payload.matchdata.player.stats.construction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Construction {

    @SerializedName("boosted")
    @Expose
    private Integer boosted;
    @SerializedName("real")
    @Expose
    private Integer real;
    @SerializedName("experience")
    @Expose
    private Integer experience;

    public Integer getBoosted() {
        return boosted;
    }

    public void setBoosted(Integer boosted) {
        this.boosted = boosted;
    }

    public Integer getReal() {
        return real;
    }

    public void setReal(Integer real) {
        this.real = real;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

}