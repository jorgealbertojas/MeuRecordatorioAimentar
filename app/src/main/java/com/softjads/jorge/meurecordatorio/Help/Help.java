package com.softjads.jorge.meurecordatorio.Help;

import com.google.gson.annotations.SerializedName;

public class Help {


    @SerializedName("key")
    private String mkey;

    @SerializedName("value")
    private String mvalue;

    @SerializedName("last")
    private String mlast;

    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public String getMvalue() {
        return mvalue;
    }

    public void setMvalue(String mvalue) {
        this.mvalue = mvalue;
    }

    public String getMlast() {
        return mlast;
    }

    public void setMlast(String mlast) {
        this.mlast = mlast;
    }
}
