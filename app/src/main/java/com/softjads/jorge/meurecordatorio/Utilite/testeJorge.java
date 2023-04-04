package com.softjads.jorge.meurecordatorio.Utilite;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import android.util.Log;

import androidx.annotation.RequiresApi;

import com.softjads.jorge.meurecordatorio.Model.Adicao;
import com.softjads.jorge.meurecordatorio.Model.AdicaoAlimento;
import com.softjads.jorge.meurecordatorio.Model.Alimento;
import com.softjads.jorge.meurecordatorio.Model.Local;
import com.softjads.jorge.meurecordatorio.Model.OcasiaoConsumo;
import com.softjads.jorge.meurecordatorio.Model.Preparacao;
import com.softjads.jorge.meurecordatorio.Model.PreparacaoAlimento;
import com.softjads.jorge.meurecordatorio.Model.Unidade;
import com.softjads.jorge.meurecordatorio.Model.UnidadeAlimento;
import com.softjads.jorge.meurecordatorio.PersistentData.DataBase;
import com.softjads.jorge.meurecordatorio.PersistentData.DbCreate;
import com.softjads.jorge.meurecordatorio.PersistentData.DbInstance;
import com.softjads.jorge.meurecordatorio.PersistentData.DbSelect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.softjads.jorge.meurecordatorio.Utilite.Modulo.storage;

    public class testeJorge {

        @SuppressWarnings("unchecked")

        static Context mContext;
        static SQLiteDatabase mDb;
        static DataBase mDataBase;

        private static String mNomeArquivo = "";

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public static void main(Context context, String nomeArquivo) throws IOException, JSONException {
            mContext = context;
            mNomeArquivo = nomeArquivo;

            arquivo_Txt_alimento("alimentos");



        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public static void arquivo_Txt_alimento(String nomearquivo) throws JSONException {



            mDataBase = new DataBase(mContext);
            mDb = mDataBase.getReadableDatabase();

            DbInstance.getInstance(mContext);

            StringBuilder text = new StringBuilder();
            try {




                JSONArray items = new JSONArray();

                List<Alimento> data = new ArrayList<>();

                Cursor cursor = mDb.rawQuery(DbSelect.GET_teste_duda,null);
                cursor.moveToFirst();
                while(!cursor.isAfterLast() ){


                    String id = Integer.toString(cursor.getInt(0));
                    String nome = cursor.getString(1);

                    JSONObject obj = new JSONObject();
                    obj.put("alimento_id", id);
                    obj.put("alimento", nome);

                    Alimento alimento = new Alimento();
                    alimento.setAlimento_id(id);
                    alimento.setAlimento(nome);
                    data.add(alimento);

                    items.put(obj);
                    cursor.moveToNext();


                }

                escreveJson(items,mNomeArquivo);

            } finally {

            }
        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public static void escreveJson(JSONArray items, String partNameFile) throws JSONException {

            JSONObject obj = new JSONObject();

            obj.put("items", items);

            // try-with-resources statement based on post comment below :)
            try (FileWriter file = new FileWriter(storage + partNameFile + "file.txt")) {
                file.write(obj.toString());
                System.out.println("Successfully Copied JSON Object to File...");
                System.out.println("\nJSON Object: " + obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

