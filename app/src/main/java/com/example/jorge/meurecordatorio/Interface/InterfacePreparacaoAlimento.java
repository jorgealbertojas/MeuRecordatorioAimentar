package com.example.jorge.meurecordatorio.Interface;

import com.example.jorge.meurecordatorio.Model.ListWrapper;
import com.example.jorge.meurecordatorio.Model.PreparacaoAlimento;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by jorge on 30/04/2018.
 */

public interface InterfacePreparacaoAlimento {
    @Headers({
            "Cache-Control: cache",
            "Ocp-Apim-Trace: true",
            "Ocp-Apim-Subscription-Key: 07901815f746485db4a7fe5daf5acba1"
    })
    @GET("/preparacaopreparacao")
    Call<ListWrapper<PreparacaoAlimento>> getPreparacaoAliemnto() ;
}
