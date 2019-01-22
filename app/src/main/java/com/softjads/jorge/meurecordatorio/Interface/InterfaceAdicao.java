package com.softjads.jorge.meurecordatorio.Interface;

import com.softjads.jorge.meurecordatorio.Model.Adicao;
import com.softjads.jorge.meurecordatorio.Model.ListWrapper;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jorge on 30/04/2018.
 */

public interface InterfaceAdicao {
    @GET("/questions")
    Call<ListWrapper<Adicao>> getAdicao() ;
}
