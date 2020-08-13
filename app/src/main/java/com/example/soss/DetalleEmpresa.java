package com.example.soss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soss.Model.ClsEmpresa;
import com.example.soss.Model.ClsRating;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class DetalleEmpresa extends AppCompatActivity {

    private DetalleEmpresaViewModel detalleEmpresaViewModel;
    String latitudservicio;
    String longitudservicio;
    String idEmpresa;
    TextView txtPrueba;
    Button btnUbicacion, btnCalificacion;
    private DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    android.app.AlertDialog alertDialog;
    List<Rating> Ratings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalle_empresa);
        btnUbicacion = (Button)findViewById(R.id.btnUbicacion);
        btnCalificacion = (Button)findViewById(R.id.btnCalificacion);
        txtPrueba = (TextView)findViewById(R.id.txtPrueba);
        mAuth = FirebaseAuth.getInstance();
        String IdUser = mAuth.getCurrentUser().getUid();
        txtPrueba.setText(IdUser);
        alertDialog = new SpotsDialog.Builder().setCancelable(false).setContext(this).build();

        detalleEmpresaViewModel = ViewModelProviders.of(this).get(DetalleEmpresaViewModel.class);
       /* detalleEmpresaViewModel.getMutableLiveDataEmpresa().observe(this, clsEmpresa -> {
           // displayInfo(clsEmpresa);
        });*/
      /*  detalleEmpresaViewModel.getMutableLiveDataRating().observe(this, clsRating -> {
            EnviarRating(clsRating);
        });*/

        Bundle extras = getIntent().getExtras();
        latitudservicio =  extras.getString("Latitud");
        longitudservicio = extras.getString("Longitud");
        idEmpresa = extras.getString("IdEmpresa");

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleEmpresa.this, UbicacionServicio.class);
                //intent.putExtra("id", datos.get(i).getId());
                intent.putExtra("Latitud", latitudservicio);
                intent.putExtra("Longitud", longitudservicio );
                startActivity(intent);
            }
        });

      btnCalificacion.setOnClickListener(v -> {
          MostrarDialogRating();
      });




    }

   /* private void EnviarRating(ClsRating clsRating) {
        alertDialog.show();
        FirebaseDatabase.getInstance()
                .getReference("Ratings")
                .child(idEmpresa)
                .push()
                .setValue(clsRating)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        EnviarRatingEmpresa(clsRating.getValorRating());
                    }
                    alertDialog.dismiss();
                });
    }*/

    /*private void EnviarRatingEmpresa(float valorRating) {
        FirebaseDatabase.getInstance()
                .getReference("Empresa")
                .child(idEmpresa)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            ClsEmpresa clsEmpresa = dataSnapshot.getValue(ClsEmpresa.class);
                            clsEmpresa.setIdEmpresa(idEmpresa);
                            *//*if (clsEmpresa.getValorRating() == null)
                                clsEmpresa.setValorRating(0d);
                            if (clsEmpresa.getContadorRating() == null)
                                clsEmpresa.setContadorRating(0l);*//*

                            double sumaRating = clsEmpresa.getValorRating() + valorRating ;
                            long contarRating = clsEmpresa.getContadorRating() + 1;
                            double resultado = sumaRating/contarRating;

                            Map<String,Object> actualizarData = new HashMap<>();
                            actualizarData.put("valorRating",resultado);
                            actualizarData.put("ContadorRating",contarRating);

                            dataSnapshot.getRef()
                                    .updateChildren(actualizarData)
                                    .addOnCompleteListener(task -> {
                                        alertDialog.dismiss();
                                       if (task.isSuccessful()){
                                        Toast.makeText(getBaseContext(),clsEmpresa.getValorRating().toString() + " " ,Toast.LENGTH_SHORT).show();
                                        ////
                                        detalleEmpresaViewModel.setEmpresaModel(clsEmpresa);
                                       }
                                    });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        alertDialog.dismiss();
                        Toast.makeText(getBaseContext(), ""+databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }*/

    /*private void displayInfo(ClsEmpresa clsEmpresa){
      //  clsEmpresa.setNombre(new StringBuilder(clsEmpresa.getNombre()));
    }*/

    private void MostrarDialogRating(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DetalleEmpresa.this);
        builder.setTitle("Rating de Empresa");
        builder.setMessage("Descripcion");

        View itemView = LayoutInflater.from(DetalleEmpresa.this).inflate(R.layout.layout_rating,null);

        RatingBar ratingBar = (RatingBar)itemView.findViewById(R.id.rating_bar);
        EditText edtComentario = (EditText)itemView.findViewById(R.id.edtComentario);

        builder.setView(itemView);
        builder.setNegativeButton("Cancelar",(dialogInterface,i) -> {
            dialogInterface.dismiss();
        });
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
           // Toast.makeText(getBaseContext(), "ok", Toast.LENGTH_LONG).show();
           // Toast.makeText(getBaseContext(), mAuth.getCurrentUser().getUid() + edtComentario.getText().toString() + ratingBar.getRating(), Toast.LENGTH_LONG).show();
            ClsRating ratingModel = new ClsRating();
            ratingModel.setUid(mAuth.getCurrentUser().getUid());
            ratingModel.setComentario(edtComentario.getText().toString());
            ratingModel.setValorRating(ratingBar.getRating());
            Map<String,Object> serverTimeStamp = new HashMap<>();
            serverTimeStamp.put("timeStamp", ServerValue.TIMESTAMP);
            ratingModel.setComanetarioTimeStamp(serverTimeStamp);

            GuardarRating(mAuth.getCurrentUser().getUid(),edtComentario.getText().toString(),ratingBar.getRating());

           // ratingModel.setNombre();
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void GuardarRating(String Uid, String Comentario, float Rating) {
        FirebaseDatabase.getInstance()
                .getReference("Empresa")
                .child(idEmpresa)
                .child("Rating")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int ayu =1;
                        ClsRating ModeloRating = new ClsRating();

                        //VERIFICAR SI EXISTE ALGUN RATING DESDE 1

                        //VERIFICAR QUE EXISTA ALGUN RATING HECHO POR EL USUARIO

                        //SI EL USUARIO YA CALIFICO O COMENTO LA EMPRESA ENTONCES ACTUALIZAR

                        //SINO AGREGAR
                            ModeloRating.setUid(Uid);
                            ModeloRating.setComentario(Comentario);
                            ModeloRating.setValorRating(Rating);

                            DatabaseReference RatingReference = FirebaseDatabase.getInstance().getReference("Empresa").child(idEmpresa).child("Rating").child(String.valueOf(dataSnapshot.getChildrenCount()+1));
                            RatingReference.setValue(ModeloRating);

                            //OBETENER EMPRESA SELECCIONADA

                        FirebaseDatabase.getInstance()
                                .getReference("Empresa")
                                .child(idEmpresa).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               // ClsEmpresa ModeloEmpresa = new ClsEmpresa();
                                //AUMENTAR EL CONTEO DE RATING
                                String actual = dataSnapshot.child("RatingConteo").getValue().toString();

                                long contarRating = Long.parseLong(actual)  + 1;
                                //ModeloEmpresa.setRatingConteo(contarRating);
                               // Toast.makeText(getBaseContext(),ModeloEmpresa.getRatingConteo().toString() , Toast.LENGTH_SHORT).show();


                                /*ModeloEmpresa.setRatingTotal(resultado);*/
                                //MANDAR DATOS EMPRESA
                                DatabaseReference EmpresaReference = FirebaseDatabase.getInstance().getReference("Empresa").child(idEmpresa);
                                //EmpresaReference.setValue(ModeloEmpresa.getRatingConteo().toString());
                                Map<String,Object> hopperUpdates = new HashMap<>();
                                hopperUpdates.put("RatingConteo", contarRating);


                                //TODOS POR DEFECTO EL CONTEO
                                //CALCULAR EL RATING TOTAL
                                double sumaRating = (Double.valueOf(dataSnapshot.child("RatingTotal").getValue().toString()) + Rating)*contarRating;
                                double resultado = sumaRating/contarRating;
                                if (contarRating == 1){
                                    hopperUpdates.put("RatingTotal", resultado);
                                }else{
                                    hopperUpdates.put("RatingTotal", resultado/2);
                                }


                                EmpresaReference.updateChildren(hopperUpdates);
                                Toast.makeText(getBaseContext(),  "rating" + String.valueOf(Rating) + "SUMA" +sumaRating + "RatingTotal" + dataSnapshot.child("RatingTotal").getValue().toString() + "RatinConteo " +contarRating, Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });





                        //Toast.makeText(getBaseContext(),dataSnapshot.child("1").child("Comentario").getValue().toString() , Toast.LENGTH_SHORT).show();
                       // Toast.makeText(getBaseContext(),String.valueOf(dataSnapshot.getChildrenCount()) , Toast.LENGTH_SHORT).show();
                        //getChildrenCount();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
