package com.example.jorge.meurecordatorio;

import android.app.AlertDialog;
import android.content.Context;
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

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.example.jorge.meurecordatorio.Utilite.Modulo.storageCliente;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private DataBase mDataBase;

    public List<Alimentacao> dataPersistent = null;

    public static String mUsuario = "0";
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

    public RecyclerView mRecyclerView;
    public RecyclerView mRecyclerViewCheck;
    public RecyclerView mRecyclerViewCheckCompleto;

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

    AlimentacaoAdapter alimentacaoAdapter;

    public static ImageView proximo;
    public static ImageView anterior;



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

        proximo = (ImageView) findViewById(R.id.proximo);
        anterior = (ImageView) findViewById(R.id.anterior);

        imagepiscar = (ImageView) findViewById(R.id.image);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setVisibility(View.INVISIBLE);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    imagepiscar.setVisibility(View.INVISIBLE);
                    imagepiscar.setAnimation(null);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerViewCheck.setVisibility(View.GONE);
                    mRecyclerViewCheckCompleto.setVisibility(View.GONE);
                    anterior.setVisibility(View.INVISIBLE);
                    proximo.setVisibility(View.VISIBLE);


                } else if (position == 1) {
                        anterior.setVisibility(View.VISIBLE);
                        proximo.setVisibility(View.VISIBLE);

                        imagepiscar.setVisibility(View.INVISIBLE);
                        imagepiscar.setAnimation(null);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mRecyclerViewCheck.setVisibility(View.GONE);
                        mRecyclerViewCheckCompleto.setVisibility(View.GONE);



                } else if (position == 2) {

                    anterior.setVisibility(View.VISIBLE);
                    proximo.setVisibility(View.VISIBLE);
                    imagepiscar.setVisibility(View.INVISIBLE);
                    imagepiscar.setAnimation(null);
                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerViewCheck.setVisibility(View.VISIBLE);
                    mRecyclerViewCheckCompleto.setVisibility(View.GONE);
                    LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                    final View deleteDialogView = factory.inflate(
                            R.layout.custom_dialog1, null);
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
                    deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            deleteDialog1.dismiss();

                        }
                    });

                    deleteDialog1.show();

                } else if (position == 3){

                    anterior.setVisibility(View.VISIBLE);
                    proximo.setVisibility(View.VISIBLE);
                    imagepiscar.setVisibility(View.INVISIBLE);
                    imagepiscar.setAnimation(null);
                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerViewCheck.setVisibility(View.GONE);
                    mRecyclerViewCheckCompleto.setVisibility(View.VISIBLE);
                  /*  LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                    final View deleteDialogView = factory.inflate(
                            R.layout.custom_dialog1, null);
                    final AlertDialog deleteDialog1 = new AlertDialog.Builder(MainActivity.this).create();
                    deleteDialog1.setView(deleteDialogView);

                    TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                    nTextView.setText("ATENÇÃO!\n Coleta encerrada pode fazer a revisão, caso necessário pode iniciar os passos novamente");

                    deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            deleteDialog1.dismiss();
                        }
                    });
                    deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            deleteDialog1.dismiss();

                        }
                    });*/

                   // deleteDialog1.show();
                }else if (position == 4) {
                    anterior.setVisibility(View.VISIBLE);
                    proximo.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerViewCheck.setVisibility(View.GONE);
                    mRecyclerViewCheckCompleto.setVisibility(View.GONE);

                    imagepiscar.setVisibility(View.INVISIBLE);
                    imagepiscar.setAnimation(null);

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

            }

        });





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
                                R.layout.custom_dialog1, null);
                        final AlertDialog deleteDialog1 = new AlertDialog.Builder(MainActivity.this).create();
                        deleteDialog1.setView(deleteDialogView);

                        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                        nTextView.setText("ATENÇÃO!\n Agora eu gostaria que você me dissesse tudo que " +  NOME + " comeu ou bebeu ontem, do momento em que acordou até a hora em que foi dormir. Caso [nome da criança] tenha acordado de madrugada, também gostaria de saber o que ele/ela comeu ou bebeu de madrugada”.  Me informe também os horários em que a criança consumiu os alimentos e bebidas. Não se preocupe com a quantidade agora, pois falaremos dos detalhes depois.");

                        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
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
                    }

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getEntrevistador();
        }

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

       // iniciaRecyclerView();

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

        if (dataPersistent.size()>0) {
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



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getEntrevistador(){

        StringBuilder text = new StringBuilder();
        try {

            File file = new File(storageCliente, "chave.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            JSONArray items = new JSONArray();

            int i = 0;
            String id = "0";
            String id_crianca = "0";
            String nome_crianca = "0";
            String nome_mae = "0";

            while ((line = br.readLine()) != null) {
                mViewPager.setVisibility(View.VISIBLE);

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
                }
                text.append(line);





            }

            Entrevistado entrevistado = new Entrevistado();
            entrevistado.setEntrevistado_id(id + id_crianca);
            entrevistado.setEntrevistado(nome_crianca + " / " + nome_mae);


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



            br.close();
        } catch (IOException e) {



            e.printStackTrace();

        } finally {

        }
    }


    private void showGrauParentesco(){
        Class destinationClass = GrauParentescoActivity.class;
        Intent intentToStartDetailActivity = new Intent(this, destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_GRAU_PARENTESCO, grauParentesco.getText().toString());
        startActivity(intentToStartDetailActivity);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return FragmentViewPager.newInstance("Etapa 1 - Adiciona alimento","Adicionar Alimento.", R.mipmap.food, pos,mUsuario);
                case 1:
                    return FragmentViewPager.newInstance("Etapa 2 - Adicionar complemento", "Clique no alimento abaixo para adicinar complemento.", R.mipmap.add,pos,mUsuario);
                case 2:
                    return FragmentViewPager.newInstance("Etapa 3 - Check", "Atenção! Caso tenha alimento abaixo clique para completar.",  R.mipmap.check, pos,mUsuario);
                case 3:
                    return FragmentViewPager.newInstance("Etapa 4 - Relatório", "Confira se faltou algum alimento.",  R.mipmap.report, pos,mUsuario);
                default:
                    return FragmentViewPager.newInstance("Etapa 5 - Finalizar", "Encerrar entrevista .",  R.mipmap.finish, pos,mUsuario);
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}
