package com.example.jorge.meurecordatorio.Model;

import java.io.Serializable;

/**
 * Created by jorge on 28/04/2018.
 */

public class AdicaoAlimento implements Serializable {
    private String adicao_adicao_id;
    private String adicao_alimento_id;

    public String getAdicao_adicao_id() {
        return adicao_adicao_id;
    }

    public void setAdicao_adicao_id(String adicao_adicao_id) {
        this.adicao_adicao_id = adicao_adicao_id;
    }

    public String getAdicao_alimento_id() {
        return adicao_alimento_id;
    }

    public void setAdicao_alimento_id(String adicao_alimento_id) {
        this.adicao_alimento_id = adicao_alimento_id;
    }
}
