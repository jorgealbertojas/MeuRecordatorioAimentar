package com.example.jorge.meurecordatorio.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.meurecordatorio.DetailActivity;
import com.example.jorge.meurecordatorio.Generica.EntrevistadoActivity;
import com.example.jorge.meurecordatorio.MainActivity;
import com.example.jorge.meurecordatorio.Model.Alimentacao;
import com.example.jorge.meurecordatorio.R;
import com.example.jorge.meurecordatorio.Utilite.Common;
import com.example.jorge.meurecordatorio.Utilite.Modulo;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.jorge.meurecordatorio.MainActivity.PUT_BUNDLE_ALIMENTACAO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ALIMENTACAO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_DIA_ATIPICO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO_NOME;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ETAPA;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_GRAU_PARENTESCO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_GRAU_PARENTESCO_NOME;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_POSITION;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_USUARIO;

/**
 * Created by jorge on 01/05/2018.
 */

public class AlimentacaoAdapter extends RecyclerView.Adapter<AlimentacaoAdapter.ViewHolder>  {

    private final List<Alimentacao> data;

    private int mostrar;

    private Context mContext;

    public static boolean estaFaltando = false;


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



            tv_quantidade = (TextView) v.findViewById(R.id.tv_quantidade);

            tv_hora_coleta  = (TextView) v.findViewById(R.id.tv_hora_coleta);
            tv_dia_coleta  = (TextView) v.findViewById(R.id.tv_dia_coleta);
            tv_usuario  = (TextView) v.findViewById(R.id.tv_usuario);

            iv_check = (ImageView) v.findViewById(R.id.iv_check);

            tv_grau_parentesco = (TextView) v.findViewById(R.id.tv_grau_parentesco);


            v.setOnClickListener(this);
        }

        /** configuration the Event onclick. Pass o Object Travel **/
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Alimentacao alimentacao = data.get(adapterPosition);

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

        }



    }

    /** create lit de Adapter Travel**/
    public AlimentacaoAdapter(List<Alimentacao> data, int mostrar) {
        estaFaltando = false;
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
            } else if (alimentacao.getAlimentacao_unidade_id().toString().equals("") || alimentacao.getAlimentacao_unidade_id().toString().equals("0")) {
                check = false;
            } else if (alimentacao.getAlimentacao_local_id().toString().equals("") || alimentacao.getAlimentacao_local_id().toString().equals("0")) {
                check = false;
            } else if (alimentacao.getAlimentacao_ocasiao_consumo_id().toString().equals("") || alimentacao.getAlimentacao_ocasiao_consumo_id().toString().equals("0")) {
                check = false;
            } else if (alimentacao.getAlimentacao_hora().toString().equals("") || alimentacao.getAlimentacao_hora().toString().equals("0") || alimentacao.getAlimentacao_hora().toString().equals("0:00")) {
                check = false;
            } else if (alimentacao.getAlimentacao_quantidade().toString().equals("") || alimentacao.getAlimentacao_quantidade().toString().equals("0")) {
                check = false;
            }
        }else{
            if (alimentacao.getAlimentacao_ocasiao_consumo_id().toString().equals("") || alimentacao.getAlimentacao_ocasiao_consumo_id().toString().equals("0")){
                check = false;
            }else if (alimentacao.getAlimentacao_hora().toString().equals("") || alimentacao.getAlimentacao_hora().toString().equals("0") || alimentacao.getAlimentacao_hora().toString().equals("0:00")){
                check = false;
            }
        }





        if (check) {
            holder.iv_check.setImageResource(R.mipmap.ic_check);
            if (mostrar == 3) {
                holder.card_view.setVisibility(View.GONE);
            }
        }else{
            holder.iv_check.setImageResource(R.mipmap.ic_no_check);
            estaFaltando = true;
            if (mostrar == 4) {
                holder.card_view.setVisibility(View.GONE);
            }
        }

    }

    /** Returns the total Adapter**/
    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Alimentacao> getData() {
        return data;
    }




}
