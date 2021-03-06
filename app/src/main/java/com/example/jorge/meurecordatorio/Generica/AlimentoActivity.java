package com.example.jorge.meurecordatorio.Generica;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.meurecordatorio.Adapter.AlimentoAdapter;
import com.example.jorge.meurecordatorio.DetailActivity;
import com.example.jorge.meurecordatorio.Model.Alimento;
import com.example.jorge.meurecordatorio.PersistentData.DataBase;
import com.example.jorge.meurecordatorio.R;
import com.example.jorge.meurecordatorio.Utilite.Modulo;

import java.util.ArrayList;
import java.util.List;

import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;

public class AlimentoActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    private SQLiteDatabase mDb;
    private DataBase mDataBase;

    EditText tv_buscar;

    TextView tv_salvar_alimento;
    RelativeLayout relativeSalvar;

    String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generico);

        /**
         * Get putExtra for Activity Main .
         */
        Bundle extras = getIntent().getExtras();

        mName = extras.getString(PUT_EXTRA_ENTREVISTADO);

        tv_salvar_alimento = (TextView) findViewById(R.id.tv_salvar_alimento);
        relativeSalvar = (RelativeLayout) findViewById(R.id.relativeSalvar);

        tv_salvar_alimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {





                        LayoutInflater factory = LayoutInflater.from(AlimentoActivity.this);
                        final View deleteDialogView = factory.inflate(
                                R.layout.custom_dialog, null);
                        final AlertDialog deleteDialog = new AlertDialog.Builder(AlimentoActivity.this).create();
                        deleteDialog.setView(deleteDialogView);

                        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                        nTextView.setText("ATENÇÃO! Tem certeza que deseja salvar esse alimento?");

                        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                List<Alimento> data = new ArrayList<>();
                                Alimento alimento = new Alimento();

                                mDataBase = new DataBase(getApplicationContext());
                                mDb = mDataBase.getReadableDatabase();
                                alimento.setAlimento(tv_buscar.getText().toString());
                                alimento.setAlimento_id(Integer.toString(mDataBase.NovoID_ALIMENTO()));
                                alimento.setNovo("1");

                                data.add(alimento);

                                mDataBase.insertTABLE_ALIMENTO(data);

                                Modulo.OPCAO = "ALIMENTO";
                                Modulo.NOME = alimento.getAlimento();
                                Modulo.ID = alimento.getAlimento_id();
                                Modulo.NOVO_ALIMENTO = alimento.getNovo();

                                finish();

                                Toast.makeText(AlimentoActivity.this, "Salvo alimento com sucesso!" , Toast.LENGTH_SHORT).show();
                                deleteDialog.dismiss();
                            }
                        });
                        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                deleteDialog.dismiss();

                            }
                        });

                        deleteDialog.show();








                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(AlimentoActivity.this, "ATENÇÃO! ALIMENTO NÃO SALVO" , Toast.LENGTH_LONG).show();
                }
            }
        });


        /**
         * Put Name Repositorie in  title.
         */
        this.setTitle("Alimento");


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


                if (!tv_buscar.getText().toString().equals("")) {
                    List<Alimento> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListAlimento(tv_buscar.getText().toString());

                    if (dataPersistent.size()>0) {
                        relativeSalvar.setVisibility(View.GONE);
                    }else{
                        relativeSalvar.setVisibility(View.VISIBLE);
                    }

                    iniciaRecyclerView();
                    mRecyclerView.setAdapter(new AlimentoAdapter(dataPersistent));

                }else{
                    iniciaRecyclerView();
                }
            }
        });

    }


    private void iniciaRecyclerView(){
        List<Alimento> dataPersistent = new ArrayList<>();
        dataPersistent = mDataBase.getListAlimento();

        if (dataPersistent.size()>0) {
            mRecyclerView.setAdapter(new AlimentoAdapter(dataPersistent));
         //   relativeSalvar.setVisibility(View.INVISIBLE);
        }else{
         //   relativeSalvar.setVisibility(View.VISIBLE);
        }
    }
}
