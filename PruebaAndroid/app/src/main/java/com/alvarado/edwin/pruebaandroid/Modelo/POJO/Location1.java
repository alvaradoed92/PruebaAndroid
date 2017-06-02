package com.alvarado.edwin.pruebaandroid.Modelo.POJO;

/**
 * Created by edwinalvarado on 31/05/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Location1 {

    @DatabaseField(generatedId = true)
    private Integer id;
    @SerializedName("latitude")
    @Expose
    @DatabaseField
    private String latitude;
    @SerializedName("human_address")
    @Expose
    private String humanAddress;
    @SerializedName("needs_recoding")
    @Expose
    private Boolean needsRecoding;
    @SerializedName("longitude")
    @Expose
    @DatabaseField
    private String longitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getHumanAddress() {
        return humanAddress;
    }

    public void setHumanAddress(String humanAddress) {
        this.humanAddress = humanAddress;
    }

    public Boolean getNeedsRecoding() {
        return needsRecoding;
    }

    public void setNeedsRecoding(Boolean needsRecoding) {
        this.needsRecoding = needsRecoding;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}