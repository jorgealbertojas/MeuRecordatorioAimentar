package com.softjads.jorge.meurecordatorio.Utilite;

import android.annotation.TargetApi;
import android.content.Context;
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

/**
 * Created by jorge on 05/06/2018.
 */

public class CrunchifyJSONFileWrite {

    @SuppressWarnings("unchecked")

    static Context mContext;
    static SQLiteDatabase mDb;
    static DataBase mDataBase;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void main(Context context) throws IOException {
        mContext = context;

        arquivo_Txt_alimento("alimentos");
        arquivo_Txt_adicao("adicao");
        arquivo_Txt_adicao_adicao("adicao_adicao");
        arquivo_Txt_entrevistado("entrevistado");
        arquivo_Txt_local("local");
        arquivo_Txt_ocasiao_consumo("ocasiao_consumo");
        arquivo_Txt_preparacao("preparacao");
        arquivo_Txt_preparacao_preparacao("preparacao_preparacao");
        arquivo_Txt_unidade("unidade");
        arquivo_Txt_unidade_unidade("unidade_unidade");
        arquivo_Txt_usuario("usuario");


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_alimento(String nomearquivo){



        mDataBase = new DataBase(mContext);
        mDb = mDataBase.getReadableDatabase();

        DbInstance.getInstance(mContext);

            StringBuilder text = new StringBuilder();
            try {

                File file = new File(storage, nomearquivo + ".txt");

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                JSONArray items = new       JSONArray();

                List<Alimento> data = new ArrayList<>();

                while ((line = br.readLine()) != null) {
                    text.append(line);

                    String id = line.substring(0,line.indexOf("_"));
                    String nome = line.substring(line.indexOf("_") + 1, line.length());

                    JSONObject obj = new JSONObject();
                    obj.put("alimento_id", id);
                    obj.put("alimento", nome);

                    Alimento alimento = new Alimento();
                    alimento.setAlimento_id(id);
                    alimento.setAlimento(nome);
                    data.add(alimento);

                    items.put(obj);


                }

                mDataBase = new DataBase(mContext);
                mDataBase.insertTABLE_ALIMENTO(data);

               // escreveJson(items,nomearquivo);
                br.close();
            } catch (IOException | JSONException e) {
                e.printStackTrace();

            } finally {

            }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_adicao(String nomearquivo){

        mDataBase = new DataBase(mContext);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(mContext);


        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storage, nomearquivo + ".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            JSONArray items = new JSONArray();
            List<Adicao> data = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                text.append(line);

                String id = line.substring(0,line.indexOf("_"));
                String nome = line.substring(line.indexOf("_") + 1, line.length());

                JSONObject obj = new JSONObject();
                obj.put("adicao_id", id);
                obj.put("adicao", nome);

                Adicao adicao = new Adicao();
                adicao.setAdicao_id(id);
                adicao.setAdicao(nome);
                data.add(adicao);

                items.put(obj);


            }

            mDataBase = new DataBase(mContext);
            mDataBase.insertTABLE_ADICAO(data);

            // escreveJson(items,nomearquivo);
            br.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();

        } finally {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_adicao_adicao(String nomearquivo){

        mDataBase = new DataBase(mContext);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(mContext);

        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storage, nomearquivo +".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            JSONArray items = new JSONArray();
            List<AdicaoAlimento> data = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                text.append(line);

                String id = line.substring(0,line.indexOf("_"));
                String nome = line.substring(line.indexOf("_") + 1, line.length());

                Log.i(id,nome);
                JSONObject obj = new JSONObject();
                obj.put("adicao_adicao_id", nome);
                obj.put("adicao_alimento_id", id);

                AdicaoAlimento adicaoAlimento = new AdicaoAlimento();
                adicaoAlimento.setAdicao_alimento_id(id);
                adicaoAlimento.setAdicao_adicao_id(nome);
                data.add(adicaoAlimento);

                //items.add(obj);


            }

            mDataBase = new DataBase(mContext);
            mDataBase.insertTABLE_ADICAO_ALIMENTO(data);
            //escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();

        } finally {

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_entrevistado(String nomearquivo){

        mDataBase = new DataBase(mContext);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(mContext);

        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storage, nomearquivo +".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            JSONArray items = new JSONArray();

            while ((line = br.readLine()) != null) {
                text.append(line);

                String id = line.substring(0,line.indexOf("_"));
                String nome = line.substring(line.indexOf("_") + 1, line.length());
                Log.i(id,nome);
                JSONObject obj = new JSONObject();
                obj.put("entrevistado_id", id);
                obj.put("entrevistado_id", nome);

                items.put(obj);


            }

            escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();

        } finally {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_local(String nomearquivo){

        mDataBase = new DataBase(mContext);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(mContext);

        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storage, nomearquivo +".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            JSONArray items = new JSONArray();
            List<Local> data = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                text.append(line);

                String id = line.substring(0,line.indexOf("_"));
                String nome = line.substring(line.indexOf("_") + 1, line.length());
                Log.i(id,nome);
                JSONObject obj = new JSONObject();
                obj.put("local_id", id);
                obj.put("local_id", nome);

                Local local = new Local();
                local.setLocal_id(id);
                local.setLocal(nome);
                data.add(local);

                //items.add(obj);


            }

            mDataBase = new DataBase(mContext);
            mDataBase.insertTABLE_LOCAL(data);
            //escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();

        } finally {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_ocasiao_consumo(String nomearquivo){

        mDataBase = new DataBase(mContext);
        mDb = mDataBase.getReadableDatabase();
        mDb.execSQL(" DELETE FROM " + DbCreate.TABLE_OCASIAO_CONSUMO);
        DbInstance.getInstance(mContext);

        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storage, nomearquivo +".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            JSONArray items = new JSONArray();
            List<OcasiaoConsumo> data = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                text.append(line);

                String id = line.substring(0,line.indexOf("_"));
                String nome = line.substring(line.indexOf("_") + 1, line.length());
                Log.i(id,nome);
                JSONObject obj = new JSONObject();
                obj.put("ocasiao_consumo_id", id);
                obj.put("ocasiao_consumo", nome);

                OcasiaoConsumo ocasiaoConsumo = new OcasiaoConsumo();
                ocasiaoConsumo.setOcasiao_consumo_id(id);
                ocasiaoConsumo.setOcasiao_consumo(nome);
                data.add(ocasiaoConsumo);

                //items.add(obj);


            }

            mDataBase = new DataBase(mContext);
            mDataBase.insertTABLE_OCASIAO_CONSUMO(data);
            //escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();

        } finally {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_preparacao(String nomearquivo){

        mDataBase = new DataBase(mContext);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(mContext);

        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storage, nomearquivo +".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            JSONArray items = new JSONArray();
            List<Preparacao> data = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                text.append(line);

                String id = line.substring(0,line.indexOf("_"));
                String nome = line.substring(line.indexOf("_") + 1, line.length());
                Log.i(id,nome);
                JSONObject obj = new JSONObject();
                obj.put("preparacao_id", id);
                obj.put("preparacao", nome);

                Preparacao preparacao = new Preparacao();
                preparacao.setPreparacao_id(id);
                preparacao.setPreparacao(nome);
                data.add(preparacao);

                //items.add(obj);


            }

            mDataBase = new DataBase(mContext);
            mDataBase.insertTABLE_PREPARACAO(data);
            //escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();

        } finally {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_preparacao_preparacao(String nomearquivo){

        mDataBase = new DataBase(mContext);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(mContext);

        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storage, nomearquivo +".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            JSONArray items = new JSONArray();
            List<PreparacaoAlimento> data = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                text.append(line);

                String id = line.substring(0,line.indexOf("_"));
                String nome = line.substring(line.indexOf("_") + 1, line.length());
                Log.i(id,nome);
                JSONObject obj = new JSONObject();
                obj.put("preparacao_preparacao_id", nome);
                obj.put("preparacao_alimento_id", id);

                PreparacaoAlimento preparacaoAlimento = new PreparacaoAlimento();
                preparacaoAlimento.setPreparacao_alimento_id(id);
                preparacaoAlimento.setPreparacao_preparacao_id(nome);
                data.add(preparacaoAlimento);

                //items.add(obj);


            }

            mDataBase = new DataBase(mContext);
            mDataBase.insertTABLE_PREPARACAO_ALIMENTO(data);
            //escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();

        } finally {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_unidade(String nomearquivo){

        mDataBase = new DataBase(mContext);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(mContext);

        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storage, nomearquivo +".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            JSONArray items = new JSONArray();
            List<Unidade> data = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                text.append(line);

                String id = line.substring(0,line.indexOf("_"));
                String nome = line.substring(line.indexOf("_") + 1, line.length());
                Log.i(id,nome);
                JSONObject obj = new JSONObject();
                obj.put("unidade_id", id);
                obj.put("unidade", nome);

                Unidade unidade = new Unidade();
                unidade.setUnidade_id(id);
                unidade.setUnidade(nome);
                data.add(unidade);

                //items.add(obj);


            }

            mDataBase = new DataBase(mContext);
            mDataBase.insertTABLE_UNIDADE(data);
            //escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();

        } finally {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_unidade_unidade(String nomearquivo){

        mDataBase = new DataBase(mContext);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(mContext);

        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storage, nomearquivo +".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            JSONArray items = new JSONArray();
            List<UnidadeAlimento> data = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                text.append(line);

                String id = line.substring(0,line.indexOf("_"));
                String nome = line.substring(line.indexOf("_") + 1, line.length());
                Log.i(id,nome);
                JSONObject obj = new JSONObject();
                obj.put("unidade_unidade_id", nome);
                obj.put("unidade_alimento_id", id);

                UnidadeAlimento unidadeAlimento = new UnidadeAlimento();
                unidadeAlimento.setUnidade_alimento_id(id);
                unidadeAlimento.setUnidade_unidade_id(nome);
                data.add(unidadeAlimento);

                //items.add(obj);


            }

            mDataBase = new DataBase(mContext);
            mDataBase.insertTABLE_UNIDADE_ALIMENTO(data);
            //escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();

        } finally {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_usuario(String nomearquivo){

        mDataBase = new DataBase(mContext);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(mContext);

        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storage, nomearquivo +".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            JSONArray items = new JSONArray();

            while ((line = br.readLine()) != null) {
                text.append(line);

                String id = line.substring(0,line.indexOf("_"));
                String nome = line.substring(line.indexOf("_") + 1, line.length());
                Log.i(id,nome);
                JSONObject obj = new JSONObject();
                obj.put("usuario", id);
                obj.put("senha", nome);

                items.put(obj);


            }

            escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();

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
