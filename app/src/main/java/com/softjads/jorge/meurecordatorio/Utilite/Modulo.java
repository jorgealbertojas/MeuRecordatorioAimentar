package com.softjads.jorge.meurecordatorio.Utilite;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

import static com.softjads.jorge.meurecordatorio.PersistentData.DbCreate.DB_NAME;

/**
 * Created by jorge on 01/05/2018.
 */

public class Modulo {

    public final static String NAO_SE_APLICA = "999";

    public static int ID_TAB_POSITION = 0;

    public static String ID = "0";
    public static String OPCAO = "0";
    public static String NOME = "0";
    public static String NOVO_ALIMENTO = "0";

    public static int ETAPA = 0;

    public static String NOME_PARENTESCO = "0";
    public static String PARENTESCO = "0";
    public static String DIAATIPICO = "NÃO";

    public static String storage = "/storage/emulated/0/";

    // 10 - BEBETO
   // public static String storageCliente = "/storage/emulated/0/carcsentry/inani/";

   // public static String storageCliente = "/storage/emulated/0/carcsentry/inani/";

    //public static String storageCliente = "/storage/emulated/0/";

     public static String storageCliente = "csentry/inani/";

    // 11 - BEBETO
    public static String filename  = "inaniR.txt";
    //public static String filename  = "recordatorio24h.txt";

    public static String NomeCopia = "backup_";

    // know type connect wifi
    public static boolean typeConectyWifi = false;

    public static String caminhobanco = "/data/data/com.softjads.jorge.meurecordatorio/databases/" + DB_NAME;

    public static boolean Liberado = true;

    public static String nomeArquivoINI = "/data/data/com.softjads.jorge.meurecordatorio/configuracao.properties";

    public static String getSDCardPath(Context context) {
        // Verifica se o cartão SD está disponível
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // Obter o caminho do diretório raiz do cartão SD
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            Toast.makeText(context, "ATENÇÃO! Cartão não diponivel" , Toast.LENGTH_LONG).show();
            return "0";
        }
    }

    public static String getSDCardPathNew(Context context) {

        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

    }

}
