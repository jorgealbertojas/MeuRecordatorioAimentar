package com.softjads.jorge.meurecordatorio.Generica;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softjads.jorge.meurecordatorio.Adapter.LocalAdapter;
import com.softjads.jorge.meurecordatorio.Model.Local;
import com.softjads.jorge.meurecordatorio.PersistentData.DataBase;
import com.softjads.jorge.meurecordatorio.R;

import java.util.ArrayList;
import java.util.List;

import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ALIMENTO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;

public class LocalActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    private DataBase mDataBase;

    String mName;
    String mAlimento;

    private TextView title2;


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
        this.setTitle("Cadastro Local");
        title2 = findViewById(R.id.title2);
        title2.setText("Onde " + mName + " comeu este alimento ou bebida?");

        /**
         * use RecyclerView for list the PullRequest .
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_alimento);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDataBase = new DataBase(this);

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
                    List<Local> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListLocal(tv_buscar.getText().toString());iniciaRecyclerView();
                    mRecyclerView.setAdapter(new LocalAdapter(dataPersistent));
                }else{
                    iniciaRecyclerView();
                }
            }
        });

    }


    private void iniciaRecyclerView(){
        List<Local> dataPersistent = new ArrayList<>();
        dataPersistent = mDataBase.getListLocal();

        if (dataPersistent.size()>0) {
            mRecyclerView.setAdapter(new LocalAdapter(dataPersistent));
        }
    }
}
