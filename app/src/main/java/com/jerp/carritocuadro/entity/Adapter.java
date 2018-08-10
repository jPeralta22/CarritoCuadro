package com.jerp.carritocuadro.entity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jerp.carritocuadro.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.CuadroviewHolder> {

    private List<Cuadro> cuadros;

    public Adapter(List<Cuadro> cuadros){this.cuadros = cuadros;}

    @Override
    public Adapter.CuadroviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carrito_recycler,parent,false);
        CuadroviewHolder holder = new CuadroviewHolder (v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CuadroviewHolder holder, int position) {

        Cuadro cuadro = cuadros.get(position);
        holder.txtAno.setText(cuadro.getAno_realizacion());
        holder.txtDescripcion.setText(cuadro.getDescripcion());
        holder.txtGenero.setText(cuadro.getGenero());
        holder.txtTituloCuadro.setText(cuadro.getTitulo_cuadro());
        holder.txtArtista.setText(cuadro.getArtista());
        holder.txtPrecio.setText(String.valueOf(cuadro.getPrecio()));
        holder.setOnClickListener();
    }

    @Override
    public int getItemCount() { return cuadros.size();}

    public static class CuadroviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private DatabaseReference CuadroBDReference;

        TextView txtTituloCuadro, txtArtista, txtDescripcion, txtAno, txtGenero, txtPrecio;
        Button agregar;

        public CuadroviewHolder(View itemView){
            super(itemView);

            agregar = itemView.findViewById(R.id.btnagregar);

            txtTituloCuadro = itemView.findViewById(R.id.txttitulo_cuadro);
            txtArtista = itemView.findViewById(R.id.txtartista);
            txtDescripcion = itemView.findViewById(R.id.txtdescripcion);
            txtAno = itemView.findViewById(R.id.txtano);
            txtGenero = itemView.findViewById(R.id.txtgenero);
            txtPrecio = itemView.findViewById(R.id.txtprecio);
        }

        public void setOnClickListener(){
            agregar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnagregar:

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    CuadroBDReference = database.getReference();
                    String ColeccionCarrito ="Carrito_Compra";

                    Carrito cuadro = new Carrito(txtTituloCuadro.getText().toString(), txtArtista.getText().toString(),
                            txtDescripcion.getText().toString(), txtAno.getText().toString(), txtGenero.getText().toString(), Double.valueOf(txtPrecio.getText().toString()));

                    String cadena = "Cuadro" + txtTituloCuadro.getText().toString().substring(0, txtTituloCuadro.getText().toString().indexOf(' ', 0)-1);


                    CuadroBDReference.child(ColeccionCarrito).child(cadena).setValue(cuadro);
            }

        }
    }
}
