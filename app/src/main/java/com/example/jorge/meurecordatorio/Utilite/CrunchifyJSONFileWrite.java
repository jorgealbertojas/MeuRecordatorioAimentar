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
        arquivo_Txt();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void arquivo_Txt(){

            StringBuilder text = new StringBuilder();
            try {

                File file = new File(storage, "alimento.txt");

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

                escreveJson(items);
                br.close();
            } catch (IOException e) {
                e.printStackTrace();

            } finally {

            }






    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void escreveJson(JSONArray items){

        JSONObject obj = new JSONObject();

        obj.put("items", items);

        // try-with-resources statement based on post comment below :)
        try (FileWriter file = new FileWriter(storage + "file.txt")) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
