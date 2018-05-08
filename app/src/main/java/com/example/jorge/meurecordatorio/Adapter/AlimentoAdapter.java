package com.example.jorge.meurecordatorio.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jorge.meurecordatorio.MainActivity;
import com.example.jorge.meurecordatorio.Model.Adicao;
import com.example.jorge.meurecordatorio.Model.Alimento;
import com.example.jorge.meurecordatorio.R;
import com.example.jorge.meurecordatorio.Utilite.Modulo;

import java.util.List;

/**
 * Created by jorge on 30/04/2018.
 */

public class AlimentoAdapter extends RecyclerView.Adapter<AlimentoAdapter.ViewHolder>  {

    private final List<Alimento> data;

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
        void onClick(Alimento object);
    }

    /** Constructs the class**/
    public AlimentoAdapter(RepositoriesAdapterOnClickHandler clickHandler) {
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
            Alimento object = data.get(adapterPosition);

            Modulo.OPCAO = "ALIMENTO";
            Modulo.NOME = object.getAlimento();
            Modulo.ID = object.getAlimento_id();
            Modulo.NOVO_ALIMENTO = object.getNovo();
            ((Activity) mContext).finish();


        }
    }

    /** create lit de Adapter Travel**/
    public AlimentoAdapter(List<Alimento> data) {
        this.data = data;
    }

    /** Create information View holder**/
    @Override
    public AlimentoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_informacao_generica, parent, false);
        mContext = parent.getContext();


        return new ViewHolder(v);
    }

    /** Create filed bind hold full **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        /** Create filed bind hold full **/

        Alimento object = ((Alimento) data.get(position));
        holder.mTextViewId.setText(object.getAlimento_id());
        holder.mTextViewDescricao.setText(object.getAlimento());



    }

    /** Returns the total Adapter**/
    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Alimento> getData() {
        return data;
    }




}