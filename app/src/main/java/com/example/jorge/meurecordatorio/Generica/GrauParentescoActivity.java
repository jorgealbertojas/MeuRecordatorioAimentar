package com.example.jorge.meurecordatorio.Generica;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.jorge.meurecordatorio.Adapter.AdicaoAdapter;
import com.example.jorge.meurecordatorio.Adapter.GrauParentescoAdapter;
import com.example.jorge.meurecordatorio.ConfiguracaoActivity;
import com.example.jorge.meurecordatorio.Model.Adicao;
import com.example.jorge.meurecordatorio.Model.GrauParentesco;
import com.example.jorge.meurecordatorio.PersistentData.DataBase;
import com.example.jorge.meurecordatorio.PersistentData.DbCreate;
import com.example.jorge.meurecordatorio.PersistentData.DbInstance;
import com.example.jorge.meurecordatorio.PersistentData.Field;
import com.example.jorge.meurecordatorio.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_DIA_ATIPICO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_GRAU_PARENTESCO;

/**
 * Created by jorge on 25/05/2018.
 */

public class GrauParentescoActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    private DataBase mDataBase;
    private SQLiteDatabase mDb;

    String mParentesco;

    GrauParentesco grauParentesco;
    List<GrauParentesco> grauParentescoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generico);

        /**
         * Get putExtra for Activity Main .
         */
        Bundle extras = getIntent().getExtras();

        mParentesco = extras.getString(PUT_EXTRA_GRAU_PARENTESCO);



        /**
         * Put Name Repositorie in  title.
         */
        this.setTitle("Grau parentesco");


        /**
         * use RecyclerView for list the PullRequest .
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_alimento);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDataBase = new DataBase(this);
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(this);

        iniciaRecyclerView();

        final EditText tv_buscar =  (EditText) findViewById(R.id.tv_buscar);
        tv_buscar.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                if (!tv_buscar.getText().toString().equals("")) {
                    List<GrauParentesco> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListGrauParentesco(tv_buscar.getText().toString());iniciaRecyclerView();
                    mRecyclerView.setAdapter(new GrauParentescoAdapter(dataPersistent));
                }else{
                    iniciaRecyclerView();
                }
            }
        });

    }


    private void iniciaRecyclerView(){
        List<GrauParentesco> dataPersistent = new ArrayList<>();
        dataPersistent = mDataBase.getListGrauParentesco();

        if (dataPersistent.size()==0){
            grauParentescoList = new ArrayList<>();

            grauParentesco = new GrauParentesco();
            grauParentesco.setId("1");
            grauParentesco.setParentesco("Mãe");
            grauParentescoList.add(grauParentesco);

            grauParentesco = new GrauParentesco();
            grauParentesco.setId("2");
            grauParentesco.setParentesco("Pai");
            grauParentescoList.add(grauParentesco);

            grauParentesco = new GrauParentesco();
            grauParentesco.setId("3");
            grauParentesco.setParentesco("Tia");
            grauParentescoList.add(grauParentesco);

            mDataBase = new DataBase(getApplicationContext());
            mDb = mDataBase.getReadableDatabase();

            mDataBase.insertTABLE_GRAU_PARENTESCO(grauParentescoList);


        }

        if (dataPersistent.size()>0) {
            mRecyclerView.setAdapter(new GrauParentescoAdapter(dataPersistent));
        }
    }




}
