package com.softjads.jorge.meurecordatorio.Model;

import java.io.Serializable;

/**
 * Created by jorge on 28/04/2018.
 */

public class Preparacao implements Serializable {
    private String preparacao_id;
    private String preparacao;

    public String getPreparacao_id() {
        return preparacao_id;
    }

    public void setPreparacao_id(String preparacao_id) {
        this.preparacao_id = preparacao_id;
    }

    public String getPreparacao() {
        return preparacao;
    }

    public void setPreparacao(String preparacao) {
        this.preparacao = preparacao;
    }



}
