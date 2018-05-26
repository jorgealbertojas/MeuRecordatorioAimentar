package com.example.jorge.meurecordatorio.PersistentData;

/**
 * Created by jorge on 28/04/2018.
 */

public class DbSelect {

    public static String GET_ALIMENTO =
            " SELECT "
                    + Field.FIELD_ALIMENTO_ID  + ","
                    + Field.FIELD_NOVO + ","
                    + Field.FIELD_ALIMENTO +
                    " FROM " + DbCreate.TABLE_ALIMENTO ;

    public static String GET_UNIDADE_SEM_RELACIONAMENTO =
            " SELECT "
                    + Field.FIELD_UNIDADE_ID  + ","
                    + Field.FIELD_UNIDADE +
                    " FROM " + DbCreate.TABLE_UNIDADE ;

    public static String GET_PREPARACAO =
            " SELECT distinct "
                    + Field.FIELD_PREPARACAO_ID  + ","
                    + Field.FIELD_PREPARACAO +
                    " FROM " + DbCreate.TABLE_PREPARACAO + " P, " + DbCreate.TABLE_PREPARACAO_ALIMENTO  + " PA WHERE P." + Field.FIELD_PREPARACAO_ID + " = PA." + Field.FIELD_PREPARACAO_PREPARACAO_ID    ;

    public static String GET_PREPARACAO_SEM_RELACIONAMENTO =
            " SELECT distinct "
                    + Field.FIELD_PREPARACAO_ID  + ","
                    + Field.FIELD_PREPARACAO +
                    " FROM " + DbCreate.TABLE_PREPARACAO   ;

    public static String GET_UNIDADE =
            " SELECT distinct "
                    + Field.FIELD_UNIDADE_ID  + ","
                    + Field.FIELD_UNIDADE +
                    " FROM " + DbCreate.TABLE_UNIDADE + " U, " + DbCreate.TABLE_UNIDADE_ALIMENTO  + " UA WHERE U." + Field.FIELD_UNIDADE_ID + " = UA." + Field.FIELD_UNIDADE_UNIDADE_ID    ;

    public static String GET_ADICAO_SEM_RELACIONAMENTO =
            " SELECT distinct "
                    + Field.FIELD_ADICAO_ID  + ","
                    + Field.FIELD_ADICAO +
                    " FROM " + DbCreate.TABLE_ADICAO ;

    public static String GET_ADICAO =
            " SELECT distinct "
                    + Field.FIELD_ADICAO_ID  + ","
                    + Field.FIELD_ADICAO +
                    " FROM " + DbCreate.TABLE_ADICAO + " A, " + DbCreate.TABLE_ADICAO_ALIMENTO  + " AA WHERE A." + Field.FIELD_ADICAO_ID + " = AA." + Field.FIELD_ADICAO_ADICAO_ID    ;

    public static String GET_OCASIAO_CONSUMO =
            " SELECT "
                    + Field.FIELD_OCASIAO_CONSUMO_ID  + ","
                    + Field.FIELD_OCASIAO_CONSUMO +
                    " FROM " + DbCreate.TABLE_OCASIAO_CONSUMO;

    public static String GET_LOCAL =
            " SELECT "
                    + Field.FIELD_LOCAL_ID  + ","
                    + Field.FIELD_LOCAL +
                    " FROM " + DbCreate.TABLE_LOCAL ;

    public static String GET_ALIMENTACAO =
            " SELECT "
                    + Field.FIELD_ALIMENTACAO_ID  + ","
                    + Field.FIELD_ALIMENTACAO_ALIMENTO_ID  + ","
                    + Field.FIELD_ALIMENTACAO_ALIMENTO  + ","
                    + Field.FIELD_ALIMENTACAO_PREPARACAO_ID  + ","
                    + Field.FIELD_ALIMENTACAO_PREPARACAO  + ","
                    + Field.FIELD_ALIMENTACAO_LOCAL_ID   + ","
                    + Field.FIELD_ALIMENTACAO_LOCAL   + ","
                    + Field.FIELD_ALIMENTACAO_UNIDADE_ID   + ","
                    + Field.FIELD_ALIMENTACAO_UNIDADE   + ","
                    + Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO_ID  + ","
                    + Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO  + ","
                    + Field.FIELD_ALIMENTACAO_ADICAO_ID  + ","
                    + Field.FIELD_ALIMENTACAO_ADICAO  + ","
                    + Field.FIELD_ALIMENTACAO_QUANTIDADE  + ","
                    + Field.FIELD_ALIMENTACAO_HORA  + ","
                    + Field.FIELD_ALIMENTACAO_USUARIO  + ","
                    + Field.FIELD_ALIMENTACAO_ENTREVISTADO_ID  + ","
                    + Field.FIELD_ALIMENTACAO_ENTREVISTADO  + ","
                    + Field.FIELD_ALIMENTACAO_HORA_COLETA  + ","
                    + Field.FIELD_ALIMENTACAO_HORA_COLETA_FIM  + ","
                    + Field.FIELD_ALIMENTACAO_OBS  + ","
                    + Field.FIELD_ALIMENTACAO_DIA_COLETA + ","
                    + Field.FIELD_ALIMENTACAO_GRAU_PARENTESCO  + ","
                    + Field.FIELD_ALIMENTACAO_DIA_ATIPICO +
                    " FROM " + DbCreate.TABLE_ALIMENTACAO ;

    public static String GET_USUARIO =
            " SELECT "
                    + Field.FIELD_USUARIO  + ","
                    + Field.FIELD_SENHA +
                    " FROM " + DbCreate.TABLE_USUARIO ;

    public static String GET_USUARIO_PARAMETRO =
            " SELECT "
                    + Field.FIELD_USUARIO  + ","
                    + Field.FIELD_SENHA +
                    " FROM " + DbCreate.TABLE_USUARIO + " WHERE " + Field.FIELD_USUARIO + " = ? AND " + Field.FIELD_SENHA + " = ? ";

    public static String GET_ENTREVISTADO =
            " SELECT "
                    + Field.FIELD_ENTREVISTADO_ID  + ","
                    + Field.FIELD_ENTREVISTADO +
                    " FROM " + DbCreate.TABLE_ENTREVISTADO ;

    public static String GET_ENTREVISTADO_COLETADO =
            " SELECT distinct "
                    + Field.FIELD_ENTREVISTADO_ID  + ","
                    + Field.FIELD_ENTREVISTADO +
                    " FROM " + DbCreate.TABLE_ENTREVISTADO + " E , " +DbCreate.TABLE_ALIMENTACAO + " A WHERE " + Field.FIELD_ENTREVISTADO_ID + " = " + Field.FIELD_ALIMENTACAO_ENTREVISTADO_ID  ;

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

    public static String GET_GRAU_PARENTESCO =
            " SELECT "
                    + Field.FIELD_GRAU_PARENTESCO  + ","
                    + Field.FIELD_GRAU_PARENTESCO_ID +
                    " FROM " + DbCreate.TABLE_GRAU_PARENTESCO ;


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
