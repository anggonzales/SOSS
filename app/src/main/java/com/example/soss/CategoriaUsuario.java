package com.example.soss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.soss.Adapters.CategoriaAdapter;
import com.example.soss.Clases.ClsCategoria;
import com.example.soss.Clases.ClsCategoriaUsuario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CategoriaUsuario extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CategoriaAdapter adaptador;
    private ArrayList<ClsCategoria> misdatos;
    private ClsCategoria[] categorias;
    private static final String PATH_CATEGORIA = "Categoria";
    DatabaseReference reference;
    ArrayList<String> ServiciosCheck = new ArrayList<String>();
    private Button check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_usuario);
        recyclerView = findViewById(R.id.rcvListaCategorias);
        check = (Button) findViewById(R.id.btnGuardar);
        reference = FirebaseDatabase.getInstance().getReference(PATH_CATEGORIA);
        misdatos = new ArrayList<>();
        adaptador = new CategoriaAdapter(this, misdatos);
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ListarCategorias();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ClsCategoria cat : misdatos){

                }
            }
        });
    }

    void ListarCategorias() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsCategoria categoria = dataSnapshot.getValue(ClsCategoria.class);
                categoria.setId(dataSnapshot.getKey());
                if (!misdatos.contains(categoria)) {
                    misdatos.add(categoria);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsCategoria categoria = dataSnapshot.getValue(ClsCategoria.class);
                categoria.setId(dataSnapshot.getKey());
                int index = -1;
                for (ClsCategoria cat : misdatos) {
                    Log.i("iteracion", cat.getId() + " = " + cat.getId());
                    index++;
                    if (cat.getId().equals(cat.getId())) {
                        misdatos.set(index, cat);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ClsCategoria categoria = dataSnapshot.getValue(ClsCategoria.class);
                categoria.setId(dataSnapshot.getKey());
                int index = -1;
                for (ClsCategoria cat : misdatos) {
                    Log.i("iteracion", cat.getId() + " = " + cat.getId());
                    index++;
                    if (cat.getId().equals(cat.getId())) {
                        misdatos.set(index, cat);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ViewPrincipal(View view) {
        Intent intent = new Intent(this, Principal.class);
        startActivity(intent);
        finish();
    }
}
