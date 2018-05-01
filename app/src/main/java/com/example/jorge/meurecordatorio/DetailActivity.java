
package com.example.jorge.meurecordatorio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.jorge.meurecordatorio.Generica.AdicaoActivity;
import com.example.jorge.meurecordatorio.Generica.AlimentoActivity;
import com.example.jorge.meurecordatorio.Generica.LocalActivity;
import com.example.jorge.meurecordatorio.Generica.OcasiaoConsumoActivity;
import com.example.jorge.meurecordatorio.Generica.PreparacaoActivity;
import com.example.jorge.meurecordatorio.Generica.UnidadeActivity;
import com.example.jorge.meurecordatorio.Utilite.Modulo;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ALIMENTO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_USUARIO;

public class DetailActivity extends AppCompatActivity {


    String mEntrevistado;
    String mUsuario;

    TextView alimento;
    TextView alimento_nome;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /**
         * Get putExtra for Activity Main .
         */
        Bundle extras = getIntent().getExtras();

        mEntrevistado = extras.getString(PUT_EXTRA_ENTREVISTADO);
        mUsuario = extras.getString(PUT_EXTRA_USUARIO);

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



        /**
         * Put Name Repositorie in  title.
         */
        this.setTitle(mEntrevistado);



    }

    @Override
    protected void onResume() {

        if (Modulo.OPCAO.equals("ALIMENTO")){
            alimento_nome.setText(Modulo.NOME);
            alimento.setText(Modulo.ID);
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
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, alimento.getText().toString());
        startActivity(intentToStartDetailActivity);
    }

    private void showPreparacao(){
        Class destinationClass = PreparacaoActivity.class;
        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistado);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, alimento.getText().toString());
        startActivity(intentToStartDetailActivity);
    }

    private void showAdicao(){
        Class destinationClass = AdicaoActivity.class;
        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistado);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, alimento.getText().toString());
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
