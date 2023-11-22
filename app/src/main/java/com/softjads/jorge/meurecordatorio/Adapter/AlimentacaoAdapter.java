package com.softjads.jorge.meurecordatorio.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.softjads.jorge.meurecordatorio.ConfiguracaoActivity;
import com.softjads.jorge.meurecordatorio.DetailActivity;
import com.softjads.jorge.meurecordatorio.MainActivity;
import com.softjads.jorge.meurecordatorio.Model.Alimentacao;
import com.softjads.jorge.meurecordatorio.R;
import com.softjads.jorge.meurecordatorio.Utilite.Common;
import com.softjads.jorge.meurecordatorio.Utilite.Modulo;

import java.util.List;

import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_BUNDLE_ALIMENTACAO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ALIMENTACAO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_DIA_ATIPICO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO_NOME;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ETAPA;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_GRAU_PARENTESCO;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_GRAU_PARENTESCO_NOME;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_POSITION;
import static com.softjads.jorge.meurecordatorio.MainActivity.PUT_EXTRA_USUARIO;

/**
 * Created by jorge on 01/05/2018.
 */

public class AlimentacaoAdapter extends RecyclerView.Adapter<AlimentacaoAdapter.ViewHolder>  {

    private final List<Alimentacao> data;

    private int mostrar;

    private Context mContext;

    public static boolean estaFaltando = false;
    public static boolean estaFaltando2 = false;


    /*
 * An on-click handler that we've defined to make it easy for an Activity to interface with
 * our RecyclerView
 */
    private static AlimentacaoAdapterOnClickHandler mClickHandler;
    /**
     * The interface that receives onClick messages.
     */
    public interface AlimentacaoAdapterOnClickHandler {
        void onClick(Alimentacao alimentacao);
    }

    /** Constructs the class**/
    public  AlimentacaoAdapter(AlimentacaoAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        data = null;
    }




    /** class view holder**/
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_alimento;
        TextView tv_preparacao;
        TextView tv_adicao;
        TextView tv_hora;
        TextView tv_ocasiao_consumo;
        TextView tv_local;
        TextView tv_unidade;
        TextView tv_hora_coleta;
        TextView tv_dia_coleta;
        TextView tv_quantidade;
        TextView tv_usuario;
        ImageView iv_check;
        TextView tv_grau_parentesco;
        TextView tv_quantificacao;
        ImageView iv_check2;

        CardView card_view;



        /** get field of the main for show recyclerView**/
        public ViewHolder(View v) {
            super(v);

            card_view = (CardView) v.findViewById(R.id.card_view);

            tv_alimento = (TextView) v.findViewById(R.id.tv_alimento);

            tv_preparacao  = (TextView) v.findViewById(R.id.tv_preparacao);
            tv_unidade  = (TextView) v.findViewById(R.id.tv_unidade);
            tv_adicao = (TextView) v.findViewById(R.id.tv_adicao);

            tv_local  = (TextView) v.findViewById(R.id.tv_local);
            tv_ocasiao_consumo  = (TextView) v.findViewById(R.id.tv_ocasiao_consumo);

            tv_hora  = (TextView) v.findViewById(R.id.tv_hora);

            tv_quantificacao = (TextView) v.findViewById(R.id.tv_quantificacao);




            tv_quantidade = (TextView) v.findViewById(R.id.tv_quantidade);

            tv_hora_coleta  = (TextView) v.findViewById(R.id.tv_hora_coleta);
            tv_dia_coleta  = (TextView) v.findViewById(R.id.tv_dia_coleta);
            tv_usuario  = (TextView) v.findViewById(R.id.tv_usuario);

            iv_check = (ImageView) v.findViewById(R.id.iv_check);
            iv_check2 = (ImageView) v.findViewById(R.id.iv_check2);

            tv_grau_parentesco = (TextView) v.findViewById(R.id.tv_grau_parentesco);


            v.setOnClickListener(this);
        }

        /** configuration the Event onclick. Pass o Object Travel **/
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Alimentacao alimentacao = data.get(adapterPosition);

            if (((MainActivity) mContext).lastposition != 5 && ((MainActivity) mContext).lastposition != 2) {
                Class destinationClass = DetailActivity.class;
                Intent intentToStartDetailActivity = new Intent(mContext, destinationClass);
                Modulo.OPCAO = "0";
                Bundle bundle = new Bundle();
                bundle.putSerializable(PUT_BUNDLE_ALIMENTACAO, alimentacao);
                intentToStartDetailActivity.putExtra(PUT_EXTRA_ALIMENTACAO, bundle);
                intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, alimentacao.getAlimentacao_entrevistado_id());
                intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO_NOME, alimentacao.getAlimentacao_entrevistado());
                intentToStartDetailActivity.putExtra(PUT_EXTRA_USUARIO, alimentacao.getAlimentacao_usuario());
                intentToStartDetailActivity.putExtra(PUT_EXTRA_ETAPA, 2);
                intentToStartDetailActivity.putExtra(PUT_EXTRA_GRAU_PARENTESCO_NOME, ((MainActivity) mContext).grau_parentesco_nome.getText().toString());
                intentToStartDetailActivity.putExtra(PUT_EXTRA_GRAU_PARENTESCO, ((MainActivity) mContext).grauParentesco.getText().toString());
                intentToStartDetailActivity.putExtra(PUT_EXTRA_DIA_ATIPICO, ((MainActivity) mContext).diaAtipico.getText().toString());
                intentToStartDetailActivity.putExtra(PUT_EXTRA_POSITION, ((MainActivity) mContext).lastposition);
                mContext.startActivity(intentToStartDetailActivity);
            }else  if (((MainActivity) mContext).lastposition == 2) {
                Toast.makeText(((MainActivity) mContext), "No próximo passo pode fazer alterações!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(((MainActivity) mContext), "Volte ao Passo 4 para fazer alterações.", Toast.LENGTH_SHORT).show();
            }

        }



    }

    /** create lit de Adapter Travel**/
    public AlimentacaoAdapter(List<Alimentacao> data, int mostrar) {
        estaFaltando = false;
        estaFaltando2 = false;
        this.mostrar = mostrar;
        this.data = data;
    }

    /** Create information View holder**/
    @Override
    public AlimentacaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (mostrar == 5) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_alimentacao, parent, false);
        }
        else if (mostrar < 2) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_alimentacao, parent, false);
        }else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_alimentacao_check, parent, false);
        }

        mContext = parent.getContext();




        return new ViewHolder(v);
    }

    /** Create filed bind hold full **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        /** Paint dynamic forks image and Star image**/
     //   Resources res = mContext.getResources();
     //   final int newColor = res.getColor(R.color.colorYellow);
     //   holder.mForks.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
     //   holder.mStar.setColo        tv_grau_parentesco.


        /** Create filed bind hold full **/

        Alimentacao alimentacao = ((Alimentacao) data.get(position));
       // holder.tv_alimento.setText(alimentacao.getAlimentacao_alimento_id() + " - " + alimentacao.getAlimentacao_alimento());
        holder.tv_alimento.setText(alimentacao.getAlimentacao_alimento());

/*        holder.tv_preparacao.setText(alimentacao.getAlimentacao_preparacao_id()  + " - " + alimentacao.getAlimentacao_preparacao());
        holder.tv_adicao.setText(alimentacao.getAlimentacao_adicao_id()  + " - " + alimentacao.getAlimentacao_adicao());
        holder.tv_unidade.setText(alimentacao.getAlimentacao_unidade_id()  + " - " + alimentacao.getAlimentacao_unidade());*/

/*        holder.tv_local.setText(alimentacao.getAlimentacao_local_id()  + " - " + alimentacao.getAlimentacao_local());
        holder.tv_ocasiao_consumo.setText(alimentacao.getAlimentacao_ocasiao_consumo_id()  + " - " + alimentacao.getAlimentacao_ocasiao_consumo());*/

        holder.tv_hora.setText("Hora do consumo: " + alimentacao.getAlimentacao_hora());
        holder.tv_quantidade.setText("Quantidade: " + alimentacao.getAlimentacao_quantidade());

        holder.tv_hora_coleta.setText(alimentacao.getAlimentacao_hora_coleta());
        holder.tv_dia_coleta.setText(alimentacao.getAlimentacao_dia_coleta());
        holder.tv_usuario.setText(alimentacao.getAlimentacao_usuario());



        holder.tv_preparacao.setText(alimentacao.getAlimentacao_preparacao());
        holder.tv_adicao.setText(alimentacao.getAlimentacao_adicao());
        holder.tv_unidade.setText(alimentacao.getAlimentacao_unidade());
        holder.tv_local.setText(alimentacao.getAlimentacao_local());
        holder.tv_ocasiao_consumo.setText(alimentacao.getAlimentacao_ocasiao_consumo());

       /* holder.tv_preparacao.setText(alimentacao.getAlimentacao_preparacao_id()  + " - " + alimentacao.getAlimentacao_preparacao());
        holder.tv_adicao.setText(alimentacao.getAlimentacao_adicao_id()  + " - " + alimentacao.getAlimentacao_adicao());
        holder.tv_unidade.setText(alimentacao.getAlimentacao_unidade_id()  + " - " + alimentacao.getAlimentacao_unidade());
        holder.tv_local.setText(alimentacao.getAlimentacao_local_id()  + " - " + alimentacao.getAlimentacao_local());
        holder.tv_ocasiao_consumo.setText(alimentacao.getAlimentacao_ocasiao_consumo_id()  + " - " + alimentacao.getAlimentacao_ocasiao_consumo());*/
        holder.tv_hora.setText("Hora do consumo: " + alimentacao.getAlimentacao_hora());
        if (Common.eLeitematerno(alimentacao.getAlimentacao_alimento())) {
            holder.tv_quantidade.setText("Minutos: " + alimentacao.getAlimentacao_quantidade());
        }else{
            holder.tv_quantidade.setText("Quantidade: " + alimentacao.getAlimentacao_quantidade());
        }

        holder.tv_grau_parentesco.setText(alimentacao.getAlimentacao_grau_parentesco());
        if (alimentacao.getAlimentacao_dia_atico() == null){

        }

        Boolean check = true;
        if (mostrar != 2) {
            if (alimentacao.getAlimentacao_preparacao_id().toString().equals("") || alimentacao.getAlimentacao_preparacao_id().toString().equals("0")) {
                check = false;
            } else if (alimentacao.getAlimentacao_adicao_id().toString().equals("") || alimentacao.getAlimentacao_adicao_id().toString().equals("0")) {
                check = false;
            } else if (alimentacao.getAlimentacao_unidade_id().toString().equals("") || alimentacao.getAlimentacao_unidade_id().toString().equals("0") && (alimentacao.getAlimentacao_fracao().toString().equals("") || alimentacao.getAlimentacao_fracao().toString().equals("0") ) && (alimentacao.getAlimentacao_quantidade().toString().equals("")) || alimentacao.getAlimentacao_quantidade().toString().equals("0") ) {
                check = false;
            } else if (alimentacao.getAlimentacao_local_id().toString().equals("") || alimentacao.getAlimentacao_local_id().toString().equals("0")) {
                check = false;
            } else if (alimentacao.getAlimentacao_ocasiao_consumo_id().toString().equals("") || alimentacao.getAlimentacao_ocasiao_consumo_id().toString().equals("0")) {
                check = false;
            } else if (alimentacao.getAlimentacao_hora().toString().equals("") || alimentacao.getAlimentacao_hora().toString().equals("0") || alimentacao.getAlimentacao_hora().toString().equals("0:00")) {
                check = false;
            } else if ((alimentacao.getAlimentacao_quantidade().toString().equals("")) && (alimentacao.getAlimentacao_fracao().toString().equals("") )) {
                check = false;
            } else if (alimentacao.getAlimentacao_unidade_id().toString().equals("") || alimentacao.getAlimentacao_unidade_id().toString().equals("0") && (!alimentacao.getAlimentacao_quantificacao().equals("3") )) {
                check = false;
            }
        }else{
            if (alimentacao.getAlimentacao_ocasiao_consumo_id().toString().equals("") || alimentacao.getAlimentacao_ocasiao_consumo_id().toString().equals("0")){
                check = false;
            }else if (alimentacao.getAlimentacao_hora().toString().equals("") || alimentacao.getAlimentacao_hora().toString().equals("0") || alimentacao.getAlimentacao_hora().toString().equals("0:00") || alimentacao.getAlimentacao_hora().toString().equals(":00")){
                check = false;
            }
        }



        if (mostrar < 2) {
            holder.iv_check.setImageResource(R.mipmap.ic_check);
            holder.iv_check.setVisibility(View.INVISIBLE);
            holder.iv_check2.setImageResource(R.mipmap.ic_check);
            holder.iv_check2.setVisibility(View.INVISIBLE);
        }


        if (check) {
            holder.iv_check.setImageResource(R.mipmap.ic_check);
            holder.iv_check2.setImageResource(R.mipmap.ic_check);
            if (mostrar == 3) {
              //  holder.card_view.setVisibility(View.GONE);
            }
        }else{
            holder.iv_check.setImageResource(R.mipmap.ic_no_check);
            holder.iv_check2.setImageResource(R.mipmap.ic_no_check);
            if (mostrar != 0 && (mostrar == 2 )) {
                estaFaltando = true;
            }
            if (mostrar != 0 && (mostrar == 3 )) {
                estaFaltando2 = true;
            }
            if (mostrar != 0 && mostrar == 4) {
                estaFaltando2 = true;
            }
        }

        if (mostrar == 1) {
            holder.iv_check.setVisibility(View.INVISIBLE);
        }

    }

    /** Returns the total Adapter**/
    @Override
    public int getItemCount() {
        if (mostrar == 4){
            if (estaFaltando) {
                (((MainActivity) mContext)).mViewPager.setCurrentItem(3);
                if (mContext != null) {
                    //Toast.makeText(mContext, "você deve finalizar todos os alimentos", Toast.LENGTH_SHORT).show();

                }
            }
        }
        if (mostrar == 0){
            if (estaFaltando2) {
                (((MainActivity) mContext)).mViewPager.setCurrentItem(4);
                if (mContext != null) {
                   // Toast.makeText(mContext, "você deve finalizar todos os alimentos", Toast.LENGTH_SHORT).show();

                }
            }
        }
        return data.size();
    }

    public List<Alimentacao> getData() {
        return data;
    }




}
