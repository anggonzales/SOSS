package com.example.soss.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.soss.Model.ClsCategoria;
import com.example.soss.R;

import java.util.ArrayList;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    private LayoutInflater inflador;
    ArrayList<ClsCategoria> ListaCategoria;
    ArrayList<ClsCategoria> ListaCategoriaUsuario = new ArrayList<>();
    Context micontext;

    public CategoriaAdapter(Context context, ArrayList<ClsCategoria> datos) {
        this.ListaCategoria = datos;
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

        holder.Categorias.setText(ListaCategoria.get(i).getNombre());

    }

    @Override
    public int getItemCount() {
        return ListaCategoria.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox NombreCategoria;
        public TextView Categorias;

        ViewHolder(View itemView) {
            super(itemView);
            NombreCategoria = (CheckBox) itemView.findViewById(R.id.chkCategoria);
            Categorias = (TextView) itemView.findViewById(R.id.txtNombreCategoria);

        }
    }

    interface OnItemCheckListener {
        void onItemCheck(View v, int pos);
    }
}
