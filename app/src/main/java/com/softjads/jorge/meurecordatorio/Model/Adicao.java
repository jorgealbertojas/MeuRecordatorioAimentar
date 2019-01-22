package com.softjads.jorge.meurecordatorio.Model;

import java.io.Serializable;

/**
 * Created by jorge on 28/04/2018.
 */

public class Adicao implements Serializable {

    private String adicao_id;
    private String adicao;

    public String getAdicao_id() {
        return adicao_id;
    }

    public void setAdicao_id(String adicao_id) {
        this.adicao_id = adicao_id;
    }

    public String getAdicao() {
        return adicao;
    }

    public void setAdicao(String adicao) {
        this.adicao = adicao;
    }
}
