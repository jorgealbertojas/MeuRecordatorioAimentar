
package com.example.jorge.meurecordatorio;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.meurecordatorio.Generica.AdicaoActivity;
import com.example.jorge.meurecordatorio.Generica.AlimentoActivity;
import com.example.jorge.meurecordatorio.Generica.LocalActivity;
import com.example.jorge.meurecordatorio.Generica.OcasiaoConsumoActivity;
import com.example.jorge.meurecordatorio.Generica.PreparacaoActivity;
import com.example.jorge.meurecordatorio.Generica.UnidadeActivity;
import com.example.jorge.meurecordatorio.Model.Alimentacao;
import com.example.jorge.meurecordatorio.Model.Alimento;
import com.example.jorge.meurecordatorio.PersistentData.DataBase;
import com.example.jorge.meurecordatorio.PersistentData.DbInstance;
import com.example.jorge.meurecordatorio.Utilite.Modulo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.jorge.meurecordatorio.MainActivity.PUT_BUNDLE_ALIMENTACAO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ALIMENTACAO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ALIMENTO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO_NOME;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_USUARIO;

public class DetailActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private DataBase mDataBase;

    String mEntrevistado;
    String mEntrevistadoNome;
    String mUsuario;

    TextView entrevistado;

    TextView usuario;

    TextView alimento;
    TextView alimento_nome;
    String novoAliemnto = "0";

    TextView unidade;
    TextView unidade_nome;

    TextView preparacao;
    TextView preparacao_nome;

    TextView adicao;
    TextView adicao_nome;

    TextView local;
    TextView local_nome;

    TextView ocasiaoConsumo;
    TextView ocasiaoConsumo_nome;

    TextView tvDiaColeta;
    TextView tvHoraColeta;
    EditText obs;

    TextView tv_salvar_alimento;

    EditText horaEditText;
    TextView minutoEditText;
    EditText quantidadeEditText;

    TextView tv_deletar_alimento;
    TextView tv_duplicar_alimento;

    public static Alimentacao mAlimentacao;
    public static boolean alimentacoAlteracao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /**
         * Get putExtra for Activity Main .
         */
        Bundle extras = getIntent().getExtras();

        mDataBase = new DataBase(this);
        mDb = mDataBase.getReadableDatabase();

        DbInstance.getInstance(this);

        mEntrevistado = extras.getString(PUT_EXTRA_ENTREVISTADO);
        mEntrevistadoNome = extras.getString(PUT_EXTRA_ENTREVISTADO_NOME);
        mUsuario = extras.getString(PUT_EXTRA_USUARIO);

        Bundle bundle = getIntent().getBundleExtra(PUT_EXTRA_ALIMENTACAO);

        mAlimentacao = new Alimentacao();
        if (bundle != null){
            mAlimentacao = (Alimentacao) bundle.getSerializable(PUT_BUNDLE_ALIMENTACAO);
            getIntent().getBundleExtra(PUT_EXTRA_ALIMENTACAO).putSerializable(PUT_BUNDLE_ALIMENTACAO,null);
            alimentacoAlteracao = true;

        }


        entrevistado = (TextView) findViewById(R.id.entrevistado);
        usuario = (TextView) findViewById(R.id.usuario) ;
        entrevistado.setText(mEntrevistado + " - " + mEntrevistadoNome);
        usuario.setText(mUsuario);

        tvDiaColeta = (TextView) findViewById(R.id.dia_coleta);
        tvHoraColeta = (TextView) findViewById(R.id.hora_coleta);
        horaEditText = (EditText) findViewById(R.id.hora);
        minutoEditText = (TextView) findViewById(R.id.hora_minuto);
        obs = (EditText) findViewById(R.id.obs) ;
        quantidadeEditText = (EditText) findViewById(R.id.quantidade);



        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        String dia = Integer.toString(today.monthDay);
        String mes = Integer.toString(today.month + 1);
        String ano = Integer.toString(today.year);
        String diaCompleto = dia + "/" + mes + "/" + ano;

        String hora_coleta = Integer.toString(today.hour);
        String minuto_coleta = Integer.toString(today.minute);
        String minuto_segundos = Integer.toString(today.second);

        String horacompleta = hora_coleta + ":" + minuto_coleta + ":" + minuto_segundos;

        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    int tamanho = Integer.parseInt(s.toString());
                    if (tamanho > 23) {
                        Toast.makeText(DetailActivity.this, "Atenção! Máximo permitido:" + Integer.toString(23), Toast.LENGTH_LONG).show();
                        s.clear();
                    }
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };


        horaEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        horaEditText.addTextChangedListener(textWatcher);


        minutoEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {

                    if (minutoEditText.getText().toString().equals("00")) {
                        minutoEditText.setText("30");
                    }else{
                        minutoEditText.setText("00");
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });


        tv_duplicar_alimento = (TextView)  findViewById(R.id.tv_duplicar_alimento);
        tv_duplicar_alimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {

                    LayoutInflater factory = LayoutInflater.from(DetailActivity.this);
                    final View deleteDialogView = factory.inflate(
                            R.layout.custom_dialog, null);
                    final AlertDialog deleteDialog = new AlertDialog.Builder(DetailActivity.this).create();
                    deleteDialog.setView(deleteDialogView);

                    TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                    nTextView.setText("ATENÇÃO! Tem certeza que deseja duplicar esse alimento?");

                    deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            List<Alimentacao> data = new ArrayList<>();


                            mDataBase = new DataBase(getApplicationContext());
                            mDb = mDataBase.getReadableDatabase();

                            mAlimentacao.setAlimentacao_id(Integer.toString(mDataBase.NovoID_ALIMENTACAO()));
                            data.add(mAlimentacao);

                            mDataBase.insertTABLE_ALIMENTACAO(data);


                            finish();

                            Toast.makeText(DetailActivity.this, "Duplicado alimento com sucesso!" , Toast.LENGTH_SHORT).show();
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
                }
            }
        });


        tv_deletar_alimento = (TextView)  findViewById(R.id.tv_deletar_alimento);
        tv_deletar_alimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {

                    LayoutInflater factory = LayoutInflater.from(DetailActivity.this);
                    final View deleteDialogView = factory.inflate(
                            R.layout.custom_dialog, null);
                    final AlertDialog deleteDialog = new AlertDialog.Builder(DetailActivity.this).create();
                    deleteDialog.setView(deleteDialogView);

                    TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                    nTextView.setText("ATENÇÃO! Tem certeza que deseja deletar esse alimento?");

                    deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            List<Alimento> data = new ArrayList<>();
                            Alimento alimento = new Alimento();

                            mDataBase = new DataBase(getApplicationContext());
                            mDb = mDataBase.getReadableDatabase();

                            mDataBase.deleteAlimentacao(mAlimentacao.getAlimentacao_id().toString());


                            finish();

                            Toast.makeText(DetailActivity.this, "Deletado alimento com sucesso!" , Toast.LENGTH_SHORT).show();
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
                }
            }
        });



        // alimento
        alimento_nome = (TextView)  findViewById(R.id.alimento_nome);
        alimento = (TextView)  findViewById(R.id.alimento);
        alimento_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showAlimento();

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        alimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showAlimento();

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });


        // Unidade
        unidade_nome = (TextView)  findViewById(R.id.unidade_nome);
        unidade = (TextView)  findViewById(R.id.unidade);
        unidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showUnidade();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        unidade_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showUnidade();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        // Preparacao
        preparacao_nome = (TextView)  findViewById(R.id.preparacao_nome);
        preparacao = (TextView)  findViewById(R.id.preparacao);
        preparacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showPreparacao();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        preparacao_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showPreparacao();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        // Adicao
        adicao_nome = (TextView)  findViewById(R.id.adicao_nome);
        adicao = (TextView)  findViewById(R.id.adicao);
        adicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showAdicao();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        adicao_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showAdicao();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        // local
        local_nome = (TextView)  findViewById(R.id.local_nome);
        local = (TextView)  findViewById(R.id.local);
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showLocal();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        local_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showLocal();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        // OcasiaoCaonsumo
        ocasiaoConsumo_nome = (TextView)  findViewById(R.id.ocasiao_consumo_nome);
        ocasiaoConsumo = (TextView)  findViewById(R.id.ocasiao_consumo);
        ocasiaoConsumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showOcasiaoConsumo();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        ocasiaoConsumo_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    showOcasiaoConsumo();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });


        tv_salvar_alimento = (TextView)  findViewById(R.id.tv_salvar_alimento);
        tv_salvar_alimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    List<Alimentacao> alimentacaoList = new ArrayList<Alimentacao>();
                    Alimentacao alimentacao = new Alimentacao();

                    if (alimentacoAlteracao){
                        mDataBase.deleteAlimentacao(mAlimentacao.getAlimentacao_id().toString());
                        alimentacao.setAlimentacao_id(mAlimentacao.getAlimentacao_id().toString());
                    }else {
                        alimentacao.setAlimentacao_id(Integer.toString(mDataBase.NovoID_ALIMENTACAO()));
                    }

                    alimentacao.setAlimentacao_entrevistado_id(mEntrevistado);
                    alimentacao.setAlimentacao_entrevistado(mEntrevistadoNome);
                    alimentacao.setAlimentacao_alimento_id(alimento.getText().toString());
                    alimentacao.setAlimentacao_alimento(alimento_nome.getText().toString());
                    alimentacao.setAlimentacao_preparacao_id(preparacao.getText().toString());
                    alimentacao.setAlimentacao_preparacao(preparacao_nome.getText().toString());
                    alimentacao.setAlimentacao_adicao_id(adicao.getText().toString());
                    alimentacao.setAlimentacao_adicao(adicao_nome.getText().toString());
                    alimentacao.setAlimentacao_local_id(local.getText().toString());
                    alimentacao.setAlimentacao_local(local_nome.getText().toString());
                    alimentacao.setAlimentacao_unidade_id(unidade.getText().toString());
                    alimentacao.setAlimentacao_unidade(unidade_nome.getText().toString());
                    alimentacao.setAlimentacao_ocasiao_consumo_id(ocasiaoConsumo.getText().toString());
                    alimentacao.setAlimentacao_ocasiao_consumo(ocasiaoConsumo_nome.getText().toString());
                    alimentacao.setAlimentacao_quantidade(quantidadeEditText.getText().toString());
                    alimentacao.setAlimentacao_hora(horaEditText.getText().toString() + ":" + minutoEditText.getText().toString());
                    alimentacao.setAlimentacao_hora_coleta(tvHoraColeta.getText().toString());

                    Time today_fim = new Time(Time.getCurrentTimezone());
                    today_fim.setToNow();

                    String dia_fim = Integer.toString(today_fim.monthDay);
                    String mes_fim = Integer.toString(today_fim.month + 1);
                    String ano_fim = Integer.toString(today_fim.year);
                    String diaCompleto_fim = dia_fim + "/" + mes_fim + "/" + ano_fim;

                    String hora_coleta_fim = Integer.toString(today_fim.hour);
                    String minuto_coleta_fim = Integer.toString(today_fim.minute);
                    String minuto_segundos_fim = Integer.toString(today_fim.second);

                    String horacompleta_fim = hora_coleta_fim + ":" + minuto_coleta_fim + ":" + minuto_segundos_fim;



                    alimentacao.setAlimentacao_hora_coleta_fim(horacompleta_fim);
                    alimentacao.setAlimentacao_obs(obs.getText().toString());
                    alimentacao.setAlimentacao_usuario(mUsuario);
                    alimentacao.setAlimentacao_dia_coleta(diaCompleto_fim);
                    alimentacaoList.add(alimentacao);

                    mDataBase.insertTABLE_ALIMENTACAO(alimentacaoList);
                    Toast.makeText(DetailActivity.this, "Salvo alimento com sucesso!" , Toast.LENGTH_SHORT).show();
                    DetailActivity.this.finish();
                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(DetailActivity.this, "ATENÇÃO! ALIMENTO NÃO SALVO" , Toast.LENGTH_LONG).show();
                }
            }
        });

    if (alimentacoAlteracao){

        alimento.setOnClickListener(null);
        alimento_nome.setOnClickListener(null);
        alimento_nome.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        alimento.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        alimento_nome.setTypeface(null, Typeface.BOLD);
        alimento.setTypeface(null, Typeface.BOLD);
        tv_deletar_alimento.setVisibility(View.VISIBLE);
        tv_duplicar_alimento.setVisibility(View.VISIBLE);

        tvDiaColeta.setText(mAlimentacao.getAlimentacao_dia_coleta());
        tvHoraColeta.setText(mAlimentacao.getAlimentacao_hora_coleta());
        quantidadeEditText.setText(mAlimentacao.getAlimentacao_quantidade());
        minutoEditText.setText(pegarSoMinuto(mAlimentacao.getAlimentacao_hora()));
        horaEditText.setText(pegarSoHora(mAlimentacao.getAlimentacao_hora()));
        alimento.setText(mAlimentacao.getAlimentacao_alimento_id());
        alimento_nome.setText(mAlimentacao.getAlimentacao_alimento());
        preparacao.setText(mAlimentacao.getAlimentacao_preparacao_id());
        preparacao_nome.setText(mAlimentacao.getAlimentacao_preparacao());
        adicao.setText(mAlimentacao.getAlimentacao_adicao_id());
        adicao_nome.setText(mAlimentacao.getAlimentacao_adicao());
        unidade.setText(mAlimentacao.getAlimentacao_unidade_id());
        unidade_nome.setText(mAlimentacao.getAlimentacao_unidade());
        local.setText(mAlimentacao.getAlimentacao_local_id());
        local_nome.setText(mAlimentacao.getAlimentacao_local());
        ocasiaoConsumo.setText(mAlimentacao.getAlimentacao_ocasiao_consumo_id());
        ocasiaoConsumo_nome.setText(mAlimentacao.getAlimentacao_ocasiao_consumo());
        obs.setText(mAlimentacao.getAlimentacao_obs());
    }else {

        tv_deletar_alimento.setVisibility(View.GONE);
        tv_duplicar_alimento.setVisibility(View.GONE);
        quantidadeEditText.setText("0");
        minutoEditText.setText("00");
        horaEditText.setText("0");
        alimento.setText("0");
        alimento_nome.setText("0");
        preparacao.setText("0");
        preparacao_nome.setText("0");
        adicao.setText("0");
        adicao_nome.setText("0");
        unidade.setText("0");
        unidade_nome.setText("0");
        local.setText("0");
        local_nome.setText("0");
        ocasiaoConsumo.setText("0");
        ocasiaoConsumo_nome.setText("0");
        obs.setText("0");

        Modulo.NOME = "0";
        Modulo.ID = "0";
        Modulo.NOVO_ALIMENTO = "0";

        tvDiaColeta.setText(diaCompleto);
        tvHoraColeta.setText(horacompleta);
    }


    }

    @Override
    protected void onDestroy() {
        alimentacoAlteracao = false;
        super.onDestroy();
    }

    public String pegarSoHora(String hora){

        String resultado = "";

        if (hora != null){
            if (hora.length() >0 ){
                int i = hora.indexOf(":");

                if (i > 0){
                    resultado = hora.substring(0,i);
                }
            }
        }

        return resultado;
    }



    public String pegarSoMinuto(String minuto){

        String resultado = "";

        if (minuto != null){
            if (minuto.length() >0 ){
                int i = minuto.indexOf(":");

                if (i > 0){
                    resultado = minuto.substring(i+1,minuto.length());
                }
            }
        }

        return resultado;
    }

    @Override
    protected void onResume() {

        if (Modulo.OPCAO.equals("ALIMENTO")){
            alimento_nome.setText(Modulo.NOME);
            alimento.setText(Modulo.ID);
            novoAliemnto = Modulo.NOVO_ALIMENTO;
        }else if (Modulo.OPCAO.equals("UNIDADE")){
            unidade_nome.setText(Modulo.NOME);
            unidade.setText(Modulo.ID);
        }else if (Modulo.OPCAO.equals("PREPARACAO")){
            preparacao_nome.setText(Modulo.NOME);
            preparacao.setText(Modulo.ID);
        }else if (Modulo.OPCAO.equals("ADICAO")){
            adicao_nome.setText(Modulo.NOME);
            adicao.setText(Modulo.ID);
        }else if (Modulo.OPCAO.equals("LOCAL")){
            local_nome.setText(Modulo.NOME);
            local.setText(Modulo.ID);
        }else if (Modulo.OPCAO.equals("OCASIAO_CONSUMO")){
            ocasiaoConsumo_nome.setText(Modulo.NOME);
            ocasiaoConsumo.setText(Modulo.ID);
        }



        super.onResume();
    }



    private void showAlimento(){
        Class destinationClass = AlimentoActivity.class;
        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistado);
        startActivity(intentToStartDetailActivity);
    }

    private void showUnidade(){
        Class destinationClass = UnidadeActivity.class;
        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistado);

        if ((novoAliemnto!= null) && novoAliemnto.equals("1")) {
            intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, "S");
        }else{
            intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, alimento.getText().toString());
        }
        startActivity(intentToStartDetailActivity);
    }

    private void showPreparacao(){
        Class destinationClass = PreparacaoActivity.class;
        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistado);
        if ((novoAliemnto!= null) && novoAliemnto.equals("1")) {
            intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, "S");
        }else {
            intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, alimento.getText().toString());
        }
        startActivity(intentToStartDetailActivity);
    }

    private void showAdicao(){
        Class destinationClass = AdicaoActivity.class;
        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistado);
        if ((novoAliemnto!= null) && novoAliemnto.equals("1")) {
            intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, "S");
        }else {
            intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, alimento.getText().toString());
        }
        startActivity(intentToStartDetailActivity);
    }

    private void showOcasiaoConsumo(){
        Class destinationClass = OcasiaoConsumoActivity.class;
        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistado);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, alimento.getText().toString());
        startActivity(intentToStartDetailActivity);
    }

    private void showLocal(){
        Class destinationClass = LocalActivity.class;
        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistado);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, alimento.getText().toString());
        startActivity(intentToStartDetailActivity);
    }

}
