package com.example.soss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.soss.Adapters.ClsCategoriaAdapter;
import com.example.soss.Adapters.EmpresaAdapter;
import com.example.soss.Model.ClsCategoria;
import com.example.soss.Model.ClsEmpresa;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PrincipalServicio extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EmpresaAdapter AdaptadorEmpresa;
    private ClsCategoriaAdapter AdaptadorCategoria;
    private ArrayList<ClsEmpresa> ListaEmpresa;
    private static final String PATH_SERVICIO = "Empresa";
    private static final String PATH_CATEGORIA = "Categoria";
    DatabaseReference reference;
    DatabaseReference referenceCategoria;
    private EditText etbuscarnombre;
    private Spinner spnCategoria;
    String categoriaSeleccionada = "";
    Query consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_servicio);

        recyclerView = findViewById(R.id.rcvListaServicios);

        ListaEmpresa = new ArrayList<>();

        AdaptadorEmpresa = new EmpresaAdapter(PrincipalServicio.this, ListaEmpresa);
        recyclerView.setAdapter(AdaptadorEmpresa);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        etbuscarnombre = (EditText) findViewById(R.id.edtBusqueda);
        spnCategoria = (Spinner) findViewById(R.id.spnCategoria);

        reference = FirebaseDatabase.getInstance().getReference(PATH_SERVICIO);
        referenceCategoria = FirebaseDatabase.getInstance().getReference(PATH_CATEGORIA);

      //  spnCategoria = LayoutInflater.from(this).inflate(R.layout.item_spinner,);
        ListarCombo();


        etbuscarnombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AdaptadorEmpresa = new EmpresaAdapter(PrincipalServicio.this, ListaEmpresa);
                recyclerView.setAdapter(AdaptadorEmpresa);
                filtrar(s.toString());
            }
        });
    }

    private void filtrar(String texto) {
        ArrayList<ClsEmpresa> filtradatos = new ArrayList<>();

        for(ClsEmpresa item : ListaEmpresa){
            if (item.getNombre().contains(texto)){
                filtradatos.add(item);
            }
            AdaptadorEmpresa.filtrar(filtradatos);
        }
    }

    public void ListarCombo() {
        final List<ClsCategoria> ListaCategorias = new ArrayList<>();
        referenceCategoria.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ClsCategoria categoria = snapshot.getValue(ClsCategoria.class);
                        ListaCategorias.add(categoria);
                    }
                }

                AdaptadorCategoria = new ClsCategoriaAdapter(PrincipalServicio.this, android.R.layout.simple_dropdown_item_1line, ListaCategorias);

                spnCategoria.setAdapter(AdaptadorCategoria);

                spnCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        categoriaSeleccionada = adapterView.getItemAtPosition(i).toString();
                        String IdCategoria = ListaCategorias.get(i).getIdCategoria();
                        Log.e("idcategoria", IdCategoria);

                        consulta = FirebaseDatabase.getInstance().getReference("Empresa")
                                .orderByChild("IdCategoria")
                                .equalTo(IdCategoria);

                        if (ListaEmpresa.size() > 0) {
                            ListaEmpresa.clear();
                        } else {
                            AdaptadorEmpresa = new EmpresaAdapter(PrincipalServicio.this, ListaEmpresa);
                            recyclerView.setAdapter(AdaptadorEmpresa);
                            ListarEmpresasLoad();
                        }

                        ListarEmpresas();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void ListarEmpresas() {
        consulta.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsEmpresa empresa = dataSnapshot.getValue(ClsEmpresa.class);
                empresa.setIdEmpresa(dataSnapshot.getKey());
                if (!ListaEmpresa.contains(empresa)) {
                    ListaEmpresa.add(empresa);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsEmpresa empresa = dataSnapshot.getValue(ClsEmpresa.class);
                empresa.setIdEmpresa(dataSnapshot.getKey());
                int index = -1;
                for (ClsEmpresa objempresa : ListaEmpresa) {
                    Log.i("iteracion", objempresa.getIdEmpresa() + " = " + empresa.getIdEmpresa());
                    index++;
                    if (objempresa.getIdEmpresa().equals(empresa.getIdEmpresa())) {
                        ListaEmpresa.set(index, empresa);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ClsEmpresa empresa = dataSnapshot.getValue(ClsEmpresa.class);
                empresa.setIdEmpresa(dataSnapshot.getKey());
                int index = -1;
                for (ClsEmpresa objempresa : ListaEmpresa) {
                    Log.i("iteracion", objempresa.getIdEmpresa() + " = " + empresa.getIdEmpresa());
                    index++;
                    if (objempresa.getIdEmpresa().equals(empresa.getIdEmpresa())) {
                        ListaEmpresa.remove(index);
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

    public void ListarEmpresasLoad() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsEmpresa empresa = dataSnapshot.getValue(ClsEmpresa.class);
                empresa.setIdEmpresa(dataSnapshot.getKey());
                if (!ListaEmpresa.contains(empresa)) {
                    ListaEmpresa.add(empresa);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsEmpresa empresa = dataSnapshot.getValue(ClsEmpresa.class);
                empresa.setIdEmpresa(dataSnapshot.getKey());
                int index = -1;
                for (ClsEmpresa objempresa : ListaEmpresa) {
                    Log.i("iteracion", objempresa.getIdEmpresa() + " = " + empresa.getIdEmpresa());
                    index++;
                    if (objempresa.getIdEmpresa().equals(empresa.getIdEmpresa())) {
                        ListaEmpresa.set(index, empresa);
                        break;
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ClsEmpresa empresa = dataSnapshot.getValue(ClsEmpresa.class);
                empresa.setIdEmpresa(dataSnapshot.getKey());
                int index = -1;
                for (ClsEmpresa objempresa : ListaEmpresa) {
                    Log.i("iteracion", objempresa.getIdEmpresa() + " = " + empresa.getIdEmpresa());
                    index++;
                    if (objempresa.getIdEmpresa().equals(empresa.getIdEmpresa())) {
                        ListaEmpresa.remove(index);
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
