package com.example.joaos.virtualhelper.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.joaos.virtualhelper.R;

public class ResultadoPesquisaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_pesquisa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }//pesquisa por autor ou titulo

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
