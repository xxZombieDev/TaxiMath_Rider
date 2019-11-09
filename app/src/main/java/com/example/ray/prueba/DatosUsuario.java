package com.example.ray.prueba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ray.prueba.Models.Clientes;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class DatosUsuario extends AppCompatActivity {

    EditText txtNombre, txtApellidos, txtUsuario, txtPass;
    Button btn_Siguiente;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_usuario);

        // Asociación de los elementos del layaout a los elementos creados en la clase
        txtNombre = findViewById(R.id.txt_Nombre);
        txtApellidos = findViewById(R.id.txt_AP);
        txtUsuario = findViewById(R.id.txt_Usuario);
        txtPass = findViewById(R.id.txt_Contraseña);

        btn_Siguiente = findViewById(R.id.btn_Siguiente);

        iniciarFirebase();

        btn_Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nombre = txtNombre.getText().toString();
                String Apellidos = txtApellidos.getText().toString();
                String Usuario = txtUsuario.getText().toString();
                String Contraseña = txtPass.getText().toString();
                if (Nombre.equals("") || Apellidos.equals("") || Usuario.equals("") || Contraseña.equals("")){
                    validaciones();
                } else {
                    Clientes c = new Clientes();
                    c.setUid(UUID.randomUUID().toString());
                    c.setNombre(Nombre);
                    c.setApellidos(Apellidos);
                    c.setUsuario(Usuario);
                    c.setContraseña(Contraseña);
                    databaseReference.child("Clientes").child(c.getUid()).setValue(c);
                    Intent intent = new Intent(DatosUsuario.this, RegistroUsuario.class);
                    startActivity(intent);
              //      Toast.makeText(DatosUsuario.this, "Inserción Exitosa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void validaciones() {
        String Nombre = txtNombre.getText().toString();
        String Apellidos = txtApellidos.getText().toString();
        String Usuario = txtUsuario.getText().toString();
        String Contraseña = txtPass.getText().toString();

        if (Nombre.equals("")){
            txtNombre.setError("Campo Requerido");
        }
        if (Apellidos.equals("")){
            txtApellidos.setError("Campo Requerido");
        }
        if (Usuario.equals("")){
            txtUsuario.setError("Campo Requerido");
        }
        if (Contraseña.equals("")){
            txtPass.setError("Campo Requerido");
        }
    }


}
