package com.softjads.jorge.meurecordatorio.Utilite;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.softjads.jorge.meurecordatorio.DetailActivity;
import com.softjads.jorge.meurecordatorio.MainActivity;
import com.softjads.jorge.meurecordatorio.Model.Alimentacao;
import com.softjads.jorge.meurecordatorio.PersistentData.DataBase;
import com.softjads.jorge.meurecordatorio.PersistentData.DbCreate;
import com.softjads.jorge.meurecordatorio.PersistentData.DbInstance;
import com.softjads.jorge.meurecordatorio.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_DIA_ATIPICO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO_NOME;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ETAPA;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_GRAU_PARENTESCO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_GRAU_PARENTESCO_NOME;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_POSITION;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_USUARIO;
import static com.softjads.jorge.meurecordatorio.Utilite.Modulo.filename;

public class FragmentViewPager extends Fragment {

    private static int mPos = 0;
    private String mUsuario = "";

    private SQLiteDatabase mDb;
    private DataBase mDataBase;


    File myExternalFile;



    public static boolean continua = true;
    public static int contador = 0;

    private String filtro_desc_pesquisa = "REC24";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mDataBase = new DataBase(getContext());
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(getContext());

        TextView tv = (TextView) v.findViewById(R.id.title);
        tv.setText(getArguments().getString("text"));

        final TextView TextViewAdiciona = (TextView) v.findViewById(R.id.TextViewAdiciona);
        TextViewAdiciona.setText(getArguments().getString("text2"));

        ImageView imageView = (ImageView) v.findViewById(R.id.image);
        imageView.setBackgroundResource(getArguments().getInt("img"));

        mPos = getArguments().getInt("pos");
        mUsuario = getArguments().getString("usuario");





        try {

            if (mPos == 0){
                contador = 0;
                TextViewAdiciona.setBackground(null);
            }

            else if (mPos == 1){
                contador = 0;
                TextViewAdiciona.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                TextViewAdiciona.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if (!((MainActivity) container.getContext()).entrevistado.getText().toString().equals("0")) {
                            try {

                                Modulo.OPCAO = "0";
                                Class destinationClass = DetailActivity.class;
                                Intent intentToStartDetailActivity = new Intent(getContext(), destinationClass);
                                intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, ((MainActivity) container.getContext()).entrevistado.getText().toString());
                                intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO_NOME, ((MainActivity) container.getContext()).entrevistado_nome.getText().toString());
                                intentToStartDetailActivity.putExtra(PUT_EXTRA_USUARIO, ((MainActivity) container.getContext()).USUARIO);
                                intentToStartDetailActivity.putExtra(PUT_EXTRA_ETAPA, mPos);
                                intentToStartDetailActivity.putExtra(PUT_EXTRA_DIA_ATIPICO, ((MainActivity) container.getContext()).diaAtipico.getText().toString());
                                intentToStartDetailActivity.putExtra(PUT_EXTRA_GRAU_PARENTESCO_NOME, ((MainActivity) container.getContext()).grau_parentesco_nome.getText().toString());
                                intentToStartDetailActivity.putExtra(PUT_EXTRA_GRAU_PARENTESCO, ((MainActivity) container.getContext()).grauParentesco.getText().toString());
                                intentToStartDetailActivity.putExtra(PUT_EXTRA_POSITION,((MainActivity) container.getContext()).mViewPager.getCurrentItem());
                                startActivity(intentToStartDetailActivity);

                            } catch (Exception e) {
                                // TODO: handle exception
                            }



                        }else{
                            Toast.makeText(getContext(),"Escolha um entrevistado!",Toast.LENGTH_LONG).show();

                        }
                    }
                });
            } else if (mPos == 2) {
                contador = 0;
                TextViewAdiciona.setBackground(null);

            } else if (mPos == 3) {
                contador = 0;
                TextViewAdiciona.setBackground(null);

                if (!((MainActivity) getActivity()).voltandoNovo) {
                    try {


                        LayoutInflater factory = LayoutInflater.from(getContext());
                        final View deleteDialogView = factory.inflate(
                                R.layout.custom_dialog, null);
                        final AlertDialog deleteDialog1 = new AlertDialog.Builder(getContext()).create();
                        deleteDialog1.setView(deleteDialogView);
                        deleteDialog1.setCancelable(false);

                        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                        nTextView.setText("Bebidas como café, chá, refrigerante, suco?");

                        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {


                                LayoutInflater factory = LayoutInflater.from(getContext());
                                final View deleteDialogView = factory.inflate(
                                        R.layout.custom_dialog, null);
                                final AlertDialog deleteDialog0 = new AlertDialog.Builder(getContext()).create();
                                deleteDialog0.setView(deleteDialogView);

                                TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                                nTextView.setText("Leite materno?");
                                deleteDialog0.setCancelable(false);
                                deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        LayoutInflater factory = LayoutInflater.from(getContext());
                                        final View deleteDialogView = factory.inflate(
                                                R.layout.custom_dialog, null);
                                        final AlertDialog deleteDialog3 = new AlertDialog.Builder(getContext()).create();
                                        deleteDialog3.setView(deleteDialogView);

                                        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                                        nTextView.setText("Doces como balas, chicletes e sobremesas?");
                                        deleteDialog3.setCancelable(false);
                                        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {

                                                LayoutInflater factory = LayoutInflater.from(getContext());
                                                final View deleteDialogView = factory.inflate(
                                                        R.layout.custom_dialog, null);
                                                final AlertDialog deleteDialog2 = new AlertDialog.Builder(getContext()).create();
                                                deleteDialog2.setView(deleteDialogView);

                                                TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                                                nTextView.setText("Algum tipo de biscoito?");
                                                deleteDialog2.setCancelable(false);
                                                deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {

                                                        LayoutInflater factory = LayoutInflater.from(getContext());
                                                        final View deleteDialogView = factory.inflate(
                                                                R.layout.custom_dialog, null);
                                                        final AlertDialog deleteDialog5 = new AlertDialog.Builder(getContext()).create();
                                                        deleteDialog5.setView(deleteDialogView);

                                                        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                                                        nTextView.setText("Alguma fruta, pão ou qualquer outro alimento, bebida ou complemento alimentar?");
                                                        deleteDialog5.setCancelable(false);
                                                        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                                                            @Override
                                                            public void onClick(View v) {

                                                                LayoutInflater factory = LayoutInflater.from(getContext());
                                                                final View deleteDialogView = factory.inflate(
                                                                        R.layout.custom_dialog, null);
                                                                final AlertDialog deleteDialog4 = new AlertDialog.Builder(getContext()).create();
                                                                deleteDialog4.setView(deleteDialogView);

                                                                TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                                                                nTextView.setText("Açúcar, mel ou outro produto para adoçar algum alimento ou bebida?");
                                                                deleteDialog4.setCancelable(false);
                                                                deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        deleteDialog4.dismiss();
                                                                    }
                                                                });
                                                                deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        ((MainActivity) container.getContext()).mViewPager.setCurrentItem(1);
                                                                        deleteDialog4.dismiss();

                                                                    }
                                                                });

                                                                deleteDialog4.show();
                                                                deleteDialog5.dismiss();
                                                            }
                                                        });
                                                        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                                            @Override
                                                            public void onClick(View v) {
                                                                ((MainActivity) container.getContext()).mViewPager.setCurrentItem(1);
                                                                deleteDialog5.dismiss();

                                                            }
                                                        });

                                                        deleteDialog5.show();
                                                        deleteDialog2.dismiss();
                                                    }
                                                });
                                                deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {
                                                        ((MainActivity) container.getContext()).mViewPager.setCurrentItem(1);
                                                        deleteDialog2.dismiss();

                                                    }
                                                });

                                                deleteDialog2.show();
                                                deleteDialog3.dismiss();
                                            }
                                        });
                                        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                ((MainActivity) container.getContext()).mViewPager.setCurrentItem(1);
                                                deleteDialog3.dismiss();

                                            }
                                        });

                                        deleteDialog3.show();
                                        deleteDialog0.dismiss();
                                    }
                                });
                                deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        ((MainActivity) container.getContext()).mViewPager.setCurrentItem(1);
                                        deleteDialog0.dismiss();

                                    }
                                });

                                deleteDialog0.show();
                                deleteDialog1.dismiss();
                            }
                        });
                        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                ((MainActivity) container.getContext()).mViewPager.setCurrentItem(1);
                                deleteDialog1.dismiss();

                            }
                        });

                        deleteDialog1.show();


                        LayoutInflater factory11 = LayoutInflater.from(getContext());
                        final View deleteDialogView11 = factory11.inflate(
                                R.layout.custom_dialog10, null);
                        final AlertDialog deleteDialog11 = new AlertDialog.Builder(getContext()).create();
                        deleteDialog1.setCancelable(false);
                        deleteDialog11.setView(deleteDialogView11);

                        TextView nTextView1 = (TextView) deleteDialogView11.findViewById(R.id.txt_dia);
                       // nTextView1.setText("ATENÇÃO!\n Há alguns alimentos que às vezes são esquecidos e que fazem parte deste tipo de entrevista. Além dos alimentos e bebidas referidos pelo(a) Sr(a), a criança consumiu.");
                        nTextView1.setText("ATENÇÃO!\n Listagem de alimentos esquecidos.");

                        deleteDialogView11.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                deleteDialog11.dismiss();
                            }
                        });

                        deleteDialog11.show();


                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }

            } else if (mPos == 4) {
                contador = 0;
                TextViewAdiciona.setBackground(null);

            }else if(mPos == 5){



                    TextViewAdiciona.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                    TextViewAdiciona.setText("Confere próximo alimento");
                    TextViewAdiciona.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            if (contador < ((MainActivity) container.getContext()).mRecyclerView.getAdapter().getItemCount()-1) {
                                ((MainActivity) container.getContext()).mRecyclerView.getAdapter().notifyItemMoved(0, ((MainActivity) container.getContext()).mRecyclerView.getAdapter().getItemCount() - 1);
                                //((MainActivity) container.getContext()).mRecyclerView.getAdapter().notifyDataSetChanged();
                                contador = contador + 1;
                            }else {
                                TextViewAdiciona.setOnClickListener(null);
                                TextViewAdiciona.setBackground(getResources().getDrawable(R.drawable.rounded_corner_red));
                                TextViewAdiciona.setText("Finalizar entrevista");
                                TextViewAdiciona.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {
                                        try {

                                            LayoutInflater factory = LayoutInflater.from(getContext());
                                            final View deleteDialogView = factory.inflate(
                                                    R.layout.custom_dialog, null);
                                            final AlertDialog deleteDialog1 = new AlertDialog.Builder(getContext()).create();
                                            deleteDialog1.setView(deleteDialogView);
                                            deleteDialog1.setCancelable(false);

                                            TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                                            nTextView.setText("Tem certeza que deseja finalizar a entrevista?");

                                            deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {


                                                    LayoutInflater factory = LayoutInflater.from(getContext());
                                                    final View deleteDialogView = factory.inflate(
                                                            R.layout.custom_dialog, null);
                                                    final AlertDialog deleteDialog = new AlertDialog.Builder(getContext()).create();
                                                    deleteDialog.setView(deleteDialogView);

                                                    TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                                                    nTextView.setText("Atenção: depois de encerrar, não poderá mais adicionar alimentos para esta criança. Tem certeza que deseja encerrar?");

                                                    deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View v) {
                                                            try {
                                                                gerarArquivoTXT();
                                                                ((MainActivity) container.getContext()).mViewPager.setCurrentItem(0);
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }

                                                            deleteDialog.dismiss();
                                                        }
                                                    });
                                                    deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View v) {
                                                            ((MainActivity) container.getContext()).mViewPager.setCurrentItem(0);
                                                            deleteDialog.dismiss();

                                                        }
                                                    });

                                                    deleteDialog.show();
                                                    deleteDialog1.dismiss();
                                                }
                                            });
                                            deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    ((MainActivity) container.getContext()).mViewPager.setCurrentItem(0);
                                                    deleteDialog1.dismiss();

                                                }
                                            });

                                            deleteDialog1.show();


                                        } catch (Exception e) {
                                            // TODO: handle exception
                                        }
                                    }
                                });

                            }

                        }
                    });






            }

        } catch (Exception e) {
            // TODO: handle exception
        }





        return v;
    }

    public void deleteTUDO(){
        mDataBase = new DataBase(getContext());
        mDb = mDataBase.getReadableDatabase();
        mDb.execSQL(" DELETE FROM " + DbCreate.TABLE_ENTREVISTADO);

        mDb.execSQL(" DELETE FROM " + DbCreate.TABLE_ALIMENTACAO);
    }

    public static FragmentViewPager newInstance(String text, String text2, int image, int pos, String usuario) {

        FragmentViewPager f = new FragmentViewPager();
        Bundle b = new Bundle();
        b.putString("text", text);
        b.putString("text2", text2);
        b.putInt("img", image);
        b.putInt("pos", pos);
        b.putString("usuario", usuario);

        f.setArguments(b);

        return f;
    }


    private void gerarArquivoTXT() throws IOException {

        salvearquivo();
        deleteTUDO();
        android.os.Process.killProcess(android.os.Process.myPid());

    }

    private void salvearquivo() throws IOException {

        String DataCompleta1;
        Time now1 = new Time();
        now1.setToNow();
        DataCompleta1 = Integer.toString(now1.year);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.month + 1);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.monthDay);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.hour);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.minute);
        DataCompleta1 = DataCompleta1 + "_" + Integer.toString(now1.second);


        myExternalFile = new File(Modulo.getSDCardPath(getContext()), filename);


        OutputStreamWriter outStreamWriter = null;
        FileOutputStream outStream = null;



        if ( myExternalFile.exists() == false ){
            myExternalFile.createNewFile();
        }

        outStream = new FileOutputStream(myExternalFile,true) ;
        outStreamWriter = new OutputStreamWriter(outStream);


        try {
            String ENTREVISTADO_E_NOVO = "0";
            int contadorDiferente = 0;
            List<Alimentacao> dataPersistent = new ArrayList<>();
            dataPersistent = mDataBase.getListAlimentacao(((MainActivity) this.getContext()).entrevistado.getText().toString());
            for (int i = 0; i < dataPersistent.size(); i++) {
                if (!(ENTREVISTADO_E_NOVO.toString().equals(dataPersistent.get(i).getAlimentacao_entrevistado_id()))){
                    contadorDiferente = 0;
                }
                contadorDiferente ++;
                ENTREVISTADO_E_NOVO = dataPersistent.get(i).getAlimentacao_entrevistado_id();


                String IDENTIFICADOR = getFormatodoComEspaco(2, "50");
                String ENTREVISTADO = getFormatodoComEspaco(32, dataPersistent.get(i).getAlimentacao_entrevistado_id());
                String SEQUENCIAL_ALIMENTO = getFormatodoComEspaco(3, Integer.toString(contadorDiferente));
                String ID_RECORDATORIO = getFormatodoComEspaco(3, dataPersistent.get(i).getAlimentacao_id());
                String ID_ALIMENTO  = getFormatodoComEspaco(8, dataPersistent.get(i).getAlimentacao_alimento_id());
                String ALIMENTO_NOVO = getFormatodoComEspaco(2, alimentoENovo(dataPersistent.get(i).getAlimentacao_alimento_id()));
                String ALIMENTO_DESCRICAO = getFormatodoComEspacoDireita(90, dataPersistent.get(i).getAlimentacao_alimento());
                String ID_PREPARACAO = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_preparacao_id());
                String ID_UNIDADE = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_unidade_id());
                String ID_ADICAO = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_adicao_id());
                String ID_LOCAL = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_local_id());
                String ID_CONSUMO = getFormatodoComEspaco(4, dataPersistent.get(i).getAlimentacao_ocasiao_consumo_id());
                String QUANTIDADE = getFormatodoComEspaco(10, dataPersistent.get(i).getAlimentacao_quantidade());
                String HORA = getFormatodoComEspacoHORA(4, dataPersistent.get(i).getAlimentacao_hora());
                String HORA_COLETA = getFormatodoComEspaco(6, formataHora(dataPersistent.get(i).getAlimentacao_hora_coleta()));
                String DATA_COLETA = getFormatodoComEspaco(8, formatarData(dataPersistent.get(i).getAlimentacao_dia_coleta()));
                String USUARIO = getFormatodoComEspacoDireita(20, dataPersistent.get(i).getAlimentacao_usuario());
                String OBS = getFormatodoComEspacoDireita(130, dataPersistent.get(i).getAlimentacao_obs());

                String ESPESSURA = getFormatodoComEspacoDireita(20, dataPersistent.get(i).getAlimentacao_espessura());
                String GRAU_PARENTESCO = getFormatodoComEspacoDireita(20, dataPersistent.get(i).getAlimentacao_grau_parentesco_id());

                String DIA_ATIPICO = dataPersistent.get(i).getAlimentacao_dia_atico();
                if (DIA_ATIPICO.equals("SIM")){
                    DIA_ATIPICO = "1";
                }else{
                    DIA_ATIPICO = "2";
                }

                String QUANTIFICACAO = dataPersistent.get(i).getAlimentacao_quantificacao();

                String FRACAO = "0";
                if (dataPersistent.get(i).getAlimentacao_fracao() == null) {
                    FRACAO = dataPersistent.get(i).getAlimentacao_fracao();
                }else if (dataPersistent.get(i).getAlimentacao_fracao().equals("1/2")) {
                    FRACAO = "1";
                }else if (dataPersistent.get(i).getAlimentacao_fracao().equals("1/4")) {
                    FRACAO = "2";
                }else if (dataPersistent.get(i).getAlimentacao_fracao().equals("1/8")) {
                    FRACAO = "3";
                }else if (dataPersistent.get(i).getAlimentacao_fracao().equals("3/4")) {
                    FRACAO = "4";
                }else if (dataPersistent.get(i).getAlimentacao_fracao().equals("7/8")) {
                    FRACAO = "5";
                }


                String formatado = IDENTIFICADOR + ENTREVISTADO  + SEQUENCIAL_ALIMENTO + ID_RECORDATORIO + ID_ALIMENTO + ALIMENTO_NOVO + ALIMENTO_DESCRICAO + ID_PREPARACAO + ID_UNIDADE + ID_ADICAO + ID_LOCAL + ID_CONSUMO + QUANTIDADE + HORA + HORA_COLETA + DATA_COLETA + USUARIO + OBS + ESPESSURA + GRAU_PARENTESCO + DIA_ATIPICO + QUANTIFICACAO + FRACAO + "\n";

                outStreamWriter.append(formatado);
                outStreamWriter.flush();



            }

            bkp();
            Toast.makeText(getContext(), "Encerrado com sucesso!", Toast.LENGTH_SHORT).show();







        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String alimentoENovo(String aliemntoENovoID){
        mDataBase = new DataBase(getContext());
        mDb = mDataBase.getReadableDatabase();
        DbInstance.getInstance(getContext());
        mDataBase.getListAlimentoID(aliemntoENovoID);
        if (mDataBase.getListAlimentoID(aliemntoENovoID) > 0){
            return "1";
        }else{
            return "0";
        }

    }

    private String getFormatodoComEspaco(int espaco, String valor){
        String resultado = valor;

        int i = valor.length();
        while (i < espaco){
            resultado = " " + resultado;
            i++;
        }

        return resultado;
    }

    private String getFormatodoComEspacoDireita(int espaco, String valor){
        String resultado = valor;

        int i = valor.length();
        while (i < espaco){
            resultado = resultado +  " ";
            i++;
        }

        if(resultado != null ){
            if (resultado.length() > espaco) {
                resultado = resultado.substring(0,espaco -1);
            }
        }

        return resultado;
    }

    private void bkp(){
        /////// salvar

        String DataCompleta;
        Time now = new Time();
        now.setToNow();
        DataCompleta = Integer.toString(now.year);
        DataCompleta = DataCompleta + "_" + Integer.toString(now.month + 1);
        DataCompleta = DataCompleta + "_" + Integer.toString(now.monthDay);
        DataCompleta = DataCompleta + "_" + Integer.toString(now.hour);
        DataCompleta = DataCompleta + "_" + Integer.toString(now.minute);
        DataCompleta = DataCompleta + "_" + Integer.toString(now.second);


        try {
            bkp(Modulo.caminhobanco, Modulo.storage + Modulo.NomeCopia + filtro_desc_pesquisa + DataCompleta + ".db");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mDb = mDataBase.getReadableDatabase();
        mDataBase.deleteAlimentacaoEntrevistado(((MainActivity) this.getContext()).entrevistado.getText().toString());

        mDb = mDataBase.getReadableDatabase();
        mDataBase.deleteentrevistado(((MainActivity) this.getContext()).entrevistado.getText().toString());

        ((MainActivity) this.getContext()).iniciaRecyclerView();

    }

    private String formatarData(String data){
        try{

            String ano ="";
            String mes ="";
            String dia = "";
            if (data != null){
                if (data.length() > 0){
                    int i = (data.indexOf("/"));
                    if (i > 0) {

                        dia = data.substring(0, i);
                        mes = data.substring(i+1, data.length());

                        int ii = (mes.indexOf("/"));
                        ano = mes.substring(0,mes.length());
                        mes = mes.substring(0,ii );



                        ano = ano.substring(ii+1, ano.length());
                    }

                }
            }

            if (mes.length() == 1){
                mes = "0" + mes;
            }

            if (dia.length() == 1){
                dia = "0" + dia;
            }


            return  ano + mes +dia;

        }catch(Exception e){
            return  "00000000";
        }
    }

    private String formataHora(String hora){
        try{
            if (hora != null){
                hora = hora.replace(":","");

                if (hora.toString().indexOf(":") == 0){
                    hora = "0" + hora;
                }

                return hora;
            }else{
                return "0000";
            }

        }catch(Exception e){
            return  "00000000";
        }

    }

    private String getFormatodoComEspacoHORA(int espaco, String valor){
        String resultado = valor;

        if (resultado != null){
            if (resultado.length()>0){
                if (resultado.indexOf(":") == 1){
                    resultado = " " + resultado;

                }
            }
            resultado = resultado.replace(":","");
        }




        int i = resultado.length();
        while (i < espaco){
            resultado = " " + resultado;
            i++;
        }

        return resultado;
    }

    private void bkp(String Origem, String Destino) throws IOException {
        final String inFileName = Origem;

        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);


        //String outFileName = Environment.getExternalStoragePublicDirectory()+"/database_copy.db";
        String outFileName = Destino;

        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        // Close the streams
        output.flush();
        output.close();
        fis.close();

    }

}
