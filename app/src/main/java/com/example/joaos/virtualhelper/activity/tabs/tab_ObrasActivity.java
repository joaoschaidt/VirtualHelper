package com.example.joaos.virtualhelper.activity.tabs;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.activity.ObraDetalhadaActivity;
import com.example.joaos.virtualhelper.dao.DatabaseHelper;
import com.example.joaos.virtualhelper.dao.ObraDAO;
import com.example.joaos.virtualhelper.util.adapter.AdapterListViewObra;
import com.example.joaos.virtualhelper.model.Obra;
import java.util.List;


public class tab_ObrasActivity extends Fragment {

    private ListView listView;
    private ObraDAO obraDao;
    private SQLiteDatabase mDatabase;
    private AdapterListViewObra adapterListView;
    private List<Obra> itens;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_activity_obras, container, false);
        mDatabase = DatabaseHelper.newInstance(getActivity());
        obraDao = new ObraDAO(mDatabase);
        listView = (ListView) rootView.findViewById(R.id.listaObras);
        createListView();

        listView.setOnItemClickListener(cliqueCurto());
        return rootView;
    }


    public void createListView() {

        itens = obraDao.getListaObras();

        adapterListView = new AdapterListViewObra(getActivity(), itens);
        listView.setAdapter(adapterListView);
    }

    public AdapterView.OnItemClickListener cliqueCurto() {

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Obra obra = (Obra) adapterListView.getItem(position);

                Intent intent = new Intent(getActivity(), ObraDetalhadaActivity.class);
                intent.putExtra("obra",obra);
                startActivity(intent);

            }
        };
    }


}
