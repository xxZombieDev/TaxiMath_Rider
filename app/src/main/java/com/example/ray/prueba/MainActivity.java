package com.example.ray.prueba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    Button btn_registro, btn_inicio;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Llamado a los Botones del Layout Main Activity
        btn_registro = (Button) findViewById(R.id.btn_Registrate);
        btn_inicio = (Button) findViewById(R.id.btn_incio);

        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityLogin();
            }
        });

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityRegister();
            }
        });

    }

    private void ActivityLogin(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        Intent intentLogin = new Intent(this,DatosUsuario.class);
        startActivity(intentLogin);
        progressDialog.hide();
    }

    private void ActivityRegister() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        Intent intentLogin = new Intent(this,RegistroUsuario.class);
        startActivity(intentLogin);
        progressDialog.hide();
    }
}
