package com.alvarado.edwin.pruebaandroid.Modelo.POJO;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by edwinalvarado on 01/06/17.
 */

public class LinkItem {
    @DatabaseField(canBeNull = false,foreign = true)
    private Farmer Farmer;
    @DatabaseField(canBeNull = false,foreign = true)
    private Category Category;
    @DatabaseField(canBeNull = false,foreign = true)
    private Item Item;

    public Farmer getFarmerId() {
        return Farmer;
    }

    public void setFarmerId(Farmer farmerId) {
        Farmer = farmerId;
    }

    public Category getCategoryId() {
        return Category;
    }

    public void setCategory ( Category categoryId) {
        Category = categoryId;
    }

    public Item getItemId() {
        return Item;
    }

    public void setItemId(Item itemId) {
        Item = itemId;
    }

}
