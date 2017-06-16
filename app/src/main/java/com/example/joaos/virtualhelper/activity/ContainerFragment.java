package com.example.joaos.virtualhelper.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.model.ContainerTipos;


/**
 * Created by Didi on 5/23/2017.
 */

public class ContainerFragment extends Fragment {
    private ContainerTipos tipo;

    public static ContainerFragment newInstance(ContainerTipos c) {
        Bundle args = new Bundle();
        args.putSerializable("tipo", c);
        ContainerFragment fragment = new ContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tipo = (ContainerTipos) getArguments().getSerializable("tipo");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_container,container, false);
/*
        ImageView icone = (ImageView) v.findViewById(R.id.iconeContainer);
        TextView tipoNome = (TextView) v.findViewById(R.id.tipoContainer);
        EditText localContainer = (EditText) v.findViewById(R.id.editLocalContainer);
        EditText nomeContainer = (EditText) v.findViewById(R.id.editNomeContainer);
        TextView totalObras = (TextView) v.findViewById(R.id.txtTotalObras);
        TextView ultimaModificacao = (TextView) v.findViewById(R.id.ultimaModificacao);

        icone.setImageResource(tipo.getTipoIcone());
        tipoNome.setText(tipo.getTipoNome());
        totalObras.setText("0 obras");
        ultimaModificacao.setText(new Date().toString());
*/


        ImageView icone = (ImageView) v.findViewById(R.id.iconeContainer);
        TextView tipoNome = (TextView) v.findViewById(R.id.tipoContainer);
        icone.setImageResource(tipo.getTipoIcone());
        tipoNome.setText(tipo.getTipoNome());


        return v;
    }
}
