package com.example.jorge.meurecordatorio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.jorge.meurecordatorio.Generica.AlimentoActivity;
import com.example.jorge.meurecordatorio.Generica.EntrevistadoActivity;
import com.example.jorge.meurecordatorio.Generica.UnidadeActivity;
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
import com.example.jorge.meurecordatorio.Utilite.CrunchifyJSONFileWrite;
import com.example.jorge.meurecordatorio.Utilite.Modulo;
import com.example.jorge.meurecordatorio.Utilite.Url;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private DataBase mDataBase;

    public static String mUsuario = "0";
    public final static String PUT_EXTRA_ENTREVISTADO = "PUT_EXTRA_ENTREVISTADO";
    public final static String PUT_EXTRA_ENTREVISTADO_NOME = "PUT_EXTRA_ENTREVISTADO_NOME";
    public final static String PUT_EXTRA_USUARIO = "PUT_EXTRA_USUARIO";
    public final static String PUT_EXTRA_ALIMENTO = "PUT_EXTRA_ALIMENTO";
    public final static String PUT_EXTRA_ETAPA = "PUT_EXTRA_ETAPA";

    public final static String PUT_BUNDLE_ALIMENTACAO = "PUT_BUNDLE_ALIMENTACAO";
    public final static String PUT_EXTRA_ALIMENTACAO = "PUT_EXTRA_ALIMENTACAO";

    public final static String PUT_EXTRA_GRAU_PARENTESCO = "PUT_EXTRA_GRAU_PARENTESCO";
    public final static String PUT_EXTRA_DIA_ATIPICO = "PUT_EXTRA_DIA_ATIPICO";

    public RecyclerView mRecyclerView;

    private TextView tv_quantity;

    private TextView ListaEntrevistado;
    private TextView entrevistado;
    private TextView entrevistado_nome;

    private TextView TextViewAdiciona;

     private ImageView mMenu;

     private static TextView TextViewPasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carregarsuario_login();

        CrunchifyJSONFileWrite CrunchifyJSONFileWrite = new CrunchifyJSONFileWrite();
        try {
            CrunchifyJSONFileWrite.main();
        } catch (IOException e) {
            e.printStackTrace();
        }


        tv_quantity = (TextView) findViewById(R.id.tv_quantity);

        if (savedInstanceState == null){
            mDataBase = new DataBase(this);
            mDb = mDataBase.getReadableDatabase();
            DbInstance.getInstance(this);

            if(savedInstanceState == null){
                Intent WSActivity = new Intent(this, LoginActivity.class);
                startActivity(WSActivity);

            }

        }

        TextViewPasso = (TextView) findViewById(R.id.TextViewPasso);
        TextViewPasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    if (TextViewPasso.getTag().equals("0")){
                        TextViewPasso.setText("Finalizar entrada Alimento");
                        TextViewPasso.setTag("1");
                        TextViewPasso.setBackground(getResources().getDrawable(R.drawable.rounded_corner_red));
                        TextViewAdiciona.setVisibility(View.VISIBLE);
                        TextViewAdiciona.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                        TextViewAdiciona.setText("Adicionar alimento");
                    }else if (TextViewPasso.getTag().equals("1")){



                        try {





                            LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                            final View deleteDialogView = factory.inflate(
                                    R.layout.custom_dialog1, null);
                            final AlertDialog deleteDialog1 = new AlertDialog.Builder(MainActivity.this).create();
                            deleteDialog1.setView(deleteDialogView);

                            TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                            nTextView.setText("ATENÇÃO!\n Perguntar se houve adição de açúcar ou outra substância com o intuito de adoçar as bebidas, preparações ou alimentos. \n Ou se teve mais algum alimento! \nCaso não tenha mais nenhum alimento aperte em SIM! \n Ou aperte em NÃO para voltar e inserir mais alimentos");

                            deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {


                                    LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                                    final View deleteDialogView2 = factory.inflate(
                                            R.layout.custom_dialog2, null);
                                    final AlertDialog deleteDialog2 = new AlertDialog.Builder(MainActivity.this).create();
                                    deleteDialog2.setView(deleteDialogView2);

                                    TextView nTextView2 = (TextView) deleteDialogView2.findViewById(R.id.txt_dia);
                                    nTextView2.setText("ATENÇÃO! \n Perguntar ao adolescente se consumiu algum alimento que não tenha sido relatado, como: balas, chicletes, biscoitos, bebidas, doces, manteigas/margarina, em outros! \n Caso não tenha mais nenhum aliemnto aperte em SIM! \n Ou aperte em NÃO para voltar e inserir mais alimentos");

                                    deleteDialogView2.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            TextViewPasso.setText("Iniciar coleta complementos");
                                            TextViewPasso.setTag("2");
                                            TextViewPasso.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                                            TextViewAdiciona.setVisibility(View.GONE);
                                            deleteDialog2.dismiss();


                                        }
                                    });
                                    deleteDialogView2.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            deleteDialog2.dismiss();

                                        }
                                    });

                                    deleteDialog2.show();
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
                            Toast.makeText(MainActivity.this, "ATENÇÃO! Problema reinicie o sistema" , Toast.LENGTH_LONG).show();
                        }


                    }else if (TextViewPasso.getTag().equals("2")){
                        TextViewPasso.setText("Finalizar coleta");
                        TextViewPasso.setTag("3");
                        TextViewPasso.setBackground(getResources().getDrawable(R.drawable.rounded_corner_red));
                        TextViewAdiciona.setVisibility(View.VISIBLE);
                        TextViewAdiciona.setBackground(getResources().getDrawable(R.drawable.rounded_corner_yellow));
                        TextViewAdiciona.setText("Esqueci de adicionar alimento");
                    }else if (TextViewPasso.getTag().equals("3")){
                        TextViewPasso.setTag("0");
                        TextViewPasso.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                        TextViewPasso.setText("Iniciar escolha");
                        TextViewAdiciona.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "ATENÇÃO! Coleta encerrada pode fazer a revisão, caso necessário pode iniciar os passos novamente" , Toast.LENGTH_LONG).show();
                    }

                    Modulo.ETAPA = TextViewPasso.getTag().toString();

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });




        mMenu = (ImageView) findViewById(R.id.imageViewMenu);
        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    Class destinationClass = ConfiguracaoActivity.class;
                    Intent intentToStartConfiguracaoActivity= new Intent(getBaseContext(), destinationClass);
                    startActivity(intentToStartConfiguracaoActivity);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        // Entrevistado
        entrevistado_nome = (TextView)  findViewById(R.id.entrevistado_nome);
        entrevistado = (TextView)  findViewById(R.id.entrevistado);
        entrevistado.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(!s.equals("") ) {
                    iniciaRecyclerView();
                }
            }



            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });

        ListaEntrevistado = (TextView)  findViewById(R.id.TextViewEntrevistadoButton);
        ListaEntrevistado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showEntrevistado();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });


        /**
         * use RecyclerView for list the PullRequest .
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_alimento);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDataBase = new DataBase(this);

       // iniciaRecyclerView();

        TextViewAdiciona = (TextView) findViewById(R.id.TextViewAdiciona);
        TextViewAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!entrevistado.getText().toString().equals("0")) {
                    try {

                        Modulo.OPCAO = "0";
                        Class destinationClass = DetailActivity.class;
                        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
                        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, entrevistado.getText().toString());
                        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO_NOME, entrevistado_nome.getText().toString());
                        intentToStartDetailActivity.putExtra(PUT_EXTRA_USUARIO, mUsuario);



                        intentToStartDetailActivity.putExtra(PUT_EXTRA_ETAPA, TextViewPasso.getTag().toString());
                        startActivity(intentToStartDetailActivity);

                    } catch (Exception e) {
                        // TODO: handle exception
                    }



                }else{
                    Toast.makeText(MainActivity.this,"Escolha um entrevistado!",Toast.LENGTH_LONG).show();

                }
            }
        });



    }

    @Override
    protected void onResume() {
        iniciaRecyclerView();

        if (Modulo.OPCAO.equals("ENTREVISTADO")){
            entrevistado_nome.setText(Modulo.NOME);
            entrevistado.setText(Modulo.ID);
        }
        super.onResume();
    }




    private void iniciaRecyclerView(){
        List<Alimentacao> dataPersistent = new ArrayList<>();
        dataPersistent = mDataBase.getListAlimentacao(entrevistado.getText().toString());

        if (dataPersistent.size()>0) {
            mRecyclerView.setAdapter(new AlimentacaoAdapter(dataPersistent));
            tv_quantity.setText(Integer.toString(dataPersistent.size()));
        }else{
            mRecyclerView.setAdapter(null);
            tv_quantity.setText("0");
        }
    }




    private void showEntrevistado(){
        Class destinationClass = EntrevistadoActivity.class;
        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
        startActivity(intentToStartDetailActivity);
    }

    public void onStart() {
        super.onStart();

        if (!Modulo.Liberado) {
            this.finish();
            try {
                this.finalize();
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    };

    public void carregarsuario_login()
    {


        EditText nEditText = (EditText) findViewById(R.id.editEntrevistado);
        Properties properties = new Properties();
        try
        {
            FileInputStream fis;
            fis = new  FileInputStream(Modulo.nomeArquivoINI);
            properties.load(fis);
            mUsuario = properties.getProperty("conf.usuario_login");




        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }



}
