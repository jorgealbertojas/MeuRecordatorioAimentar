package com.softjads.jorge.meurecordatorio.Utilite;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.softjads.jorge.meurecordatorio.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorge on 30/04/2018.
 */

public class Common {

    public static Integer lastFldId = 0;

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

    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }


    public static List<String> pegarFormatosOpcoesOBS(){
        List<String> list = new ArrayList<>();
        list.add("Não sabe informar a duração");
        list.add("amassada");
        list.add("liquidificada");

        return list;
    }

    /**
     * Get Component Screen for
     */
    public static Integer getResourceString(String name, int value) {


        //  if(lastFldId == null) {
        int maxFld = 0;
        String fldName = "";
        Field[] flds = R.id.class.getDeclaredFields();

        //R.id inst = new R.id();

        for (int i = 0; i < flds.length; i++) {
            Field fld = flds[i];

         //   try {
              //  int value = fld.getInt(inst);

                if (value > maxFld) {
                    maxFld = value;
                    fldName = "tx_descartar";
                }
                if (name.equals(fld.getName())){
                    return value;
                }

          //  } catch (IllegalAccessException e) {

          //  }
        }
        lastFldId = new Integer(maxFld);
        //  }

        return 0;
    }

}
