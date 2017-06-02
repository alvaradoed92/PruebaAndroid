package com.alvarado.edwin.pruebaandroid.Modelo.Servicio;


import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Farmer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by edwinalvarado on 31/05/17.
 */

public interface ApiService {
    @GET("/resource/hma6-9xbg.json?")
    Call<List<Farmer>> getData(@Query("category") String categoryName, @Query("item") String itemName);

    @GET("/resource/hma6-9xbg.json")
    Call<List<Farmer>> getData();



}
