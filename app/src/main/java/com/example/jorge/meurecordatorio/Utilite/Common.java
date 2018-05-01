package com.example.jorge.meurecordatorio.Utilite;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
}
