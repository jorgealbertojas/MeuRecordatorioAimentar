package com.example.jorge.meurecordatorio.Interface;

import com.example.jorge.meurecordatorio.Model.ListWrapper;
import com.example.jorge.meurecordatorio.Model.UnidadeAlimento;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by jorge on 30/04/2018.
 */

public interface



InterfaceUnidadeAlimento3 {
    @Headers({
            "Cache-Control: cache",
            "Ocp-Apim-Trace: true",
            "Ocp-Apim-Subscription-Key: 5330411f96544e78b4c44df85f44f5e4"
    })
   // @GET("/questions")
    @GET("/unidadeunidade3")
    Call<ListWrapper<UnidadeAlimento>> getUniadeAliemnto() ;
}
