package com.example.joaos.virtualhelper.activity.tabs;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.activity.edit.TagEditActivity;
import com.example.joaos.virtualhelper.dao.DatabaseHelper;
import com.example.joaos.virtualhelper.dao.TagDAO;
import com.example.joaos.virtualhelper.model.Tag;
import com.example.joaos.virtualhelper.util.adapter.AdapterListViewTag;

import java.util.ArrayList;
import java.util.List;



public class tab_TagsActivity extends Fragment {

    private ListView listView;
    private AdapterListViewTag adapterListViewTags;
    private TagDAO tagDAO;
    private SQLiteDatabase mDatabase;
    private List<Tag> itens;
    private Tag tag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_activity_tags, container, false);
        mDatabase = DatabaseHelper.newInstance(getActivity());
        tagDAO= new TagDAO(mDatabase);
        listView = (ListView) rootView.findViewById(R.id.listaTags);

        createListView();

        listView.setOnItemLongClickListener(cliqueLongo());

        return rootView;
    }

    public void createListView(){

        itens = tagDAO.getListaTags();

        adapterListViewTags = new AdapterListViewTag(getActivity(), itens);
        listView.setAdapter(adapterListViewTags);
    }

    public AdapterView.OnItemLongClickListener cliqueLongo() {

        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int position, long l) {

                PopupMenu popup = new PopupMenu(getContext(), view);
                popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());

                tag = (Tag)adapterListViewTags.getItem(position);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        int idItem= item.getItemId();

                        if (idItem==R.id.popupEditar) {

                            Intent intent=new Intent(getContext(), TagEditActivity.class);
                            intent.putExtra("tag",tag);
                            startActivity(intent);

                        } else {
                            tagDAO.delete(tag.getIdTag());
                            //TODO fazer refresh
                            Toast.makeText(getContext(), "Excluído com sucesso", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                popup.show();

                return true;
            }

        };

    }


}
