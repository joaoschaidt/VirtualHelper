package com.example.joaos.virtualhelper.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.model.Tag;
import com.example.joaos.virtualhelper.util.itemListView.ItemListViewTags;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaos on 01/04/2017.
 */

public class AdapterListViewTags extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Tag> itens;


    public AdapterListViewTags(Context context, List<Tag> itens) {

        this.itens = itens;
        inflater=LayoutInflater.from(context);
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
        Tag item= (Tag)itens.get(position);

        //Resgatar o layout a ser preenchido
        view=inflater.inflate(R.layout.activity_item_lista_tags,null);

        //Resgatar components para insercao do conteudo
        TextView nome=(TextView) view.findViewById(R.id.tvNomeTag);
        LinearLayout layoutTags= (LinearLayout) view.findViewById(R.id.layoutTags);

        nome.setText(item.getNomeTag());
        layoutTags.setBackgroundColor(Integer.parseInt(item.getCorHex()));

        return view;
    }

}
