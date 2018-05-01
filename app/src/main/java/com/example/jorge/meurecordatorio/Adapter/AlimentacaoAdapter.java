package com.example.jorge.meurecordatorio.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.meurecordatorio.Model.Alimentacao;
import com.example.jorge.meurecordatorio.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jorge on 01/05/2018.
 */

public class AlimentacaoAdapter extends RecyclerView.Adapter<AlimentacaoAdapter.ViewHolder>  {

    private final List<Alimentacao> data;

    private Context mContext;


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
        TextView tv_local;
        TextView tv_unidade;
        ImageView iv_incompleto;
        ImageView iv_completo;
        TextView tv_hora;




        /** get field of the main for show recyclerView**/
        public ViewHolder(View v) {
            super(v);

            tv_alimento = (TextView) v.findViewById(R.id.tv_alimento);
            tv_preparacao  = (TextView) v.findViewById(R.id.tv_preparacao);
            tv_local  = (TextView) v.findViewById(R.id.tv_local);
            tv_unidade  = (TextView) v.findViewById(R.id.tv_unidade);
            iv_incompleto = (ImageView) v.findViewById(R.id.iv_incompleto);
            iv_completo = (ImageView) v.findViewById(R.id.iv_completo);
            tv_hora  = (TextView) v.findViewById(R.id.tv_hora);

            v.setOnClickListener(this);
        }

        /** configuration the Event onclick. Pass o Object Travel **/
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Alimentacao alimentacao = data.get(adapterPosition);
            mClickHandler.onClick(alimentacao);

        }
    }

    /** create lit de Adapter Travel**/
    public AlimentacaoAdapter(List<Alimentacao> data) {
        this.data = data;
    }

    /** Create information View holder**/
    @Override
    public AlimentacaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_alimentacao, parent, false);
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
     //   holder.mStar.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);



        /** Create filed bind hold full **/

        Alimentacao alimentacao = ((Alimentacao) data.get(position));
        holder.tv_alimento.setText(alimentacao.getAlimentacao_alimento_id());
        holder.tv_preparacao.setText(alimentacao.getAlimentacao_preparacao_id());
        holder.tv_local.setText(alimentacao.getAlimentacao_local_id());
        holder.tv_unidade.setText(alimentacao.getAlimentacao_unidade_id());
        holder.tv_hora.setText(alimentacao.getAlimentacao_hora());



        Picasso.with(mContext).load(mContext.getResources().getResourceName(R.mipmap.ic_launcher));

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
