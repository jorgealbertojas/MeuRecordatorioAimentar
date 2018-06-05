package com.example.jorge.meurecordatorio.Utilite;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorge on 30/04/2018.
 */

public class Common {

    /**
     * checks if internet is ok .
     */
    public static boolean isOnline(Object object) {
        ConnectivityManager cm =
                (ConnectivityManager) object;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean eLeitematerno(String nomeAlimento){
        if (nomeAlimento.toString().toUpperCase().toString().contains("LEITE MATERNO")) {
            return true;
        }else{
            return false;
        }
    }


    public static List<String> pegarFormatosOpcoesOBS(){
        List<String> list = new ArrayList<>();
        list.add("Não sabe informar a duração");
        list.add("amassada");
        list.add("liquidificada");

        return list;
    }

}
