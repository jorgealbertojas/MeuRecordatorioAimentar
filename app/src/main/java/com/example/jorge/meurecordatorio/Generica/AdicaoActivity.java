package com.example.jorge.meurecordatorio.Generica;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.jorge.meurecordatorio.Adapter.AdicaoAdapter;
import com.example.jorge.meurecordatorio.Adapter.UnidadeAdapter;
import com.example.jorge.meurecordatorio.Model.Adicao;
import com.example.jorge.meurecordatorio.Model.Unidade;
import com.example.jorge.meurecordatorio.PersistentData.DataBase;
import com.example.jorge.meurecordatorio.R;
import com.example.jorge.meurecordatorio.Utilite.Modulo;

import java.util.ArrayList;
import java.util.List;

import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ALIMENTO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;

public class AdicaoActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    private DataBase mDataBase;

    String mName;
    String mAlimento;

    public List<Adicao> dataPersistent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generico);

        /**
         * Get putExtra for Activity Main .
         */
        Bundle extras = getIntent().getExtras();

        mName = extras.getString(PUT_EXTRA_ENTREVISTADO);
        mAlimento = extras.getString(PUT_EXTRA_ALIMENTO);


        /**
         * Put Name Repositorie in  title.
         */
        this.setTitle("Foi adicionado algo a este alimento?");


        /**
         * use RecyclerView for list the PullRequest .
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_alimento);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDataBase = new DataBase(this);

        iniciaRecyclerView();

        if (dataPersistent != null) {
            if (dataPersistent.size() == 1) {
                Modulo.OPCAO = "ADICAO";
                Modulo.NOME = dataPersistent.get(0).getAdicao();
                Modulo.ID = dataPersistent.get(0).getAdicao_id();
                this.finish();
            }
        }

        final EditText tv_buscar =  (EditText) findViewById(R.id.tv_buscar);
        tv_buscar.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                if (!tv_buscar.getText().toString().equals("")) {
                    List<Adicao> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListAdicao(mAlimento, tv_buscar.getText().toString());iniciaRecyclerView();
                    mRecyclerView.setAdapter(new AdicaoAdapter(dataPersistent));
                }else{
                    iniciaRecyclerView();
                }
            }
        });

    }


    private void iniciaRecyclerView(){
       dataPersistent = new ArrayList<>();


        if (mAlimento.equals("S")){
            dataPersistent = mDataBase.getListAdicao();
        }else{
            dataPersistent = mDataBase.getListAdicao(mAlimento);
        }

        if (dataPersistent.size()>0) {
            mRecyclerView.setAdapter(new AdicaoAdapter(dataPersistent));
        }
    }
}
