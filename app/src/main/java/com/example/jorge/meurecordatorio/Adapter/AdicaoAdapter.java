package com.example.jorge.meurecordatorio.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jorge.meurecordatorio.Model.Adicao;
import com.example.jorge.meurecordatorio.R;

import java.util.List;

/**
 * Created by jorge on 30/04/2018.
 */

public class AdicaoAdapter extends RecyclerView.Adapter<AdicaoAdapter.ViewHolder>  {

    private final List<Adicao> data;

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
        void onClick(Adicao object);
    }

    /** Constructs the class**/
    public AdicaoAdapter(RepositoriesAdapterOnClickHandler clickHandler) {
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
            Adicao object = data.get(adapterPosition);
            mClickHandler.onClick(object);

        }
    }

    /** create lit de Adapter Travel**/
    public AdicaoAdapter(List<Adicao> data) {
        this.data = data;
    }

    /** Create information View holder**/
    @Override
    public AdicaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_informacao_generica, parent, false);
        mContext = parent.getContext();


        return new ViewHolder(v);
    }

    /** Create filed bind hold full **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        /** Create filed bind hold full **/

        Adicao object = ((Adicao) data.get(position));
        holder.mTextViewId.setText(object.getAdicao_id());
        holder.mTextViewDescricao.setText(object.getAdicao());



    }

    /** Returns the total Adapter**/
    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Adicao> getData() {
        return data;
    }




}