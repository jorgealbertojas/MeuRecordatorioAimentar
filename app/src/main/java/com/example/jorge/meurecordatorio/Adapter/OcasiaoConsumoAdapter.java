package com.example.jorge.meurecordatorio.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jorge.meurecordatorio.Model.Adicao;
import com.example.jorge.meurecordatorio.Model.OcasiaoConsumo;
import com.example.jorge.meurecordatorio.R;

import java.util.List;

/**
 * Created by jorge on 30/04/2018.
 */

public class OcasiaoConsumoAdapter extends RecyclerView.Adapter<OcasiaoConsumoAdapter.ViewHolder>  {

    private final List<OcasiaoConsumo> data;

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
        void onClick(OcasiaoConsumo object);
    }

    /** Constructs the class**/
    public OcasiaoConsumoAdapter(RepositoriesAdapterOnClickHandler clickHandler) {
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
            OcasiaoConsumo object = data.get(adapterPosition);
            mClickHandler.onClick(object);

        }
    }

    /** create lit de Adapter Travel**/
    public OcasiaoConsumoAdapter(List<OcasiaoConsumo> data) {
        this.data = data;
    }

    /** Create information View holder**/
    @Override
    public OcasiaoConsumoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_informacao_generica, parent, false);
        mContext = parent.getContext();


        return new ViewHolder(v);
    }

    /** Create filed bind hold full **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        /** Create filed bind hold full **/

        OcasiaoConsumo object = ((OcasiaoConsumo) data.get(position));
        holder.mTextViewId.setText(object.getOcasiao_consumo_id());
        holder.mTextViewDescricao.setText(object.getOcasiao_consumo());



    }

    /** Returns the total Adapter**/
    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<OcasiaoConsumo> getData() {
        return data;
    }




}