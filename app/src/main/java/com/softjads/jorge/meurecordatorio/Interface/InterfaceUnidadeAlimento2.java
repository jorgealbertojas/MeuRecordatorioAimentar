package com.softjads.jorge.meurecordatorio.Interface;

import com.softjads.jorge.meurecordatorio.Model.ListWrapper;
import com.softjads.jorge.meurecordatorio.Model.UnidadeAlimento;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by jorge on 30/04/2018.
 */

public interface InterfaceUnidadeAlimento2 {
    @Headers({
            "Cache-Control: cache",
            "Ocp-Apim-Trace: true",
            "Ocp-Apim-Subscription-Key: 5330411f96544e78b4c44df85f44f5e4"
    })
   // @GET("/questions")
    @GET("/unidadeunidade2")
    Call<ListWrapper<UnidadeAlimento>> getUniadeAliemnto() ;
}
