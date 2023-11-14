package com.softjads.jorge.meurecordatorio.Model;

import java.io.Serializable;

/**
 * Created by jorge on 28/04/2018.
 */

public class Unidade implements Serializable {

    private String unidade_id;
    private String unidade;
    private String tipo_unidade;
    private int limite;

    public String getUnidade_id() {
        return unidade_id;
    }

    public void setUnidade_id(String unidade_id) {
        this.unidade_id = unidade_id;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getTipo_unidade() {
        return tipo_unidade;
    }

    public void setTipo_unidade(String tipo_unidade) {
        this.tipo_unidade = tipo_unidade;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }
}
