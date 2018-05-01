package com.example.jorge.meurecordatorio;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.meurecordatorio.Adapter.AdicaoAdapter;
import com.example.jorge.meurecordatorio.Adapter.AlimentacaoAdapter;
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
import com.example.jorge.meurecordatorio.PersistentData.DbInstance;
import com.example.jorge.meurecordatorio.Utilite.Common;
import com.example.jorge.meurecordatorio.Utilite.Url;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private DataBase mDataBase;
    public final static String PUT_EXTRA_ENTREVISTADO = "PUT_EXTRA_ENTREVISTADO";
    public final static String PUT_EXTRA_USUARIO = "PUT_EXTRA_USUARIO";
    public final static String PUT_EXTRA_ALIMENTO = "PUT_EXTRA_ALIMENTO";

    public RecyclerView mRecyclerView;

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

    private InterfaceUsuario mInterfaceUSUARIO;

    private InterfaceOcasiaoConsumo mInterfaceOCASIAO_CONSUMO;
    OcasiaoConsumoAdapter mAdapterOCASIAO_CONSUMO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            mDataBase = new DataBase(this);
            mDb = mDataBase.getReadableDatabase();
            mDataBase.onCreate(mDb);
            DbInstance.getInstance(this);

            getJson();
        }

        /**
         * use RecyclerView for list the PullRequest .
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_alimento);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDataBase = new DataBase(this);

       // iniciaRecyclerView();

        TextView TextViewAdiciona = (TextView) findViewById(R.id.TextViewAdiciona);
        TextViewAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    Class destinationClass = DetailActivity.class;
                    Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
                    intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, "1");
                    intentToStartDetailActivity.putExtra(PUT_EXTRA_USUARIO, "1");
                    startActivity(intentToStartDetailActivity);

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });



    }

    @Override
    protected void onResume() {
        iniciaRecyclerView();
        super.onResume();
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

            create_API_USUARIO(Url.BASE_URL_USUARIO);
            mInterfaceUSUARIO.getUsuario().enqueue(USUSARIOCallback);

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

                    mDataBase.insertTABLE_USUARIO(data);


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

    private void iniciaRecyclerView(){
        List<Alimentacao> dataPersistent = new ArrayList<>();
        dataPersistent = mDataBase.getListAlimentacao();

        if (dataPersistent.size()>0) {
            mRecyclerView.setAdapter(new AlimentacaoAdapter(dataPersistent));
        }
    }






}
