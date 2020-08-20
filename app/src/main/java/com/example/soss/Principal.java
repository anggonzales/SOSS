package com.example.soss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.soss.Adapters.SugerenciaAdapter;
import com.example.soss.Model.ClsEmpresa;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SugerenciaAdapter AdaptadorSugerencia;
    private ArrayList<ClsEmpresa> ListaEmpresa;
    private static final String PATH_SERVICIO = "Empresa";
    DatabaseReference reference;
    CardView cardEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.drawer_open, R.string. drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(
                R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* Listado de servicios sugeridos */
        cardEmpresa = findViewById(R.id.cardEmpresas);
        cardEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), PrincipalServicio.class));
            }
        });
       recyclerView = findViewById(R.id.rcvPopular);
        reference = FirebaseDatabase.getInstance().getReference(PATH_SERVICIO);
        ListaEmpresa = new ArrayList<>();
        AdaptadorSugerencia = new SugerenciaAdapter(this, ListaEmpresa);
        recyclerView.setAdapter(AdaptadorSugerencia);
        layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ListarEmpresas();

    }

    void ListarEmpresas() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ClsEmpresa empresa = dataSnapshot.getValue(ClsEmpresa.class);
                empresa.setIdEmpresa(dataSnapshot.getKey());

                if (!ListaEmpresa.contains(empresa)) {
                    if (empresa.getRatingTotal()>=3)
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_micuenta:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.activity_consulta_servicios:
                startActivity(new Intent(this, PrincipalServicio.class));
                break;
            case R.id.activity_pago:
                startActivity(new Intent(this, PrincipalPago.class));
                break;
            case R.id.activity_categoria:
                startActivity(new Intent(this, CategoriaUsuario.class));
                break;
            case R.id.nav_salir:
                finish();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }
}
