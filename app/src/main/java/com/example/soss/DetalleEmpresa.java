package com.example.soss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

public class DetalleEmpresa extends AppCompatActivity {

    String latitudservicio;
    String longitudservicio;
    Button btnUbicacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_empresa);
        btnUbicacion = (Button)findViewById(R.id.btnUbicacion);

        Bundle extras = getIntent().getExtras();
        latitudservicio =  extras.getString("Latitud");
        longitudservicio = extras.getString("Longitud");

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
    }
}
