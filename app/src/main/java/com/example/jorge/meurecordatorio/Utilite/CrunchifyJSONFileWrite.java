package com.example.jorge.meurecordatorio.Utilite;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static com.example.jorge.meurecordatorio.Utilite.Modulo.storage;

/**
 * Created by jorge on 05/06/2018.
 */

public class CrunchifyJSONFileWrite {
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("unchecked")



    public static void main() throws IOException {
        arquivo_Txt_alimento("alimentos");
       // arquivo_Txt_adicao("adicao");
      //  arquivo_Txt_adicao_adicao("adicao_adicao");
      //  arquivo_Txt_entrevistado("entrevistado");
      //  arquivo_Txt_local("local");
      //  arquivo_Txt_ocasiao_consumo("ocasiao_consumo");
      //  arquivo_Txt_preparacao("preparacao");
     //   arquivo_Txt_preparacao_preparacao("preparacao_preparacao");
      //  arquivo_Txt_unidade("unidade");
     //   arquivo_Txt_unidade_unidade("unidade_unidade");
     //   arquivo_Txt_usuario("usuario");


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_alimento(String nomearquivo){

            StringBuilder text = new StringBuilder();
            try {

                File file = new File(storage, nomearquivo + ".txt");

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                JSONArray items = new JSONArray();

                while ((line = br.readLine()) != null) {
                    text.append(line);

                    String id = line.substring(0,line.indexOf("_"));
                    String nome = line.substring(line.indexOf("_") + 1, line.length());

                    JSONObject obj = new JSONObject();
                    obj.put("alimento_id", id);
                    obj.put("alimento", nome);

                    items.add(obj);


                }

                escreveJson(items,nomearquivo);
                br.close();
            } catch (IOException e) {
                e.printStackTrace();

            } finally {

            }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_adicao(String nomearquivo){

        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storage, nomearquivo + ".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            JSONArray items = new JSONArray();

            while ((line = br.readLine()) != null) {
                text.append(line);

                String id = line.substring(0,line.indexOf("_"));
                String nome = line.substring(line.indexOf("_") + 1, line.length());

                JSONObject obj = new JSONObject();
                obj.put("adicao_id", id);
                obj.put("adicao", nome);

                items.add(obj);


            }

            escreveJson(items, nomearquivo );
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_adicao_adicao(String nomearquivo){

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
                obj.put("adicao_adicao_id", nome);
                obj.put("adicao_alimento_id", id);

                items.add(obj);


            }

            escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_entrevistado(String nomearquivo){

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

                items.add(obj);


            }

            escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_local(String nomearquivo){

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
                obj.put("local_id", id);
                obj.put("local_id", nome);

                items.add(obj);


            }

            escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_ocasiao_consumo(String nomearquivo){

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
                obj.put("ocasiao_consumo_id", id);
                obj.put("ocasiao_consumo", nome);

                items.add(obj);


            }

            escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_preparacao(String nomearquivo){

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
                obj.put("preparacao_id", id);
                obj.put("preparacao", nome);

                items.add(obj);


            }

            escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_preparacao_preparacao(String nomearquivo){

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
                obj.put("preparacao_preparacao_id", nome);
                obj.put("preparacao_alimento_id", id);

                items.add(obj);


            }

            escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_unidade(String nomearquivo){

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
                obj.put("unidade_id", id);
                obj.put("unidade", nome);

                items.add(obj);


            }

            escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_unidade_unidade(String nomearquivo){

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
                obj.put("unidade_unidade_id", nome);
                obj.put("unidade_alimento_id", id);

                items.add(obj);


            }

            escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt_usuario(String nomearquivo){

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

                items.add(obj);


            }

            escreveJson(items, nomearquivo);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void escreveJson(JSONArray items, String partNameFile){

        JSONObject obj = new JSONObject();

        obj.put("items", items);

        // try-with-resources statement based on post comment below :)
        try (FileWriter file = new FileWriter(storage + partNameFile + "file.txt")) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
