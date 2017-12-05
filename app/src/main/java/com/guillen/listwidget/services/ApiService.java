package com.guillen.listwidget.services;

import com.guillen.listwidget.models.Tienda;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by guillen on 04/12/17.
 */

public interface ApiService {

    String API_BASE_URL = "https://gamarra-rest-krobawsky.c9users.io/";

    // Lista de agentes
    @GET("api/v1/tiendas")
    Call<List<Tienda>> getTiendas();

}
