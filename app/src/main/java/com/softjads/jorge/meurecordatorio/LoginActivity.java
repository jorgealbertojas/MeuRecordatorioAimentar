package com.softjads.jorge.meurecordatorio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.softjads.jorge.meurecordatorio.Interface.InterfaceUsuario;
import com.softjads.jorge.meurecordatorio.Model.ListWrapper;
import com.softjads.jorge.meurecordatorio.Model.Usuario;
import com.softjads.jorge.meurecordatorio.PersistentData.DataBase;
import com.softjads.jorge.meurecordatorio.PersistentData.DbCreate;
import com.softjads.jorge.meurecordatorio.PersistentData.DbInstance;
import com.softjads.jorge.meurecordatorio.Utilite.Common;
import com.softjads.jorge.meurecordatorio.Utilite.Modulo;
import com.softjads.jorge.meurecordatorio.Utilite.Url;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.softjads.jorge.meurecordatorio.PersistentData.DbCreate.DB_NAME;
import static com.softjads.jorge.meurecordatorio.PersistentData.DbSelect.GET_USUARIO_PARAMETRO;

public class LoginActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private DataBase mDataBase;

    private TextView imageButtonNovos;

    private InterfaceUsuario mInterfaceUSUARIO;

    private String usuario_login;
    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        Modulo.Liberado = false;
        carregarsuario_login();


        mDataBase = new DataBase(this);
        mDb = mDataBase.getReadableDatabase();
        mDb.execSQL(DbCreate.CREATE_TABLE_USUARIO);
        DbInstance.getInstance(this);

        imageButtonNovos = (TextView) findViewById(R.id.imageButtonNovos);
        imageButtonNovos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (Common.isOnline(getSystemService(Context.CONNECTIVITY_SERVICE))) {

                    create_API_USUARIO(Url.BASE_URL_USUARIO);
                    mInterfaceUSUARIO.getUsuario().enqueue(USUSARIOCallback);
                }

            }
        });


        // ACESSA O ANTIGO QUE VOCE ACABOU DE ATAULIZAR NA APLICA��O

        Properties properties = new Properties();
        FileInputStream fis;
        try {
            fis = new  FileInputStream(Modulo.nomeArquivoINI);
            properties.load(fis);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String Diretorio = properties.getProperty("conf.Diretorio");





        TextView btn_create = (TextView) findViewById(R.id.imageButton1);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Entrar();
                salvar_USuario_login();

            }
        });


    }

    private void Entrar(){
        Cursor cusrsorlogin;

        TextView txtNome = (TextView) findViewById(R.id.editEntrevistado);
        TextView txtSenha = (TextView) findViewById(R.id.editsenhalogin);

        cusrsorlogin = mDb.rawQuery(GET_USUARIO_PARAMETRO,new String[] {(txtNome.getText().toString().toUpperCase()), (txtSenha.getText().toString())});
        cusrsorlogin.moveToFirst();

        if (cusrsorlogin.getCount() > 0) {
            Modulo.Liberado = true;
            usuario = cusrsorlogin.getString(0).toString();

            if (mDataBase.getListAlimento().size()==0) {
                String sAssets = "backup_REC24_JORGE.db";
                try {
                    restaura_bkp(sAssets);
                } catch (IOException e) {
                e.printStackTrace();
                }

            }

            this.finish();


        } else
        {
            Toast.makeText(this, "Login ou senha não cadastrado!" , Toast.LENGTH_LONG).show();
        }
    }



    private void restaura_bkp(String Origem) throws IOException{

        File file = getDatabasePath(DB_NAME);
        if (file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }

            InputStream inputStream = getAssets().open(Origem);
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 8];
            int numOfBytesToRead;
            while((numOfBytesToRead = inputStream.read(buffer)) > 0)
                outputStream.write(buffer, 0, numOfBytesToRead);
            inputStream.close();
            outputStream.close();
        }

        //db = SQLiteDatabase.openOrCreateDatabase(file, null);
    }




    public void salvar_USuario_login()
    {
        EditText nEditText = (EditText) findViewById(R.id.editEntrevistado);



        Properties properties = new Properties();
        FileInputStream fis;
        try
        {
            fis = new  FileInputStream(Modulo.nomeArquivoINI);
            properties.load(fis);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        properties.setProperty("conf.usuario_login", nEditText.getText().toString().toUpperCase());
        try
        {

            FileOutputStream fos = new FileOutputStream(Modulo.nomeArquivoINI);
            properties.store(fos, "CONFIGURACAO usuario login:");
            fos.close();
            usuario_login = nEditText.getText().toString();
            //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void carregarsuario_login()
    {


        EditText nEditText = (EditText) findViewById(R.id.editEntrevistado);
        Properties properties = new Properties();
        try
        {
            FileInputStream fis;
            fis = new  FileInputStream(Modulo.nomeArquivoINI);
            properties.load(fis);
            String nusuariologin = properties.getProperty("conf.usuario_login");

            nEditText.setText(nusuariologin);



        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    /**
     * *********************USUARIO******************************* .
     */
    private void create_API_USUARIO(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceUSUARIO = retrofit.create(InterfaceUsuario.class);
    }

    private Callback<ListWrapper<Usuario>> USUSARIOCallback = new Callback<ListWrapper<Usuario>>() {
        @Override
        public void onResponse(Call<ListWrapper<Usuario>> call, Response<ListWrapper<Usuario>> response) {
            try {
                if (response.isSuccessful()) {
                    List<Usuario> data = new ArrayList<>();
                    data.addAll(response.body().items);

                    // Persistent Data for SQLLite
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    mDb.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_USUARIO );
                    mDb.execSQL(DbCreate.CREATE_TABLE_USUARIO);

                    mDataBase.insertTABLE_USUARIO(data);

                    Toast.makeText(LoginActivity.this, "Buscou os usuários com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            } catch (NullPointerException e) {
                System.out.println("onActivityResult consume crashed");
                runOnUiThread(new Runnable() {
                    public void run() {
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, R.string.Error_Access_empty, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }

        @Override
        public void onFailure(Call<ListWrapper<Usuario>> call, Throwable t) {
            t.printStackTrace();
        }
    };
}

