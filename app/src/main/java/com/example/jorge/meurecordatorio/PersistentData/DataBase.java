package com.example.jorge.meurecordatorio.PersistentData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jorge.meurecordatorio.Model.Adicao;
import com.example.jorge.meurecordatorio.Model.AdicaoAlimento;
import com.example.jorge.meurecordatorio.Model.Alimentacao;
import com.example.jorge.meurecordatorio.Model.Alimento;
import com.example.jorge.meurecordatorio.Model.Entrevistado;
import com.example.jorge.meurecordatorio.Model.Local;
import com.example.jorge.meurecordatorio.Model.OcasiaoConsumo;
import com.example.jorge.meurecordatorio.Model.Preparacao;
import com.example.jorge.meurecordatorio.Model.PreparacaoAlimento;
import com.example.jorge.meurecordatorio.Model.Unidade;
import com.example.jorge.meurecordatorio.Model.UnidadeAlimento;
import com.example.jorge.meurecordatorio.Model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorge on 28/04/2018.
 */

public class DataBase extends SQLiteOpenHelper {

    private SQLiteDatabase mDb;
    private Context context;



    public DataBase(Context context){
        super(context,DbCreate.DB_NAME,null,DbCreate.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

    };

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Drop in tablet
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_ALIMENTO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_PREPARACAO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_UNIDADE );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_ADICAO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_OCASIAO_CONSUMO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_LOCAL );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_ALIMENTACAO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_USUARIO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_ENTREVISTADO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_PREPARACAO_ALIMENTO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_UNIDADE_ALIMENTO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_ADICAO_ALIMENTO );

        db.execSQL(DbCreate.TABLE_ALIMENTO);
        db.execSQL(DbCreate.TABLE_PREPARACAO);
        db.execSQL(DbCreate.TABLE_UNIDADE);
        db.execSQL(DbCreate.TABLE_ADICAO);
        db.execSQL(DbCreate.TABLE_OCASIAO_CONSUMO);
        db.execSQL(DbCreate.TABLE_LOCAL);
        db.execSQL(DbCreate.TABLE_ALIMENTACAO);
        db.execSQL(DbCreate.TABLE_USUARIO);
        db.execSQL(DbCreate.TABLE_ENTREVISTADO);
        db.execSQL(DbCreate.TABLE_PREPARACAO_ALIMENTO);
        db.execSQL(DbCreate.TABLE_UNIDADE_ALIMENTO);
        db.execSQL(DbCreate.TABLE_ADICAO_ALIMENTO);


    }

    @Override
    public void onUpgrade(SQLiteDatabase ndb, int oldVersion, int newVersion) {

    }

    public void insertTABLE_ALIMENTO(List<Alimento> alimentoList){


        for (int i = 0; i < alimentoList.size() ; i++) {
            // Insert PullRequest
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_ALIMENTO_ID, alimentoList.get(i).getAlimento_id());
            obj.put(Field.FIELD_ALIMENTO, alimentoList.get(i).getAlimento());
            this.onInsert(context,obj, DbCreate.TABLE_ALIMENTO);

        }
    }

    public void insertTABLE_PREPARACAO(List<Preparacao> preparacaoList){


        for (int i = 0; i < preparacaoList.size() ; i++) {
            // Insert PullRequest
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_PREPARACAO_ID, preparacaoList.get(i).getPreparacao_id());
            obj.put(Field.FIELD_PREPARACAO, preparacaoList.get(i).getPreparacao());
            this.onInsert(context,obj, DbCreate.TABLE_PREPARACAO);

        }
    }

    public void insertTABLE_UNIDADE(List<Unidade> unidadeList){


        for (int i = 0; i < unidadeList.size() ; i++) {
            // Insert PullRequest
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_UNIDADE_ID, unidadeList.get(i).getUnidade_id());
            obj.put(Field.FIELD_UNIDADE, unidadeList.get(i).getUnidade());
            this.onInsert(context,obj, DbCreate.TABLE_UNIDADE);

        }
    }

    public void insertTABLE_ADICAO(List<Adicao> adicaoList){


        for (int i = 0; i < adicaoList.size() ; i++) {
            // Insert PullRequest
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_ADICAO_ID, adicaoList.get(i).getAdicao_id());
            obj.put(Field.FIELD_ADICAO, adicaoList.get(i).getAdicao());
            this.onInsert(context,obj, DbCreate.TABLE_ADICAO);

        }
    }

    public void insertTABLE_OCASIAO_CONSUMO(List<OcasiaoConsumo> ocasiaoConsumoList){


        for (int i = 0; i < ocasiaoConsumoList.size() ; i++) {
            // Insert PullRequest
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_OCASIAO_CONSUMO_ID, ocasiaoConsumoList.get(i).getOcasiao_consumo_id());
            obj.put(Field.FIELD_OCASIAO_CONSUMO, ocasiaoConsumoList.get(i).getOcasiao_consumo());
            this.onInsert(context,obj, DbCreate.TABLE_OCASIAO_CONSUMO);

        }
    }

    public void insertTABLE_LOCAL(List<Local> localList){


        for (int i = 0; i < localList.size() ; i++) {
            // Insert PullRequest
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_LOCAL_ID, localList.get(i).getLocal_id());
            obj.put(Field.FIELD_LOCAL, localList.get(i).getLocal());
            this.onInsert(context,obj, DbCreate.TABLE_LOCAL);

        }
    }

    public void insertTABLE_USUARIO(List<Usuario> usuarioList){


        for (int i = 0; i < usuarioList.size() ; i++) {
            // Insert PullRequest
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_USUARIO, usuarioList.get(i).getUsuario());
            obj.put(Field.FIELD_SENHA, usuarioList.get(i).getSenha());
            this.onInsert(context,obj, DbCreate.TABLE_USUARIO);

        }
    }

    public void insertTABLE_ENTREVISTADO(List<Entrevistado> entrevistadoList){


        for (int i = 0; i < entrevistadoList.size() ; i++) {
            // Insert PullRequest
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_ENTREVISTADO_ID, entrevistadoList.get(i).getEntrevistado_id());
            obj.put(Field.FIELD_ENTREVISTADO, entrevistadoList.get(i).getEntrevistado());
            this.onInsert(context,obj, DbCreate.TABLE_ENTREVISTADO);

        }
    }

    public void insertTABLE_PREPARACAO_ALIMENTO(List<PreparacaoAlimento> preparacaoAlimentoList){


        for (int i = 0; i < preparacaoAlimentoList.size() ; i++) {
            // Insert PullRequest
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_PREPARACAO_PREPARACAO_ID, preparacaoAlimentoList.get(i).getPreparacao_preparacao_id());
            obj.put(Field.FIELD_PREPARACAO_ALIMENTO_ID, preparacaoAlimentoList.get(i).getPreparacao_alimento_id());
            this.onInsert(context,obj, DbCreate.TABLE_PREPARACAO_ALIMENTO);

        }
    }

    public void insertTABLE_UNIDADE_ALIMENTO(List<UnidadeAlimento> unidadeAlimentoList){


        for (int i = 0; i < unidadeAlimentoList.size() ; i++) {
            // Insert PullRequest
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_UNIDADE_UNIDADE_ID, unidadeAlimentoList.get(i).getUnidade_unidade_id());
            obj.put(Field.FIELD_UNIDADE_ALIMENTO_ID, unidadeAlimentoList.get(i).getUnidade_alimento_id());
            this.onInsert(context,obj, DbCreate.TABLE_UNIDADE_ALIMENTO);

        }
    }

    public void insertTABLE_ADICAO_ALIMENTO(List<AdicaoAlimento> unidadeList){


        for (int i = 0; i < unidadeList.size() ; i++) {
            // Insert PullRequest
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_ADICAO_ADICAO_ID, unidadeList.get(i).getAdicao_adicao_id());
            obj.put(Field.FIELD_ADICAO_ALIMENTO_ID, unidadeList.get(i).getAdicao_alimento_id());
            this.onInsert(context,obj, DbCreate.TABLE_ADICAO_ALIMENTO);

        }
    }




    public List<Alimento> getListAlimento() {

        List<Alimento> alimentoList = new ArrayList<Alimento>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ALIMENTO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Alimento alimento = new Alimento();

            try {
                alimento.setAlimento_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTO_ID)));
                alimento.setAlimento(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTO)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            alimentoList.add(alimento);
            cursor.moveToNext();
        }
        cursor.close();
        return alimentoList;

    }

    public List<Preparacao> getListPreparacao() {

        List<Preparacao> preparacaoList = new ArrayList<Preparacao>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_PREPARACAO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Preparacao preparacao = new Preparacao();

            try {
                preparacao.setPreparacao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_PREPARACAO_ID)));
                preparacao.setPreparacao(cursor.getString(cursor.getColumnIndex(Field.FIELD_PREPARACAO)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            preparacaoList.add(preparacao);
            cursor.moveToNext();
        }
        cursor.close();
        return preparacaoList;

    }

    public List<Unidade> getListUnidade() {

        List<Unidade> unidadeList = new ArrayList<Unidade>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_UNIDADE,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Unidade unidade = new Unidade();

            try {
                unidade.setUnidade_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_UNIDADE_ID)));
                unidade.setUnidade(cursor.getString(cursor.getColumnIndex(Field.FIELD_UNIDADE)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            unidadeList.add(unidade);
            cursor.moveToNext();
        }
        cursor.close();
        return unidadeList;

    }

    public List<Adicao> getListAdicao() {

        List<Adicao> adicaoList = new ArrayList<Adicao>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ADICAO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Adicao adicao = new Adicao();

            try {
                adicao.setAdicao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ADICAO_ID)));
                adicao.setAdicao(cursor.getString(cursor.getColumnIndex(Field.FIELD_ADICAO)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            adicaoList.add(adicao);
            cursor.moveToNext();
        }
        cursor.close();
        return adicaoList;

    }

    public List<OcasiaoConsumo> getListOcasiaoConsumo() {

        List<OcasiaoConsumo> ocasiaoConsumoList = new ArrayList<OcasiaoConsumo>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_OCASIAO_CONSUMO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            OcasiaoConsumo ocasiaoConsumo = new OcasiaoConsumo();

            try {
                ocasiaoConsumo.setOcasiao_consumo_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_OCASIAO_CONSUMO_ID)));
                ocasiaoConsumo.setOcasiao_consumo(cursor.getString(cursor.getColumnIndex(Field.FIELD_OCASIAO_CONSUMO)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            ocasiaoConsumoList.add(ocasiaoConsumo);
            cursor.moveToNext();
        }
        cursor.close();
        return ocasiaoConsumoList;

    }

    public List<Local> getListLocal() {

        List<Local> localList = new ArrayList<Local>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_LOCAL,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Local local = new Local();

            try {
                local.setLocal_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_LOCAL_ID)));
                local.setLocal(cursor.getString(cursor.getColumnIndex(Field.FIELD_LOCAL)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            localList.add(local);
            cursor.moveToNext();
        }
        cursor.close();
        return localList;

    }

    public List<Usuario> getListUsuario() {

        List<Usuario> usuarioList = new ArrayList<Usuario>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_USUARIO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Usuario usuario = new Usuario();

            try {
                usuario.setUsuario(cursor.getString(cursor.getColumnIndex(Field.FIELD_USUARIO)));
                usuario.setUsuario(cursor.getString(cursor.getColumnIndex(Field.FIELD_SENHA)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            usuarioList.add(usuario);
            cursor.moveToNext();
        }
        cursor.close();
        return usuarioList;

    }

    public List<Entrevistado> getListEntrevistado() {

        List<Entrevistado> entrevistadoList = new ArrayList<Entrevistado>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ENTREVISTADO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Entrevistado entrevistado = new Entrevistado();

            try {
                entrevistado.setEntrevistado_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ENTREVISTADO_ID)));
                entrevistado.setEntrevistado(cursor.getString(cursor.getColumnIndex(Field.FIELD_ENTREVISTADO)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            entrevistadoList.add(entrevistado);
            cursor.moveToNext();
        }
        cursor.close();
        return entrevistadoList;

    }

    public List<PreparacaoAlimento> getListPreparacaoAlimento() {

        List<PreparacaoAlimento> PreparacaoAlimentoList = new ArrayList<PreparacaoAlimento>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_PREPARACAO_ALIMENTO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            PreparacaoAlimento preparacaoAlimento = new PreparacaoAlimento();

            try {
                preparacaoAlimento.setPreparacao_preparacao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_PREPARACAO_PREPARACAO_ID)));
                preparacaoAlimento.setPreparacao_alimento_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_PREPARACAO_ALIMENTO_ID)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            PreparacaoAlimentoList.add(preparacaoAlimento);
            cursor.moveToNext();
        }
        cursor.close();
        return PreparacaoAlimentoList;

    }

    public List<UnidadeAlimento> getListUnidadeAlimento() {

        List<UnidadeAlimento> unidadeAlimentoList = new ArrayList<UnidadeAlimento>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_PREPARACAO_ALIMENTO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            UnidadeAlimento unidadeAlimento = new UnidadeAlimento();

            try {
                unidadeAlimento.setUnidade_unidade_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_UNIDADE_UNIDADE_ID)));
                unidadeAlimento.setUnidade_alimento_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_UNIDADE_ALIMENTO_ID)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            unidadeAlimentoList.add(unidadeAlimento);
            cursor.moveToNext();
        }
        cursor.close();
        return unidadeAlimentoList;

    }

    public List<AdicaoAlimento> getListAdicaoAlimento() {

        List<AdicaoAlimento> adicaoAlimentoList = new ArrayList<AdicaoAlimento>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_PREPARACAO_ALIMENTO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            AdicaoAlimento adicaoAlimento = new AdicaoAlimento();

            try {
                adicaoAlimento.setAdicao_adicao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ADICAO_ADICAO_ID)));
                adicaoAlimento.setAdicao_alimento_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ADICAO_ALIMENTO_ID)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            adicaoAlimentoList.add(adicaoAlimento);
            cursor.moveToNext();
        }
        cursor.close();
        return adicaoAlimentoList;

    }

    public List<Alimentacao> getListAlimentacao() {

        List<Alimentacao> alimentacaoList = new ArrayList<Alimentacao>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ALIMENTACAO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Alimentacao alimentacao = new Alimentacao();

            try {
                alimentacao.setAlimentacao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ID)));
                alimentacao.setAlimentacao_alimento_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ALIMENTO_ID)));
                alimentacao.setAlimentacao_preparacao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_PREPARACAO_ID)));
                alimentacao.setAlimentacao_local_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_LOCAL_ID)));
                alimentacao.setAlimentacao_unidade_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_UNIDADE_ID)));
                alimentacao.setAlimentacao_ocasiao_consumo_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO_ID)));
                alimentacao.setAlimentacao_adicao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ADICAO_ID)));
                alimentacao.setAlimentacao_quantidade(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_QUANTIDADE)));
                alimentacao.setAlimentacao_hora(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_HORA)));
                alimentacao.setAlimentacao_usuario(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_USUARIO)));
                alimentacao.setAlimentacao_entrevistador_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ENTREVISTADO_ID)));
                alimentacao.setAlimentacao_hora_coleta(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_HORA_COLETA)));
                alimentacao.setAlimentacao_dia_coleta(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_DIA_COLETA)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            alimentacaoList.add(alimentacao);
            cursor.moveToNext();
        }
        cursor.close();
        return alimentacaoList;



    }

    private void onInsert(Context context, ContentValues obj, String nTabela) {
        try{
            DbInstance.getInstance( context ).insert( nTabela, obj );
        }
        catch (Throwable ex){

        }

    }

    public long insert(String table,ContentValues values){

        mDb = this.getWritableDatabase();

        long row = mDb.insert(table, null, values);
        mDb.close();

        return row;
    }










}
