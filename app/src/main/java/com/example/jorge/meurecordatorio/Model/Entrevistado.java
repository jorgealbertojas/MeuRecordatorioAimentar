package com.example.jorge.meurecordatorio.Model;

import java.io.Serializable;

/**
 * Created by jorge on 28/04/2018.
 */

public class Entrevistado implements Serializable {

    private String entrevistado_id;
    private String entrevistado;

    public String getEntrevistado_id() {
        return entrevistado_id;
    }

    public void setEntrevistado_id(String entrevistado_id) {
        this.entrevistado_id = entrevistado_id;
    }

    public String getEntrevistado() {
        return entrevistado;
    }

    public void setEntrevistado(String entrevistado) {
        this.entrevistado = entrevistado;
    }
}
