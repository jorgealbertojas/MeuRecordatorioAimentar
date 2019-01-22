package com.softjads.jorge.meurecordatorio;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.softjads.jorge.meurecordatorio.Adapter.AdicaoAdapter;
import com.softjads.jorge.meurecordatorio.Adapter.AlimentoAdapter;
import com.softjads.jorge.meurecordatorio.Adapter.EntrevistadoAdapter;
import com.softjads.jorge.meurecordatorio.Adapter.LocalAdapter;
import com.softjads.jorge.meurecordatorio.Adapter.OcasiaoConsumoAdapter;
import com.softjads.jorge.meurecordatorio.Adapter.PreparacaoAdapter;
import com.softjads.jorge.meurecordatorio.Adapter.UnidadeAdapter;
import com.softjads.jorge.meurecordatorio.Interface.InterfaceAdicao;
import com.softjads.jorge.meurecordatorio.Interface.InterfaceAdicaoAlimento;
import com.softjads.jorge.meurecordatorio.Interface.InterfaceAlimento;
import com.softjads.jorge.meurecordatorio.Interface.InterfaceEntrevistado;
import com.softjads.jorge.meurecordatorio.Interface.InterfaceLocal;
import com.softjads.jorge.meurecordatorio.Interface.InterfaceOcasiaoConsumo;
import com.softjads.jorge.meurecordatorio.Interface.InterfacePreparacao;
import com.softjads.jorge.meurecordatorio.Interface.InterfacePreparacaoAlimento;
import com.softjads.jorge.meurecordatorio.Interface.InterfaceUnidade;
import com.softjads.jorge.meurecordatorio.Interface.InterfaceUnidadeAlimento1;
import com.softjads.jorge.meurecordatorio.Interface.InterfaceUnidadeAlimento2;
import com.softjads.jorge.meurecordatorio.Interface.InterfaceUnidadeAlimento3;
import com.softjads.jorge.meurecordatorio.Model.Adicao;
import com.softjads.jorge.meurecordatorio.Model.AdicaoAlimento;
import com.softjads.jorge.meurecordatorio.Model.Alimentacao;
import com.softjads.jorge.meurecordatorio.Model.Alimento;
import com.softjads.jorge.meurecordatorio.Model.Entrevistado;
import com.softjads.jorge.meurecordatorio.Model.ListWrapper;
import com.softjads.jorge.meurecordatorio.Model.Local;
import com.softjads.jorge.meurecordatorio.Model.OcasiaoConsumo;
import com.softjads.jorge.meurecordatorio.Model.Preparacao;
import com.softjads.jorge.meurecordatorio.Model.PreparacaoAlimento;
import com.softjads.jorge.meurecordatorio.Model.Unidade;
import com.softjads.jorge.meurecordatorio.Model.UnidadeAlimento;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.softjads.jorge.meurecordatorio.Utilite.Modulo.filename;

public class ConfiguracaoActivity extends AppCompatActivity {


    File myExternalFile;

    private static boolean nOK = false;

    int LIMITE = 12;

    int count = 0;

    int pcountProgress = 0;

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

    private InterfaceUnidadeAlimento1 mInterfaceUNIDADE_ALIMENTO1;
    private InterfaceUnidadeAlimento2 mInterfaceUNIDADE_ALIMENTO2;
    private InterfaceUnidadeAlimento3 mInterfaceUNIDADE_ALIMENTO3;


    MyControlThread mThread;
    ProgressDialog mDialog;
    Handler mHandler;


    private InterfaceOcasiaoConsumo mInterfaceOCASIAO_CONSUMO;
    OcasiaoConsumoAdapter mAdapterOCASIAO_CONSUMO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        mDataBase = new DataBase(ConfiguracaoActivity.this);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(ConfiguracaoActivity.this);

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

                           // getJson();

                            ativaThread();

                          //  Toast.makeText(ConfiguracaoActivity.this, "Atualizado alimentos com sucesso!" , Toast.LENGTH_SHORT).show();
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

                    LayoutInflater factory = LayoutInflater.from(ConfiguracaoActivity.this);
                    final View deleteDialogView = factory.inflate(
                            R.layout.custom_dialog, null);
                    final AlertDialog deleteDialog1 = new AlertDialog.Builder(ConfiguracaoActivity.this).create();
                    deleteDialog1.setView(deleteDialogView);

                    TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                    nTextView.setText("Tem certeza que deseja finalizar a entrevista?");

                    deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {




                            LayoutInflater factory = LayoutInflater.from(ConfiguracaoActivity.this);
                            final View deleteDialogView = factory.inflate(
                                    R.layout.custom_dialog, null);
                            final AlertDialog deleteDialog = new AlertDialog.Builder(ConfiguracaoActivity.this).create();
                            deleteDialog.setView(deleteDialogView);

                            TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                            nTextView.setText("ATENÇÃO! Depois de encerrar não poderá mais adicionar alimentos para esses entrevistados! Tem certeza que deseja encerrar?");

                            deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    try {
                                        gerarArquivoTXT();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


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
                            deleteDialog1.dismiss();
                        }
                    });
                    deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            deleteDialog1.dismiss();

                        }
                    });

                    deleteDialog1.show();


                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
    }

    private void gerarArquivoTXT() throws IOException {

        salvearquivo();

    }


    private void salvearquivo() throws IOException {

        String DataCompleta1;
        Time now1 = new Time();
        now1.setToNow();
        DataCompleta1 = Integer.toString(now1.year);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.month + 1);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.monthDay);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.hour);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.minute);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.second);


        myExternalFile = new File(Modulo.storageCliente, filename);


        OutputStreamWriter outStreamWriter = null;
        FileOutputStream outStream = null;



        if ( myExternalFile.exists() == false ){
            myExternalFile.createNewFile();
        }

        outStream = new FileOutputStream(myExternalFile,true) ;
        outStreamWriter = new OutputStreamWriter(outStream);


        try {
            String ENTREVISTADO_E_NOVO = "0";
            int contadorDiferente = 0;
            List<Alimentacao> dataPersistent = new ArrayList<>();
            dataPersistent = mDataBase.getListAlimentacao();
            for (int i = 0; i < dataPersistent.size(); i++) {
                if (!(ENTREVISTADO_E_NOVO.toString().equals(dataPersistent.get(i).getAlimentacao_entrevistado_id()))){
                    contadorDiferente = 0;
                }
                contadorDiferente ++;
                ENTREVISTADO_E_NOVO = dataPersistent.get(i).getAlimentacao_entrevistado_id();


                String IDENTIFICADOR = getFormatodoComEspaco(2, "50");
                String ENTREVISTADO = getFormatodoComEspaco(32, dataPersistent.get(i).getAlimentacao_entrevistado_id());
                String SEQUENCIAL_ALIMENTO = getFormatodoComEspaco(3, Integer.toString(contadorDiferente));
                String ID_RECORDATORIO = getFormatodoComEspaco(3, dataPersistent.get(i).getAlimentacao_id());
                String ID_ALIMENTO  = getFormatodoComEspaco(8, dataPersistent.get(i).getAlimentacao_alimento_id());
                String ALIMENTO_NOVO = getFormatodoComEspaco(2, alimentoENovo(dataPersistent.get(i).getAlimentacao_alimento_id()));
                String ALIMENTO_DESCRICAO = getFormatodoComEspacoDireita(90, dataPersistent.get(i).getAlimentacao_alimento());
                String ID_PREPARACAO = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_preparacao_id());
                String ID_UNIDADE = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_unidade_id());
                String ID_ADICAO = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_adicao_id());
                String ID_LOCAL = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_local_id());
                String ID_CONSUMO = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_ocasiao_consumo_id());
                String QUANTIDADE = getFormatodoComEspaco(10, dataPersistent.get(i).getAlimentacao_quantidade());
                String HORA = getFormatodoComEspacoHORA(4, dataPersistent.get(i).getAlimentacao_hora());
                String HORA_COLETA = getFormatodoComEspaco(6, formataHora(dataPersistent.get(i).getAlimentacao_hora_coleta()));
                String DATA_COLETA = getFormatodoComEspaco(8, formatarData(dataPersistent.get(i).getAlimentacao_dia_coleta()));
                String USUARIO = getFormatodoComEspacoDireita(20, dataPersistent.get(i).getAlimentacao_usuario());
                String OBS = getFormatodoComEspacoDireita(130, dataPersistent.get(i).getAlimentacao_obs());
                String ESPESSURA = getFormatodoComEspacoDireita(20, dataPersistent.get(i).getAlimentacao_espessura());
                String GRAU_PARENTESCO = getFormatodoComEspacoDireita(20, dataPersistent.get(i).getAlimentacao_grau_parentesco_id());

                String DIA_ATIPICO = dataPersistent.get(i).getAlimentacao_dia_atico();
                if (DIA_ATIPICO.equals("sim")){
                    DIA_ATIPICO = "1";
                }else{
                    DIA_ATIPICO = "2";
                }




                String formatado = IDENTIFICADOR + ENTREVISTADO  + SEQUENCIAL_ALIMENTO + ID_RECORDATORIO + ID_ALIMENTO + ALIMENTO_NOVO + ALIMENTO_DESCRICAO + ID_PREPARACAO + ID_UNIDADE + ID_ADICAO + ID_LOCAL + ID_CONSUMO + QUANTIDADE + HORA + HORA_COLETA + DATA_COLETA + USUARIO + OBS + ESPESSURA + GRAU_PARENTESCO + DIA_ATIPICO + "\n";


                outStreamWriter.append(formatado);
                outStreamWriter.flush();



            }

            bkp();
            Toast.makeText(ConfiguracaoActivity.this, "Encerrado com sucesso!", Toast.LENGTH_SHORT).show();







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

    private void bkp(){
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



        mDb.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_ALIMENTACAO );
        mDb.execSQL(DbCreate.CREATE_TABLE_ALIMENTACAO);

        mDb.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_ENTREVISTADO );
        mDb.execSQL(DbCreate.CREATE_TABLE_ENTREVISTADO);


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

    private String getFormatodoComEspacoHORA(int espaco, String valor){


        if (valor != null){
            if (valor.length()>0){
               if (valor.indexOf(":") == 1){
                   valor = " " + valor;
                   valor = valor.replace(":","");
               }
            }
        }

        int i = valor.length();
        while (i < espaco){
            valor = " " + valor;
            i++;
        }

        return valor;
    }

    private String getFormatodoComEspacoDireita(int espaco, String valor){
        String resultado = valor;

        int i = valor.length();
        while (i < espaco){
            resultado = resultado +  " ";
            i++;
        }

        if(resultado != null ){
            if (resultado.length() > espaco) {
                resultado = resultado.substring(0,espaco -1);
            }
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

            create_API_UNIADE_ALIMENTO1(Url.BASE_URL_UNIDADE_UNIDADE);
            mInterfaceUNIDADE_ALIMENTO1.getUniadeAliemnto().enqueue(UNIDADE_ALIMENTOCallback1);

            create_API_UNIADE_ALIMENTO2(Url.BASE_URL_UNIDADE_UNIDADE);
            mInterfaceUNIDADE_ALIMENTO2.getUniadeAliemnto().enqueue(UNIDADE_ALIMENTOCallback2);

            create_API_UNIADE_ALIMENTO3(Url.BASE_URL_UNIDADE_UNIDADE);
            mInterfaceUNIDADE_ALIMENTO3.getUniadeAliemnto().enqueue(UNIDADE_ALIMENTOCallback3);


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

                    count ++;

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

                    count ++;

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

                    count ++;

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

                    count ++;

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

                    count ++;

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

                    count ++;

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

                    count ++;

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

                    count ++;



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

                    count ++;


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
    private void create_API_UNIADE_ALIMENTO1(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceUNIDADE_ALIMENTO1 = retrofit.create(InterfaceUnidadeAlimento1.class);
    }

    private void create_API_UNIADE_ALIMENTO2(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceUNIDADE_ALIMENTO2 = retrofit.create(InterfaceUnidadeAlimento2.class);
    }

    private void create_API_UNIADE_ALIMENTO3(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceUNIDADE_ALIMENTO3 = retrofit.create(InterfaceUnidadeAlimento3.class);
    }

    private Callback<ListWrapper<UnidadeAlimento>> UNIDADE_ALIMENTOCallback1 = new Callback<ListWrapper<UnidadeAlimento>>() {
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

                    count ++;



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

    private Callback<ListWrapper<UnidadeAlimento>> UNIDADE_ALIMENTOCallback2 = new Callback<ListWrapper<UnidadeAlimento>>() {
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

                    count ++;



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

    private Callback<ListWrapper<UnidadeAlimento>> UNIDADE_ALIMENTOCallback3 = new Callback<ListWrapper<UnidadeAlimento>>() {
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

                    count ++;



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




    private class MythreadWeservice implements Runnable {


        public MythreadWeservice() {

        }

        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mDialog.setMessage("Atualizando sistema aguarde... ");
                }
            });



            //Webservice v = new Webservice(SOAP_ADDRESS);
            //v.getEstados();
            try {


                getJson();


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {

                }
            });
        }

    }

    private class MyControlThread extends Thread {
        private int numTasks;

        public MyControlThread(int tasks) {
            this.numTasks = tasks;
        }

        @Override
        public void run() {
            ExecutorService executor =  Executors.newSingleThreadExecutor();
            //for (int i = 1; i <= numTasks; i++) {
            //Runnable worker = new MyTask("task" +i, 5);
            //executor.submit(worker);
            //}

            //Webservice v = new Webservice("http://146.164.25.139/EricaWebServ/EricaSrv.asmx");
            //v.getEstados();

            Runnable worker = new MythreadWeservice();
            executor.submit(worker);

            executor.shutdown();
            while (!executor.isTerminated() || count<LIMITE) {
                try {
                    Thread.sleep(1000);
                    pcountProgress ++;


                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.setMessage("Atualizando sistema aguarde... \n" + Integer.toString(count)  + " de 12" );
                        }
                    });


                    mDialog.setProgress(pcountProgress);
                    mDialog.onContentChanged();

                } catch (InterruptedException e) {
                }
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();

                    Toast.makeText(ConfiguracaoActivity.this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void ativaThread() {
        mDialog = ProgressDialog.show(this, "Aguarde", "Processando...", true, false);
        mHandler = new Handler();
        count = 0;
        mDialog.setMax(100);
        mThread = new MyControlThread(LIMITE);
        mThread.start();
    }





}
