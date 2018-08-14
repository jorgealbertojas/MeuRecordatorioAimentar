package com.example.jorge.meurecordatorio.Interface;

import com.example.jorge.meurecordatorio.Model.Alimento;
import com.example.jorge.meurecordatorio.Model.ListWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by jorge on 30/04/2018.
 */

public interface InterfaceAlimento {
    @Headers({
            "Cache-Control: cache",
            "Ocp-Apim-Trace: true",
            "Ocp-Apim-Subscription-Key: e701a890f3754280bc3adc8af66019e0"
    })
    @GET("/alimento")
    Call<ListWrapper<Alimento>> getAlimento();
}
