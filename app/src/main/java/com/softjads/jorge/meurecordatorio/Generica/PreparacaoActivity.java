package com.softjads.jorge.meurecordatorio.Generica;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softjads.jorge.meurecordatorio.Adapter.PreparacaoAdapter;
import com.softjads.jorge.meurecordatorio.Model.Preparacao;
import com.softjads.jorge.meurecordatorio.PersistentData.DataBase;
import com.softjads.jorge.meurecordatorio.R;
import com.softjads.jorge.meurecordatorio.Utilite.Modulo;

import java.util.ArrayList;
import java.util.List;

import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ALIMENTO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;

public class PreparacaoActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    private DataBase mDataBase;

    String mName;
    String mAlimento;

    private TextView title2;

    public List<Preparacao> dataPersistent = null;

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
        this.setTitle("Preparação");
       // title2 = findViewById(R.id.title2);title2.setText("Qual a preparação que " + mName + " comeu ou bebeu este alimento?");
        title2 = findViewById(R.id.title2);title2.setText("Como foi preparado este alimento?");

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
                Modulo.OPCAO = "PREPARACAO";
                Modulo.NOME = dataPersistent.get(0).getPreparacao();
                Modulo.ID = dataPersistent.get(0).getPreparacao_id();
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
                    List<Preparacao> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListPreparacao(mAlimento, tv_buscar.getText().toString());iniciaRecyclerView();
                    mRecyclerView.setAdapter(new PreparacaoAdapter(dataPersistent));
                }else{
                    iniciaRecyclerView();
                }
            }
        });

    }


    private void iniciaRecyclerView(){

       dataPersistent = new ArrayList<>();

        if (mAlimento.equals("S")){
            dataPersistent = mDataBase.getListPreparacao();
        }else{
            dataPersistent = mDataBase.getListPreparacaoCOMRELACIONAMENTO(mAlimento);
        }

        if (dataPersistent.size()==0) {
            dataPersistent = mDataBase.getListPreparacao();
        }

        if (dataPersistent.size()>0) {
            mRecyclerView.setAdapter(new PreparacaoAdapter(dataPersistent));
        }
    }
}
