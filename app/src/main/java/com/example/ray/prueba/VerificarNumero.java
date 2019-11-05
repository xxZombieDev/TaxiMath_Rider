package com.example.ray.prueba;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerificarNumero extends AppCompatActivity {

    private String CodigoVerificacion;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText txt_Verfica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_numero);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        txt_Verfica = findViewById(R.id.txt_Verificacion);

        String codigoPais = getIntent().getStringExtra("codigoPais");
        enviarCodigoVerificacion(codigoPais);

        findViewById(R.id.btn_IniciarSesion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = txt_Verfica.getText().toString().trim();

                if(codigo.isEmpty() || codigo.length() <6 ){
                    txt_Verfica.setError("Error en el codigo de verificación");
                    txt_Verfica.requestFocus();
                    return;
                }
                verificarCodigo(codigo);
            }
        });
    }

    private void verificarCodigo(String codigo) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(CodigoVerificacion,codigo);
        iniciarConCredenciales(credential);
    }

    private void iniciarConCredenciales(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(VerificarNumero.this, Rutas.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                            startActivity(intent);
                        } else {
                            Toast.makeText(VerificarNumero.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void enviarCodigoVerificacion(String numero){
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(numero,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            CodigoVerificacion = s;

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code!=null){
                txt_Verfica.setText(code);
                verificarCodigo(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerificarNumero.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
