package com.softjads.jorge.meurecordatorio.Generica;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softjads.jorge.meurecordatorio.Adapter.GrauParentescoAdapter;
import com.softjads.jorge.meurecordatorio.Model.GrauParentesco;
import com.softjads.jorge.meurecordatorio.PersistentData.DataBase;
import com.softjads.jorge.meurecordatorio.PersistentData.DbInstance;
import com.softjads.jorge.meurecordatorio.R;

import java.util.ArrayList;
import java.util.List;

import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_GRAU_PARENTESCO;

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
        this.setTitle("Parentesco");


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
            grauParentesco.setParentesco("MAE");
            grauParentescoList.add(grauParentesco);

            grauParentesco = new GrauParentesco();
            grauParentesco.setId("2");
            grauParentesco.setParentesco("PAI");
            grauParentescoList.add(grauParentesco);

            grauParentesco = new GrauParentesco();
            grauParentesco.setId("3");
            grauParentesco.setParentesco("MADRASTA");
            grauParentescoList.add(grauParentesco);

            grauParentesco = new GrauParentesco();
            grauParentesco.setId("4");
            grauParentesco.setParentesco("PADRASTO");
            grauParentescoList.add(grauParentesco);

            grauParentesco = new GrauParentesco();
            grauParentesco.setId("5");
            grauParentesco.setParentesco("TIO(A)");
            grauParentescoList.add(grauParentesco);

            grauParentesco = new GrauParentesco();
            grauParentesco.setId("6");
            grauParentesco.setParentesco("AVO");
            grauParentescoList.add(grauParentesco);

            grauParentesco = new GrauParentesco();
            grauParentesco.setId("7");
            grauParentesco.setParentesco("BISAVO");
            grauParentescoList.add(grauParentesco);

            grauParentesco = new GrauParentesco();
            grauParentesco.setId("8");
            grauParentesco.setParentesco("IRMAO (IRMA)");
            grauParentescoList.add(grauParentesco);

            grauParentesco = new GrauParentesco();
            grauParentesco.setId("9");
            grauParentesco.setParentesco("OUTRO PARENTE");
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
