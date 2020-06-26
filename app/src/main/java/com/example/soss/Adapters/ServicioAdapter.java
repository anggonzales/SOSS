package com.example.soss.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.soss.Clases.ClsServicio;
import com.example.soss.DetalleServicio;
import com.example.soss.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ViewHolder> {

    private LayoutInflater inflador;
    ArrayList<ClsServicio> datos;
    Context micontext;

    public ServicioAdapter(Context context, ArrayList<ClsServicio> datos) {
        this.datos = datos;
        micontext = context;
        inflador = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.item_principal_servicio, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        //ClsServicio objServicio = datos.get(i);
        holder.NombreServicio.setText(datos.get(i).getNombre());
        //String Inicial = objServicio.getNombre().toUpperCase().substring(0, 1);
        //holder.InicialLogo.setText(Inicial);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(micontext, DetalleServicio.class);
                //intent.putExtra("id", datos.get(i).getId());
                intent.putExtra("Nombre", datos.get(i).getNombre());
                micontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView NombreServicio;
        public TextView InicialLogo;
        public Button btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            NombreServicio = (TextView) itemView.findViewById(R.id.txtNombre);
            InicialLogo = (TextView) itemView.findViewById(R.id.txtLogo);
        }
    }

}
