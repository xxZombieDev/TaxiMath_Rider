package com.example.ray.prueba;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity {
    Button btn_iniciarS;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        btn_iniciarS = findViewById(R.id.btn_sesion);


        btn_iniciarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentMap();
            }
        });
    }

    public void intentMap() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        Intent intentLogin = new Intent(this,Rutas.class);
        startActivity(intentLogin);
    }

}
