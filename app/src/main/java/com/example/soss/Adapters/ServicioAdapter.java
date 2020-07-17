package com.example.soss.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.soss.Clases.ClsServicio;
import com.example.soss.R;

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
        View v = inflador.inflate(R.layout.item_detalle_servicio, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        holder.NombreServicio.setText(datos.get(i).getNombre());
        holder.Precio.setText("Costo : S/ " + datos.get(i).getPrecio());
        holder.Pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView NombreServicio;
        public TextView Precio;
        public Button Pagar;

        ViewHolder(View itemView) {
            super(itemView);
            NombreServicio = (TextView) itemView.findViewById(R.id.txtNombre);
            Precio = (TextView) itemView.findViewById(R.id.txtPrecio);
            Pagar = (Button) itemView.findViewById(R.id.btnPagar);
        }
    }
}
