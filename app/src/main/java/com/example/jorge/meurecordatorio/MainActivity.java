package com.example.jorge.meurecordatorio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
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
import com.example.jorge.meurecordatorio.Generica.GrauParentescoActivity;
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
import com.example.jorge.meurecordatorio.PersistentData.DbSelect;
import com.example.jorge.meurecordatorio.PersistentData.Field;
import com.example.jorge.meurecordatorio.Utilite.Common;
import com.example.jorge.meurecordatorio.Utilite.CrunchifyJSONFileWrite;
import com.example.jorge.meurecordatorio.Utilite.EndlessRecyclerViewScrollListener;
import com.example.jorge.meurecordatorio.Utilite.FragmentViewPager;
import com.example.jorge.meurecordatorio.Utilite.Modulo;
import com.example.jorge.meurecordatorio.Utilite.Url;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.example.jorge.meurecordatorio.PersistentData.DbCreate.DB_NAME;
import static com.example.jorge.meurecordatorio.PersistentData.DbSelect.GET_USUARIO_PARAMETRO;
import static com.example.jorge.meurecordatorio.Utilite.Modulo.storageCliente;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private DataBase mDataBase;

    public int lastposition = 0;
    private boolean voltando = false;

    public List<Alimentacao> dataPersistent = null;

    public final static String PUT_EXTRA_ENTREVISTADO = "PUT_EXTRA_ENTREVISTADO";
    public final static String PUT_EXTRA_ENTREVISTADO_NOME = "PUT_EXTRA_ENTREVISTADO_NOME";
    public final static String PUT_EXTRA_USUARIO = "PUT_EXTRA_USUARIO";
    public final static String PUT_EXTRA_ALIMENTO = "PUT_EXTRA_ALIMENTO";
    public final static String PUT_EXTRA_ETAPA = "PUT_EXTRA_ETAPA";

    public final static String PUT_BUNDLE_ALIMENTACAO = "PUT_BUNDLE_ALIMENTACAO";
    public final static String PUT_EXTRA_ALIMENTACAO = "PUT_EXTRA_ALIMENTACAO";

    public final static String PUT_EXTRA_GRAU_PARENTESCO = "PUT_EXTRA_GRAU_PARENTESCO";
    public final static String PUT_EXTRA_GRAU_PARENTESCO_NOME = "PUT_EXTRA_GRAU_PARENTESCO_NOME";
    public final static String PUT_EXTRA_DIA_ATIPICO = "PUT_EXTRA_DIA_ATIPICO";
    public final static String PUT_EXTRA_POSITION= "PUT_EXTRA_POSITION";

    public RecyclerView mRecyclerView;
    public RecyclerView mRecyclerViewCheck;
    public RecyclerView mRecyclerViewCheckCompleto;

    public TextView frase;

    public static ViewPager mViewPager;

    private TextView tv_quantity;

    private TextView ListaEntrevistado;
    public TextView entrevistado;
    public TextView entrevistado_nome;

    public TextView diaAtipico;

    public TextView grauParentesco;
    public TextView grau_parentesco_nome;


    private ImageView imagepiscar;
    private ImageView mMenu;

    public static String NOME = "0";
    public static String ID = "0";
    public static String USUARIO = "0";

    AlimentacaoAdapter alimentacaoAdapter;

    public static ImageView proximo;
    public static ImageView anterior;

    public static TextView proximo_palavra;
    public static TextView anterior_palavra;

    public static TextView grau_parentesco_hint;
    public static TextView diaatipico_hint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //carregarsuario_login();

        frase = (TextView) findViewById(R.id.frase);

        grau_parentesco_hint = (TextView) findViewById(R.id.grau_parentesco_hint);
        diaatipico_hint = (TextView) findViewById(R.id.dia_atipico_hint);



     //   CrunchifyJSONFileWrite CrunchifyJSONFileWrite = new CrunchifyJSONFileWrite();
     //   try {
    //        CrunchifyJSONFileWrite.main(this);
     //   } catch (IOException e) {
    //        e.printStackTrace();
    //   }

        proximo = (ImageView) findViewById(R.id.proximo);
        anterior = (ImageView) findViewById(R.id.anterior);

        proximo_palavra = (TextView) findViewById(R.id.proximo_palavra);
        anterior_palavra = (TextView) findViewById(R.id.anterior_palavra);



        imagepiscar = (ImageView) findViewById(R.id.image);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                if (position == 0) {
                    diaAtipico.setVisibility(View.VISIBLE);
                    grau_parentesco_nome.setVisibility(View.VISIBLE);
                    grauParentesco.setVisibility(View.VISIBLE);
                    grau_parentesco_hint.setVisibility(View.VISIBLE);
                    diaatipico_hint.setVisibility(View.VISIBLE);

                    imagepiscar.setVisibility(View.INVISIBLE);
                    imagepiscar.setAnimation(null);

                    anterior.setVisibility(View.INVISIBLE);
                    proximo.setVisibility(View.VISIBLE);
                    anterior_palavra.setVisibility(View.INVISIBLE);
                    proximo_palavra.setVisibility(View.VISIBLE);
                    frase.setVisibility(View.GONE);
                }

                else if (position == 1) {

                    diaAtipico.setVisibility(View.GONE);
                    grau_parentesco_nome.setVisibility(View.GONE);
                    grauParentesco.setVisibility(View.GONE);
                    grau_parentesco_hint.setVisibility(View.GONE);
                    diaatipico_hint.setVisibility(View.GONE);

                    anterior.setVisibility(View.VISIBLE);
                    proximo.setVisibility(View.VISIBLE);
                    anterior_palavra.setVisibility(View.VISIBLE);
                    proximo_palavra.setVisibility(View.VISIBLE);


                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerViewCheck.setVisibility(View.GONE);
                    mRecyclerViewCheckCompleto.setVisibility(View.GONE);
                    frase.setVisibility(View.GONE);


                } else if (position == 2) {
                    diaAtipico.setVisibility(View.GONE);
                    grau_parentesco_nome.setVisibility(View.GONE);
                    grauParentesco.setVisibility(View.GONE);
                    grau_parentesco_hint.setVisibility(View.GONE);
                    diaatipico_hint.setVisibility(View.GONE);


                    anterior.setVisibility(View.VISIBLE);
                    proximo.setVisibility(View.VISIBLE);
                    anterior_palavra.setVisibility(View.VISIBLE);
                    proximo_palavra.setVisibility(View.VISIBLE);

                    imagepiscar.setVisibility(View.INVISIBLE);
                    imagepiscar.setAnimation(null);

                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerViewCheck.setVisibility(View.GONE);
                    mRecyclerViewCheckCompleto.setVisibility(View.GONE);

                    frase.setVisibility(View.GONE);



                } else if (position == 3) {

                    diaAtipico.setVisibility(View.GONE);
                    grau_parentesco_nome.setVisibility(View.GONE);
                    grauParentesco.setVisibility(View.GONE);
                    grau_parentesco_hint.setVisibility(View.GONE);
                    diaatipico_hint.setVisibility(View.GONE);


                    anterior.setVisibility(View.VISIBLE);
                    proximo.setVisibility(View.VISIBLE);
                    anterior_palavra.setVisibility(View.VISIBLE);
                    proximo_palavra.setVisibility(View.VISIBLE);

                    imagepiscar.setVisibility(View.INVISIBLE);
                    imagepiscar.setAnimation(null);

                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerViewCheck.setVisibility(View.VISIBLE);
                    mRecyclerViewCheckCompleto.setVisibility(View.GONE);

                    frase.setVisibility(View.GONE);

                    if (voltando) {
                        LayoutInflater factory2 = LayoutInflater.from(MainActivity.this);
                        final View deleteDialogView2 = factory2.inflate(
                                R.layout.custom_dialog10, null);
                        final AlertDialog deleteDialog2 = new AlertDialog.Builder(MainActivity.this).create();
                        deleteDialog2.setView(deleteDialogView2);

                        TextView nTextView2 = (TextView) deleteDialogView2.findViewById(R.id.txt_dia);
                        nTextView2.setText("Agora vamos falar sobre os horários e tipos de refeição (como café da manhã, almoço, lanche, jantar etc)");

                        deleteDialogView2.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                deleteDialog2.dismiss();
                            }
                        });

                        deleteDialog2.show();
                    }


                } else if (position == 4){

                    diaAtipico.setVisibility(View.GONE);
                    grau_parentesco_nome.setVisibility(View.GONE);
                    grauParentesco.setVisibility(View.GONE);
                    grau_parentesco_hint.setVisibility(View.GONE);
                    diaatipico_hint.setVisibility(View.GONE);


                    anterior.setVisibility(View.VISIBLE);
                    proximo.setVisibility(View.VISIBLE);
                    anterior_palavra.setVisibility(View.VISIBLE);
                    proximo_palavra.setVisibility(View.VISIBLE);

                    imagepiscar.setVisibility(View.INVISIBLE);
                    imagepiscar.setAnimation(null);

                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerViewCheck.setVisibility(View.GONE);
                    mRecyclerViewCheckCompleto.setVisibility(View.VISIBLE);
                    frase.setVisibility(View.VISIBLE);



                    if (voltando) {
/*                        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                        final View deleteDialogView = factory.inflate(
                                R.layout.custom_dialog10, null);
                        final AlertDialog deleteDialog1 = new AlertDialog.Builder(MainActivity.this).create();
                        deleteDialog1.setView(deleteDialogView);

                        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                        nTextView.setText("ATENÇÃO!\n Confira se existe alimentos imcompletos");

                        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                deleteDialog1.dismiss();
                            }
                        });

                        deleteDialog1.show();*/




                        LayoutInflater factory2 = LayoutInflater.from(MainActivity.this);
                        final View deleteDialogView2 = factory2.inflate(
                                R.layout.custom_dialog10, null);
                        final AlertDialog deleteDialog2 = new AlertDialog.Builder(MainActivity.this).create();
                        deleteDialog2.setView(deleteDialogView2);

                        TextView nTextView2 = (TextView) deleteDialogView2.findViewById(R.id.txt_dia);
                        nTextView2.setText("Agora vamos detalhar todos os alimentos que o (a) senhor (senhora) falou: modo de preparo, quantidades e local das refeições.");

                        deleteDialogView2.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                deleteDialog2.dismiss();
                            }
                        });

                        deleteDialog2.show();



                    }

                }else if (position == 5) {

                    diaAtipico.setVisibility(View.GONE);
                    grau_parentesco_nome.setVisibility(View.GONE);
                    grauParentesco.setVisibility(View.GONE);
                    grau_parentesco_hint.setVisibility(View.GONE);
                    diaatipico_hint.setVisibility(View.GONE);


                    anterior_palavra.setVisibility(View.VISIBLE);
                    proximo_palavra.setVisibility(View.INVISIBLE);
                    anterior.setVisibility(View.VISIBLE);
                    proximo.setVisibility(View.INVISIBLE);

                    imagepiscar.setVisibility(View.INVISIBLE);
                    imagepiscar.setAnimation(null);

                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerViewCheck.setVisibility(View.GONE);
                    mRecyclerViewCheckCompleto.setVisibility(View.GONE);

                    frase.setVisibility(View.GONE);


                    if (voltando) {
                        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                        final View deleteDialogView = factory.inflate(
                                R.layout.custom_dialog10, null);
                        final AlertDialog deleteDialog1 = new AlertDialog.Builder(MainActivity.this).create();
                        deleteDialog1.setView(deleteDialogView);

                        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                        nTextView.setText("Agora vou ler todos os alimentos, horários e refeições realizados por " + entrevistado_nome.getText() + " ontem, para que confirme se todas as informações foram registradas corretamente");

                        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                deleteDialog1.dismiss();
                            }
                        });

                        deleteDialog1.show();
                    }

                    if (alimentacaoAdapter.estaFaltando){
                        imagepiscar.setVisibility(View.VISIBLE);
                        imagepiscar = (ImageView) findViewById(R.id.image);
                        Animation animation = new AlphaAnimation((float) 1, 0); // Change alpha from fully visible to invisible
                        animation.setDuration(500); // duration - half a second
                        animation.setInterpolator(new LinearInterpolator()); // do not alter
                        // animation
                        // rate
                        animation.setRepeatCount(Animation.INFINITE); // Repeat animation
                        // infinitely
                        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the
                        // end so the button will
                        // fade back in
                        imagepiscar.startAnimation(animation);
                    }







                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (lastposition < mViewPager.getCurrentItem()){
                    voltando = true;
                }else{
                    voltando = false;
                }
                lastposition = mViewPager.getCurrentItem();
            }

        });





        tv_quantity = (TextView) findViewById(R.id.tv_quantity);

        if (savedInstanceState == null){
            mDataBase = new DataBase(this);
            mDb = mDataBase.getReadableDatabase();
            DbInstance.getInstance(this);
            // bebeto
            Entrar();

            // BEBETO
           // if(savedInstanceState == null){
             //   Intent WSActivity = new Intent(this, LoginActivity.class);
               // startActivity(WSActivity);

            //}

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // BEBETO
            if (!getEntrevistador()){
                Toast.makeText(this, "ATENÇÃO! ESTE SISTEMA SÓ PODE SER CHAMADO PELO SISTEMA DE PESQUISA CSPRO" , Toast.LENGTH_LONG).show();
                this.finish();
            }
        }



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
                    statRecyclerView();

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

        mRecyclerViewCheck = (RecyclerView) findViewById(R.id.rv_alimento_check);
        mRecyclerViewCheck.setHasFixedSize(true);
        mRecyclerViewCheck.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerViewCheckCompleto = (RecyclerView) findViewById(R.id.rv_alimento_check_completo);
        mRecyclerViewCheckCompleto.setHasFixedSize(true);
        mRecyclerViewCheckCompleto.setLayoutManager(new LinearLayoutManager(this));



        mDataBase = new DataBase(this);



        //creamos un nuevo adpater
        //ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
      /*  wcViewPagerIndicator.setAdapter(viewPagerAdapter);//aplicamos el adapter

        //obtenemos el viewpager y capturamos sus cambios en tiempo de ejecucuion
        wcViewPagerIndicator.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Modulo.ID_TAB_POSITION = position;

            }

            @Override
            public void onPageSelected(int position) {
                //NOTA: las posiciones del viewpager inician en 0
                //cambiamos el indicador a la posicion del viewpager

                Modulo.NOME = NOME;
                Modulo.ID = ID;
                wcViewPagerIndicator.setSelectedindicator(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

        diaAtipico =  (TextView) findViewById(R.id.dia_atipico);
        diaAtipico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {

                    if (diaAtipico.getText().toString().equals("NÃO")) {
                        diaAtipico.setText("SIM");
                    }else{
                        diaAtipico.setText("NÃO");
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        // OcasiaoCaonsumo
        grau_parentesco_nome = (TextView)  findViewById(R.id.grau_parentesco_nome);
        grauParentesco = (TextView)  findViewById(R.id.grau_parentesco);
        grauParentesco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showGrauParentesco();
                    statRecyclerView();
                    mViewPager.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        grau_parentesco_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showGrauParentesco();
                    statRecyclerView();
                    mViewPager.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });





    }

    @Override
    protected void onResume() {
        iniciaRecyclerView();

        if (Modulo.OPCAO.equals("ENTREVISTADO")){
            NOME = Modulo.NOME;
            ID = Modulo.ID;
            entrevistado_nome.setText(Modulo.NOME);
            entrevistado.setText(Modulo.ID);
        }else if (Modulo.OPCAO.equals("GRAU_PARENTESCO")){
            grau_parentesco_nome.setText(Modulo.NOME);
            grauParentesco.setText(Modulo.ID);
        }
        super.onResume();
    }




    public void iniciaRecyclerView(){
        imagepiscar.setVisibility(View.INVISIBLE);
        imagepiscar.setAnimation(null);
        dataPersistent = new ArrayList<>();
        dataPersistent = mDataBase.getListAlimentacao(entrevistado.getText().toString());

        if (dataPersistent != null ) {
            mRecyclerView.setAdapter(new AlimentacaoAdapter(dataPersistent,0));
            mRecyclerViewCheck.setAdapter(new AlimentacaoAdapter(dataPersistent,2));
            alimentacaoAdapter = (new AlimentacaoAdapter(dataPersistent,3));
            mRecyclerViewCheckCompleto.setAdapter(alimentacaoAdapter);
            tv_quantity.setText(Integer.toString(dataPersistent.size()));



        }else{
            mRecyclerView.setAdapter(null);
            mRecyclerViewCheck.setAdapter(null);
            mRecyclerViewCheckCompleto.setAdapter(null);
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

    public void statRecyclerView(){
        iniciaRecyclerView();

        if (dataPersistent.size() > 0){
            if (dataPersistent.get(0).getAlimentacao_dia_atico() == null){
                diaAtipico.setText("NÃO");
            }
            else if (dataPersistent.get(0).getAlimentacao_dia_atico().equals("null")){
                diaAtipico.setText("NÃO");
            }else{
                diaAtipico.setText(dataPersistent.get(0).getAlimentacao_dia_atico());
            }

            if (dataPersistent.get(0).getAlimentacao_grau_parentesco() == null) {
                grau_parentesco_nome.setText("0");
            }
            else if (dataPersistent.get(0).getAlimentacao_grau_parentesco().equals("null")){
                grau_parentesco_nome.setText("0");
            }else {
                grau_parentesco_nome.setText(dataPersistent.get(0).getAlimentacao_grau_parentesco());
            }

            if (dataPersistent.get(0).getAlimentacao_grau_parentesco_id() == null){
                grauParentesco.setText("0");
            }
            else if (dataPersistent.get(0).getAlimentacao_grau_parentesco_id().equals("null")){
                grauParentesco.setText("0");
            }else {
                grauParentesco.setText(dataPersistent.get(0).getAlimentacao_grau_parentesco_id());
            }



            mViewPager.setVisibility(View.VISIBLE);

        }
        else{
            grau_parentesco_nome.setText("0");
            grauParentesco.setText("NÃO");
            mViewPager.setVisibility(View.INVISIBLE);
        }
        if (mRecyclerView.getAdapter() == null) {

            LayoutInflater factory = LayoutInflater.from(MainActivity.this);
            final View deleteDialogView = factory.inflate(
                    R.layout.custom_dialog5, null);
            final AlertDialog deleteDialog1 = new AlertDialog.Builder(MainActivity.this).create();
            deleteDialog1.setView(deleteDialogView);

            TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
            nTextView.setText("ATENÇÃO!\n Agora eu gostaria que o(a) Sr(a). me dissesse tudo que " +  NOME + " comeu ou bebeu ontem, do momento em que acordou até a hora em que foi dormir. Caso " +  NOME + "  tenha acordado de madrugada, também gostaria de saber o que ele/ela comeu ou bebeu de madrugada. Não se preocupe com horário e quantidade agora, pois falaremos dos detalhes depois.");

            deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    deleteDialog1.dismiss();
                }
            });
            deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                    final View deleteDialogView10 = factory.inflate(
                            R.layout.custom_dialog1, null);
                    final AlertDialog deleteDialog10 = new AlertDialog.Builder(MainActivity.this).create();
                    deleteDialog10.setView(deleteDialogView10);

                    TextView nTextView = (TextView) deleteDialogView10.findViewById(R.id.txt_dia);
                    nTextView.setText("Tem certeza que deseja interromper a entrevista?");

                    deleteDialogView10.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            finish();
                            deleteDialog10.dismiss();
                        }
                    });
                    deleteDialogView10.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            deleteDialog10.dismiss();

                        }
                    });

                    deleteDialog10.show();


                    deleteDialog1.dismiss();

                }
            });

            deleteDialog1.show();
        }
    }



  /*  public void carregarsuario_login()
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
*/




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean getEntrevistador() {

        String fileName = "chave.txt";

        File fileExist = new File(storageCliente, "chave.txt");
        if (fileExist.exists()){


        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storageCliente, "chave.txt");

            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line;

            JSONArray items = new JSONArray();

            int i = 0;
            String id = "0";
            String id_crianca = "0";
            String nome_crianca = "0";
            String nome_mae = "0";
            String nome_USUARIO = "0";

            while ((line = br.readLine()) != null) {


                if (!line.isEmpty()) {
                    i++;
                }


                if ((i == 1) && (!line.isEmpty())){
                    id = line;
                }else if ((i == 2 && (!line.isEmpty()))){
                    id_crianca = line;
                }else if ((i == 3 && (!line.isEmpty()))){
                    nome_crianca = line;
                }else if (i == 4 && (!line.isEmpty())){
                    nome_mae = line;
                }else if (i == 5 && (!line.isEmpty())){
                    nome_USUARIO = line;
                }
                text.append(line);





            }

            Entrevistado entrevistado = new Entrevistado();
            entrevistado.setEntrevistado_id(id + id_crianca);
            entrevistado.setEntrevistado(nome_crianca);


            List<Entrevistado> data = new ArrayList<>();
            data.add(entrevistado);
            // Persistent Data for SQLLite
            mDataBase = new DataBase(this);

            if (!(mDataBase.getListEntrevistadoEXISTE(entrevistado.getEntrevistado_id()))) {
                mDataBase.insertTABLE_ENTREVISTADO(data);
            }


            Modulo.OPCAO = "ENTREVISTADO";
            Modulo.NOME = entrevistado.getEntrevistado();
            Modulo.ID = entrevistado.getEntrevistado_id();

            NOME = entrevistado.getEntrevistado();
            ID = entrevistado.getEntrevistado_id();
            USUARIO = nome_USUARIO;



            br.close();



        } catch (IOException e) {



            e.printStackTrace();



        } finally {

        }
        //bebeto
            fileExist.delete();
            return true;
        }else{
            return false;

        }
    }


    private void showGrauParentesco(){
        Class destinationClass = GrauParentescoActivity.class;
        Intent intentToStartDetailActivity = new Intent(this, destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_GRAU_PARENTESCO, grauParentesco.getText().toString());
        startActivity(intentToStartDetailActivity);
    }

    private void Entrar(){





            if (mDataBase.getListAlimento().size()==0) {
                String sAssets = "backup_REC24_JORGE.db";
                try {
                    restaura_bkp(sAssets);
                } catch (IOException e) {
                    e.printStackTrace();
                }

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

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return FragmentViewPager.newInstance("Identificação","Arraste para o lado esquerdo para ir ao Passo 1", R.mipmap.ic_id, pos,USUARIO);
                case 1:
                    return FragmentViewPager.newInstance("Passo 1 - Listagem rápida de alimentos","Adicionar Alimento.", R.mipmap.food, pos,USUARIO);
                case 2:
                    return FragmentViewPager.newInstance("Passo 2 - Listagem de alimentos comumente esquecidos", "Arraste para o lado esquerdo para ir ao Passo 3.", R.mipmap.add,pos,USUARIO);
                case 3:
                    return FragmentViewPager.newInstance("Passo 3 - Definição do Horário e Refeição", "Clique nos alimentos para definir horário e refeição",  R.mipmap.check, pos,USUARIO);
                case 4:
                    return FragmentViewPager.newInstance("Passo 4 - Revisão do ciclo de detalhamento", "Clique nos alimentos para fazer o detalhamento",  R.mipmap.report, pos,USUARIO);
                default:
                    return FragmentViewPager.newInstance("Passo 5 - Revisão Final", "Finalizar entrevista",  R.mipmap.finish, pos,USUARIO);
            }
        }

        @Override
        public int getCount() {
            return 6;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Recordátorio 24h.");
        alertDialogBuilder
                .setMessage("Deseja sair do programa?")
                .setCancelable(false)
                .setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                closeApplication();


                            }
                        })

                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void closeApplication() {
        System.out.println("closeApplication ");
        this.finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

}
