package com.example.soss.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.soss.Clases.ClsCategoria;
import com.example.soss.R;

import java.util.ArrayList;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    private LayoutInflater inflador;
    ArrayList<ClsCategoria> datos;
    Context micontext;

    public CategoriaAdapter(Context context, ArrayList<ClsCategoria> datos) {
        this.datos = datos;
        micontext = context;
        inflador = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.item_categoria_usuario, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoriaAdapter.ViewHolder holder, final int i) {
        //ClsServicio objServicio = datos.get(i);
        holder.NombreCategoria.setText(datos.get(i).getNombre());
        //String Inicial = objServicio.getNombre().toUpperCase().substring(0, 1);
        //holder.InicialLogo.setText(Inicial);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(micontext, UbicacionServicio.class);
                //intent.putExtra("id", datos.get(i).getId());
                intent.putExtra("Nombre", datos.get(i).getNombre());
                intent.putExtra("Latitud", datos.get(i).getLatitud());
                intent.putExtra("Longitud", datos.get(i).getLongitud());
                micontext.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox NombreCategoria;

        ViewHolder(View itemView) {
            super(itemView);
            NombreCategoria = (CheckBox) itemView.findViewById(R.id.chkCategoria);
        }
    }
}
