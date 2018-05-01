package com.example.jorge.meurecordatorio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jorge.meurecordatorio.Adapter.AlimentoAdapter;
import com.example.jorge.meurecordatorio.Interface.InterfaceAlimento;
import com.example.jorge.meurecordatorio.Model.Alimento;
import com.example.jorge.meurecordatorio.Model.ListWrapper;
import com.example.jorge.meurecordatorio.PersistentData.DataBase;
import com.example.jorge.meurecordatorio.PersistentData.DbInstance;
import com.example.jorge.meurecordatorio.Utilite.Common;
import com.example.jorge.meurecordatorio.Utilite.url;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private DataBase mDataBase;

    private InterfaceAlimento mInterfaceAlimento;
    AlimentoAdapter mAlimentoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataBase = new DataBase(this);
        mDb = mDataBase.getReadableDatabase();
        mDataBase.onCreate(mDb);
        DbInstance.getInstance(this);


        getJson();
    }

    private void getJson(){

        /**
         * checks if internet is ok .
         */
        if (Common.isOnline(getSystemService(Context.CONNECTIVITY_SERVICE))) {
            createStackOverflowAPI(url.BASE_URL_ALIMENTO);
            mInterfaceAlimento.getAlimento().enqueue(AlimentoCallback);
        } else {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, R.string.Error_Access, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Open connect with URL for get JSON  .
     */
    private void createStackOverflowAPI(String url) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceAlimento = retrofit.create(InterfaceAlimento.class);
    }


    private Callback<ListWrapper<Alimento>> AlimentoCallback = new Callback<ListWrapper<Alimento>>() {
        @Override
        public void onResponse(Call<ListWrapper<Alimento>> call, Response<ListWrapper<Alimento>> response) {
            try {
                if (response.isSuccessful()) {
                    List<Alimento> data = new ArrayList<>();
                    data.addAll(response.body().items);

                 //   if (mRecyclerView.getAdapter() != null) {
                 //       List<Alimento> dataOld = mAlimentoAdapter.getData();
                 //       data.addAll(dataOld);
                //    }

                    // Persistent Data for SQLLite
                    mDataBase = new DataBase(getApplicationContext());
                    mDb = mDataBase.getReadableDatabase();

                    mDataBase.insertTABLE_ALIMENTO(data);

                    List<Alimento> dataPersistent = new ArrayList<>();
                    dataPersistent = mDataBase.getListAlimento();

                    mAlimentoAdapter = new AlimentoAdapter(dataPersistent);
                 //   mRecyclerView.setAdapter(mAlimentoAdapter);

                } else {
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            } catch (NullPointerException e) {
                System.out.println("onActivityResult consume crashed");
                runOnUiThread(new Runnable() {
                    public void run() {
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, R.string.Error_Access_empty, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }

        @Override
        public void onFailure(Call<ListWrapper<Alimento>> call, Throwable t) {
            t.printStackTrace();
        }
    };
}
