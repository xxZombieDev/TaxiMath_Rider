package com.example.ray.prueba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegistroUsuario extends AppCompatActivity {

    Button btn_continua;
    EditText txt_Telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        // Vinculación de Elementos del Layout con los botones creados al inicio
        txt_Telefono = findViewById(R.id.txt_Telefono);
        btn_continua = findViewById(R.id.btn_Continuar);

        //Creación Eventos botones

        btn_continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telefono = txt_Telefono.getText().toString();
                if (telefono.isEmpty() || telefono.length() < 10) {
                    txt_Telefono.setError("Numero incorrecto o no valido");
                    txt_Telefono.requestFocus();
                    return;
                }
                String codigoPais="+52"+telefono;
                Intent intent = new Intent(RegistroUsuario.this, VerificarNumero.class);
                intent.putExtra("codigoPais",codigoPais);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent = new Intent(this,Rutas.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity(intent);
        }
    }

    private void ValidaRegistro(){
        Toast.makeText(this,"Registrado Correctamente",Toast.LENGTH_LONG).show();
    }


}
