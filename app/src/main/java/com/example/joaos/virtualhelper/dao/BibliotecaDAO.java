package com.example.joaos.virtualhelper.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.joaos.virtualhelper.model.Biblioteca;


public class BibliotecaDAO {
    private SQLiteDatabase mDatabaseHelper;

    public long insert(Biblioteca b, Integer idUsuario) {
        return mDatabaseHelper.insert("Biblioteca", null, getContentFrom(b, idUsuario));
    }

    public int delete(Integer id) {
        return mDatabaseHelper.delete("Biblioteca", "_id = ?", new String[] { id.toString() });
    }

    private ContentValues getContentFrom(Biblioteca b,Integer idUsuario) {
        ContentValues content = new ContentValues();
        content.put("_id", b.getIdBiblioteca());
        content.put("usuario_id", idUsuario);

        return content;
    }

}
