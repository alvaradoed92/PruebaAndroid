package com.alvarado.edwin.pruebaandroid.Modelo.POJO;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by edwinalvarado on 01/06/17.
 */

public class Item {

    @DatabaseField(id=true)
    private String ItemId;

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }
}
