package com.example.myhoroscopedaily.data;

import com.example.myhoroscopedaily.models.AstrologyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Service {
    @GET("sunsigns")
    Call<List> getSunsigns();

    @GET
    Call<AstrologyModel> getAstrology(@Url String url);
}
