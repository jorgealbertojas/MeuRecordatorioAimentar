package com.example.jorge.meurecordatorio.Generica;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jorge.meurecordatorio.Adapter.AlimentoAdapter;
import com.example.jorge.meurecordatorio.Adapter.EntrevistadoAdapter;
import com.example.jorge.meurecordatorio.Model.Alimento;
import com.example.jorge.meurecordatorio.Model.Entrevistado;
import com.example.jorge.meurecordatorio.PersistentData.DataBase;
import com.example.jorge.meurecordatorio.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;

public class EntrevistadoActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    private DataBase mDataBase;

    String mName;

    private EditText tv_buscar;

    private static CheckBox checkBoxColetados;

    private TextView title2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generico);

        /**
         * Get putExtra for Activity Main .
         */
        Bundle extras = getIntent().getExtras();


        /**
         * Put Name Repositorie in  title.
         */
        this.setTitle("Entrevistador");
        title2 = findViewById(R.id.title2);
        title2.setTextSize(0);

        checkBoxColetados = (CheckBox) findViewById(R.id.checkBoxColetados);
        checkBoxColetados.setVisibility(View.VISIBLE);
        checkBoxColetados.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preeecheEntrevistado();
            }
        });



        /**
         * use RecyclerView for list the PullRequest .
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_alimento);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDataBase = new DataBase(this);

        iniciaRecyclerView();

        tv_buscar =  (EditText) findViewById(R.id.tv_buscar);
        tv_buscar.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                preeecheEntrevistado();

            }
        });

    }


    private void iniciaRecyclerView(){
        List<Entrevistado> dataPersistent = new ArrayList<>();
        dataPersistent = mDataBase.getListEntrevistado();

        if (dataPersistent.size()>0) {
            mRecyclerView.setAdapter(new EntrevistadoAdapter(dataPersistent));
        }
    }

    private void preeecheEntrevistado(){
       // if (!tv_buscar.getText().toString().equals("")) {
            List<Entrevistado> dataPersistent = new ArrayList<>();
            if (!checkBoxColetados.isChecked()) {
                dataPersistent = mDataBase.getListEntrevistado(tv_buscar.getText().toString());
            }else{
                dataPersistent = mDataBase.getListEntrevistadoColetado(tv_buscar.getText().toString());
            }
            iniciaRecyclerView();
            mRecyclerView.setAdapter(new EntrevistadoAdapter(dataPersistent));
     //   }else{
         //   iniciaRecyclerView();
      //  }
    }
}
