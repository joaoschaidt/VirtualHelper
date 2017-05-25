package com.example.joaos.virtualhelper.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.model.Obra;

import java.sql.Blob;

public class ObraDetalhadaActivity extends AppCompatActivity {

    private Obra obra;
    private ImageView capa;
    private TextView tvTitulo, tvEditora, tvAutor, tvIsbn, tvAno, tvDescricao;
    private CheckBox emprestado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra_detalhada);
        //setTitle(----); pegar nome da obra

        tvTitulo= (TextView) findViewById(R.id.TextViewTitulo);
        tvAno= (TextView) findViewById(R.id.TextViewAno);
        tvAutor= (TextView) findViewById(R.id.TextViewAutor);
        tvDescricao= (TextView) findViewById(R.id.TextViewBreveDesc);
        tvEditora= (TextView) findViewById(R.id.TextViewEditora);
        tvIsbn= (TextView) findViewById(R.id.TextViewIsbn);
        emprestado= (CheckBox) findViewById(R.id.CheckBoxEmprestado);
        capa= (ImageView) findViewById(R.id.ImageViewCapa);

        Bundle parametros = getIntent().getExtras();

        if(parametros != null) {

            obra= (Obra) parametros.getSerializable("obra");
            preencheCampos(obra);
        }


    }


    public void obraDetalhadaEditar(View v){

        //chamar obraDetalhadaEdit prenchendo os edits


    }

    public void obraDetalhadaVoltar(View v){ finish(); }


    public void preencheCampos(Obra obra){

        tvTitulo.setText(obra.getTitulo());
        tvEditora.setText(obra.getEditora());
        tvIsbn.setText(obra.getIsbn());
        tvDescricao.setText(obra.getDescricao());
        tvAno.setText(String.valueOf(obra.getAnoPublicacao()));
        tvAutor.setText(obra.getAutor());
        emprestado.setChecked(obra.isEmprestado());
/*
        Bitmap bitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmap = BitmapFactory.decodeByteArray(obra.getCapa(), 0, obra.getCapa().length, options);
        capa.setImageBitmap( bitmap);*/
    }
}
