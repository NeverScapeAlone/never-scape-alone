package com.neverscapealone.enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SoundPing {
    @SerializedName("sound")
    @Expose
    private SoundPingEnum sound = null;

    public SoundPingEnum getSound() {
        return sound;
    }

    public SoundPing buildSound(SoundPingEnum sound) {
        this.sound = sound;
        return this;
    }

}