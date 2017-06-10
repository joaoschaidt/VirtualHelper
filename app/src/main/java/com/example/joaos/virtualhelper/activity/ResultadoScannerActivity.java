package com.example.joaos.virtualhelper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.model.Obra;
import com.example.joaos.virtualhelper.util.Scanner;
import com.example.joaos.virtualhelper.util.adapter.AdapterListViewObra;

import java.util.List;

public class ResultadoScannerActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterListViewObra adapterListViewObra;
    private List<Obra> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_scanner);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView= (ListView) findViewById(R.id.listaScanner);

        Bundle parametros=getIntent().getExtras();

        Scanner scanner=new Scanner(this);
        lista=scanner.pesquisar(parametros.getString("isbn"));

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        adapterListViewObra = new AdapterListViewObra(this, lista);
        listView.setAdapter(adapterListViewObra);

        listView.setOnItemClickListener(cliqueCurto());
    }

//click curto -instanciar obraedit com as informacoes

    public AdapterView.OnItemClickListener cliqueCurto() {

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Obra obra = (Obra) adapterListViewObra.getItem(position);

                Intent intent = new Intent(ResultadoScannerActivity.this, ObraDetalhadaActivity.class);
                intent.putExtra("obra",obra);
                startActivity(intent);
            }
        };


    }

}
