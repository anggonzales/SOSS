package com.example.soss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.soss.Adapters.ServicioAdapter;
import com.example.soss.Clases.ClsServicio;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrincipalServicio extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ServicioAdapter adaptador;
    private ArrayList<ClsServicio> misdatos;
    private static final String PATH_SERVICIO = "Servicio";
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_servicio);

        recyclerView = findViewById(R.id.rcvListaServicios);
        reference = FirebaseDatabase.getInstance().getReference(PATH_SERVICIO);
        misdatos = new ArrayList<>();
        adaptador = new ServicioAdapter(this, misdatos);
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        AddItems();
    }

    void AddItems() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsServicio servicio = dataSnapshot.getValue(ClsServicio.class);
                servicio.setId(dataSnapshot.getKey());
                if (!misdatos.contains(servicio)) {
                    misdatos.add(servicio);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsServicio servicio = dataSnapshot.getValue(ClsServicio.class);
                servicio.setId(dataSnapshot.getKey());
                int index = -1;
                for (ClsServicio servi : misdatos) {
                    Log.i("iteracion", servi.getId() + " = " + servicio.getId());
                    index++;
                    if (servi.getId().equals(servicio.getId())) {
                        misdatos.set(index, servicio);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ClsServicio servicio = dataSnapshot.getValue(ClsServicio.class);
                servicio.setId(dataSnapshot.getKey());
                int index = -1;
                for (ClsServicio servi : misdatos) {
                    Log.i("iteracion", servi.getId() + " = " + servicio.getId());
                    index++;
                    if (servi.getId().equals(servicio.getId())) {
                        misdatos.remove(index);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Toast.makeText(PrincipalServicio.this, "Se movio el producto", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PrincipalServicio.this, "Transaccion cancelada", Toast.LENGTH_LONG).show();
            }
        });
    }
}
