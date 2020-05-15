package com.behl.cdm_02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Sqlite extends SQLiteOpenHelper implements Sqlite_interface {
    private static final String NOME_BANCO = "banco.db";
    public static final String TABELA = "user";

    public static final String ID = "_id";
    public static final String NOME = "name";
    public static final String PASS = "password";
    public static final String EMAIL = "email";
    private static final int VERSAO = 3;

    private Sqlite_interface banco;

    public Sqlite(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {

        Log.i("ONCREATE DB", "YES");
        String sql =
                "CREATE TABLE IF NOT EXISTS " + TABELA + "("
                + ID + " integer primary key autoincrement,"
                + NOME + " text,"
                + PASS + " text,"
                + EMAIL + " text"
                + ")";
        bd.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }

    @Override
    public Boolean insereDado(String nome, String email, String pass){

        try{
            ContentValues cv = new ContentValues();

            cv.put(NOME, nome);
            cv.put(EMAIL, email);
            cv.put(PASS, pass);

            SQLiteDatabase db = this.getWritableDatabase();

            db.insert(TABELA, null, cv);
            db.close();

            return true;
        }
        catch (Exception ex){
            Log.i("EXCEPTION: ", ex.getMessage());
            return false;
        }


    }

    @Override
    public String[] carregaDados(){

        String[] dados = new String[3];

        SQLiteDatabase db = getReadableDatabase();

        String selectAll = "SELECT * FROM " + TABELA;

        Cursor cursor = db.rawQuery(selectAll, null);

        if(cursor.moveToFirst()){
            do{
                dados[0] = (cursor.getString(1));
                dados[1] = (cursor.getString(2));
                dados[2] = (cursor.getString(3));

            }
            while (cursor.moveToNext());
            cursor.moveToFirst();
        }
        else {
            return null;
        }
        db.close();


        return dados;
    }
}
