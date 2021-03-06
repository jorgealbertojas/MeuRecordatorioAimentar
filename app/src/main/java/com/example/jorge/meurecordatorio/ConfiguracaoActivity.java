package com.example.jorge.meurecordatorio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.meurecordatorio.Adapter.AdicaoAdapter;
import com.example.jorge.meurecordatorio.Adapter.AlimentoAdapter;
import com.example.jorge.meurecordatorio.Adapter.EntrevistadoAdapter;
import com.example.jorge.meurecordatorio.Adapter.LocalAdapter;
import com.example.jorge.meurecordatorio.Adapter.OcasiaoConsumoAdapter;
import com.example.jorge.meurecordatorio.Adapter.PreparacaoAdapter;
import com.example.jorge.meurecordatorio.Adapter.UnidadeAdapter;
import com.example.jorge.meurecordatorio.Interface.InterfaceAdicao;
import com.example.jorge.meurecordatorio.Interface.InterfaceAdicaoAlimento;
import com.example.jorge.meurecordatorio.Interface.InterfaceAlimento;
import com.example.jorge.meurecordatorio.Interface.InterfaceEntrevistado;
import com.example.jorge.meurecordatorio.Interface.InterfaceLocal;
import com.example.jorge.meurecordatorio.Interface.InterfaceOcasiaoConsumo;
import com.example.jorge.meurecordatorio.Interface.InterfacePreparacao;
import com.example.jorge.meurecordatorio.Interface.InterfacePreparacaoAlimento;
import com.example.jorge.meurecordatorio.Interface.InterfaceUnidade;
import com.example.jorge.meurecordatorio.Interface.InterfaceUnidadeAlimento;
import com.example.jorge.meurecordatorio.Interface.InterfaceUsuario;
import com.example.jorge.meurecordatorio.Model.Adicao;
import com.example.jorge.meurecordatorio.Model.AdicaoAlimento;
import com.example.jorge.meurecordatorio.Model.Alimentacao;
import com.example.jorge.meurecordatorio.Model.Alimento;
import com.example.jorge.meurecordatorio.Model.Entrevistado;
import com.example.jorge.meurecordatorio.Model.ListWrapper;
import com.example.jorge.meurecordatorio.Model.Local;
import com.example.jorge.meurecordatorio.Model.OcasiaoConsumo;
import com.example.jorge.meurecordatorio.Model.Preparacao;
import com.example.jorge.meurecordatorio.Model.PreparacaoAlimento;
import com.example.jorge.meurecordatorio.Model.Unidade;
import com.example.jorge.meurecordatorio.Model.UnidadeAlimento;
import com.example.jorge.meurecordatorio.Model.Usuario;
import com.example.jorge.meurecordatorio.PersistentData.DataBase;
import com.example.jorge.meurecordatorio.PersistentData.DbCreate;
import com.example.jorge.meurecordatorio.PersistentData.DbInstance;
import com.example.jorge.meurecordatorio.Utilite.Common;
import com.example.jorge.meurecordatorio.Utilite.Modulo;
import com.example.jorge.meurecordatorio.Utilite.Url;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfiguracaoActivity extends AppCompatActivity {

    String filename = "REC24.txt";
    File myExternalFile;

    private String filtro_desc_pesquisa = "REC24";

    private TextView TextViewCOPIAR;
    private TextView TextViewBUSCAR;
    private TextView TextViewENVIAR;

    private SQLiteDatabase mDb;
    private DataBase mDataBase;

    private InterfaceAdicao mInterfaceADICAO;
    AdicaoAdapter mAdapterADICAO;

    private InterfaceAdicaoAlimento mInterfaceADICAO_ALIMENTO;

    private InterfaceAlimento mInterfaceALIMENTO;
    AlimentoAdapter mAdapterALIMENTO;

    private InterfaceEntrevistado mInterfaceENTREVISTADO;
    EntrevistadoAdapter mAdapterENTREVISTADO;

    private InterfaceLocal mInterfaceLOCAL;
    LocalAdapter mAdapterLOCAL;

    private InterfacePreparacao mInterfacePREPARACAO;
    PreparacaoAdapter mAdapterPREPARACAO;

    private InterfacePreparacaoAlimento mInterfacePREPARACAO_ALIMENTO;

    private InterfaceUnidade mInterfaceUNIDADE;
    UnidadeAdapter mAdapterUNIDADE;

    private InterfaceUnidadeAlimento mInterfaceUNIDADE_ALIMENTO;





    private InterfaceOcasiaoConsumo mInterfaceOCASIAO_CONSUMO;
    OcasiaoConsumoAdapter mAdapterOCASIAO_CONSUMO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);



        TextViewCOPIAR = (TextView) findViewById(R.id.TextViewCOPIAR);
        TextViewCOPIAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    Intent WSActivity = new Intent(ConfiguracaoActivity.this, DbExportImport.class);
                    startActivity(WSActivity);

                    String ocupado = getAvailableExternalMemorySize(Modulo.storage);
                    String total = getTotalExternalMemorySize(Modulo.storage);

                    int indice = 0;

                    indice = total.toString().indexOf("MB");
                    if (indice > 0){
                        total =    total.substring(0,indice);
                    }else{ indice = 0;};

                    indice = total.toString().indexOf("KB");
                    if (indice > 0){
                        total =    total.substring(0,indice);
                    }else{ indice = 0;};

                    indice = ocupado.toString().indexOf("MB");
                    if (indice > 0){
                        ocupado =    ocupado.substring(0,indice);
                    }else{ indice = 0;};

                    indice = ocupado.toString().indexOf("KB");
                    if (indice > 0){
                        ocupado =    ocupado.substring(0,indice);
                    }else{ indice = 0;};

                    double resultado = 0.0;
                    String resultadoSTR = "";
                    String e = "";

                    if (!ocupado.toString().equals("") && (!total.toString().equals(""))){
                        resultado = Double.parseDouble(ocupado) * 100;
                        resultado = resultado / Double.parseDouble(total);
                        resultadoSTR = Double.toString(resultado);



                        indice = resultadoSTR.toString().indexOf(".");
                        if (indice > 0){
                            e=    resultadoSTR.substring(0,indice);
                        }
                    }

                    Toast.makeText(ConfiguracaoActivity.this, (e) + "% Livre - Capacidade:" + getTotalExternalMemorySize(Modulo.storage), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        TextViewBUSCAR = (TextView) findViewById(R.id.TextViewBUSCAR);
        TextViewBUSCAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {

                    LayoutInflater factory = LayoutInflater.from(ConfiguracaoActivity.this);
                    final View deleteDialogView = factory.inflate(
                            R.layout.custom_dialog, null);
                    final AlertDialog deleteDialog = new AlertDialog.Builder(ConfiguracaoActivity.this).create();
                    deleteDialog.setView(deleteDialogView);

                    TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                    nTextView.setText("ATENÇÃO! Tem certeza que deseja buscar dados do servidor? Os dados coletados deste aparelho serão perdidos e atualizados com os novos dados trazidos do servidor exemplo ALIMENTOS, USUáRIOS ENTREVISTADOS e outros dados referente ao alimento!");

                    deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            mDataBase = new DataBase(ConfiguracaoActivity.this);
                            mDb = mDataBase.getReadableDatabase();
                            mDataBase.onCreate(mDb);
                            DbInstance.getInstance(ConfiguracaoActivity.this);

                            getJson();

                            Toast.makeText(ConfiguracaoActivity.this, "Atualizado alimentos com sucesso!" , Toast.LENGTH_SHORT).show();
                            deleteDialog.dismiss();
                        }
                    });
                    deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            deleteDialog.dismiss();

                        }
                    });

                    deleteDialog.show();

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        TextViewENVIAR = (TextView) findViewById(R.id.TextViewENVIAR);
        TextViewENVIAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    gerarArquivoTXT();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
    }

    private void gerarArquivoTXT(){
        mDataBase = new DataBase(ConfiguracaoActivity.this);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(ConfiguracaoActivity.this);

        salvearquivo();
    }

    private void salvearquivo(){

        String DataCompleta1;
        Time now1 = new Time();
        now1.setToNow();
        DataCompleta1 = Integer.toString(now1.year);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.month + 1);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.monthDay);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.hour);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.minute);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.second);


        myExternalFile = new File(Modulo.storage, DataCompleta1 +  filename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myExternalFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            List<Alimentacao> dataPersistent = new ArrayList<>();
            dataPersistent = mDataBase.getListAlimentacao();
            for (int i = 0; i < dataPersistent.size(); i++) {

                String ID_RECORDATORIO = getFormatodoComEspaco(3, dataPersistent.get(i).getAlimentacao_id());
                String ID_ALIMENTO  = getFormatodoComEspaco(8, dataPersistent.get(i).getAlimentacao_alimento_id());
                String ALIMENTO_NOVO = getFormatodoComEspaco(1, alimentoENovo(dataPersistent.get(i).getAlimentacao_alimento_id()));
                String ALIMENTO_DESCRICAO = getFormatodoComEspaco(50, dataPersistent.get(i).getAlimentacao_alimento());
                String ID_PREPARACAO = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_preparacao_id());
                String ID_UNIDADE = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_unidade_id());
                String ID_ADICAO = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_adicao_id());
                String ID_LOCAL = getFormatodoComEspaco(2, dataPersistent.get(i).getAlimentacao_local_id());
                String ID_CONSUMO = getFormatodoComEspaco(2, dataPersistent.get(i).getAlimentacao_ocasiao_consumo_id());
                String QUANTIDADE = getFormatodoComEspaco(6, dataPersistent.get(i).getAlimentacao_quantidade());
                String HORA = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_hora());
                String HORA_COLETA = getFormatodoComEspaco(6, formataHora(dataPersistent.get(i).getAlimentacao_hora_coleta()));
                String DATA_COLETA = getFormatodoComEspaco(8, formatarData(dataPersistent.get(i).getAlimentacao_dia_coleta()));
                String USUARIO = getFormatodoComEspaco(10, dataPersistent.get(i).getAlimentacao_usuario());
                String ENTREVISTADO = getFormatodoComEspaco(10, dataPersistent.get(i).getAlimentacao_entrevistado_id());
                String OBS = getFormatodoComEspaco(130, dataPersistent.get(i).getAlimentacao_obs());


                String formatado = ID_RECORDATORIO + ID_ALIMENTO + ALIMENTO_NOVO + ALIMENTO_DESCRICAO + ID_PREPARACAO + ID_UNIDADE + ID_ADICAO + ID_LOCAL + ID_CONSUMO + QUANTIDADE + HORA + HORA_COLETA + DATA_COLETA + USUARIO + ENTREVISTADO + OBS + "\n";
                fos.write(formatado.getBytes());

            }



            /////// salvar

            String DataCompleta;
            Time now = new Time();
            now.setToNow();
            DataCompleta = Integer.toString(now.year);
            DataCompleta = DataCompleta + "_" + Integer.toString(now.month + 1);
            DataCompleta = DataCompleta + "_" + Integer.toString(now.monthDay);
            DataCompleta = DataCompleta + "_" + Integer.toString(now.hour);
            DataCompleta = DataCompleta + "_" + Integer.toString(now.minute);
            DataCompleta = DataCompleta + "_" + Integer.toString(now.second);


            try {
                bkp(Modulo.caminhobanco, Modulo.storage + Modulo.NomeCopia + filtro_desc_pesquisa + DataCompleta + ".db");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Toast.makeText(ConfiguracaoActivity.this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            mDb.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_ALIMENTACAO );
            mDb.execSQL(DbCreate.CREATE_TABLE_ALIMENTACAO);



        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formataHora(String hora){
        try{
        if (hora != null){
            hora = hora.replace(":","");
            return hora;
        }else{
            return "0000";
        }

        }catch(Exception e){
            return  "00000000";
        }

    }

    private String formatarData(String data){
        try{

        String ano ="";
        String mes ="";
        String dia = "";
        if (data != null){
            if (data.length() > 0){
                int i = (data.indexOf("/"));
                if (i > 0) {

                    dia = data.substring(0, i);
                    mes = data.substring(i+1, data.length());

                    int ii = (mes.indexOf("/"));
                    ano = mes.substring(0,mes.length());
                    mes = mes.substring(0,ii );



                    ano = ano.substring(ii+1, ano.length());
                }

            }
        }

        if (mes.length() == 1){
            mes = "0" + mes;
        }

        if (dia.length() == 1){
            dia = "0" + dia;
        }


        return  ano + mes +dia;

        }catch(Exception e){
            return  "00000000";
        }
    }

    private void bkp(String Origem, String Destino) throws IOException {
        final String inFileName = Origem;

        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);


        //String outFileName = Environment.getExternalStoragePublicDirectory()+"/database_copy.db";
        String outFileName = Destino;

        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        // Close the streams
        output.flush();
        output.close();
        fis.close();

    }

    private String alimentoENovo(String aliemntoENovoID){
        mDataBase = new DataBase(ConfiguracaoActivity.this);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(ConfiguracaoActivity.this);
        mDataBase.getListAlimentoID(aliemntoENovoID);
        if (mDataBase.getListAlimentoID(aliemntoENovoID) > 0){
            return "1";
        }else{
            return "0";
        }

    }

    private String getFormatodoComEspaco(int espaco, String valor){
        String resultado = valor;

        int i = valor.length();
        while (i < espaco){
            resultado = " " + resultado;
            i++;
        }

        return resultado;
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private void getJson(){

        /**
         * checks if internet is ok .
         */
        if (Common.isOnline(getSystemService(Context.CONNECTIVITY_SERVICE))) {

            create_API_ADICAO(Url.BASE_URL_ADICAO);
            mInterfaceADICAO.getAdicao().enqueue(ADICAOCallback);

            create_API_ALIMENTO(Url.BASE_URL_ALIMENTO);
            mInterfaceALIMENTO.getAlimento().enqueue(ALIMENTOCallback);

            create_API_ENTREVISTADO(Url.BASE_URL_ENTREVISTADOR);
            mInterfaceENTREVISTADO.getentrevistado().enqueue(ENTREVISTADOCallback);

            create_API_LOCAL(Url.BASE_URL_LOCAL);
            mInterfaceLOCAL.getLocal().enqueue(LOCALCallback);

            create_API_OCASIAO_CONSUMO(Url.BASE_URL_OCASIAO_CONSUMO);
            mInterfaceOCASIAO_CONSUMO.getOcasoioConsumo().enqueue(OCASIAO_CONSUMOCallback);

            create_API_PREPARACAO(Url.BASE_URL_PREPARACAO);
            mInterfacePREPARACAO.getPreparacao().enqueue(PREPARACAOCallback);

            create_API_PREPARACAO_ALIMENTO(Url.BASE_URL_PREPARACAO_PREPARACAO);
            mInterfacePREPARACAO_ALIMENTO.getPreparacaoAliemnto().enqueue(PREPARACAO_ALIMENTOCallback);

            create_API_UNIDADE(Url.BASE_URL_UNIDADE);
            mInterfaceUNIDADE.getUnidade().enqueue(UNIDADECallback);

            create_API_UNIADE_ALIMENTO(Url.BASE_URL_UNIDADE_UNIDADE);
            mInterfaceUNIDADE_ALIMENTO.getUniadeAliemnto().enqueue(UNIDADE_ALIMENTOCallback);


            create_API_ADICAO_ALIMENTO(Url.BASE_URL_ADICAO_ADICAO);
            mInterfaceADICAO_ALIMENTO.getAdicaoAlimento().enqueue(ADICAO_ALIMENTOCallback);
        } else {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, R.string.Error_Access, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * *********************ALIMENTO******************************* .
     */
    private void create_API_ALIMENTO(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceALIMENTO = retrofit.create(InterfaceAlimento.class);
    }

    private Callback<ListWrapper<Alimento>> ALIMENTOCallback = new Callback<ListWrapper<Alimento>>() {
        @Override
        public void onResponse(Call<ListWrapper<Alimento>> call, Response<ListWrapper<Alimento>> response) {
            try {
                if (response.isSuccessful()) {
                    List<Alimento> data = new ArrayList<>();
                    data.addAll(response.body().items);

                    // Persistent Data for SQLLite
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    mDataBase.insertTABLE_ALIMENTO(data);

                    List<Alimento> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListAlimento();

                    mAdapterALIMENTO = new AlimentoAdapter(dataPersistent);

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
        public void onFailure(Call<ListWrapper<Alimento>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    /**
     * *********************ADICAO******************************* .
     */
    private void create_API_ADICAO(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceADICAO = retrofit.create(InterfaceAdicao.class);
    }

    private Callback<ListWrapper<Adicao>> ADICAOCallback = new Callback<ListWrapper<Adicao>>() {
        @Override
        public void onResponse(Call<ListWrapper<Adicao>> call, Response<ListWrapper<Adicao>> response) {
            try {
                if (response.isSuccessful()) {
                    List<Adicao> data = new ArrayList<>();
                    data.addAll(response.body().items);

                    // Persistent Data for SQLLite
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    mDataBase.insertTABLE_ADICAO(data);

                    List<Adicao> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListAdicao();

                    mAdapterADICAO = new AdicaoAdapter(dataPersistent);

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
        public void onFailure(Call<ListWrapper<Adicao>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    /**
     * *********************PREPARACAO******************************* .
     */
    private void create_API_PREPARACAO(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfacePREPARACAO = retrofit.create(InterfacePreparacao.class);
    }

    private Callback<ListWrapper<Preparacao>> PREPARACAOCallback = new Callback<ListWrapper<Preparacao>>() {
        @Override
        public void onResponse(Call<ListWrapper<Preparacao>> call, Response<ListWrapper<Preparacao>> response) {
            try {
                if (response.isSuccessful()) {
                    List<Preparacao> data = new ArrayList<>();
                    data.addAll(response.body().items);

                    // Persistent Data for SQLLite
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    mDataBase.insertTABLE_PREPARACAO(data);

                    List<Preparacao> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListPreparacao();

                    mAdapterPREPARACAO = new PreparacaoAdapter(dataPersistent);

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
        public void onFailure(Call<ListWrapper<Preparacao>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    /**
     * *********************PREPARACAO******************************* .
     */
    private void create_API_ENTREVISTADO(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceENTREVISTADO = retrofit.create(InterfaceEntrevistado.class);
    }

    private Callback<ListWrapper<Entrevistado>> ENTREVISTADOCallback = new Callback<ListWrapper<Entrevistado>>() {
        @Override
        public void onResponse(Call<ListWrapper<Entrevistado>> call, Response<ListWrapper<Entrevistado>> response) {
            try {
                if (response.isSuccessful()) {
                    List<Entrevistado> data = new ArrayList<>();
                    data.addAll(response.body().items);

                    // Persistent Data for SQLLite
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    mDataBase.insertTABLE_ENTREVISTADO(data);

                    List<Entrevistado> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListEntrevistado();

                    mAdapterENTREVISTADO = new EntrevistadoAdapter(dataPersistent);

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
        public void onFailure(Call<ListWrapper<Entrevistado>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    /**
     * *********************LOCAL******************************* .
     */
    private void create_API_LOCAL(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceLOCAL = retrofit.create(InterfaceLocal.class);
    }

    private Callback<ListWrapper<Local>> LOCALCallback = new Callback<ListWrapper<Local>>() {
        @Override
        public void onResponse(Call<ListWrapper<Local>> call, Response<ListWrapper<Local>> response) {
            try {
                if (response.isSuccessful()) {
                    List<Local> data = new ArrayList<>();
                    data.addAll(response.body().items);

                    // Persistent Data for SQLLite
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    mDataBase.insertTABLE_LOCAL(data);

                    List<Local> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListLocal();

                    mAdapterLOCAL = new LocalAdapter(dataPersistent);

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
        public void onFailure(Call<ListWrapper<Local>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    /**
     * *********************OCASIAO CONSUMO******************************* .
     */
    private void create_API_OCASIAO_CONSUMO(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceOCASIAO_CONSUMO = retrofit.create(InterfaceOcasiaoConsumo.class);
    }

    private Callback<ListWrapper<OcasiaoConsumo>> OCASIAO_CONSUMOCallback = new Callback<ListWrapper<OcasiaoConsumo>>() {
        @Override
        public void onResponse(Call<ListWrapper<OcasiaoConsumo>> call, Response<ListWrapper<OcasiaoConsumo>> response) {
            try {
                if (response.isSuccessful()) {
                    List<OcasiaoConsumo> data = new ArrayList<>();
                    data.addAll(response.body().items);

                    // Persistent Data for SQLLite
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    mDataBase.insertTABLE_OCASIAO_CONSUMO(data);

                    List<OcasiaoConsumo> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListOcasiaoConsumo();

                    mAdapterOCASIAO_CONSUMO = new OcasiaoConsumoAdapter(dataPersistent);

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
        public void onFailure(Call<ListWrapper<OcasiaoConsumo>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    /**
     * *********************UNIDADE******************************* .
     */
    private void create_API_UNIDADE(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceUNIDADE = retrofit.create(InterfaceUnidade.class);
    }

    private Callback<ListWrapper<Unidade>> UNIDADECallback = new Callback<ListWrapper<Unidade>>() {
        @Override
        public void onResponse(Call<ListWrapper<Unidade>> call, Response<ListWrapper<Unidade>> response) {
            try {
                if (response.isSuccessful()) {
                    List<Unidade> data = new ArrayList<>();
                    data.addAll(response.body().items);

                    // Persistent Data for SQLLite
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    mDataBase.insertTABLE_UNIDADE(data);

                    List<Unidade> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListUnidade();

                    mAdapterUNIDADE = new UnidadeAdapter(dataPersistent);

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
        public void onFailure(Call<ListWrapper<Unidade>> call, Throwable t) {
            t.printStackTrace();
        }
    };




    /**
     * *********************ADICAO_ALIMENTO******************************* .
     */
    private void create_API_ADICAO_ALIMENTO(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceADICAO_ALIMENTO = retrofit.create(InterfaceAdicaoAlimento.class);
    }

    private Callback<ListWrapper<AdicaoAlimento>> ADICAO_ALIMENTOCallback = new Callback<ListWrapper<AdicaoAlimento>>() {
        @Override
        public void onResponse(Call<ListWrapper<AdicaoAlimento>> call, Response<ListWrapper<AdicaoAlimento>> response) {
            try {
                if (response.isSuccessful()) {
                    List<AdicaoAlimento> data = new ArrayList<>();
                    data.addAll(response.body().items);

                    // Persistent Data for SQLLite
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    mDataBase.insertTABLE_ADICAO_ALIMENTO(data);



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
        public void onFailure(Call<ListWrapper<AdicaoAlimento>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    /**
     * *********************PREPARACAO ALIEMNTO******************************* .
     */
    private void create_API_PREPARACAO_ALIMENTO(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfacePREPARACAO_ALIMENTO = retrofit.create(InterfacePreparacaoAlimento.class);
    }

    private Callback<ListWrapper<PreparacaoAlimento>> PREPARACAO_ALIMENTOCallback = new Callback<ListWrapper<PreparacaoAlimento>>() {
        @Override
        public void onResponse(Call<ListWrapper<PreparacaoAlimento>> call, Response<ListWrapper<PreparacaoAlimento>> response) {
            try {
                if (response.isSuccessful()) {
                    List<PreparacaoAlimento> data = new ArrayList<>();
                    data.addAll(response.body().items);

                    // Persistent Data for SQLLite
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    mDataBase.insertTABLE_PREPARACAO_ALIMENTO(data);


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
        public void onFailure(Call<ListWrapper<PreparacaoAlimento>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    /**
     * *********************OCASIAO CONSUMO******************************* .
     */
    private void create_API_UNIADE_ALIMENTO(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceUNIDADE_ALIMENTO = retrofit.create(InterfaceUnidadeAlimento.class);
    }

    private Callback<ListWrapper<UnidadeAlimento>> UNIDADE_ALIMENTOCallback = new Callback<ListWrapper<UnidadeAlimento>>() {
        @Override
        public void onResponse(Call<ListWrapper<UnidadeAlimento>> call, Response<ListWrapper<UnidadeAlimento>> response) {
            try {
                if (response.isSuccessful()) {
                    List<UnidadeAlimento> data = new ArrayList<>();
                    data.addAll(response.body().items);

                    // Persistent Data for SQLLite
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    mDataBase.insertTABLE_UNIDADE_ALIMENTO(data);



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
        public void onFailure(Call<ListWrapper<UnidadeAlimento>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    public static String getAvailableExternalMemorySize(String caminhoStorage) {
        if (externalMemoryAvailable()) {
            //File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(caminhoStorage);
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return formatSize(availableBlocks * blockSize);
        } else {
            return "ERROR";
        }
    }

    public static String getTotalExternalMemorySize(String caminhoStorage) {
        if (externalMemoryAvailable()) {
            //File path = Environment.getExternalStorageDirectory();
            //StatFs stat = new StatFs(path.getPath());
            StatFs stat = new StatFs(caminhoStorage);
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return formatSize(totalBlocks * blockSize);
        } else {
            return "ERROR";
        }
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, '.');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

}
