package com.softjads.jorge.meurecordatorio.PersistentData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.softjads.jorge.meurecordatorio.Help.Help;
import com.softjads.jorge.meurecordatorio.Model.Adicao;
import com.softjads.jorge.meurecordatorio.Model.AdicaoAlimento;
import com.softjads.jorge.meurecordatorio.Model.Alimentacao;
import com.softjads.jorge.meurecordatorio.Model.Alimento;
import com.softjads.jorge.meurecordatorio.Model.Entrevistado;
import com.softjads.jorge.meurecordatorio.Model.GrauParentesco;
import com.softjads.jorge.meurecordatorio.Model.Local;
import com.softjads.jorge.meurecordatorio.Model.OcasiaoConsumo;
import com.softjads.jorge.meurecordatorio.Model.Preparacao;
import com.softjads.jorge.meurecordatorio.Model.PreparacaoAlimento;
import com.softjads.jorge.meurecordatorio.Model.Unidade;
import com.softjads.jorge.meurecordatorio.Model.UnidadeAlimento;
import com.softjads.jorge.meurecordatorio.Model.Usuario;

import java.util.ArrayList;
import java.util.List;

import static com.softjads.jorge.meurecordatorio.PersistentData.Field.FIELD_ALIMENTACAO_DIA_ATIPICO;
import static com.softjads.jorge.meurecordatorio.PersistentData.Field.FIELD_ALIMENTACAO_ENTREVISTADO_ID;
import static com.softjads.jorge.meurecordatorio.PersistentData.Field.FIELD_ALIMENTACAO_GRAU_PARENTESCO;
import static com.softjads.jorge.meurecordatorio.PersistentData.Field.FIELD_ALIMENTACAO_GRAU_PARENTESCO_ID;
import static com.softjads.jorge.meurecordatorio.PersistentData.Field.FIELD_ALIMENTACAO_ID;
import static com.softjads.jorge.meurecordatorio.PersistentData.Field.FIELD_ALIMENTO_ID;
import static com.softjads.jorge.meurecordatorio.PersistentData.Field.FIELD_ENTREVISTADO_ID;
import static com.softjads.jorge.meurecordatorio.PersistentData.Field.FIELD_HELP_KEY;

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
        //db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_USUARIO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_ENTREVISTADO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_PREPARACAO_ALIMENTO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_UNIDADE_ALIMENTO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_ADICAO_ALIMENTO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_GRAU_PARENTESCO );
        db.execSQL(" DROP TABLE IF EXISTS " + DbCreate.TABLE_HELP );

        db.execSQL(DbCreate.CREATE_TABLE_HELP);
        db.execSQL(DbCreate.CREATE_TABLE_ALIMENTO);
        db.execSQL(DbCreate.CREATE_TABLE_GRAU_PARENTESCO);
        db.execSQL(DbCreate.CREATE_TABLE_PREPARACAO);
        db.execSQL(DbCreate.CREATE_TABLE_UNIDADE);
        db.execSQL(DbCreate.CREATE_INDEX_TABLE_UNIDADE);
        db.execSQL(DbCreate.CREATE_TABLE_ADICAO);
        db.execSQL(DbCreate.CREATE_TABLE_OCASIAO_CONSUMO);
        db.execSQL(DbCreate.CREATE_TABLE_LOCAL);
        db.execSQL(DbCreate.CREATE_TABLE_ALIMENTACAO);
       // db.execSQL(DbCreate.CREATE_TABLE_USUARIO);
        db.execSQL(DbCreate.CREATE_TABLE_ENTREVISTADO);
        db.execSQL(DbCreate.CREATE_TABLE_PREPARACAO_ALIMENTO);
        db.execSQL(DbCreate.CREATE_TABLE_UNIDADE_ALIMENTO);
        db.execSQL(DbCreate.CREATE_TABLE_ADICAO_ALIMENTO);









    }

    @Override
    public void onUpgrade(SQLiteDatabase ndb, int oldVersion, int newVersion) {

    }

    public void insertTABLE_ALIMENTO(List<Alimento> alimentoList){


        for (int i = 0; i < alimentoList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(FIELD_ALIMENTO_ID, alimentoList.get(i).getAlimento_id());
            obj.put(Field.FIELD_ALIMENTO, alimentoList.get(i).getAlimento());
            obj.put(Field.FIELD_NOVO, alimentoList.get(i).getNovo());
            this.onInsert(context,obj, DbCreate.TABLE_ALIMENTO);

        }
    }

    public void insertTABLE_PREPARACAO(List<Preparacao> preparacaoList){


        for (int i = 0; i < preparacaoList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_PREPARACAO_ID, preparacaoList.get(i).getPreparacao_id());
            obj.put(Field.FIELD_PREPARACAO, preparacaoList.get(i).getPreparacao());
            this.onInsert(context,obj, DbCreate.TABLE_PREPARACAO);

        }
    }

    public void insertTABLE_UNIDADE(List<Unidade> unidadeList){


        for (int i = 0; i < unidadeList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_UNIDADE_ID, unidadeList.get(i).getUnidade_id());
            obj.put(Field.FIELD_UNIDADE, unidadeList.get(i).getUnidade());
            this.onInsert(context,obj, DbCreate.TABLE_UNIDADE);

        }
    }

    public void insertTABLE_ADICAO(List<Adicao> adicaoList){


        for (int i = 0; i < adicaoList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_ADICAO_ID, adicaoList.get(i).getAdicao_id());
            obj.put(Field.FIELD_ADICAO, adicaoList.get(i).getAdicao());
            this.onInsert(context,obj, DbCreate.TABLE_ADICAO);

        }
    }

    public void insertTABLE_OCASIAO_CONSUMO(List<OcasiaoConsumo> ocasiaoConsumoList){


        for (int i = 0; i < ocasiaoConsumoList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_OCASIAO_CONSUMO_ID, ocasiaoConsumoList.get(i).getOcasiao_consumo_id());
            obj.put(Field.FIELD_OCASIAO_CONSUMO, ocasiaoConsumoList.get(i).getOcasiao_consumo());
            this.onInsert(context,obj, DbCreate.TABLE_OCASIAO_CONSUMO);

        }
    }

    public void insertTABLE_LOCAL(List<Local> localList){


        for (int i = 0; i < localList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_LOCAL_ID, localList.get(i).getLocal_id());
            obj.put(Field.FIELD_LOCAL, localList.get(i).getLocal());
            this.onInsert(context,obj, DbCreate.TABLE_LOCAL);

        }
    }

    public void insertTABLE_USUARIO(List<Usuario> usuarioList){


        for (int i = 0; i < usuarioList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_USUARIO, usuarioList.get(i).getUsuario());
            obj.put(Field.FIELD_SENHA, usuarioList.get(i).getSenha());
            this.onInsert(context,obj, DbCreate.TABLE_USUARIO);

        }
    }

    public void insertTABLE_ENTREVISTADO(List<Entrevistado> entrevistadoList){


        for (int i = 0; i < entrevistadoList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_ENTREVISTADO_ID, entrevistadoList.get(i).getEntrevistado_id());
            obj.put(Field.FIELD_ENTREVISTADO, entrevistadoList.get(i).getEntrevistado());
            this.onInsert(context,obj, DbCreate.TABLE_ENTREVISTADO);

        }
    }

    public void insertTABLE_PREPARACAO_ALIMENTO(List<PreparacaoAlimento> preparacaoAlimentoList){


        for (int i = 0; i < preparacaoAlimentoList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_PREPARACAO_PREPARACAO_ID, preparacaoAlimentoList.get(i).getPreparacao_preparacao_id());
            obj.put(Field.FIELD_PREPARACAO_ALIMENTO_ID, preparacaoAlimentoList.get(i).getPreparacao_alimento_id());
            this.onInsert(context,obj, DbCreate.TABLE_PREPARACAO_ALIMENTO);

        }
    }

    public void insertTABLE_UNIDADE_ALIMENTO(List<UnidadeAlimento> unidadeAlimentoList){


        for (int i = 0; i < unidadeAlimentoList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_UNIDADE_UNIDADE_ID, unidadeAlimentoList.get(i).getUnidade_unidade_id());
            obj.put(Field.FIELD_UNIDADE_ALIMENTO_ID, unidadeAlimentoList.get(i).getUnidade_alimento_id());
            this.onInsert(context,obj, DbCreate.TABLE_UNIDADE_ALIMENTO);


        }
    }

    public void insertTABLE_ADICAO_ALIMENTO(List<AdicaoAlimento> unidadeList){


        for (int i = 0; i < unidadeList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_ADICAO_ADICAO_ID, unidadeList.get(i).getAdicao_adicao_id());
            obj.put(Field.FIELD_ADICAO_ALIMENTO_ID, unidadeList.get(i).getAdicao_alimento_id());
            this.onInsert(context,obj, DbCreate.TABLE_ADICAO_ALIMENTO);

        }
    }

    public void insertTABLE_HELP(List<Help> helpList){


        for (int i = 0; i < helpList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_HELP_KEY, helpList.get(i).getMkey());
            obj.put(Field.FIELD_HELP_VALUE, helpList.get(i).getMvalue());
            obj.put(Field.FIELD_HELP_LAST, helpList.get(i).getMlast());
            this.onInsert(context,obj, DbCreate.TABLE_HELP);

        }
    }

    public void insertTABLE_GRAU_PARENTESCO(List<GrauParentesco> grauParentescoList){


        for (int i = 0; i < grauParentescoList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(Field.FIELD_GRAU_PARENTESCO_ID, grauParentescoList.get(i).getId());
            obj.put(Field.FIELD_GRAU_PARENTESCO, grauParentescoList.get(i).getParentesco());
            this.onInsert(context,obj, DbCreate.TABLE_GRAU_PARENTESCO);

        }
    }

    public int NovoID_ALIMENTACAO() {
        mDb = this.getWritableDatabase();
        Cursor cursorTMP = mDb.rawQuery(" SELECT MAX(" + FIELD_ALIMENTACAO_ID + ") FROM " + DbCreate.TABLE_ALIMENTACAO, null);
        cursorTMP.moveToFirst();
        int i = 0;
        if (cursorTMP.getCount() > 0) {
            i = cursorTMP.getInt(0);
        }
        return i + 1;
    }

    public int NovoID_ALIMENTO() {
        mDb = this.getWritableDatabase();
        Cursor cursorTMP = mDb.rawQuery(" SELECT " + FIELD_ALIMENTO_ID + " FROM " + DbCreate.TABLE_ALIMENTO, null);
        cursorTMP.moveToFirst();
        int i = 0;
        if (cursorTMP.getCount() > 0) {
            i = cursorTMP.getCount();
        }
        return i + 1;
    }


    public void insertTABLE_ALIMENTACAO(List<Alimentacao> alimentacaoList){


        for (int i = 0; i < alimentacaoList.size() ; i++) {
            ContentValues obj = new ContentValues();
            obj.put(FIELD_ALIMENTACAO_ID, alimentacaoList.get(i).getAlimentacao_id());
            obj.put(Field.FIELD_ALIMENTACAO_ALIMENTO_ID, alimentacaoList.get(i).getAlimentacao_alimento_id());
            obj.put(Field.FIELD_ALIMENTACAO_ALIMENTO, alimentacaoList.get(i).getAlimentacao_alimento());
            obj.put(Field.FIELD_ALIMENTACAO_PREPARACAO_ID, alimentacaoList.get(i).getAlimentacao_preparacao_id());
            obj.put(Field.FIELD_ALIMENTACAO_PREPARACAO, alimentacaoList.get(i).getAlimentacao_preparacao());
            obj.put(Field.FIELD_ALIMENTACAO_LOCAL_ID, alimentacaoList.get(i).getAlimentacao_local_id());
            obj.put(Field.FIELD_ALIMENTACAO_LOCAL, alimentacaoList.get(i).getAlimentacao_local());
            obj.put(Field.FIELD_ALIMENTACAO_UNIDADE_ID, alimentacaoList.get(i).getAlimentacao_unidade_id());
            obj.put(Field.FIELD_ALIMENTACAO_UNIDADE, alimentacaoList.get(i).getAlimentacao_unidade());
            obj.put(Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO_ID, alimentacaoList.get(i).getAlimentacao_ocasiao_consumo_id());
            obj.put(Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO, alimentacaoList.get(i).getAlimentacao_ocasiao_consumo());
            obj.put(Field.FIELD_ALIMENTACAO_ADICAO_ID, alimentacaoList.get(i).getAlimentacao_adicao_id());
            obj.put(Field.FIELD_ALIMENTACAO_ADICAO, alimentacaoList.get(i).getAlimentacao_adicao());
            obj.put(Field.FIELD_ALIMENTACAO_QUANTIDADE, alimentacaoList.get(i).getAlimentacao_quantidade());
            obj.put(Field.FIELD_ALIMENTACAO_HORA, alimentacaoList.get(i).getAlimentacao_hora());
            obj.put(Field.FIELD_ALIMENTACAO_USUARIO, alimentacaoList.get(i).getAlimentacao_usuario());
            obj.put(Field.FIELD_ALIMENTACAO_ENTREVISTADO_ID, alimentacaoList.get(i).getAlimentacao_entrevistado_id());
            obj.put(Field.FIELD_ALIMENTACAO_ENTREVISTADO, alimentacaoList.get(i).getAlimentacao_entrevistado());
            obj.put(Field.FIELD_ALIMENTACAO_HORA_COLETA, alimentacaoList.get(i).getAlimentacao_hora_coleta());
            obj.put(Field.FIELD_ALIMENTACAO_HORA_COLETA_FIM, alimentacaoList.get(i).getAlimentacao_hora_coleta_fim());
            obj.put(Field.FIELD_ALIMENTACAO_OBS, alimentacaoList.get(i).getAlimentacao_obs());
            obj.put(Field.FIELD_ALIMENTACAO_DIA_COLETA, alimentacaoList.get(i).getAlimentacao_dia_coleta());
            obj.put(FIELD_ALIMENTACAO_GRAU_PARENTESCO_ID, alimentacaoList.get(i).getAlimentacao_grau_parentesco_id());
            obj.put(FIELD_ALIMENTACAO_GRAU_PARENTESCO, alimentacaoList.get(i).getAlimentacao_grau_parentesco());
            obj.put(Field.FIELD_ALIMENTACAO_DIA_ATIPICO, alimentacaoList.get(i).getAlimentacao_dia_atico());
            obj.put(Field.FIELD_ALIMENTACAO_ESPESSURA, alimentacaoList.get(i).getAlimentacao_espessura());
            this.onInsert(context,obj, DbCreate.TABLE_ALIMENTACAO);

        }
    }

    public List<Alimento> getListAlimento() {

        List<Alimento> alimentoList = new ArrayList<Alimento>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ALIMENTO + " ORDER BY  " + Field.FIELD_ALIMENTO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Alimento alimento = new Alimento();

            try {
                alimento.setAlimento_id(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTO_ID)));
                alimento.setAlimento(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTO)));
                alimento.setNovo(cursor.getString(cursor.getColumnIndex(Field.FIELD_NOVO)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            alimentoList.add(alimento);
            cursor.moveToNext();
        }
        cursor.close();
        return alimentoList;

    }

    public List<Help> getListHelp() {

        List<Help> helpList = new ArrayList<Help>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_HELP,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Help help = new Help();

            try {
                help.setMkey(cursor.getString(cursor.getColumnIndex(FIELD_HELP_KEY)));
                help.setMvalue(cursor.getString(cursor.getColumnIndex(Field.FIELD_HELP_VALUE)));
                help.setMlast(cursor.getString(cursor.getColumnIndex(Field.FIELD_HELP_LAST)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            helpList.add(help);
            cursor.moveToNext();
        }
        cursor.close();
        return helpList;

    }

    public int getListAlimentoID(String id) {

        List<Alimento> alimentoList = new ArrayList<Alimento>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ALIMENTO + " WHERE  " + FIELD_ALIMENTO_ID + " = '" + id + "'" + " ORDER BY  " + Field.FIELD_ALIMENTO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Alimento alimento = new Alimento();

            try {
                alimento.setAlimento_id(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTO_ID)));
                alimento.setAlimento(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTO)));
                alimento.setNovo(cursor.getString(cursor.getColumnIndex(Field.FIELD_NOVO)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            alimentoList.add(alimento);
            cursor.moveToNext();
        }
        cursor.close();
        return alimentoList.size();

    }

    public void deleteAlimentacao(String id){
        mDb = this.getWritableDatabase();
        mDb.execSQL(" DELETE FROM " + DbCreate.TABLE_ALIMENTACAO + " WHERE " + FIELD_ALIMENTACAO_ID + " = '" + id +"'");
    }

    public void deleteAlimentacaoEntrevistado(String idEntrevistado){
        mDb = this.getWritableDatabase();
        mDb.execSQL(" DELETE FROM " + DbCreate.TABLE_ALIMENTACAO + " WHERE " + FIELD_ALIMENTACAO_ENTREVISTADO_ID + " = '" + idEntrevistado +"'");
    }

    public void updatealimentacao(String entrevistado, String parentesco, String parentesco_id, String dia_tipico){
        mDb = this.getWritableDatabase();
        mDb.execSQL(" UPDATE " + DbCreate.TABLE_ALIMENTACAO + "  SET " + FIELD_ALIMENTACAO_GRAU_PARENTESCO_ID + " = '" + parentesco_id + "'," + FIELD_ALIMENTACAO_GRAU_PARENTESCO + " = '" + parentesco + "'," + FIELD_ALIMENTACAO_DIA_ATIPICO + " = '" + dia_tipico + "' WHERE " + FIELD_ALIMENTACAO_ENTREVISTADO_ID + " = '" + entrevistado +"'");
    }

    public void deleteentrevistado(String id){
        mDb = this.getWritableDatabase();
        mDb.execSQL(" DELETE FROM " + DbCreate.TABLE_ENTREVISTADO + " WHERE " + FIELD_ENTREVISTADO_ID + " = '" + id +"'");
    }

    public void deleteentrevistadoTESTE(){
        mDb = this.getWritableDatabase();
        mDb.execSQL(" DELETE FROM " + DbCreate.TABLE_ENTREVISTADO );
    }

    public List<Alimento> getListAlimentoInicio(String partNome) {

        List<Alimento> alimentoList = new ArrayList<Alimento>();

        mDb = this.getWritableDatabase();

        Cursor cursor;
        if (partNome.indexOf(" ",partNome.length()-1) == partNome.length()-1) {
            cursor = mDb.rawQuery(DbSelect.GET_ALIMENTO + " WHERE " + Field.FIELD_ALIMENTO + " LIKE '" + partNome.substring(0,partNome.length()-1) + "'" + " ORDER BY  " + Field.FIELD_ALIMENTO, null);
        }else{
            cursor = mDb.rawQuery(DbSelect.GET_ALIMENTO + " WHERE " + Field.FIELD_ALIMENTO + " LIKE '" + partNome + "%'" + " ORDER BY  " + Field.FIELD_ALIMENTO, null);
        }
        cursor.moveToFirst();
        if (cursor.getCount() == 0){
            cursor = mDb.rawQuery(DbSelect.GET_ALIMENTO + " WHERE " + Field.FIELD_ALIMENTO + " LIKE '" + partNome + "%'" + " ORDER BY  " + Field.FIELD_ALIMENTO, null);
        }

        while(!cursor.isAfterLast() ){
            Alimento alimento = new Alimento();

            try {
                alimento.setAlimento_id(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTO_ID)));
                alimento.setAlimento(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTO)));
                alimento.setNovo(cursor.getString(cursor.getColumnIndex(Field.FIELD_NOVO)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            alimentoList.add(alimento);
            cursor.moveToNext();
        }
        cursor.close();
        return alimentoList;

    }

    public List<Alimento> getListAlimento(String partNome) {

        List<Alimento> alimentoList = new ArrayList<Alimento>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ALIMENTO + " WHERE "+ Field.FIELD_ALIMENTO + " LIKE '%" + partNome + "%'" + " ORDER BY  " + Field.FIELD_ALIMENTO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Alimento alimento = new Alimento();

            try {
                alimento.setAlimento_id(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTO_ID)));
                alimento.setAlimento(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTO)));
                alimento.setNovo(cursor.getString(cursor.getColumnIndex(Field.FIELD_NOVO)));

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

        Cursor cursor = mDb.rawQuery(DbSelect.GET_PREPARACAO_SEM_RELACIONAMENTO,null);
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

    public List<Preparacao> getListPreparacao(String alimento, String partNome) {

        List<Preparacao> preparacaoList = new ArrayList<Preparacao>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_PREPARACAO + " AND PA." + Field.FIELD_PREPARACAO_ALIMENTO_ID + " = '" + alimento + "' AND P."+ Field.FIELD_PREPARACAO + " LIKE '%" + partNome + "%'",null);
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

    public List<Preparacao> getListPreparacaoCOMRELACIONAMENTO(String alimento) {

        List<Preparacao> preparacaoList = new ArrayList<Preparacao>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_PREPARACAO + " AND PA." + Field.FIELD_PREPARACAO_ALIMENTO_ID + " = '" + alimento  + "'",null);
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

    public List<Preparacao> getListPreparacao(String partNome) {

        List<Preparacao> preparacaoList = new ArrayList<Preparacao>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_PREPARACAO + " AND P."+ Field.FIELD_PREPARACAO + " LIKE '%" + partNome + "%'",null);
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

        Cursor cursor = mDb.rawQuery(DbSelect.GET_UNIDADE_SEM_RELACIONAMENTO ,null);
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

    public List<Unidade> getListUnidade(String alimento) {
        List<Unidade> unidadeList = new ArrayList<Unidade>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_UNIDADE + " AND UA." + Field.FIELD_UNIDADE_ALIMENTO_ID + " = '" + alimento  + "'",null);
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


    public List<Unidade> getListUnidade(String alimento, String partNome) {

        List<Unidade> unidadeList = new ArrayList<Unidade>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_UNIDADE + " AND UA." + Field.FIELD_UNIDADE_ALIMENTO_ID + " =  ? AND U."+ Field.FIELD_UNIDADE + " LIKE '%" + partNome + "%'",new String[] {(alimento)});
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

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ADICAO_SEM_RELACIONAMENTO,null);
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

    public List<GrauParentesco> getListGrauParentesco(String partNome) {

        List<GrauParentesco> grauParentescoList = new ArrayList<GrauParentesco>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_GRAU_PARENTESCO + " WHERE "+ Field.FIELD_GRAU_PARENTESCO + " LIKE '%" + partNome + "%'",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            GrauParentesco grauParentesco = new GrauParentesco();

            try {
                grauParentesco.setId(cursor.getString(cursor.getColumnIndex(Field.FIELD_GRAU_PARENTESCO_ID)));
                grauParentesco.setParentesco(cursor.getString(cursor.getColumnIndex(Field.FIELD_GRAU_PARENTESCO)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            grauParentescoList.add(grauParentesco);
            cursor.moveToNext();
        }
        cursor.close();
        return grauParentescoList;

    }

    public List<GrauParentesco> getListGrauParentesco() {

        List<GrauParentesco> grauParentescoList = new ArrayList<GrauParentesco>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_GRAU_PARENTESCO,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            GrauParentesco grauParentesco = new GrauParentesco();

            try {
                grauParentesco.setId(cursor.getString(cursor.getColumnIndex(Field.FIELD_GRAU_PARENTESCO_ID)));
                grauParentesco.setParentesco(cursor.getString(cursor.getColumnIndex(Field.FIELD_GRAU_PARENTESCO)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            grauParentescoList.add(grauParentesco);
            cursor.moveToNext();
        }
        cursor.close();
        return grauParentescoList;

    }

    public List<Adicao> getListAdicao(String alimento, String partNome) {

        List<Adicao> adicaoList = new ArrayList<Adicao>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ADICAO + " AND AA." + Field.FIELD_ADICAO_ALIMENTO_ID + " = '" + alimento + "' AND A."+ Field.FIELD_ADICAO + " LIKE '%" + partNome + "%'",null);
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

    public List<Adicao> getListAdicao(String alimento) {

        List<Adicao> adicaoList = new ArrayList<Adicao>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ADICAO + " AND AA." + Field.FIELD_ADICAO_ALIMENTO_ID + " = '" + alimento  + "'",null);
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



    public List<OcasiaoConsumo> getListOcasiaoConsumo(String partNome) {

        List<OcasiaoConsumo> ocasiaoConsumoList = new ArrayList<OcasiaoConsumo>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_OCASIAO_CONSUMO + " WHERE "+ Field.FIELD_OCASIAO_CONSUMO + " LIKE '%" + partNome + "%'",null);
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


    public List<Local> getListLocal(String partNome) {

        List<Local> localList = new ArrayList<Local>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_LOCAL + " where "+ Field.FIELD_LOCAL + " LIKE '%" + partNome + "%'",null);
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

    public List<Entrevistado> getListEntrevistado(String partNome) {

        List<Entrevistado> entrevistadoList = new ArrayList<Entrevistado>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ENTREVISTADO + " where "+ Field.FIELD_ENTREVISTADO + " LIKE '%" + partNome + "%'",null);
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

    public List<Entrevistado> getListEntrevistadoColetado(String partNome) {

        List<Entrevistado> entrevistadoList = new ArrayList<Entrevistado>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ENTREVISTADO_COLETADO + " AND E."+ Field.FIELD_ENTREVISTADO + " LIKE '%" + partNome + "%'",null);
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

    public boolean getListEntrevistadoEXISTE(String ID) {

        List<Entrevistado> entrevistadoList = new ArrayList<Entrevistado>();

        mDb = this.getWritableDatabase();

        boolean resultado = false;



        Cursor cursor = mDb.rawQuery(DbSelect.GET_ENTREVISTADO + " where "  + Field.FIELD_ENTREVISTADO_ID + " = ? ",new String[] {ID});
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            resultado = true;
        }
        cursor.close();
        return resultado;

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

    public List<Alimentacao> getListAlimentacao(String Entrevistado) {

        List<Alimentacao> alimentacaoList = new ArrayList<Alimentacao>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ALIMENTACAO + " WHERE " + Field.FIELD_ALIMENTACAO_ENTREVISTADO_ID + " = ? order by " +  Field.FIELD_ALIMENTACAO_HORA + "," +  Field.FIELD_ALIMENTACAO_ID + "," + Field.FIELD_ALIMENTACAO_ALIMENTO,new String[] {Entrevistado});
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Alimentacao alimentacao = new Alimentacao();

            try {
                alimentacao.setAlimentacao_id(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTACAO_ID)));
                alimentacao.setAlimentacao_alimento_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ALIMENTO_ID)));
                alimentacao.setAlimentacao_alimento(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ALIMENTO)));
                alimentacao.setAlimentacao_preparacao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_PREPARACAO_ID)));
                alimentacao.setAlimentacao_preparacao(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_PREPARACAO)));
                alimentacao.setAlimentacao_local_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_LOCAL_ID)));
                alimentacao.setAlimentacao_local(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_LOCAL)));
                alimentacao.setAlimentacao_unidade_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_UNIDADE_ID)));
                alimentacao.setAlimentacao_unidade(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_UNIDADE)));
                alimentacao.setAlimentacao_ocasiao_consumo_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO_ID)));
                alimentacao.setAlimentacao_ocasiao_consumo(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO)));
                alimentacao.setAlimentacao_adicao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ADICAO_ID)));
                alimentacao.setAlimentacao_adicao(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ADICAO)));
                alimentacao.setAlimentacao_quantidade(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_QUANTIDADE)));
                alimentacao.setAlimentacao_hora(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_HORA)));
                alimentacao.setAlimentacao_usuario(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_USUARIO)));
                alimentacao.setAlimentacao_entrevistado_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ENTREVISTADO_ID)));
                alimentacao.setAlimentacao_entrevistado(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ENTREVISTADO)));
                alimentacao.setAlimentacao_hora_coleta(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_HORA_COLETA)));
                alimentacao.setAlimentacao_dia_coleta(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_DIA_COLETA)));
                alimentacao.setAlimentacao_hora_coleta_fim(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_HORA_COLETA_FIM)));
                alimentacao.setAlimentacao_grau_parentesco_id(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTACAO_GRAU_PARENTESCO_ID)));
                alimentacao.setAlimentacao_grau_parentesco(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTACAO_GRAU_PARENTESCO)));
                alimentacao.setAlimentacao_dia_atico(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_DIA_ATIPICO)));
                alimentacao.setAlimentacao_obs(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_OBS)));
                alimentacao.setAlimentacao_espessura(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ESPESSURA)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            alimentacaoList.add(alimentacao);
            cursor.moveToNext();
        }
        cursor.close();
        return alimentacaoList;



    }


    public List<Alimentacao> getListAlimentacao() {

        List<Alimentacao> alimentacaoList = new ArrayList<Alimentacao>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ALIMENTACAO ,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Alimentacao alimentacao = new Alimentacao();

            try {
                alimentacao.setAlimentacao_id(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTACAO_ID)));
                alimentacao.setAlimentacao_alimento_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ALIMENTO_ID)));
                alimentacao.setAlimentacao_alimento(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ALIMENTO)));
                alimentacao.setAlimentacao_preparacao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_PREPARACAO_ID)));
                alimentacao.setAlimentacao_preparacao(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_PREPARACAO)));
                alimentacao.setAlimentacao_local_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_LOCAL_ID)));
                alimentacao.setAlimentacao_local(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_LOCAL)));
                alimentacao.setAlimentacao_unidade_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_UNIDADE_ID)));
                alimentacao.setAlimentacao_unidade(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_UNIDADE)));
                alimentacao.setAlimentacao_ocasiao_consumo_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO_ID)));
                alimentacao.setAlimentacao_ocasiao_consumo(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO)));
                alimentacao.setAlimentacao_adicao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ADICAO_ID)));
                alimentacao.setAlimentacao_adicao(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ADICAO)));
                alimentacao.setAlimentacao_quantidade(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_QUANTIDADE)));
                alimentacao.setAlimentacao_hora(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_HORA)));
                alimentacao.setAlimentacao_usuario(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_USUARIO)));
                alimentacao.setAlimentacao_entrevistado_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ENTREVISTADO_ID)));
                alimentacao.setAlimentacao_entrevistado(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ENTREVISTADO)));
                alimentacao.setAlimentacao_hora_coleta(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_HORA_COLETA)));
                alimentacao.setAlimentacao_dia_coleta(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_DIA_COLETA)));
                alimentacao.setAlimentacao_hora_coleta_fim(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_HORA_COLETA_FIM)));
                alimentacao.setAlimentacao_obs(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_OBS)));
                alimentacao.setAlimentacao_grau_parentesco(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTACAO_GRAU_PARENTESCO)));
                alimentacao.setAlimentacao_grau_parentesco_id(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTACAO_GRAU_PARENTESCO_ID)));
                alimentacao.setAlimentacao_dia_atico(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_DIA_ATIPICO)));
                alimentacao.setAlimentacao_espessura(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ESPESSURA)));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            alimentacaoList.add(alimentacao);
            cursor.moveToNext();
        }
        cursor.close();
        return alimentacaoList;



    }


    public List<Alimentacao> getListAlimentacao_DISTICT() {

        List<Alimentacao> alimentacaoList = new ArrayList<Alimentacao>();

        mDb = this.getWritableDatabase();

        Cursor cursor = mDb.rawQuery(DbSelect.GET_ALIMENTACAO_DISTICT ,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            Alimentacao alimentacao = new Alimentacao();

            try {
                alimentacao.setAlimentacao_id(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTACAO_ID)));
                alimentacao.setAlimentacao_alimento_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ALIMENTO_ID)));
                alimentacao.setAlimentacao_alimento(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ALIMENTO)));
                alimentacao.setAlimentacao_preparacao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_PREPARACAO_ID)));
                alimentacao.setAlimentacao_preparacao(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_PREPARACAO)));
                alimentacao.setAlimentacao_local_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_LOCAL_ID)));
                alimentacao.setAlimentacao_local(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_LOCAL)));
                alimentacao.setAlimentacao_unidade_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_UNIDADE_ID)));
                alimentacao.setAlimentacao_unidade(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_UNIDADE)));
                alimentacao.setAlimentacao_ocasiao_consumo_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO_ID)));
                alimentacao.setAlimentacao_ocasiao_consumo(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO)));
                alimentacao.setAlimentacao_adicao_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ADICAO_ID)));
                alimentacao.setAlimentacao_adicao(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ADICAO)));
                alimentacao.setAlimentacao_quantidade(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_QUANTIDADE)));
                alimentacao.setAlimentacao_hora(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_HORA)));
                alimentacao.setAlimentacao_usuario(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_USUARIO)));
                alimentacao.setAlimentacao_entrevistado_id(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ENTREVISTADO_ID)));
                alimentacao.setAlimentacao_entrevistado(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ENTREVISTADO)));
                alimentacao.setAlimentacao_hora_coleta(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_HORA_COLETA)));
                alimentacao.setAlimentacao_dia_coleta(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_DIA_COLETA)));
                alimentacao.setAlimentacao_hora_coleta_fim(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_HORA_COLETA_FIM)));
                alimentacao.setAlimentacao_obs(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_OBS)));
                alimentacao.setAlimentacao_grau_parentesco(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTACAO_GRAU_PARENTESCO)));
                alimentacao.setAlimentacao_grau_parentesco_id(cursor.getString(cursor.getColumnIndex(FIELD_ALIMENTACAO_GRAU_PARENTESCO_ID)));
                alimentacao.setAlimentacao_dia_atico(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_DIA_ATIPICO)));
                alimentacao.setAlimentacao_espessura(cursor.getString(cursor.getColumnIndex(Field.FIELD_ALIMENTACAO_ESPESSURA)));

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
