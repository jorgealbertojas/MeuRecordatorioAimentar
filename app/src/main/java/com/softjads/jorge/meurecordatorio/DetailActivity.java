
package com.softjads.jorge.meurecordatorio;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.softjads.jorge.meurecordatorio.Generica.AdicaoActivity;
import com.softjads.jorge.meurecordatorio.Generica.AlimentoActivity;
import com.softjads.jorge.meurecordatorio.Generica.LocalActivity;
import com.softjads.jorge.meurecordatorio.Generica.OcasiaoConsumoActivity;
import com.softjads.jorge.meurecordatorio.Generica.PreparacaoActivity;
import com.softjads.jorge.meurecordatorio.Generica.UnidadeActivity;
import com.softjads.jorge.meurecordatorio.Help.Help;
import com.softjads.jorge.meurecordatorio.Help.HelpAppActivity;
import com.softjads.jorge.meurecordatorio.Model.Alimentacao;
import com.softjads.jorge.meurecordatorio.Model.Alimento;
import com.softjads.jorge.meurecordatorio.PersistentData.DataBase;
import com.softjads.jorge.meurecordatorio.PersistentData.DbInstance;
import com.softjads.jorge.meurecordatorio.Utilite.Common;
import com.softjads.jorge.meurecordatorio.Utilite.Modulo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_BUNDLE_ALIMENTACAO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ALIMENTACAO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ALIMENTO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_DIA_ATIPICO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO_NOME;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ETAPA;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_GRAU_PARENTESCO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_GRAU_PARENTESCO_NOME;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_POSITION;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_USUARIO;
import static com.softjads.jorge.meurecordatorio.Utilite.Modulo.NAO_SE_APLICA;

public class DetailActivity extends AppCompatActivity {

    public static int[] locationInScreen = new int[]{0,0};

    private ArrayList<CheckBox> checkboxs;
    private LinearLayout ll;

    private SQLiteDatabase mDb;
    private DataBase mDataBase;

    String mEntrevistado;
    String mEntrevistadoNome;
    String mUsuario;

    private static int lastposition = 0;

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
    public static EditText quantidadeEditText;
    public static TextView quantidadeEditText2;
    public static RadioButton rbFotoManual;
    public static RadioButton rbMedidaCaseira;
    public static RadioButton rbGramaMl;
    public static TextView fracao_hint;
    public static ImageView imageview_trash;

    TextView tv_deletar_alimento;
    TextView tv_duplicar_alimento;



    TextView quantidadeEditTextHINT;
    TextView horaEditTextHINT;
    TextView preparacaoHINT;
    TextView adicaoHINT;
    TextView unidadeHINT;
    TextView localHINT;
    TextView ocasiaoConsumoHINT;
    TextView obsHINT;
    TextView hora_ponto;
    TextView hora_minuto_coleta_hint;
    TextView tv_entrar_obs;
    TextView tv_quantificacao;

    Button buttonfracao;
    Button buttonfracao2;
    Button buttonfracao3;
    Button buttonfracao4;
    Button buttonfracao5;

    Button buttonvirgula_espessura;

    public static EditText espessura;
    TextView espessura_hint;




    public static Alimentacao mAlimentacao;
    public static boolean alimentacoAlteracao = false;
    public static int mEtapa = 0;

    public String diaAtipico;

    public String grau_parentesco;
    public String grau_parentesco_nome;

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.ll = (LinearLayout) this.findViewById(R.id.edits_ll);

        mContext = this.ll.getContext();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDataBase = new DataBase(DetailActivity.this);
                mDb = mDataBase.getReadableDatabase();
                DbInstance.getInstance(DetailActivity.this);
                List<Help> helpList = mDataBase.getListHelp();
                if (helpList.size() > 0){
                    onHelpLoaded(helpList,view,DetailActivity.this);
                }

            }
        });

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
        mEtapa = extras.getInt(PUT_EXTRA_ETAPA);

        grau_parentesco = extras.getString(PUT_EXTRA_GRAU_PARENTESCO);
        grau_parentesco_nome = extras.getString(PUT_EXTRA_GRAU_PARENTESCO_NOME);
        diaAtipico = extras.getString(PUT_EXTRA_DIA_ATIPICO);

        lastposition = extras.getInt(PUT_EXTRA_POSITION);


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

        tv_quantificacao = (TextView) findViewById(R.id.tv_quantificacao);

        espessura = (EditText) findViewById(R.id.espessura);
        espessura.setInputType(InputType.TYPE_CLASS_NUMBER);

        minutoEditText = (TextView) findViewById(R.id.hora_minuto);
        obs = (EditText) findViewById(R.id.obs) ;
        quantidadeEditText = (EditText) findViewById(R.id.quantidade);
        quantidadeEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        quantidadeEditText2 = (TextView) findViewById(R.id.quantidade2);
        fracao_hint = (TextView) findViewById(R.id.fracao_hint);

        rbFotoManual = (RadioButton) findViewById(R.id.rb_foto_manual);
        rbMedidaCaseira = (RadioButton) findViewById(R.id.rb_medida_caseira);
        rbGramaMl = (RadioButton) findViewById(R.id.rb_grama_ou_ml);
        imageview_trash = (ImageView) findViewById(R.id.imageview_trash);

        imageview_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                quantidadeEditText2.setText("");
            }
        });

        rbFotoManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                rbFotoManual.setChecked(true);
                rbMedidaCaseira.setChecked(false);
                rbGramaMl.setChecked(false);

                buttonfracao.setVisibility(View.VISIBLE);
                buttonfracao2.setVisibility(View.VISIBLE);
                buttonfracao3.setVisibility(View.VISIBLE);
                buttonfracao4.setVisibility(View.VISIBLE);
                buttonfracao5.setVisibility(View.VISIBLE);

                buttonfracao.setText("1/2");
                buttonfracao2.setText("1/4");
                buttonfracao3.setText("1/8");
                buttonfracao4.setText("3/4");
                buttonfracao5.setText("7/8");

                tv_quantificacao.setVisibility(View.VISIBLE);
                putSizeLenght(2);
                quantidadeEditText2.setVisibility(View.VISIBLE);
                fracao_hint.setVisibility(View.VISIBLE);
                imageview_trash.setVisibility(View.VISIBLE);
                unidade.setVisibility(View.VISIBLE);
                unidadeHINT.setVisibility(View.VISIBLE);
                unidade_nome.setVisibility(View.VISIBLE);
            }
        });

        rbMedidaCaseira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                rbFotoManual.setChecked(false);
                rbMedidaCaseira.setChecked(true);
                rbGramaMl.setChecked(false);
                putSizeLenght(2);
                buttonfracao.setVisibility(View.VISIBLE);
                buttonfracao2.setVisibility(View.VISIBLE);
                buttonfracao3.setVisibility(View.VISIBLE);
                buttonfracao4.setVisibility(View.VISIBLE);
                buttonfracao5.setVisibility(View.VISIBLE);

                buttonfracao.setText("1/2");
                buttonfracao2.setText("1/3");
                buttonfracao3.setText("1/4");
                buttonfracao4.setText("2/3");
                buttonfracao5.setText("3/4");

                tv_quantificacao.setVisibility(View.VISIBLE);
                quantidadeEditText2.setVisibility(View.VISIBLE);
                imageview_trash.setVisibility(View.VISIBLE);
                fracao_hint.setVisibility(View.VISIBLE);

                unidade.setVisibility(View.VISIBLE);
                unidadeHINT.setVisibility(View.VISIBLE);
                unidade_nome.setVisibility(View.VISIBLE);
            }
        });

        rbGramaMl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                rbFotoManual.setChecked(false);
                rbMedidaCaseira.setChecked(false);
                rbGramaMl.setChecked(true);
                putSizeLenght(3);
                putFotoManualvisible();
                unidade.setVisibility(View.GONE);
                unidadeHINT.setVisibility(View.GONE);
                unidade_nome.setVisibility(View.GONE);
            }

        });


        /// HINT
        quantidadeEditTextHINT  =  (TextView) findViewById(R.id.quantidade_hint);
        horaEditTextHINT  =  (TextView) findViewById(R.id.hora_hint);
        preparacaoHINT  =  (TextView) findViewById(R.id.preparacao_hint);
        adicaoHINT  =  (TextView) findViewById(R.id.adicao_hint);
        unidadeHINT  =  (TextView) findViewById(R.id.unidade_hint);
        localHINT =  (TextView) findViewById(R.id.local_hint);
        ocasiaoConsumoHINT  =  (TextView) findViewById(R.id.ocasiao_consumo_hint);
        obsHINT  =  (TextView) findViewById(R.id.obs_hint);
        hora_ponto   =  (TextView) findViewById(R.id.hora_ponto);
        hora_minuto_coleta_hint   =  (TextView) findViewById(R.id.hora_minuto_coleta_hint);


        espessura_hint = (TextView) findViewById(R.id.espessura_hint);


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
                minutoEditTextClick();
            }
        });

        buttonfracao = (Button) findViewById(R.id.buttonfracao);
        buttonfracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                quantidadeEditText2.setText(buttonfracao.getText().toString());

            }
        });

        buttonfracao2 = (Button) findViewById(R.id.buttonfracao2);
        buttonfracao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                quantidadeEditText2.setText(buttonfracao2.getText().toString());

            }
        });

        buttonfracao3 = (Button) findViewById(R.id.buttonfracao3);
        buttonfracao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                quantidadeEditText2.setText(buttonfracao3.getText().toString());

            }
        });

        buttonfracao4 = (Button) findViewById(R.id.buttonfracao4);
        buttonfracao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                quantidadeEditText2.setText(buttonfracao4.getText().toString());

            }
        });

        buttonfracao5 = (Button) findViewById(R.id.buttonfracao5);
        buttonfracao5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                quantidadeEditText2.setText(buttonfracao5.getText().toString());

            }
        });

        buttonvirgula_espessura = (Button) findViewById(R.id.buttonvirgula_espessura);
        buttonvirgula_espessura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!verificaExiste(espessura.getText().toString())) {
                    espessura.setText(espessura.getText().toString() + buttonvirgula_espessura.getText().toString());
                    espessura.setSelection(espessura.length());
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

                            data.get(0).setAlimentacao_espessura("0");
                            data.get(0).setAlimentacao_adicao("0");
                            data.get(0).setAlimentacao_adicao_id("0");
                            data.get(0).setAlimentacao_preparacao("0");
                            data.get(0).setAlimentacao_preparacao_id("0");
                            data.get(0).setAlimentacao_obs("");
                            data.get(0).setAlimentacao_hora(":00");
                            data.get(0).setAlimentacao_unidade("0");
                            data.get(0).setAlimentacao_unidade_id("0");
                            data.get(0).setAlimentacao_ocasiao_consumo("0");
                            data.get(0).setAlimentacao_ocasiao_consumo_id("0");
                            data.get(0).setAlimentacao_local("0");
                            data.get(0).setAlimentacao_local_id("0");
                            data.get(0).setAlimentacao_quantidade("");
                            data.get(0).setAlimentacao_fracao("");

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



        if (Common.eLeitematerno(alimento_nome.getText().toString())){
            quantidadeEditTextHINT.setText("Minutos:");
        }

        tv_entrar_obs = (TextView) findViewById(R.id.tv_entrar_obs);
        tv_entrar_obs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {

                    showOpcoesOBS();

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        TextView tv_sair = (TextView) findViewById(R.id.tv_sair);
        tv_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {

                    closeOpcoesOBS();

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

                    Boolean salvar = true;
                    if  ((lastposition == 2) && (horaEditText.getText().toString().equals(""))){
                        Toast.makeText(DetailActivity.this, "ATENÇÃO! Obrigatório inserir hora.", Toast.LENGTH_LONG).show();
                        salvar = false;
                    }

                    if ((!alimento.getText().toString().equals("0")) && salvar) {
                        mDataBase = new DataBase(getApplicationContext());
                        mDb = mDataBase.getReadableDatabase();

                        mDataBase.updatealimentacao(mEntrevistado,grau_parentesco_nome,grau_parentesco,diaAtipico);

                        List<Alimentacao> alimentacaoList = new ArrayList<Alimentacao>();
                        Alimentacao alimentacao = new Alimentacao();

                        if (alimentacoAlteracao) {
                            mDataBase.deleteAlimentacao(mAlimentacao.getAlimentacao_id().toString());
                            alimentacao.setAlimentacao_id(mAlimentacao.getAlimentacao_id().toString());
                        } else {
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
                        alimentacao.setAlimentacao_fracao(quantidadeEditText2.getText().toString());
                        alimentacao.setAlimentacao_espessura(espessura.getText().toString());

                        if (rbGramaMl.isChecked()){
                            alimentacao.setAlimentacao_quantificacao("3");
                        } else if (rbMedidaCaseira.isChecked()){
                            buttonfracao.setText("1/2");
                            buttonfracao2.setText("1/4");
                            buttonfracao3.setText("1/8");
                            buttonfracao4.setText("3/4");
                            buttonfracao5.setText("7/8");
                            alimentacao.setAlimentacao_quantificacao("2");
                        } else {

                            buttonfracao.setText("1/2");
                            buttonfracao2.setText("1/3");
                            buttonfracao3.setText("1/4");
                            buttonfracao4.setText("2/3");
                            buttonfracao5.setText("3/4");
                            alimentacao.setAlimentacao_quantificacao("1");
                        }


                        String hora = horaEditText.getText().toString();
                        if (hora != null) {
                            if (hora.length() == 1){
                                hora = "0" + hora;
                            }
                        }

                        String minuto = minutoEditText.getText().toString();
                        if (hora != null) {
                            if (minuto.length() == 0) {
                                minuto = "00";
                            }
                        }

                        alimentacao.setAlimentacao_hora(hora + ":" + minuto);
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

                        alimentacao.setAlimentacao_grau_parentesco(grau_parentesco_nome);
                        alimentacao.setAlimentacao_grau_parentesco_id(grau_parentesco);
                        alimentacao.setAlimentacao_dia_atico(diaAtipico);

                        alimentacaoList.add(alimentacao);





                        if (Common.eLeitematerno(alimento_nome.getText().toString())){
                            alimentacao = naoSeaplica(alimentacao);
                        }

                        mDataBase.insertTABLE_ALIMENTACAO(alimentacaoList);
                        Toast.makeText(DetailActivity.this, "Salvo alimento com sucesso!", Toast.LENGTH_SHORT).show();
                        DetailActivity.this.finish();
                    }else{
                        if (salvar) {
                            Toast.makeText(DetailActivity.this, "ATENÇÃO! Obrigatório a seleção de um alimento", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch(Exception e){
                    // TODO: handle exception
                    Toast.makeText(DetailActivity.this, "ATENÇÃO! ALIMENTO NÃO SALVO", Toast.LENGTH_LONG).show();
                }

            }
        });

    if (alimentacoAlteracao){
         preencherValor();

    }else {

        tv_deletar_alimento.setVisibility(View.GONE);
        tv_duplicar_alimento.setVisibility(View.GONE);
        quantidadeEditText.setText("");
        quantidadeEditText2.setText("");
        rbFotoManual.setChecked(true);
        espessura.setText("");
        minutoEditText.setText("00");
        horaEditText.setText("");

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
        obs.setText("");

        Modulo.NOME = "0";
        Modulo.ID = "0";
        Modulo.NOVO_ALIMENTO = "0";

        tvDiaColeta.setText(diaCompleto);
        tvHoraColeta.setText(horacompleta);
    }


        if (lastposition < 4){
            colocarEtapaInvisivel();
            ocasiaoConsumo.setVisibility(View.VISIBLE);
            ocasiaoConsumo_nome.setVisibility(View.VISIBLE);
            ocasiaoConsumoHINT.setVisibility(View.VISIBLE);


        }

    showEtapa( lastposition);


        CheckComOpcoesOBS();

    }

    private void preencherValor() {
        alimento.setOnClickListener(null);
        alimento_nome.setOnClickListener(null);
        alimento_nome.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        alimento.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        alimento_nome.setTypeface(null, Typeface.BOLD);
        alimento.setTypeface(null, Typeface.BOLD);
        tv_deletar_alimento.setVisibility(View.VISIBLE);
        tv_duplicar_alimento.setVisibility(View.VISIBLE);
        if (mAlimentacao.getAlimentacao_dia_coleta() != null){
            tvDiaColeta.setText(mAlimentacao.getAlimentacao_dia_coleta());
        }

        if (mAlimentacao.getAlimentacao_hora_coleta() != null) {
            tvHoraColeta.setText(mAlimentacao.getAlimentacao_hora_coleta());
        }

        quantidadeEditText.setText(mAlimentacao.getAlimentacao_quantidade());
        quantidadeEditText2.setText(mAlimentacao.getAlimentacao_fracao());
        quantidadeEditText2.setText(mAlimentacao.getAlimentacao_fracao());

        quantidadeEditText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(!s.equals("")  ) {
                    if(s.length() > 0) {


                        // 3) Para Fotos do manual
                        if (rbFotoManual.isChecked()){
                            // - se fotos de medidores e de colher infantil --> 10
                            if ((Integer.parseInt(s.toString())) > 10) {
                                Toast.makeText(mContext , "Digite de novo a quantidade deste alimento!", Toast.LENGTH_SHORT).show();
                            // - se fotos de mamadeira --> 3
                            } else if ((Integer.parseInt(s.toString())) > 3) {
                                Toast.makeText(mContext , "Digite de novo a quantidade deste alimento!", Toast.LENGTH_SHORT).show();
                            // - para todas as outras fotos - 5
                            } else if ((Integer.parseInt(s.toString())) > 5) {
                                Toast.makeText(mContext , "Digite de novo a quantidade deste alimento!", Toast.LENGTH_SHORT).show();
                            }

                        } else if (rbGramaMl.isChecked()){
                            //1) Para Grama ou mililitro --> 500
                            if ((Integer.parseInt(s.toString())) > 500) {
                                Toast.makeText(mContext , "Digite de novo a quantidade deste alimento!", Toast.LENGTH_SHORT).show();
                            }

                        } else if (rbMedidaCaseira.isChecked()){
                            //  2) Para Medidas caseiras

                            // - se for colher de chá, colher infantil, medidor - 10
                            if ((Integer.parseInt(s.toString())) > 10) {
                                Toast.makeText(mContext , "Digite de novo a quantidade deste alimento!", Toast.LENGTH_SHORT).show();
                            // - se for mamadeira - 3
                            } else if ((Integer.parseInt(s.toString())) > 3) {
                                Toast.makeText(mContext , "Digite de novo a quantidade deste alimento!", Toast.LENGTH_SHORT).show();
                            // - para todos os outros alimentos - 5
                            } else if ((Integer.parseInt(s.toString())) > 5) {
                                Toast.makeText(mContext , "Digite de novo a quantidade deste alimento!", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }

                }
            }



            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });

        if (mAlimentacao.getAlimentacao_quantificacao().equals("3")) {
            rbGramaMl.setChecked(true);
            putFotoManualvisible();
            putSizeLenght(3);
        } else if (mAlimentacao.getAlimentacao_quantificacao().equals("2")) {
            rbMedidaCaseira.setChecked(true);
            buttonfracao.setText("1/2");
            buttonfracao2.setText("1/3");
            buttonfracao3.setText("1/4");
            buttonfracao4.setText("2/3");
            buttonfracao5.setText("3/4");
            putSizeLenght(2);
        } else {
            rbFotoManual.setChecked(true);
            buttonfracao.setText("1/2");
            buttonfracao2.setText("1/4");
            buttonfracao3.setText("1/8");
            buttonfracao4.setText("3/4");
            buttonfracao5.setText("7/8");
            putSizeLenght(2);
        }

        espessura.setText(mAlimentacao.getAlimentacao_espessura());
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

        if (mAlimentacao.getAlimentacao_alimento() != null){
            if (Common.eLeitematerno(mAlimentacao.getAlimentacao_alimento())) {
                quantidadeEditTextHINT.setText("Minutos:");
            }
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

            if (Common.eLeitematerno(alimento_nome.getText().toString())){
                quantidadeEditTextHINT.setText("Minutos:");
            }

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

        espessura.setVisibility(View.GONE);
        espessura_hint.setVisibility(View.GONE);
        buttonvirgula_espessura.setVisibility(View.GONE);
        if (unidade_nome.getText().toString() != null){
            if (unidade_nome.getText().length()>1) {
                if (unidade_nome.getText().toString().substring(0,2).toString().equals("FM")) {
                   // espessura.setVisibility(View.VISIBLE);
                  //  espessura_hint.setVisibility(View.VISIBLE);
                  //  buttonvirgula_espessura.setVisibility(View.VISIBLE);
                }
            }
        }




        super.onResume();
    }



    private void showAlimento(){
        Class destinationClass = AlimentoActivity.class;
        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistadoNome);
        startActivity(intentToStartDetailActivity);
    }

    private void showUnidade(){
        Class destinationClass = UnidadeActivity.class;
        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistadoNome);

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
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistadoNome);
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
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistadoNome);
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
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistadoNome);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, alimento.getText().toString());
        startActivity(intentToStartDetailActivity);
    }

    private void showLocal(){
        Class destinationClass = LocalActivity.class;
        Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistadoNome);
        intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTO, alimento.getText().toString());
        startActivity(intentToStartDetailActivity);
    }




    private void showEtapa(int etapa) {

        if (etapa == 1) {
            colocarEtapaInvisivel();
        }




    }

    private void colocarEtapaInvisivel(){
        quantidadeEditText.setVisibility(View.GONE);
        quantidadeEditText2.setVisibility(View.GONE);
        imageview_trash.setVisibility(View.GONE);

        rbFotoManual.setVisibility(View.GONE);
        rbMedidaCaseira.setVisibility(View.GONE);
        rbGramaMl.setVisibility(View.GONE);

        espessura.setVisibility(View.GONE);
        preparacao.setVisibility(View.GONE);
        preparacao_nome.setVisibility(View.GONE);
        adicao.setVisibility(View.GONE);
        adicao_nome.setVisibility(View.GONE);
        unidade.setVisibility(View.GONE);
        unidade_nome.setVisibility(View.GONE);
        local.setVisibility(View.GONE);
        local_nome.setVisibility(View.GONE);
        ocasiaoConsumo.setVisibility(View.INVISIBLE);
        ocasiaoConsumo_nome.setVisibility(View.GONE);
        obs.setVisibility(View.INVISIBLE);
        tv_entrar_obs.setVisibility(View.GONE);



        quantidadeEditTextHINT.setVisibility(View.GONE);
        preparacaoHINT.setVisibility(View.GONE);
        adicaoHINT.setVisibility(View.GONE);
        unidadeHINT.setVisibility(View.GONE);
        localHINT.setVisibility(View.GONE);
        ocasiaoConsumoHINT.setVisibility(View.GONE);
        obsHINT.setVisibility(View.GONE);
        espessura_hint.setVisibility(View.GONE);

        buttonfracao.setVisibility(View.GONE);
        buttonfracao2.setVisibility(View.GONE);
        buttonfracao3.setVisibility(View.GONE);
        buttonfracao4.setVisibility(View.GONE);
        buttonfracao5.setVisibility(View.GONE);

        tv_quantificacao.setVisibility(View.GONE);

    }



    private Alimentacao naoSeaplica(Alimentacao alimentacao){
        alimentacao.setAlimentacao_preparacao_id(NAO_SE_APLICA);
        alimentacao.setAlimentacao_preparacao(NAO_SE_APLICA);
        alimentacao.setAlimentacao_adicao_id(NAO_SE_APLICA);
        alimentacao.setAlimentacao_adicao(NAO_SE_APLICA);
        alimentacao.setAlimentacao_local_id(NAO_SE_APLICA);
        alimentacao.setAlimentacao_local(NAO_SE_APLICA);
        alimentacao.setAlimentacao_unidade_id(NAO_SE_APLICA);
        alimentacao.setAlimentacao_unidade(NAO_SE_APLICA);
        alimentacao.setAlimentacao_ocasiao_consumo_id(NAO_SE_APLICA);
        alimentacao.setAlimentacao_ocasiao_consumo(NAO_SE_APLICA);


        return alimentacao;
    }

    private void showOpcoesOBS(){
        ll.setVisibility(View.VISIBLE);

    }

    private void closeOpcoesOBS(){
        ll.setVisibility(View.GONE);
    }


    private void CheckComOpcoesOBS() {

        List<String> list = Common.pegarFormatosOpcoesOBS();

        checkboxs = new ArrayList<CheckBox>();

        for (int i = 0; i < list.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

            CheckBox checkbox = new CheckBox(this);
            checkbox.setText(list.get(i).toString());
            checkbox.setTypeface(null, Typeface.NORMAL);
            checkbox.setTextColor(getResources().getColor(R.color.colorWhite));

            checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);


            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(checkbox.getWindowToken(), 0);

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    boolean pegarEstado = isChecked;

                    if (isChecked) {
                        obs.setText(obs.getText() + ((CheckBox) buttonView).getText().toString() + ", ");
                    } else {

                        obs.setText(obs.getText().toString().replace(((CheckBox) buttonView).getText().toString() + ", ",""));

                    }
                }
            });

            checkboxs.add(checkbox); // adiciona a nova editText a lista.
            ll.addView(checkbox, params); // adiciona a editText ao ViewGroup
        }
    }

    private void minutoEditTextClick(){
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

    public boolean verificaExiste(String texto){

        if ((texto.indexOf(",") >= 0) || (texto.indexOf("/") >= 0)){
            return  true;
        }else{
            return  false;
        }


    }

    public void onHelpLoaded(List<Help> helpList, View root, Context context) {
        List<String> stringList = new ArrayList<String>();
        List<String> stringListTemp = new ArrayList<String>();
        List<Integer> stringListX = new ArrayList<Integer>();
        List<Integer> stringListY = new ArrayList<Integer>();
        List<String> stringListTOP= new ArrayList<String>();

        // We filter the tasks based on the requestType
        int i = 0;
        for (Help help : helpList) {
            int valueID = 0;
            int id_view = getResources().getIdentifier(help.getMkey(), "id", getPackageName());
            valueID = Common.getResourceString(help.getMkey(), id_view);
            if (valueID != 0) {
                View vievHelp2 = (View) root.findViewById(valueID);

                if (vievHelp2  == null){
                    vievHelp2 = (View) root.getRootView().findViewById(valueID);
                }

                if ((vievHelp2  != null) && (!stringList.contains(help.getMkey())) && (vievHelp2.getVisibility() == View.VISIBLE)) {
                    vievHelp2.getLocationOnScreen(locationInScreen);
                    stringList.add(help.getMkey());
                    stringListTemp.add(help.getMvalue());
                    stringListTOP.add(help.getMlast());
                    int tempX = locationInScreen[0];
                    if (tempX < 0){
                        tempX = tempX * (-1);
                        tempX = tempX - (vievHelp2.getWidth());
                    }
                    stringListX.add(tempX);


                    int tempY = locationInScreen[1] - (vievHelp2.getHeight() / 2);
                    if (tempY < 0){
                        tempY = tempY * (-1);
                    }
                    stringListY.add(tempY);
                }
            }
            i++;
        }

        Intent intent = new Intent(context, HelpAppActivity.class);
        intent.putExtra("HELP_ID", (Serializable) stringListTemp);
        intent.putExtra("HELP_X", (Serializable) stringListX);
        intent.putExtra("HELP_Y", (Serializable) stringListY);
        intent.putExtra("HELP_TOP", (Serializable) stringListTOP);
        intent.putExtra("HELP_POSITION_TAB",0 );

        context.startActivity(intent);

    }

    private void putFotoManualvisible() {
        buttonfracao.setVisibility(View.GONE);
        buttonfracao2.setVisibility(View.GONE);
        buttonfracao3.setVisibility(View.GONE);
        buttonfracao4.setVisibility(View.GONE);
        buttonfracao5.setVisibility(View.GONE);
        unidade.setVisibility(View.GONE);
        unidade_nome.setVisibility(View.GONE);
        unidadeHINT.setVisibility(View.GONE);
        quantidadeEditText2.setVisibility(View.GONE);
        imageview_trash.setVisibility(View.GONE);
        fracao_hint.setVisibility(View.GONE);
        quantidadeEditText2.setText("");
    }

    private void putSizeLenght(int novoMaxLength){
        // Crie um filtro para limitar o comprimento do texto
        InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(novoMaxLength);
        // Aplique o filtro ao EditText
        quantidadeEditText.setFilters(new InputFilter[]{lengthFilter});
        quantidadeEditText.setText("");
    }


}
