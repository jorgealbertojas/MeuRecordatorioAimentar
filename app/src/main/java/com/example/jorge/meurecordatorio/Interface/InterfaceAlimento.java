package com.example.jorge.meurecordatorio.Interface;

import com.example.jorge.meurecordatorio.Model.Alimento;
import com.example.jorge.meurecordatorio.Model.ListWrapper;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jorge on 30/04/2018.
 */

public interface InterfaceAlimento {
    @GET("/questions")
    Call<ListWrapper<Alimento>> getAlimento();
}
