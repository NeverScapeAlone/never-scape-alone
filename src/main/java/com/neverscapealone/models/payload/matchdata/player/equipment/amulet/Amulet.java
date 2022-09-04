package com.neverscapealone.models.payload.matchdata.player.equipment.amulet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Amulet {

    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("item_amount")
    @Expose
    private Integer itemAmount;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Integer itemAmount) {
        this.itemAmount = itemAmount;
    }

}