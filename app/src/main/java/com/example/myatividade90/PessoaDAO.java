package com.example.myatividade90;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pessoa_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PESSOA = "pessoa";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_TELEFONE = "telefone";
    private static final String COLUMN_EMAIL = "email";

    public PessoaDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PESSOA_TABLE = "CREATE TABLE " + TABLE_PESSOA + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOME + " TEXT,"
                + COLUMN_TELEFONE + " TEXT,"
                + COLUMN_EMAIL + " TEXT"
                + ")";
        db.execSQL(CREATE_PESSOA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PESSOA);
        onCreate(db);
    }

    public void addPessoa(Pessoa pessoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, pessoa.getNome());
        values.put(COLUMN_TELEFONE, pessoa.getTelefone());
        values.put(COLUMN_EMAIL, pessoa.getEmail());
        db.insert(TABLE_PESSOA, null, values);
        db.close();
    }

    public List<Pessoa> getAllPessoas() {
        List<Pessoa> pessoaList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PESSOA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Pessoa pessoa = new Pessoa();
                pessoa.setCod(Integer.parseInt(cursor.getString(0)));
                pessoa.setNome(cursor.getString(1));
                pessoa.setTelefone(cursor.getString(2));
                pessoa.setEmail(cursor.getString(3));
                pessoaList.add(pessoa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pessoaList;
    }

    public void deletePessoa(Pessoa pessoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PESSOA, COLUMN_ID + " = ?",
                new String[]{String.valueOf(pessoa.getCod())});
        db.close();
    }
}

