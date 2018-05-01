package com.example.jorge.meurecordatorio.Interface;

import com.example.jorge.meurecordatorio.Model.ListWrapper;
import com.example.jorge.meurecordatorio.Model.PreparacaoAlimento;
import com.example.jorge.meurecordatorio.Model.UnidadeAlimento;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jorge on 30/04/2018.
 */

public interface InterfaceUnidadeAlimento {
    @GET("/questions")
    Call<ListWrapper<UnidadeAlimento>> getUniadeAliemnto() ;
}
