package com.example.joaos.virtualhelper.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.dao.ContainerDAO;
import com.example.joaos.virtualhelper.helpers.DatabaseHelper;
import com.example.joaos.virtualhelper.model.Container;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class ContainerListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ContainerDAO containerDAO;
    private ContainerAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    //private FloatingActionButton fbMain;
    //private Animation FabOpen,FabClose;
    //private boolean isOpen=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_container, container, false);
        mDatabase = DatabaseHelper.newInstance(getActivity());
        containerDAO = new ContainerDAO(mDatabase);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.container_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        primeiroAcesso();
        refresh();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private class ContainerHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private Container mContainer;
        private ImageView imgContainer;
        private TextView txtNome, txtTotalObras, txtDtModificacao, txtLocal;

        public ContainerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_container, parent, false));
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            imgContainer = (ImageView) itemView.findViewById(R.id.imgContainer);
            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtTotalObras = (TextView) itemView.findViewById(R.id.txtTotalObras);
            txtDtModificacao = (TextView) itemView.findViewById(R.id.txtDtModificacao);
            txtLocal = (TextView) itemView.findViewById(R.id.txtLocal);
        }

        public void bind(Container c) {
            mContainer = c;
            imgContainer.setImageResource(mContainer.getContainerTipos().getTipoIcone());
            txtNome.setText(mContainer.getNomeContainer());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            txtDtModificacao.setText(df.format(mContainer.getUltimaModificacao()));
            //txtTotalObras.setText(mContainer.getTotalObras()); npe
            txtLocal.setText(mContainer.getLocalContainer());
        }

        @Override
        public void onClick(View v) {
            //launch containerfragment + dados sem editar
        }

        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setItems(R.array.dialog_lista, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch(which) {
                        case 0: Intent intent = new Intent(getActivity(), CadastroPagerActivity.class);
                                intent.putExtra("container_id", mContainer.getIdContainer());
                                startActivity(intent);
                                //editar CPagerActivity pra seetar valores via intent etc
                                break;
                        case 1: containerDAO.delete(mContainer.getIdContainer());
                                refresh();
                                break;
                    }
                }
            });
            dialog.show();
            return true;
        }
    }

    private class ContainerAdapter extends RecyclerView.Adapter<ContainerHolder> {
         private List<Container> mContainerList;

         public ContainerAdapter(List<Container> containerList) {
            mContainerList = containerList;
         }

         @Override
         public ContainerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ContainerHolder(layoutInflater, parent);
         }

         @Override
         public void onBindViewHolder(ContainerHolder holder, int position) {
             Container c = mContainerList.get(position);
             holder.bind(c);
         }

         @Override
         public int getItemCount() {
             return mContainerList.size();
         }

         public void setContainerList(List<Container> itens) {
             mContainerList = itens;
         }
    }

    private void refresh() {
        List<Container> itens = containerDAO.getAll(); //get da biblioteca especifica
        if (mAdapter == null) {
            mAdapter = new ContainerAdapter(itens);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setContainerList(itens);
            mAdapter.notifyDataSetChanged();
        }

    }

    private void primeiroAcesso() {
        List<Container> itens = containerDAO.getAll();
        if(itens.isEmpty()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setIcon(R.drawable.container_estante_icon);
            alert.setTitle(R.string.bem_vindo);
            alert.setMessage(R.string.primeiro_acesso);
            //alert.setView(R.layout.dialog_bem_vindo);
            alert.setPositiveButton(R.string.seguinte, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getActivity(), CadastroPagerActivity.class));

                }
            });
            alert.show();
        }
    }
}

