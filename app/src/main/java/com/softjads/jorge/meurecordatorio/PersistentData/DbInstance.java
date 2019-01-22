package com.softjads.jorge.meurecordatorio.PersistentData;

import android.content.Context;

/**
 * Created by jorge on 28/04/2018.
 */

public class DbInstance {

    private static DataBase instance;


        public static DataBase getInstance(Context context) {


            if ( instance == null)
            {
                instance = new DataBase(context);


            }
            return instance;


        }

}

