package com.jerp.carritocuadro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jerp.carritocuadro.entity.Cuadro;
import com.jerp.carritocuadro.repository.CuadroRepository;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements CuadroRepository {

    TextInputEditText titulo, artista, descripcion, ano, genero, precio ;
    Button guardar, eliminar, foto;
    ImageView imagen;
    Cuadro cuadro;

    private FirebaseDatabase CuadroBD;
    private DatabaseReference CuadroBDReference;
    private StorageReference storage;
    private ProgressDialog progress;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            progress.setMessage("Subiendo Imagen");
            progress.show();

            Uri uri = data.getData();

            StorageReference filepath = storage.child("Fotos de Cuadros").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    progress.dismiss();

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    Picasso.with(MainActivity.this).load(downloadUri).fit().centerCrop().into (imagen);

                    Toast.makeText(MainActivity.this, "Foto Subida...", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance().getReference();

        titulo = findViewById(R.id.ettitulo_cuadro);
        artista = findViewById(R.id.etartista);
        descripcion = findViewById(R.id.etdescripcion);
        ano = findViewById(R.id.etano);
        genero = findViewById(R.id.etgenero);
        precio = findViewById(R.id.etprecio);
        imagen = findViewById(R.id.ivimagen);

        FloatingActionButton fab = findViewById(R.id.fab);

        foto = findViewById(R.id.btnfoto);
        guardar = findViewById(R.id.btnguardar);
        eliminar = findViewById(R.id.btneliminar);

        guardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                create();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        foto = findViewById(R.id.btnfoto);
        foto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CatalogoActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void create() {
        CuadroBD = FirebaseDatabase.getInstance();
        CuadroBDReference = CuadroBD.getReference();

        String cadena = "Cuadro" + titulo.getText().toString().substring(0, titulo.getText().toString().indexOf(' ', 0)-1);

        cuadro = new Cuadro(titulo.getText().toString(), artista.getText().toString(),
                descripcion.getText().toString(), ano.getText().toString(), genero.getText().toString(), Double.valueOf(precio.getText().toString()));

        CuadroBDReference.child("Cuadro_Arte").child(cadena).setValue(cuadro);

    }

    @Override
    public void read() {}

    @Override
    public void update() {}


    @Override
    public void delete(){
        CuadroBD = FirebaseDatabase.getInstance();
        CuadroBDReference = CuadroBD.getReference();

        String cadena = "Cuadro" + titulo.getText().toString().substring(0, titulo.getText().toString().indexOf(' ', 0)-1);

        CuadroBDReference.child("Cuadro_Arte").child(cadena).removeValue();
    }
}
