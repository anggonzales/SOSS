package com.example.soss.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.soss.DetalleEmpresa;
import com.example.soss.Model.ClsEmpresa;
import com.example.soss.DetalleServicio;
import com.example.soss.R;
import com.example.soss.UbicacionServicio;

import java.util.ArrayList;

public class EmpresaAdapter extends RecyclerView.Adapter<EmpresaAdapter.ViewHolder> {

    private LayoutInflater inflador;
    ArrayList<ClsEmpresa> ListaEmpresas;
    Context micontext;
    ViewHolder viewHolder;

    public EmpresaAdapter(Context context, ArrayList<ClsEmpresa> datos) {
        this.ListaEmpresas = datos;
        micontext = context;
        inflador = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.item_principal_servicio, parent, false);
        return new ViewHolder(v);
    }

    public View getView (final int position, View convertView, ViewGroup parent){

        if(convertView == null) {

            convertView = inflador.inflate(R.layout.item_principal_servicio, parent, false);
            viewHolder.NombreEmpresa = (TextView) convertView.findViewById(R.id.txtNombre);
            /*viewHolder.DescripcionEmpresa =  (TextView) convertView.findViewById(R.id.txtDescripcion);
            viewHolder.CalificacionEmpresa =(TextView) convertView.findViewById(R.id.idPuntajetexto);*/

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.NombreEmpresa.setText(ListaEmpresas.get(position).getNombre());
      /*  viewHolder.DescripcionEmpresa.setText(ListaEmpresas.get(position).getDescripcion());
        viewHolder.CalificacionEmpresa.setText(ListaEmpresas.get(position).getRatingTotal().toString());*/
        return convertView;
    }

    public  void filtrar(ArrayList<ClsEmpresa> filtro){
        this.ListaEmpresas = filtro;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {

        holder.NombreEmpresa.setText(ListaEmpresas.get(i).getNombre());
        //holder.DescripcionEmpresa.setText(ListaEmpresas.get(i).getDescripcion());
        holder.CalificacionEmpresa.setText(ListaEmpresas.get(i).getRatingTotal().toString());
        holder.rbEmpresa.setRating((float) Double.parseDouble(ListaEmpresas.get(i).getRatingTotal().toString()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(micontext, DetalleEmpresa.class);
                //intent.putExtra("id", datos.get(i).getId());
                intent.putExtra("IdEmpresa", ListaEmpresas.get(i).getIdEmpresa());
                intent.putExtra("Nombre", ListaEmpresas.get(i).getNombre());
                intent.putExtra("Latitud", ListaEmpresas.get(i).getLatitud());
                intent.putExtra("Longitud", ListaEmpresas.get(i).getLongitud());
                intent.putExtra("Calificacion", ListaEmpresas.get(i).getRatingTotal());
                intent.putExtra("Celular", ListaEmpresas.get(i).getCelular());
                micontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListaEmpresas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView NombreEmpresa;
        public TextView DescripcionEmpresa;
        public TextView CalificacionEmpresa;
        public TextView InicialLogo;
        public RatingBar rbEmpresa;

        ViewHolder(View itemView) {
            super(itemView);
            NombreEmpresa = (TextView) itemView.findViewById(R.id.txtNombre);
          //  DescripcionEmpresa = (TextView) itemView.findViewById(R.id.txtDescripcion);
            CalificacionEmpresa =(TextView) itemView.findViewById(R.id.idPuntajetexto);
            InicialLogo = (TextView) itemView.findViewById(R.id.txtLogo);
            rbEmpresa = (RatingBar) itemView.findViewById(R.id.item_empresa_ratingbar);
        }
    }
}
