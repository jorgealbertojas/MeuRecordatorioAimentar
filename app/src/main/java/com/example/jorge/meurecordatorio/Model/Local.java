package com.example.jorge.meurecordatorio.Model;

import java.io.Serializable;

/**
 * Created by jorge on 28/04/2018.
 */

public class Local implements Serializable {

    private String local_id;
    private String local;

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
