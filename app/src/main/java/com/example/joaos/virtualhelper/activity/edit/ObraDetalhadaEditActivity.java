package com.example.joaos.virtualhelper.activity.edit;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.dao.DatabaseHelper;
import com.example.joaos.virtualhelper.dao.ObraDAO;
import com.example.joaos.virtualhelper.model.Obra;
import com.example.joaos.virtualhelper.util.Constantes;
import com.example.joaos.virtualhelper.util.SingleChoiceClass;
import com.google.zxing.client.android.CaptureActivity;

import java.util.Arrays;
import java.util.List;

public class ObraDetalhadaEditActivity extends AppCompatActivity {

    private FloatingActionButton fbMain,fb1,fb2;
    private Animation FabOpen,FabClose,FabRClockWise,FabRantiClockWise;
    private boolean isOpen=false;
  /*  private CheckBox checkBox;
    private LinearLayout layoutTags;
    private AlertDialog dialog;
    private TextView tagCriar,textViewConteiner;
    private AlertDialog.Builder builder;
    private String[] tags = new String[]{// Boolean array for initial selected items
            "horror",
            "velhos",
            "sujos",
            "recentes",
            "capa dura"
    };
    private final boolean[] checkedTags = new boolean[]{
            false,
            false,
            false,
            false,
            false
    };
*/
    private ObraDAO obraDao;
    private SQLiteDatabase mDatabase;
    private EditText editTitulo, editAutor, editEditora, editDescricao, editISBN, editAnoPublicacao;
    private CheckBox emprestado;
    private ImageView imgCapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra_detalhada_edit);
        //setTitle(----); pegar nome da obra

        //dialogTags();


        imgCapa = (ImageView) findViewById(R.id.imageViewCapa);
        editTitulo = (EditText) findViewById(R.id.editTitulo);
        editAutor = (EditText) findViewById(R.id.editAutor);
        editEditora = (EditText) findViewById(R.id.editEditora);
        editDescricao = (EditText) findViewById(R.id.editDescricao);
        editISBN = (EditText) findViewById(R.id.editIsbn);
        editAnoPublicacao = (EditText) findViewById(R.id.editAno);
        emprestado = (CheckBox) findViewById(R.id.checkBoxEmprestado);

        mDatabase = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
        obraDao = new ObraDAO(mDatabase);

        //capturando o FAB e enviando sua animacao quando clicado

        fbMain= (FloatingActionButton) findViewById(R.id.fbMain);
        fb1= (FloatingActionButton) findViewById(R.id.fbTags);
        fb2= (FloatingActionButton) findViewById(R.id.fbContainer);
        FabOpen= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRClockWise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_cloclwise);
        FabRantiClockWise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);

        fbMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen){

                    fb2.startAnimation(FabClose);
                    fb1.startAnimation(FabClose);
                    fbMain.startAnimation(FabRantiClockWise);
                    fb1.setClickable(false);
                    fb2.setClickable(false);
                    isOpen=false;

                }else{
                    fb2.startAnimation(FabOpen);
                    fb1.startAnimation(FabOpen);
                    fbMain.startAnimation(FabRClockWise);
                    fb1.setClickable(true);
                    fb2.setClickable(true);
                    isOpen=true;

                }
            }
        });



    }
/*
    public void dialogTags(){

        builder = new AlertDialog.Builder(this);

        //dialog de multipla escolha
        final List<String> tagsList = Arrays.asList(tags);
        builder.setMultiChoiceItems(tags, checkedTags, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                // Update the current focused item's checked status
                checkedTags[which] = isChecked;

                // Get the current focused item
                String currentItem = tagsList.get(which);

                // Notify the current action
                Toast.makeText(getApplicationContext(),
                        currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setTitle("Tags");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for(int i=0;i<tagsList.size();i++){

                    if(checkedTags[i]==true){
                        tagCriar=new TextView(new ContextThemeWrapper(ObraDetalhadaEditActivity.this, R.style.tag_style));
                        tagCriar.setText(tagsList.get(i));
                        layoutTags.addView(tagCriar);
                    }
                }
            }
        });
        // Set the negative/no button click listener
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog= builder.create();

    }*/

    public void scannerIsbn(View v){

        //instanciando scanner
        Intent intent = new Intent(getApplicationContext(),CaptureActivity.class);
        intent.setAction("com.google.zxing.client.android.SCAN");
        intent.putExtra("SAVE_HISTORY", false);
        startActivityForResult(intent, 0);

    }
    public void obraDetalhadaEditConcluir (View v){

        // if id==null ..cadastrar else atualizar
        Obra obra=new Obra();
        obra.setTitulo(editTitulo.getText().toString());
        obra.setAutor(editAutor.getText().toString());
        obra.setAnoPublicacao((Integer.parseInt(editAnoPublicacao.getText().toString())));
        obra.setDescricao(editDescricao.getText().toString());
        obra.setEditora(editEditora.getText().toString());
        obra.setEmprestado(emprestado.isChecked());
        obra.setIsbn(editISBN.getText().toString());

        obraDao.insert(obra);
        finish();
    }

    public void obraDetalhadaEditCancelar(View v){ finish();}

    public void adcFoto(View v){

        //instanciando camera
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Constantes.CAMERA_REQUEST);

    }

    public void adicionarContainers(View v){

        SingleChoiceClass dialogContainers=new SingleChoiceClass();
        dialogContainers.show(getSupportFragmentManager(),"dialogContainer");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){

            case 0:

                if (resultCode == RESULT_OK) {

                    //capturando o resultado do scanner
                    String contents = data.getStringExtra("SCAN_RESULT");
                    editISBN.setText(contents);
                    Toast.makeText(this, "Ação realizada com sucesso!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Falha ao realizar esta ação!", Toast.LENGTH_SHORT).show();
                }
                break;

            case Constantes.CAMERA_REQUEST:

                if (resultCode == RESULT_OK) {

                    //setando imageview com a imagem pega pela camera
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imgCapa.setImageBitmap(photo);
                    Toast.makeText(this, "Ação realizada com sucesso!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(this, "Falha ao realizar esta ação!", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

}
