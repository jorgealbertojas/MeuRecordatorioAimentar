package com.softjads.jorge.meurecordatorio.PersistentData;

/**
 * Created by jorge on 28/04/2018.
 */

public class DbCreate {

    public static final int DB_VERSION = 1;
    public static String DB_NAME = "DB.db";

    public static String TABLE_HELP = "TABLE_HELP";
    public static String CREATE_TABLE_HELP =
            "CREATE TABLE IF NOT EXISTS " + TABLE_HELP +  "(" +
                    Field.FIELD_HELP_KEY + " VARCHAR(500)," +
                    Field.FIELD_HELP_VALUE + " VARCHAR(500)," +
                    Field.FIELD_HELP_LAST + " VARCHAR(500) " +
                    ");";

    public static String TABLE_ALIMENTO = "TABLE_ALIMENTO";
    public static String CREATE_TABLE_ALIMENTO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ALIMENTO +  "(" +
                    Field.FIELD_ALIMENTO_ID + " INTEGER PRIMARY KEY," +
                    Field.FIELD_NOVO + " VARCHAR(500)," +
                    Field.FIELD_ALIMENTO + " VARCHAR(500) " +
                    ");";

    public static String TABLE_GRAU_PARENTESCO = "TABLE_GRAU_PARENTESCO";
    public static String CREATE_TABLE_GRAU_PARENTESCO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_GRAU_PARENTESCO +  "(" +
                    Field.FIELD_GRAU_PARENTESCO_ID + " INTEGER PRIMARY KEY," +
                    Field.FIELD_GRAU_PARENTESCO + " VARCHAR(500) " +
                    ");";

    public static String TABLE_PREPARACAO = "TABLE_PREPARACAO";
    public static String CREATE_TABLE_PREPARACAO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_PREPARACAO +  "(" +
                    Field.FIELD_PREPARACAO_ID + " INTEGER," +
                    Field.FIELD_PREPARACAO + " VARCHAR(500) " +
                    ");";

    public static String TABLE_UNIDADE = "TABLE_UNIDADE";
    public static String CREATE_TABLE_UNIDADE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_UNIDADE +  "(" +
                    Field.FIELD_UNIDADE_ID + " INTEGER PRIMARY KEY," +
                    Field.FIELD_UNIDADE + " TEXT COLLATE NOCASE " +
                    ");";

    public static String CREATE_INDEX_TABLE_UNIDADE =
            "CREATE INDEX FIELD_UNIDADE_INDEX ON " + TABLE_UNIDADE +  "(" +Field.FIELD_UNIDADE + ");";

    public static String TABLE_ADICAO = "TABLE_ADICAO";
    public static String CREATE_TABLE_ADICAO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ADICAO +  "(" +
                    Field.FIELD_ADICAO_ID + " INTEGER PRIMARY KEY," +
                    Field.FIELD_ADICAO + " VARCHAR(500) " +
                    ");";

    public static String TABLE_OCASIAO_CONSUMO = "TABLE_OCASIAO_CONSUMO";
    public static String CREATE_TABLE_OCASIAO_CONSUMO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_OCASIAO_CONSUMO +  "(" +
                    Field.FIELD_OCASIAO_CONSUMO_ID + " INTEGER PRIMARY KEY," +
                    Field.FIELD_OCASIAO_CONSUMO + " VARCHAR(500) " +
                    ");";

    public static String TABLE_LOCAL = "TABLE_LOCAL";
    public static String CREATE_TABLE_LOCAL =
            "CREATE TABLE IF NOT EXISTS " + TABLE_LOCAL +  "(" +
                    Field.FIELD_LOCAL_ID + " INTEGER PRIMARY KEY," +
                    Field.FIELD_LOCAL + " VARCHAR(500) " +
                    ");";

    public static String TABLE_ALIMENTACAO = "TABLE_ALIMENTACAO";
    public static String CREATE_TABLE_ALIMENTACAO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ALIMENTACAO +  "(" +
                    Field.FIELD_ALIMENTACAO_ID + " INTEGER PRIMARY KEY," +
                    Field.FIELD_ALIMENTACAO_ALIMENTO_ID + " INTEGER," +
                    Field.FIELD_ALIMENTACAO_ALIMENTO + " VARCHAR(500)," +
                    Field.FIELD_ALIMENTACAO_PREPARACAO_ID + " INTEGER," +
                    Field.FIELD_ALIMENTACAO_PREPARACAO + " VARCHAR(100)," +
                    Field.FIELD_ALIMENTACAO_LOCAL_ID + " INTEGER," +
                    Field.FIELD_ALIMENTACAO_LOCAL + " VARCHAR(100)," +
                    Field.FIELD_ALIMENTACAO_UNIDADE_ID + " INTEGER," +
                    Field.FIELD_ALIMENTACAO_UNIDADE + " VARCHAR(500)," +
                    Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO_ID + " INTEGER," +
                    Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO + " VARCHAR(100)," +
                    Field.FIELD_ALIMENTACAO_ADICAO_ID + " INTEGER," +
                    Field.FIELD_ALIMENTACAO_ADICAO + " VARCHAR(500)," +
                    Field.FIELD_ALIMENTACAO_QUANTIDADE + "  VARCHAR(100)," +
                    Field.FIELD_ALIMENTACAO_HORA + "  VARCHAR(10)," +
                    Field.FIELD_ALIMENTACAO_USUARIO + "  VARCHAR(100)," +
                    Field.FIELD_ALIMENTACAO_ENTREVISTADO_ID + "  VARCHAR(100)," +
                    Field.FIELD_ALIMENTACAO_ENTREVISTADO + " VARCHAR(100)," +
                    Field.FIELD_ALIMENTACAO_HORA_COLETA + "  VARCHAR(50)," +
                    Field.FIELD_ALIMENTACAO_HORA_COLETA_FIM + "  VARCHAR(50)," +
                    Field.FIELD_ALIMENTACAO_OBS + "  VARCHAR(500)," +
                    Field.FIELD_ALIMENTACAO_DIA_COLETA + " VARCHAR(50), " +
                    Field.FIELD_ALIMENTACAO_GRAU_PARENTESCO_ID + " INTEGER, " +
                    Field.FIELD_ALIMENTACAO_GRAU_PARENTESCO + " VARCHAR(50), " +
                    Field.FIELD_ALIMENTACAO_DIA_ATIPICO + " VARCHAR(50), " +
                    Field.FIELD_ALIMENTACAO_ESPESSURA + " VARCHAR(100), " +
                    " FOREIGN KEY("+ Field.FIELD_ALIMENTACAO_ALIMENTO_ID +") REFERENCES " + TABLE_ALIMENTO +"("+Field.FIELD_ALIMENTO_ID+"), " +
                    " FOREIGN KEY("+ Field.FIELD_ALIMENTACAO_PREPARACAO_ID +") REFERENCES " + TABLE_PREPARACAO +"("+Field.FIELD_PREPARACAO_ID+"), " +
                    " FOREIGN KEY("+ Field.FIELD_ALIMENTACAO_LOCAL_ID +") REFERENCES " + TABLE_LOCAL +"("+Field.FIELD_LOCAL_ID+"), " +
                    " FOREIGN KEY("+ Field.FIELD_ALIMENTACAO_UNIDADE_ID +") REFERENCES " + TABLE_UNIDADE +"("+Field.FIELD_UNIDADE_ID+"), " +
                    " FOREIGN KEY("+ Field.FIELD_ALIMENTACAO_OCASIAO_CONSUMO_ID +") REFERENCES " + TABLE_OCASIAO_CONSUMO+"("+Field.FIELD_OCASIAO_CONSUMO_ID+"), " +
                    " FOREIGN KEY("+ Field.FIELD_ALIMENTACAO_ADICAO_ID +") REFERENCES " + TABLE_ADICAO +"("+Field.FIELD_ADICAO_ID+") " +
                    ");";

    public static String TABLE_USUARIO = "TABLE_USUARIO";
    public static String CREATE_TABLE_USUARIO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USUARIO+  "(" +
                    Field.FIELD_USUARIO + " VARCHAR(500)," +
                    Field.FIELD_SENHA + " VARCHAR(500) " +
                    ");";

    public static String TABLE_ENTREVISTADO = "TABLE_ENTREVISTADO";
    public static String CREATE_TABLE_ENTREVISTADO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ENTREVISTADO +  "(" +
                    Field.FIELD_ENTREVISTADO_ID + " VARCHAR(500)," +
                    Field.FIELD_ENTREVISTADO + " VARCHAR(500) " +
                    ");";

    public static String TABLE_PREPARACAO_ALIMENTO = "TABLE_PREPARACAO_ALIMENTO";
    public static String CREATE_TABLE_PREPARACAO_ALIMENTO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_PREPARACAO_ALIMENTO +  "(" +
                    Field.FIELD_PREPARACAO_PREPARACAO_ID + " INTEGER ," +
                    Field.FIELD_PREPARACAO_ALIMENTO_ID + " INTEGER, " +
                    " FOREIGN KEY("+ Field.FIELD_PREPARACAO_PREPARACAO_ID +") REFERENCES " + TABLE_PREPARACAO+"("+Field.FIELD_PREPARACAO_ID+"), " +
                    " FOREIGN KEY("+ Field.FIELD_PREPARACAO_ALIMENTO_ID +") REFERENCES " + TABLE_ALIMENTO +"("+Field.FIELD_ALIMENTO_ID+") " +
                    ");";

    public static String TABLE_UNIDADE_ALIMENTO = "TABLE_UNIDADE_ALIMENTO";
    public static String CREATE_TABLE_UNIDADE_ALIMENTO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_UNIDADE_ALIMENTO +  "(" +
                    Field.FIELD_UNIDADE_UNIDADE_ID + " INTEGER," +
                    Field.FIELD_UNIDADE_ALIMENTO_ID  + " INTEGER, " +
                    " FOREIGN KEY("+ Field.FIELD_UNIDADE_UNIDADE_ID +") REFERENCES " + TABLE_UNIDADE+"("+Field.FIELD_UNIDADE_ID+"), " +
                    " FOREIGN KEY("+ Field.FIELD_UNIDADE_ALIMENTO_ID +") REFERENCES " + TABLE_ALIMENTO +"("+Field.FIELD_ALIMENTO_ID+") " +
                    ");";

    public static String TABLE_ADICAO_ALIMENTO = "TABLE_ADICAO_ALIMENTO";
    public static String CREATE_TABLE_ADICAO_ALIMENTO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ADICAO_ALIMENTO +  "(" +
                    Field.FIELD_ADICAO_ADICAO_ID + " INTEGER," +
                    Field.FIELD_ADICAO_ALIMENTO_ID + " INTEGER, " +
                    " FOREIGN KEY("+ Field.FIELD_ADICAO_ADICAO_ID +") REFERENCES " + TABLE_ADICAO+"("+Field.FIELD_ADICAO_ID+"), " +
                    " FOREIGN KEY("+ Field.FIELD_ADICAO_ALIMENTO_ID +") REFERENCES " + TABLE_ALIMENTO +"("+Field.FIELD_ALIMENTO_ID+") " +
                    ");";

}
