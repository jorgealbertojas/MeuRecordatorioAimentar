package com.example.jorge.meurecordatorio.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jorge.meurecordatorio.Model.Entrevistado;
import com.example.jorge.meurecordatorio.Model.Local;
import com.example.jorge.meurecordatorio.Model.Unidade;
import com.example.jorge.meurecordatorio.R;
import com.example.jorge.meurecordatorio.Utilite.Modulo;

import java.util.List;

/**
 * Created by jorge on 30/04/2018.
 */

public class EntrevistadoAdapter extends RecyclerView.Adapter<EntrevistadoAdapter.ViewHolder>  {

    private final List<Entrevistado> data;

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
        void onClick(Entrevistado object);
    }

    /** Constructs the class**/
    public EntrevistadoAdapter(RepositoriesAdapterOnClickHandler clickHandler) {
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
            Entrevistado object = data.get(adapterPosition);

            Modulo.OPCAO = "ENTREVISTADO";
            Modulo.NOME = object.getEntrevistado();
            Modulo.ID = object.getEntrevistado_id();

            ((Activity) mContext).finish();

        }
    }

    /** create lit de Adapter Travel**/
    public EntrevistadoAdapter(List<Entrevistado> data) {
        this.data = data;
    }

    /** Create information View holder**/
    @Override
    public EntrevistadoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_informacao_generica, parent, false);
        mContext = parent.getContext();


        return new ViewHolder(v);
    }

    /** Create filed bind hold full **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        /** Create filed bind hold full **/

        Entrevistado object = ((Entrevistado) data.get(position));
        holder.mTextViewId.setText(object.getEntrevistado_id());
        holder.mTextViewDescricao.setText(object.getEntrevistado());



    }

    /** Returns the total Adapter**/
    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Entrevistado> getData() {
        return data;
    }




}