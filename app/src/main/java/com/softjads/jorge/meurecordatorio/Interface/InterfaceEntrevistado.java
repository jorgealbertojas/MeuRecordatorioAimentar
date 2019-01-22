package com.softjads.jorge.meurecordatorio.Interface;

import com.softjads.jorge.meurecordatorio.Model.Entrevistado;
import com.softjads.jorge.meurecordatorio.Model.ListWrapper;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jorge on 30/04/2018.
 */

public interface InterfaceEntrevistado {
    @GET("/questions")
    Call<ListWrapper<Entrevistado>> getentrevistado() ;
}
