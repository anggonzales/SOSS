package com.example.soss.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.soss.Model.ClsCategoria;

import java.util.List;

public class ClsCategoriaAdapter extends ArrayAdapter<ClsCategoria> {

    private Context context;
    private List<ClsCategoria> values;

    public ClsCategoriaAdapter(@NonNull Context context, int resource, List<ClsCategoria> values) {
        super(context, resource);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public ClsCategoria getItem(int position) {
        return  values.get(position);
    }


    public long getItemId(int position) {return position;}

}
