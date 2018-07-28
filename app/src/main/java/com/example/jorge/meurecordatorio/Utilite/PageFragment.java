package com.example.jorge.meurecordatorio.Utilite;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.darwindeveloper.wcviewpager.WCViewPagerIndicator;
import com.example.jorge.meurecordatorio.DetailActivity;
import com.example.jorge.meurecordatorio.Generica.GrauParentescoActivity;
import com.example.jorge.meurecordatorio.MainActivity;
import com.example.jorge.meurecordatorio.R;

import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO_NOME;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ETAPA;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_GRAU_PARENTESCO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_USUARIO;

@SuppressLint("ValidFragment")
public class PageFragment extends Fragment {

    private TextView TextViewAdiciona;


    public static final String PAGE = "com.darwindeveloper.wcviewpager.PAGE";

    private Context context;

    public static String MNOME = "0";
    public static String MID = "0";
    public static String USUARIO = "0";

    public WCViewPagerIndicator mWcViewPagerIndicator;

    public static int numPage = 0;



    @SuppressLint("ValidFragment")
    public PageFragment(String nome, String ID, WCViewPagerIndicator wcViewPagerIndicator) {
        mWcViewPagerIndicator = wcViewPagerIndicator;
        MNOME = nome;
        MID = ID;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.fragment_page, container, false);

        //

       // numPage = getArguments().getInt(PAGE);
        numPage = mWcViewPagerIndicator.indicadorAdapter.mPosition;
        TextViewAdiciona = (TextView) rootView.findViewById(R.id.TextViewAdiciona);

        TextViewAdiciona.setVisibility(View.VISIBLE);
        TextViewAdiciona.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        TextViewAdiciona.setText("Adicionar alimento");



                try {



                     if (numPage == 1){



                        try {

                            TextViewAdiciona.setVisibility(View.VISIBLE);
                            TextViewAdiciona.setVisibility(View.VISIBLE);
                            TextViewAdiciona.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                            TextViewAdiciona.setText("Adicionar alimento");



                            LayoutInflater factory = LayoutInflater.from(container.getContext());
                            final View deleteDialogView = factory.inflate(
                                    R.layout.custom_dialog1, null);
                            final AlertDialog deleteDialog1 = new AlertDialog.Builder(container.getContext()).create();
                            deleteDialog1.setView(deleteDialogView);

                            TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                            nTextView.setText("ATENÇÃO!\n Perguntar se houve adição de açúcar ou outra substância com o intuito de adoçar as bebidas, preparações ou alimentos. \n Ou se teve mais algum alimento! \nCaso não tenha mais nenhum alimento aperte em SIM! \n Ou aperte em NÃO para voltar e inserir mais alimentos");

                            deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {


                                    LayoutInflater factory = LayoutInflater.from(container.getContext());
                                    final View deleteDialogView2 = factory.inflate(
                                            R.layout.custom_dialog2, null);
                                    final AlertDialog deleteDialog2 = new AlertDialog.Builder(container.getContext()).create();
                                    deleteDialog2.setView(deleteDialogView2);

                                    TextView nTextView2 = (TextView) deleteDialogView2.findViewById(R.id.txt_dia);
                                    nTextView2.setText("ATENÇÃO! \n Perguntar se a criança consumiu algum alimento que não tenha sido relatado, como: balas, chicletes, biscoitos, bebidas, doces, manteigas/margarina, em outros! \n Caso não tenha mais nenhum alimento aperte em SIM! \n Ou aperte em NÃO para voltar e inserir mais alimentos");

                                    deleteDialogView2.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            TextViewAdiciona.setVisibility(View.GONE);
                                            deleteDialog2.dismiss();


                                        }
                                    });
                                    deleteDialogView2.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            deleteDialog2.dismiss();

                                        }
                                    });

                                    deleteDialog2.show();
                                    deleteDialog1.dismiss();







                                }
                            });
                            deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    deleteDialog1.dismiss();

                                }
                            });

                            deleteDialog1.show();


                        } catch (Exception e) {
                            // TODO: handle exception
                            Toast.makeText(container.getContext(), "ATENÇÃO! Problema reinicie o sistema" , Toast.LENGTH_LONG).show();
                        }


                    }else if (numPage == 2){
                        TextViewAdiciona.setVisibility(View.VISIBLE);
                        TextViewAdiciona.setBackground(getResources().getDrawable(R.drawable.rounded_corner_yellow));
                        TextViewAdiciona.setText("Adicionar Complemento");


                         LayoutInflater factory = LayoutInflater.from(container.getContext());
                         final View deleteDialogView = factory.inflate(
                                 R.layout.custom_dialog1, null);
                         final AlertDialog deleteDialog1 = new AlertDialog.Builder(container.getContext()).create();
                         deleteDialog1.setView(deleteDialogView);

                         TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                         nTextView.setText("ATENÇÃO!\n Clique em cada alimento para adicionar o complemento");

                         deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                             @Override
                             public void onClick(View v) {
                                 deleteDialog1.dismiss();
                             }
                         });
                         deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                             @Override
                             public void onClick(View v) {
                                 deleteDialog1.dismiss();

                             }
                         });

                         deleteDialog1.show();



                     }else if ( numPage == 3){
                         TextViewAdiciona.setVisibility(View.VISIBLE);
                         TextViewAdiciona.setBackground(getResources().getDrawable(R.drawable.rounded_corner_red));
                         TextViewAdiciona.setText("Adicionar Complemento");

                         LayoutInflater factory = LayoutInflater.from(container.getContext());
                         final View deleteDialogView = factory.inflate(
                                 R.layout.custom_dialog1, null);
                         final AlertDialog deleteDialog1 = new AlertDialog.Builder(container.getContext()).create();
                         deleteDialog1.setView(deleteDialogView);

                         TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                         nTextView.setText("ATENÇÃO!\n Coleta encerrada pode fazer a revisão, caso necessário pode iniciar os passos novamente");

                         deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {

                             @Override
                             public void onClick(View v) {
                                 deleteDialog1.dismiss();
                             }
                         });
                         deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {

                             @Override
                             public void onClick(View v) {
                                 deleteDialog1.dismiss();

                             }
                         });

                         deleteDialog1.show();

                    }

                    Modulo.ETAPA = numPage;

                } catch (Exception e) {
                    // TODO: handle exception
                }





        TextViewAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MID = "1";
                if (!MID.toString().equals("0")) {
                    try {

                        Modulo.OPCAO = "0";
                        Class destinationClass = DetailActivity.class;
                        Intent intentToStartDetailActivity = new Intent(container.getContext(), destinationClass);
                        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, MID.toString());
                        intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO_NOME, MNOME.toString());
                        intentToStartDetailActivity.putExtra(PUT_EXTRA_USUARIO, USUARIO);
                        intentToStartDetailActivity.putExtra(PUT_EXTRA_ETAPA, Integer.toString(numPage));
                        startActivity(intentToStartDetailActivity);

                    } catch (Exception e) {
                        // TODO: handle exception
                    }



                }else{
                    Toast.makeText(container.getContext(),"Escolha um entrevistado!",Toast.LENGTH_LONG).show();

                }
            }
        });






        return rootView;
    }


}
