package com.example.jorge.meurecordatorio.Model;

import java.io.Serializable;

/**
 * Created by jorge on 28/04/2018.
 */

public class UnidadeAlimento implements Serializable {

    private String unidade_unidade_id;
    private String unidade_alimento_id;

    public String getUnidade_unidade_id() {
        return unidade_unidade_id;
    }

    public void setUnidade_unidade_id(String unidade_unidade_id) {
        this.unidade_unidade_id = unidade_unidade_id;
    }

    public String getUnidade_alimento_id() {
        return unidade_alimento_id;
    }

    public void setUnidade_alimento_id(String unidade_alimento_id) {
        this.unidade_alimento_id = unidade_alimento_id;
    }
}
