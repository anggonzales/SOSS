package com.example.soss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.soss.Adapters.EmpresaAdapter;
import com.example.soss.Clases.ClsEmpresa;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PrincipalServicio extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EmpresaAdapter adaptador;
    private ArrayList<ClsEmpresa> misdatos;
    private static final String PATH_SERVICIO = "Empresa";
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_servicio);

        recyclerView = findViewById(R.id.rcvListaServicios);
        reference = FirebaseDatabase.getInstance().getReference(PATH_SERVICIO);
        misdatos = new ArrayList<>();
        adaptador = new EmpresaAdapter(this, misdatos);
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ListarEmpresa();
    }

    void ListarEmpresa() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsEmpresa servicio = dataSnapshot.getValue(ClsEmpresa.class);
                servicio.setIdEmpresa(dataSnapshot.getKey());
                if (!misdatos.contains(servicio)) {
                    misdatos.add(servicio);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsEmpresa servicio = dataSnapshot.getValue(ClsEmpresa.class);
                servicio.setIdEmpresa(dataSnapshot.getKey());
                int index = -1;
                for (ClsEmpresa servi : misdatos) {
                    Log.i("iteracion", servi.getIdEmpresa() + " = " + servicio.getIdEmpresa());
                    index++;
                    if (servi.getIdEmpresa().equals(servicio.getIdEmpresa())) {
                        misdatos.set(index, servicio);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ClsEmpresa servicio = dataSnapshot.getValue(ClsEmpresa.class);
                servicio.setIdEmpresa(dataSnapshot.getKey());
                int index = -1;
                for (ClsEmpresa servi : misdatos) {
                    Log.i("iteracion", servi.getIdEmpresa() + " = " + servicio.getIdEmpresa());
                    index++;
                    if (servi.getIdEmpresa().equals(servicio.getIdEmpresa())) {
                        misdatos.remove(index);
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
}
