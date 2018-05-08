package com.example.jorge.meurecordatorio.Model;

import java.io.Serializable;

/**
 * Created by jorge on 28/04/2018.
 */

public class Alimento implements Serializable {
    private String alimento_id;
    private String alimento;
    private String novo;

    public String getNovo() {
        return novo;
    }

    public void setNovo(String novo) {
        this.novo = novo;
    }

    public String getAlimento_id() {
        return alimento_id;
    }

    public void setAlimento_id(String alimento_id) {
        this.alimento_id = alimento_id;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }


}
