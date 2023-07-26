package com.softjads.jorge.meurecordatorio;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.softjads.jorge.meurecordatorio.Adapter.AlimentacaoAdapter;
import com.softjads.jorge.meurecordatorio.Generica.EntrevistadoActivity;
import com.softjads.jorge.meurecordatorio.Generica.GrauParentescoActivity;
import com.softjads.jorge.meurecordatorio.Help.AppHelp;
import com.softjads.jorge.meurecordatorio.Help.Help;
import com.softjads.jorge.meurecordatorio.Help.HelpAppActivity;
import com.softjads.jorge.meurecordatorio.Model.Alimentacao;
import com.softjads.jorge.meurecordatorio.Model.Entrevistado;
import com.softjads.jorge.meurecordatorio.PersistentData.DataBase;
import com.softjads.jorge.meurecordatorio.PersistentData.DbCreate;
import com.softjads.jorge.meurecordatorio.PersistentData.DbInstance;
import com.softjads.jorge.meurecordatorio.Splash.SplashActivity;
import com.softjads.jorge.meurecordatorio.Utilite.Common;
import com.softjads.jorge.meurecordatorio.Utilite.CrunchifyJSONFileWrite;
import com.softjads.jorge.meurecordatorio.Utilite.FragmentViewPager;
import com.softjads.jorge.meurecordatorio.Utilite.Modulo;


import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.softjads.jorge.meurecordatorio.PersistentData.DbCreate.DB_NAME;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private DataBase mDataBase;

    public static int[] locationInScreen = new int[]{0,0};

    public int lastposition = 0;
    public boolean voltando = false;
    public boolean voltandoNovo = false;
    public int lastpositionNOVO = 0;

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
    private TextView descartar;

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

    public static Boolean mensagemInicial = true;

    public static  TextView informacao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //solicitarPermisos();


        //carregarsuario_login();

        //1 - BEBETO
        //Intent i = new Intent(MainActivity.this, SplashActivity.class);
        //startActivity(i);
        informacao = findViewById(R.id.informacao);
        //informacao.setSelected(true);
        informacao.setVisibility(View.GONE);



        frase = (TextView) findViewById(R.id.frase);

        grau_parentesco_hint = (TextView) findViewById(R.id.grau_parentesco_hint);
        diaatipico_hint = (TextView) findViewById(R.id.dia_atipico_hint);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDataBase = new DataBase(MainActivity.this);
                mDb = mDataBase.getReadableDatabase();
                DbInstance.getInstance(MainActivity.this);
                List<Help> helpList = mDataBase.getListHelp();
                if (helpList.size() > 0){
                    onHelpLoaded(helpList,view,MainActivity.this);
                }

            }
        });

        // 2 - BEBETO
       //CrunchifyJSONFileWrite CrunchifyJSONFileWrite = new CrunchifyJSONFileWrite();
      // try {
       //    CrunchifyJSONFileWrite.main(this);
      // } catch (IOException e) {
      //      e.printStackTrace();
      // }

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

                if (lastpositionNOVO < position){

                    voltandoNovo = false;
                }else{
                    voltandoNovo = true;
                }
                lastpositionNOVO = position;

                ImageView iv_check = mRecyclerViewCheck.findViewById(R.id.iv_check);

                if (position == 0) {
                    diaAtipico.setVisibility(View.VISIBLE);
                    grau_parentesco_nome.setVisibility(View.VISIBLE);
                    grauParentesco.setVisibility(View.VISIBLE);
                    grau_parentesco_hint.setVisibility(View.VISIBLE);
                    diaatipico_hint.setVisibility(View.VISIBLE);
                    ListaEntrevistado.setVisibility(View.VISIBLE);

                    imagepiscar.setVisibility(View.INVISIBLE);
                    imagepiscar.setAnimation(null);

/*                    anterior.setVisibility(View.INVISIBLE);
                    proximo.setVisibility(View.VISIBLE);
                    anterior_palavra.setVisibility(View.INVISIBLE);
                    proximo_palavra.setVisibility(View.VISIBLE);*/
                    frase.setVisibility(View.GONE);

                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerViewCheck.setVisibility(View.GONE);
                    mRecyclerViewCheckCompleto.setVisibility(View.GONE);

                }

                else if (position == 1) {

                    diaAtipico.setVisibility(View.GONE);
                    grau_parentesco_nome.setVisibility(View.GONE);
                    grauParentesco.setVisibility(View.GONE);
                    ListaEntrevistado.setVisibility(View.GONE);
                    grau_parentesco_hint.setVisibility(View.GONE);
                    diaatipico_hint.setVisibility(View.GONE);

/*                    anterior.setVisibility(View.VISIBLE);
                    proximo.setVisibility(View.VISIBLE);
                    anterior_palavra.setVisibility(View.VISIBLE);
                    proximo_palavra.setVisibility(View.VISIBLE);*/


                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerViewCheck.setVisibility(View.VISIBLE);
                    mRecyclerViewCheckCompleto.setVisibility(View.GONE);

                    frase.setVisibility(View.GONE);


                    if (iv_check != null) {
                        iv_check.setVisibility(View.GONE);
                    }

                } else if (position == 2) {
                    diaAtipico.setVisibility(View.GONE);
                    grau_parentesco_nome.setVisibility(View.GONE);
                    grauParentesco.setVisibility(View.GONE);
                    ListaEntrevistado.setVisibility(View.GONE);
                    grau_parentesco_hint.setVisibility(View.GONE);
                    diaatipico_hint.setVisibility(View.GONE);


/*                    anterior.setVisibility(View.VISIBLE);
                    proximo.setVisibility(View.VISIBLE);
                    anterior_palavra.setVisibility(View.VISIBLE);
                    proximo_palavra.setVisibility(View.VISIBLE);*/

                    imagepiscar.setVisibility(View.INVISIBLE);
                    imagepiscar.setAnimation(null);

                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerViewCheck.setVisibility(View.VISIBLE);
                    mRecyclerViewCheckCompleto.setVisibility(View.GONE);

                    frase.setVisibility(View.GONE);


                } else if (position == 3) {

                    diaAtipico.setVisibility(View.GONE);
                    grau_parentesco_nome.setVisibility(View.GONE);
                    grauParentesco.setVisibility(View.GONE);
                    ListaEntrevistado.setVisibility(View.GONE);
                    grau_parentesco_hint.setVisibility(View.GONE);
                    diaatipico_hint.setVisibility(View.GONE);


/*                    anterior.setVisibility(View.VISIBLE);
                    proximo.setVisibility(View.VISIBLE);
                    anterior_palavra.setVisibility(View.VISIBLE);
                    proximo_palavra.setVisibility(View.VISIBLE);*/

                    imagepiscar.setVisibility(View.INVISIBLE);
                    imagepiscar.setAnimation(null);

                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerViewCheck.setVisibility(View.VISIBLE);
                    mRecyclerViewCheckCompleto.setVisibility(View.GONE);

                    frase.setVisibility(View.GONE);

                    if (voltando) {
                       // if (!alimentacaoAdapter.estaFaltando) {
                            LayoutInflater factory2 = LayoutInflater.from(MainActivity.this);
                            final View deleteDialogView2 = factory2.inflate(
                                    R.layout.custom_dialog10, null);
                            final AlertDialog deleteDialog2 = new AlertDialog.Builder(MainActivity.this).create();
                            deleteDialog2.setView(deleteDialogView2);

                            TextView nTextView2 = (TextView) deleteDialogView2.findViewById(R.id.txt_dia);

                            nTextView2.setText("Agora vamos falar sobre as refeições que " + NOME + " realizou (se era café da manhã, almoço, lanche, jantar etc) e incluir os horários das refeições)");


                            deleteDialogView2.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    deleteDialog2.dismiss();
                                }
                            });

                            deleteDialog2.show();
                      //  }
                    }


                } else if (position == 4){

                    diaAtipico.setVisibility(View.GONE);
                    grau_parentesco_nome.setVisibility(View.GONE);
                    grauParentesco.setVisibility(View.GONE);
                    ListaEntrevistado.setVisibility(View.GONE);
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

                        if (alimentacaoAdapter.estaFaltando) {
                            LayoutInflater factory2 = LayoutInflater.from(MainActivity.this);
                            final View deleteDialogView2 = factory2.inflate(
                                    R.layout.custom_dialog10, null);
                            final AlertDialog deleteDialog2 = new AlertDialog.Builder(MainActivity.this).create();
                            deleteDialog2.setView(deleteDialogView2);

                            TextView nTextView2 = (TextView) deleteDialogView2.findViewById(R.id.txt_dia);
                            nTextView2.setText("Agora vamos detalhar todos os alimentos que o(a) Sr.(a) falou, em relação ao tipo de preparo, adição, quantidade e local das refeições.");


                            deleteDialogView2.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    deleteDialog2.dismiss();
                                }
                            });

                            deleteDialog2.show();
                        }



                    }

                }else if (position == 5) {

                    diaAtipico.setVisibility(View.GONE);
                    grau_parentesco_nome.setVisibility(View.GONE);
                    grauParentesco.setVisibility(View.GONE);
                    ListaEntrevistado.setVisibility(View.GONE);
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

                    if (voltando && !alimentacaoAdapter.estaFaltando && !alimentacaoAdapter.estaFaltando2) {
                        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                        final View deleteDialogView = factory.inflate(
                                R.layout.custom_dialog10, null);
                        final AlertDialog deleteDialog1 = new AlertDialog.Builder(MainActivity.this).create();
                        deleteDialog1.setView(deleteDialogView);

                        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                        nTextView.setText("Agora eu vou ler todos os alimentos, horários e refeições realizados por " + entrevistado_nome.getText() + " ontem, para que a/o Sra(Sr) confirme se tudo foi registrado corretamente.");

                        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                deleteDialog1.dismiss();
                            }
                        });

                        deleteDialog1.show();
                    }else{
                        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                        final View deleteDialogView = factory.inflate(
                            R.layout.custom_dialog10, null);
                        final AlertDialog deleteDialog1 = new AlertDialog.Builder(MainActivity.this).create();
                        deleteDialog1.setView(deleteDialogView);

                        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                        nTextView.setText("você deve finalizar todos os alimentos");

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
            // 3 - BEBETO
            Entrar();



            // 4 - BEBETO
           // if(savedInstanceState == null){
             //   Intent WSActivity = new Intent(this, LoginActivity.class);
               // startActivity(WSActivity);

            //}

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 5 - BEBETO
            if (!getEntrevistador()){
                Toast.makeText(this, "ATENÇÃO! ESTE SISTEMA SÓ PODE SER CHAMADO PELO SISTEMA DE PESQUISA CSPRO" , Toast.LENGTH_LONG).show();
                this.finish();
            }

            // 6 - BEBETO
            //insereEntrevistadoDemostracao();

        }

        //7 - BEBETO
      // mDataBase.deleteentrevistadoTESTE();



        descartar = (TextView) findViewById(R.id.tx_descartar);
        descartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
/*                    Class destinationClass = ConfiguracaoActivity.class;
                    Intent intentToStartConfiguracaoActivity= new Intent(getBaseContext(), destinationClass);
                    startActivity(intentToStartConfiguracaoActivity);*/
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setTitle("Recordatório 24h.");
                    alertDialogBuilder
                            .setMessage("Você tem certeza que quer descartar o recordatório de 24h que está sendo preenchido?")
                            .setCancelable(false)
                            .setPositiveButton("Sim",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(MainActivity.this);
                                            alertDialogBuilder2.setTitle("Recordatório 24h.");
                                            alertDialogBuilder2
                                                    .setMessage("Ao descartar, você não poderá recuperar os alimentos que foram incluídos até agora. Tem certeza que deseja descartar?")
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

                                            AlertDialog alertDialog2 = alertDialogBuilder2.create();
                                            alertDialog2.show();


                                        }
                                    })

                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
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
        loadSessionConfig();
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
        if (mensagemInicial)  {
            mensagemInicial = false;
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

/*    @TargetApi(29)
    void solicitarPermisos() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }
    }*/


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean getEntrevistador() {

        String fileName = "chave.txt";

        File fileExist = new File(Modulo.getSDCardPath(this) , "chave.txt");
       // if (fileExist.exists() || BuildConfig.DEBUG){
        if (fileExist.exists()) {

        StringBuilder text = new StringBuilder();
        try {

            File file = new File(Modulo.getSDCardPath(this), "chave.txt");

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

           // if (!BuildConfig.DEBUG) {
                while ((line = br.readLine()) != null) {


                    if (!line.isEmpty()) {
                        i++;
                    }


                    if ((i == 1) && (!line.isEmpty())) {
                        id = line;
                    } else if ((i == 2 && (!line.isEmpty()))) {
                        id_crianca = line;
                    } else if ((i == 3 && (!line.isEmpty()))) {
                        nome_crianca = line;
                    } else if (i == 4 && (!line.isEmpty())) {
                        nome_mae = line;
                    } else if (i == 5 && (!line.isEmpty())) {
                        nome_USUARIO = line;
                    }
                    text.append(line);


                }
           /* }else{
                id = "0000000000000000000000001";
                id_crianca = "1";
                nome_crianca = "nometeste";
                nome_mae = "nometeste";
                nome_USUARIO = "usuarioteste";
            }*/

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


         //   if (!BuildConfig.DEBUG) {
                br.close();
        //    }



        } catch (IOException e) {



            e.printStackTrace();



        } finally {

        }
        // 8 - BEBETO
      //  if (!BuildConfig.DEBUG){
           fileExist.delete();
      //  }

            return true;
        }else{
            return false;

        }
    }


    public void insereEntrevistadoDemostracao() {




        Entrevistado entrevistado1 = new Entrevistado();
        entrevistado1.setEntrevistado_id("1");
        entrevistado1.setEntrevistado("ENTREVISTADO 1");

        Entrevistado entrevistado2 = new Entrevistado();
        entrevistado2.setEntrevistado_id("2");
        entrevistado2.setEntrevistado("ENTREVISTADO 2");

        Entrevistado entrevistado3 = new Entrevistado();
        entrevistado3.setEntrevistado_id("3");
        entrevistado3.setEntrevistado("ENTREVISTADO 3");


        List<Entrevistado> data = new ArrayList<>();
        data.add(entrevistado1);
        data.add(entrevistado2);
        data.add(entrevistado3);

        // Persistent Data for SQLLite
        mDataBase = new DataBase(this);

        if (!(mDataBase.getListEntrevistadoEXISTE(entrevistado1.getEntrevistado_id()))) {
            mDataBase.insertTABLE_ENTREVISTADO(data);
        }




        Modulo.OPCAO = "ENTREVISTADO";
        Modulo.NOME = entrevistado1.getEntrevistado();
        Modulo.ID = entrevistado1.getEntrevistado_id();

        NOME = entrevistado1.getEntrevistado();
        ID = entrevistado1.getEntrevistado_id();
        USUARIO = "SOFTJADS";



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
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return FragmentViewPager.newInstance("Identificação","Arraste para o lado esquerdo para ir ao Passo 1", R.mipmap.ic_id, pos,USUARIO);
                case 1:
                    return FragmentViewPager.newInstance("Passo 1 - Listagem rápida de alimentos","Adicionar Alimento", R.mipmap.food, pos,USUARIO);
                case 2:
                    return FragmentViewPager.newInstance("Passo 2 - Listagem de alimentos comumente esquecidos", "Arraste para o lado esquerdo para ir ao Passo 3.", R.mipmap.add,pos,USUARIO);
                case 3:
                    return FragmentViewPager.newInstance("Passo 3 – Definição de horário e tipo de refeição", "Clique nos alimentos para definir horário e refeição",  R.mipmap.check, pos,USUARIO);
                case 4:
                    return FragmentViewPager.newInstance("Passo 4 - Detalhamento dos alimentos", "Clique nos alimentos para fazer o detalhamento",  R.mipmap.report, pos,USUARIO);
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
        alertDialogBuilder.setTitle("Recordatório 24h.");
        alertDialogBuilder
                .setMessage("Quer descartar esse recordatório de 24h?")
                .setCancelable(false)
                .setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(MainActivity.this);
                                alertDialogBuilder2.setTitle("Recordatório 24h.");
                                alertDialogBuilder2
                                        .setMessage("Todos os alimentos incluídos serão perdidos. Tem certeza que deseja descartar?")
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

                                AlertDialog alertDialog2 = alertDialogBuilder2.create();
                                alertDialog2.show();


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

        // 9 - BEBETO
        deleteTUDO();

        android.os.Process.killProcess(android.os.Process.myPid());

    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    public void deleteTUDO(){
        mDataBase = new DataBase(getApplicationContext());
        mDb = mDataBase.getReadableDatabase();
        mDb.execSQL(" DELETE FROM " + DbCreate.TABLE_ENTREVISTADO);

        mDb.execSQL(" DELETE FROM " + DbCreate.TABLE_ALIMENTACAO);
    }


    public void onHelpLoaded(List<Help> helpList, View root, Context context) {
        List<String> stringList = new ArrayList<String>();
        List<String> stringListTemp = new ArrayList<String>();
        List<Integer> stringListX = new ArrayList<Integer>();
        List<Integer> stringListY = new ArrayList<Integer>();
        List<String> stringListTOP= new ArrayList<String>();

        // We filter the tasks based on the requestType
        int i = 0;
        for (Help help : helpList) {
            int valueID = 0;
            int id_view = getResources().getIdentifier(help.getMkey(), "id", getPackageName());
            valueID = Common.getResourceString(help.getMkey(), id_view );
            if (valueID != 0) {
                View vievHelp2 = (View) root.findViewById(valueID);

                if (vievHelp2  == null){
                    vievHelp2 = (View) root.getRootView().findViewById(valueID);
                }

                if ((vievHelp2  != null) && (!stringList.contains(help.getMkey())) && (vievHelp2.getVisibility() == View.VISIBLE)) {
                    vievHelp2.getLocationOnScreen(locationInScreen);
                    stringList.add(help.getMkey());
                    stringListTemp.add(help.getMvalue());
                    stringListTOP.add(help.getMlast());
                    int tempX = locationInScreen[0];
                    if (tempX < 0){
                        tempX = tempX * (-1);
                        tempX = tempX - (vievHelp2.getWidth());
                    }
                    stringListX.add(tempX);


                    int tempY = locationInScreen[1] - (vievHelp2.getHeight() / 2);
                    if (tempY < 0){
                        tempY = tempY * (-1);
                    }
                    stringListY.add(tempY);
                }
            }
            i++;
        }

        Intent intent = new Intent(context, HelpAppActivity.class);
        intent.putExtra("HELP_ID", (Serializable) stringListTemp);
        intent.putExtra("HELP_X", (Serializable) stringListX);
        intent.putExtra("HELP_Y", (Serializable) stringListY);
        intent.putExtra("HELP_TOP", (Serializable) stringListTOP);
        intent.putExtra("HELP_POSITION_TAB",mViewPager.getCurrentItem() );

        context.startActivity(intent);

    }

    protected String readJsonFile(InputStream inputStream) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte bufferByte[] = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(bufferByte)) != -1) {
                outputStream.write(bufferByte, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
        return outputStream.toString();
    }

    private void loadSessionConfig(){
        try {
            mDataBase = new DataBase(getApplicationContext());
            mDb = mDataBase.getReadableDatabase();
            mDb.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_HELP );

            mDb.execSQL(DbCreate.CREATE_TABLE_HELP);
            Gson gson = new Gson();
            InputStream inputStream = this.getAssets().open("config_help.json");
            AppHelp appHelp = (gson.fromJson(readJsonFile(inputStream), AppHelp.class));

            List<Help> helpList = new ArrayList<>();

            for (int i = 0;  i < appHelp.getConfigHelp().size()  ;i ++ ){
                Help help = new Help();
                help.setMkey(appHelp.getConfigHelp().get(i).getMkey());
                help.setMvalue(appHelp.getConfigHelp().get(i).getMvalue());
                help.setMlast(appHelp.getConfigHelp().get(i).getMlast());
                helpList.add(help);
            }

            mDataBase.insertTABLE_HELP(helpList);
        } catch (Exception e) {
            //   errorLog(e.toString());
        }
    }



/*    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }*/


}
