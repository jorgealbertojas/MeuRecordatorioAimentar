

package com.example.jorge.meurecordatorio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jorge.meurecordatorio.Adapter.AlimentacaoAdapter;
import com.example.jorge.meurecordatorio.Model.Alimentacao;
import com.example.jorge.meurecordatorio.PersistentData.DataBase;

import java.util.ArrayList;
import java.util.List;

import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_ENTREVISTADO;
import static com.example.jorge.meurecordatorio.MainActivity.PUT_EXTRA_USUARIO;

public class DetailActivity extends AppCompatActivity {


    String mEntrevistado;
    String mUsuario;

    TextView aliemnto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /**
         * Get putExtra for Activity Main .
         */
        Bundle extras = getIntent().getExtras();

        mEntrevistado = extras.getString(PUT_EXTRA_ENTREVISTADO);
        mUsuario = extras.getString(PUT_EXTRA_USUARIO);

        aliemnto = (TextView)  findViewById(R.id.aliemnto);
        aliemnto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    Class destinationClass = AlimentoActivity.class;
                    Intent intentToStartDetailActivity = new Intent(getBaseContext(), destinationClass);
                    intentToStartDetailActivity.putExtra(PUT_EXTRA_ENTREVISTADO, mEntrevistado);
                    startActivity(intentToStartDetailActivity);

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });


        /**
         * Put Name Repositorie in  title.
         */
        this.setTitle(mEntrevistado);



    }
}
