package com.jerp.carritocuadro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jerp.carritocuadro.entity.Adapter;
import com.jerp.carritocuadro.entity.Cuadro;

import java.util.ArrayList;
import java.util.List;

public class CatalogoActivity extends AppCompatActivity {

    RecyclerView recyclerv;
    List<Cuadro> cuadros;
    Adapter adapter;

    private DatabaseReference CuadroBDReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        recyclerv = (RecyclerView) findViewById(R.id.recycler);
        recyclerv.setLayoutManager(new LinearLayoutManager(this));

        cuadros = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapter = new Adapter(cuadros);

        recyclerv.setAdapter(adapter);

        database.getReference().child("Cuadro_Arte").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cuadros.removeAll(cuadros);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Cuadro cuadro = snapshot.getValue(Cuadro.class);
                    cuadros.add(cuadro);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
