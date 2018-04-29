package com.example.jorge.meurecordatorio.PersistentData;

/**
 * Created by jorge on 28/04/2018.
 */

public class DbSelect {

    public static String GET_ALIMENTO =
            " SELECT "
                    + Field.FIELD_ALIMENTO_ID  + ","
                    + Field.FIELD_ALIMENTO +
                    " FROM " + DbCreate.TABLE_ALIMENTO ;

    public static String GET_PREPARACAO =
            " SELECT "
                    + Field.FIELD_PREPARACAO_ID  + ","
                    + Field.FIELD_PREPARACAO +
                    " FROM " + DbCreate.TABLE_PREPARACAO ;

    public static String GET_UNIDADE =
            " SELECT "
                    + Field.FIELD_UNIDADE_ID  + ","
                    + Field.FIELD_UNIDADE +
                    " FROM " + DbCreate.TABLE_UNIDADE ;

    public static String GET_ADICAO =
            " SELECT "
                    + Field.FIELD_ADICAO_ID  + ","
                    + Field.FIELD_ADICAO +
                    " FROM " + DbCreate.TABLE_ADICAO ;

    public static String GET_OCASIAO_CONSUMO =
            " SELECT "
                    + Field.FIELD_OCASIAO_CONSUMO_ID  + ","
                    + Field.FIELD_OCASIAO_CONSUMO +
                    " FROM " + DbCreate.TABLE_OCASIAO_CONSUMO ;

    public static String GET_LOCAL =
            " SELECT "
                    + Field.FIELD_LOCAL_ID  + ","
                    + Field.FIELD_LOCAL +
                    " FROM " + DbCreate.TABLE_LOCAL ;

    public static String GET_ALIMENTACAO =
            " SELECT "
                    + Field.FIELD_ALIMENTACAO_ID  + ","
                    + Field.FIELD_ALIMENTACAO_ALIMENTO_ID  + ","
                    + Field.FIELD_ALIMENTACAO_PREPARACAO_ID  + ","
                    + Field.FIELD_ALIMENTACAO_LOCAL_ID   + ","
                    + Field.FIELD_ALIMENTACAO_UNIDADE_ID   + ","
                    + Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO_ID  + ","
                    + Field.FIELD_ALIMENTACAO_ADICAO_ID  + ","
                    + Field.FIELD_ALIMENTACAO_QUANTIDADE  + ","
                    + Field.FIELD_ALIMENTACAO_HORA  + ","
                    + Field.FIELD_ALIMENTACAO_USUARIO  + ","
                    + Field.FIELD_ALIMENTACAO_ENTREVISTADO_ID  + ","
                    + Field.FIELD_ALIMENTACAO_HORA_COLETA  + ","
                    + Field.FIELD_ALIMENTACAO_DIA_COLETA +
                    " FROM " + DbCreate.TABLE_ALIMENTACAO ;

    public static String GET_USUARIO =
            " SELECT "
                    + Field.FIELD_USUARIO  + ","
                    + Field.FIELD_SENHA +
                    " FROM " + DbCreate.TABLE_USUARIO ;

    public static String GET_ENTREVISTADO =
            " SELECT "
                    + Field.FIELD_ENTREVISTADO_ID  + ","
                    + Field.FIELD_ENTREVISTADO +
                    " FROM " + DbCreate.TABLE_ENTREVISTADO ;

    public static String GET_PREPARACAO_ALIMENTO =
            " SELECT "
                    + Field.FIELD_PREPARACAO_PREPARACAO_ID  + ","
                    + Field.FIELD_PREPARACAO_ALIMENTO_ID +
                    " FROM " + DbCreate.TABLE_PREPARACAO_ALIMENTO ;

    public static String GET_UNIDADE_ALIMENTO =
            " SELECT "
                    + Field.FIELD_UNIDADE_UNIDADE_ID  + ","
                    + Field.FIELD_UNIDADE_ALIMENTO_ID +
                    " FROM " + DbCreate.TABLE_UNIDADE ;


    public static String GET_ADICAO_ALIMENTO =
            " SELECT "
                    + Field.FIELD_ADICAO_ADICAO_ID  + ","
                    + Field.FIELD_ADICAO_ALIMENTO_ID +
                    " FROM " + DbCreate.TABLE_ADICAO ;

/*
    public static String GET_SQL_PULL_REQUEST =
            " SELECT "
                    + Field.FIELD_TITLE + ","
                    + Field.FIELD_HTML_URL + ","
                    + Field.FIELD_CREATED_AT + ","
                    + Field.FIELD_BODY + ","
                    + Field.FIELD_USER_LOGIN + ","
                    + Field.FIELD_USER_AVATAR_URL + ","
                    + Field.FIELD_NAME +
                    " FROM " + DbCreate.TABLE_PULL_REQUEST +
                    " WHERE " + Field.FIELD_LOGIN + " = ? AND " + Field.FIELD_NAME + " = ? ";
                    }*/




}