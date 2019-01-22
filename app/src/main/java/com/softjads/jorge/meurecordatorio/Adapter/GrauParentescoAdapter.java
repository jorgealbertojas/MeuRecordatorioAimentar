package com.softjads.jorge.meurecordatorio.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softjads.jorge.meurecordatorio.Model.Adicao;
import com.softjads.jorge.meurecordatorio.Model.GrauParentesco;
import com.softjads.jorge.meurecordatorio.R;
import com.softjads.jorge.meurecordatorio.Utilite.Modulo;

import java.util.List;

/**
 * Created by jorge on 25/05/2018.
 */

public class GrauParentescoAdapter extends RecyclerView.Adapter<GrauParentescoAdapter.ViewHolder>  {

    private final List<GrauParentesco> data;

    private Context mContext;

    /*
 * An on-click handler that we've defined to make it easy for an Activity to interface with
 * our RecyclerView
 */
    private static GrauParentescoAdapter.RepositoriesAdapterOnClickHandler mClickHandler;
    /**
     * The interface that receives onClick messages.
     */
    public interface RepositoriesAdapterOnClickHandler {
        void onClick(Adicao object);
    }

    /** Constructs the class**/
    public GrauParentescoAdapter(GrauParentescoAdapter.RepositoriesAdapterOnClickHandler clickHandler) {
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
            GrauParentesco object = data.get(adapterPosition);

            Modulo.OPCAO = "GRAU_PARENTESCO";
            Modulo.NOME = object.getParentesco();
            Modulo.ID = object.getId();

            ((Activity) mContext).finish();

        }
    }

    /** create lit de Adapter Travel**/
    public GrauParentescoAdapter(List<GrauParentesco> data) {
        this.data = data;
    }

    /** Create information View holder**/
    @Override
    public GrauParentescoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_informacao_generica, parent, false);
        mContext = parent.getContext();


        return new GrauParentescoAdapter.ViewHolder(v);
    }

    /** Create filed bind hold full **/
    @Override
    public void onBindViewHolder(GrauParentescoAdapter.ViewHolder holder, int position) {


        /** Create filed bind hold full **/

        GrauParentesco object = ((GrauParentesco) data.get(position));
        holder.mTextViewId.setText(object.getId());
        holder.mTextViewDescricao.setText(object.getParentesco());



    }

    /** Returns the total Adapter**/
    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<GrauParentesco> getData() {
        return data;
    }




}