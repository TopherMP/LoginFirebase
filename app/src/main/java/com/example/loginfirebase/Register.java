package com.example.loginfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private FirebaseAuth fAuth;
    EditText etEmailReg, etPassReg, etConfirmPassReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fAuth = FirebaseAuth.getInstance();
        etEmailReg = findViewById(R.id.etEmailReg);
        etPassReg = findViewById(R.id.etPassReg);
        etConfirmPassReg = findViewById(R.id.etConfirmPassReg);
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser curentUser = fAuth.getCurrentUser();
    }

    public void Registrar(View v){
        if (etPassReg.getText().toString().equals(etConfirmPassReg.getText().toString())){
            fAuth.createUserWithEmailAndPassword(etEmailReg.getText().toString(), etPassReg.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Registro exitoso
                            Log.d("Registro", "createUserWithEmail:success");
                            Toast.makeText(Register.this, "Usuario creado con éxito!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = fAuth.getCurrentUser();
                            // Redirigir al usuario a la actividad principal (página de inicio de sesión)
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Manejo de errores específicos
                            if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(Register.this, "La contraseña es demasiado débil. Por favor, elige una más fuerte.", Toast.LENGTH_SHORT).show();
                            } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Register.this, "El formato del correo electrónico es inválido.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Otros errores de autenticación
                                Log.w("Registro", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Register.this, "Autenticación fallida.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(Register.this, "No coincieden las contraseñas", Toast.LENGTH_SHORT).show();
        }

    }
}