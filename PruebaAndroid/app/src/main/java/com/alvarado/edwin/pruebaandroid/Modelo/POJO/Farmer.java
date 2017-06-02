package com.alvarado.edwin.pruebaandroid.Modelo.POJO;

/**
 * Created by edwinalvarado on 31/05/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Farmer {

    @SerializedName("farmer_id")
    @Expose
    @DatabaseField(id = true)
    private Integer farmerId;

    @SerializedName("zipcode")
    @Expose
    @DatabaseField
    private String zipcode;

    @SerializedName("location_1")
    @Expose
    @DatabaseField(canBeNull = false, foreign = true)
    private Location1 location1;

    @SerializedName("item")
    @Expose
    private String item;

    @SerializedName("website")
    @Expose
    private Website website;

    @SerializedName("business")
    @Expose
    @DatabaseField
    private String business;

    @SerializedName("category")
    @Expose

    private String category;

    @SerializedName("l")
    @Expose
    private String l;

    @SerializedName("farm_name")
    @Expose
    @DatabaseField
    private String farmName;

    @SerializedName("phone1")
    @Expose
    @DatabaseField(columnName = "phone")
    private String phone1;

    @DatabaseField(columnName = "url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Location1 getLocation1() {
        return location1;
    }

    public void setLocation1(Location1 location1) {
        this.location1 = location1;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Website getWebsite() {
        return website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

}