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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.darwindeveloper.wcviewpager.WCViewPagerIndicator;
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
import com.example.jorge.meurecordatorio.Utilite.Modulo;
import com.example.jorge.meurecordatorio.Utilite.PageFragment;
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

    TextView diaAtipico;

    TextView grauParentesco;
    TextView grau_parentesco_nome;



     private ImageView mMenu;

    public static String NOME = "0";
    public static String ID = "0";

    //inicializamos la vista
    static WCViewPagerIndicator wcViewPagerIndicator;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wcViewPagerIndicator = (WCViewPagerIndicator) findViewById(R.id.wcviewpager);

        carregarsuario_login();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getEntrevistador();
        }

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





        //creamos un nuevo adpater
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        wcViewPagerIndicator.setAdapter(viewPagerAdapter);//aplicamos el adapter

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
        });

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
            entrevistado_nome.setText(Modulo.NOME);
            entrevistado.setText(Modulo.ID);
            NOME = Modulo.NOME;
            ID = Modulo.ID;
        }else if (Modulo.OPCAO.equals("GRAU_PARENTESCO")){
            grau_parentesco_nome.setText(Modulo.NOME);
            grauParentesco.setText(Modulo.ID);
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

    /**
     * adaptador para nuextro wcviewpager
     */
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {

            //usaremos la misma clase para todos los fragment solo cambiaremos el texto de cada fragment
            Fragment fragment = new PageFragment(NOME,ID,wcViewPagerIndicator);
            Bundle args = new Bundle();//para pasar datos al fragment
            args.putInt(PageFragment.PAGE, position);//le sumamos 1 a la posicion para que el texto mostrado corresponda con el del indicador
            fragment.setArguments(args);//le pasamos los datos(numero de pagina) al fragment

            return fragment;
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return 5;//nuemro de paginas para nuestro wcviewpager
        }
    }

    private void showGrauParentesco(){
        Class destinationClass = GrauParentescoActivity.class;
        Intent intentToStartDetailActivity = new Intent(this, destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_GRAU_PARENTESCO, grauParentesco.getText().toString());
        startActivity(intentToStartDetailActivity);
    }

}
