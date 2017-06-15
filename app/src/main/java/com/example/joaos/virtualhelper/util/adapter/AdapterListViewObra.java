package com.example.joaos.virtualhelper.util.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.model.Obra;

import java.util.List;


public class AdapterListViewObra extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Obra> itens;


    public AdapterListViewObra(Context context, List<Obra> itens) {

        this.itens = itens;
        inflater=LayoutInflater.from(context);
    }

    public void setItens(List<Obra> itens) {
        this.itens = itens;
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //Resgatar o item do ListView pelo position
        Obra item= (Obra) itens.get(position);

        //Resgatar o layout a ser preenchido
        view=inflater.inflate(R.layout.activity_item_lista_obras,null);

        //Resgatar os dois textviews e o imageview para insercao do conteudo
        TextView titulo=(TextView) view.findViewById(R.id.TextViewTituloLista);
        TextView autor=(TextView) view.findViewById(R.id.TextViewAutorLista);
        TextView editora=(TextView) view.findViewById(R.id.TextViewEditoraLista);
        ImageView capa=(ImageView) view.findViewById(R.id.ImageViewCapaLista);

        titulo.setText(item.getTitulo());
        autor.setText(item.getAutor());
        editora.setText(item.getEditora());
        capa.setImageBitmap(item.getCapa());

        return view;
    }

}
