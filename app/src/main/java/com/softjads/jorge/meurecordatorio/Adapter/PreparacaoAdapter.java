package com.softjads.jorge.meurecordatorio.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softjads.jorge.meurecordatorio.Model.Preparacao;
import com.softjads.jorge.meurecordatorio.R;
import com.softjads.jorge.meurecordatorio.Utilite.Modulo;

import java.util.List;

/**
 * Created by jorge on 30/04/2018.
 */

public class PreparacaoAdapter extends RecyclerView.Adapter<PreparacaoAdapter.ViewHolder>  {

    private final List<Preparacao> data;

    private Context mContext;

    /*
 * An on-click handler that we've defined to make it easy for an Activity to interface with
 * our RecyclerView
 */
    private static RepositoriesAdapterOnClickHandler mClickHandler;
    /**
     * The interface that receives onClick messages.
     */
    public interface RepositoriesAdapterOnClickHandler {
        void onClick(Preparacao object);
    }

    /** Constructs the class**/
    public PreparacaoAdapter(RepositoriesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        data = null;
    }




    /** class view holder**/
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTextViewId;
        TextView mTextViewDescricao;

        /** get field of the main for show recyclerView**/
        public ViewHolder(View v) {
            super(v);

            mTextViewId = v.findViewById(R.id.id);
            mTextViewDescricao = v.findViewById(R.id.descricao);
            v.setOnClickListener(this);
        }

        /** configuration the Event onclick. Pass o Object Travel **/
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Preparacao object = data.get(adapterPosition);

            Modulo.OPCAO = "PREPARACAO";
            Modulo.NOME = object.getPreparacao();
            Modulo.ID = object.getPreparacao_id();

            ((Activity) mContext).finish();

        }
    }

    /** create lit de Adapter Travel**/
    public PreparacaoAdapter(List<Preparacao> data) {
        this.data = data;
    }

    /** Create information View holder**/
    @Override
    public PreparacaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_informacao_generica, parent, false);
        mContext = parent.getContext();


        return new ViewHolder(v);
    }

    /** Create filed bind hold full **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        /** Create filed bind hold full **/

        Preparacao object = ((Preparacao) data.get(position));
        holder.mTextViewId.setText(object.getPreparacao_id());
        holder.mTextViewDescricao.setText(object.getPreparacao());



    }

    /** Returns the total Adapter**/
    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Preparacao> getData() {
        return data;
    }




}