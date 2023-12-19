package com.softjads.jorge.meurecordatorio.Utilite;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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

     public static String storageCliente = "/ENANI2/";

    // 11 - BEBETO
    public static String fileAnswerName  = "inaniR.txt";


    public static String NomeCopia = "backup_";

    // know type connect wifi
    public static boolean typeConectyWifi = false;

    public static String caminhobanco = "/data/data/com.softjads.jorge.meurecordatorio/databases/" + DB_NAME;

    public static boolean Liberado = true;

    public static String nomeArquivoINI = "/data/data/com.softjads.jorge.meurecordatorio/configuracao.properties";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getSDCardPathNew(Context context) {

        if (isExternalStorageWritable()) {

            File sdCard = null;

            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);

            if (storageManager != null) {
                List<StorageVolume> storageVolumes = storageManager.getStorageVolumes();

                for (StorageVolume volume : storageVolumes) {

                    String path = volume.getUuid();
                    if (!volume.isPrimary() ) {
                        sdCard = new File(path, storageCliente);
                    }
                }
            }
            if (sdCard != null ) {
                return "/storage" + sdCard.getAbsolutePath();
            }else {
                return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            }

        } else {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        }

    }

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

}
