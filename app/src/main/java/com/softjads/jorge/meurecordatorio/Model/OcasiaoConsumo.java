package com.softjads.jorge.meurecordatorio.Model;

import java.io.Serializable;

/**
 * Created by jorge on 28/04/2018.
 */

public class OcasiaoConsumo implements Serializable {

    private String ocasiao_consumo_id;
    private String ocasiao_consumo;

    public String getOcasiao_consumo_id() {
        return ocasiao_consumo_id;
    }

    public void setOcasiao_consumo_id(String ocasiao_consumo_id) {
        this.ocasiao_consumo_id = ocasiao_consumo_id;
    }

    public String getOcasiao_consumo() {
        return ocasiao_consumo;
    }

    public void setOcasiao_consumo(String ocasiao_consumo) {
        this.ocasiao_consumo = ocasiao_consumo;
    }
}
