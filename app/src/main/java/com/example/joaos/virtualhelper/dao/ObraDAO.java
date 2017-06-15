package com.example.joaos.virtualhelper.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.joaos.virtualhelper.model.Obra;
import com.example.joaos.virtualhelper.util.ImageConverter;

import java.util.ArrayList;
import java.util.List;


public class ObraDAO {
    private SQLiteDatabase mDatabaseHelper;

    public ObraDAO(SQLiteDatabase databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    public long insert(Obra o) {
        return mDatabaseHelper.insert("Obra", null, getContentFrom(o));
    }

    public void update(Obra o) {
        mDatabaseHelper.update("Obra",getContentFrom(o), "_id = ?", new String[] { o.getIdObra().toString() });
    }

    public int delete(Integer id) {
        return mDatabaseHelper.delete("Obra", "_id = ?", new String[] { id.toString() });
    }

    public Obra getById(Integer id) {
        Cursor cursor = mDatabaseHelper.query("Obra",null, "_id = ?", new String[] { id.toString() },null,null,null);
        return getObra(cursor);
    }

    public List<Obra> getListaObras() {
        Cursor cursor = mDatabaseHelper.query("Obra",null, null, null, null, null, null);
        List<Obra> lista = new ArrayList<>();

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                lista.add(getObra(cursor));
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return lista;
    }


    //fix metodo numerodeobras -> numeroobras
    //considerar idcontainer

    private ContentValues getContentFrom(Obra o) {
        ContentValues content = new ContentValues();
        content.put("titulo", o.getTitulo());
        content.put("autor", o.getAutor());
        content.put("editora", o.getEditora());
        content.put("_id", o.getIdObra());
        content.put("descricao", o.getDescricao());
        content.put("anoPublicacao", o.getAnoPublicacao());
        content.put("emprestado", o.isEmprestado());
        content.put("isbn", o.getIsbn());
        content.put("edicao", o.getNumeroEdicao());

        if (o.getCapa()!=null) {
            content.put("capa",ImageConverter.toByteArray(o.getCapa()));
        } else {
            content.put("capa", (byte[]) null);
        }

        return content;
    }

    private Obra getObra(Cursor cursor) {
        Obra o = new Obra();
        o.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
        o.setIdObra(cursor.getInt(cursor.getColumnIndex("_id")));
        o.setAutor(cursor.getString(cursor.getColumnIndex("autor")));
        o.setEditora(cursor.getString(cursor.getColumnIndex("editora")));

        if (cursor.getBlob(cursor.getColumnIndex("capa"))!=null) {
            o.setCapa(ImageConverter.toBitmap(cursor.getBlob(cursor.getColumnIndex("capa"))));
        }else {
            o.setCapa(null);
        }

        o.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
        o.setAnoPublicacao(cursor.getInt(cursor.getColumnIndex("anoPublicacao")));
        o.setEmprestado(cursor.getInt(cursor.getColumnIndex("emprestado")) == 1);
        o.setIsbn(cursor.getString(cursor.getColumnIndex("isbn")));
        o.setNumeroEdicao(cursor.getInt(cursor.getColumnIndex("edicao")));

        return o;
    }

}
