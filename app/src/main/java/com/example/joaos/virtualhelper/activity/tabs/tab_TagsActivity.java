package com.example.joaos.virtualhelper.activity.tabs;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.dao.DatabaseHelper;
import com.example.joaos.virtualhelper.dao.TagDAO;
import com.example.joaos.virtualhelper.model.Tag;
import com.example.joaos.virtualhelper.util.adapter.AdapterListViewTags;
import com.example.joaos.virtualhelper.util.itemListView.ItemListViewTags;

import java.util.ArrayList;
import java.util.List;



public class tab_TagsActivity extends Fragment {

    private ListView listView;
    private AdapterListViewTags adapterListViewTags;
    private TagDAO tagDAO;
    private SQLiteDatabase mDatabase;
    private List<Tag> itens;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_activity_tags, container, false);

        mDatabase = DatabaseHelper.newInstance(getActivity());
        tagDAO= new TagDAO(mDatabase);
        listView = (ListView) rootView.findViewById(R.id.listaTags);

        //createListView();

        return rootView;
    }

    public void createListView(){

        itens = tagDAO.getListaTags();

        adapterListViewTags = new AdapterListViewTags(getActivity(), itens);
        listView.setAdapter(adapterListViewTags);

    }

    


}
