package com.alvarado.edwin.pruebaandroid.Modelo.POJO;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by edwinalvarado on 01/06/17.
 */

public class Category {

    @DatabaseField(id=true)
    private String CategoryId;

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
