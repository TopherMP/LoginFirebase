package com.example.loginfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    EditText email, pass;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.etEmail);
        pass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar();
            }
        });
    }

    public void ingresar() {
        if (email.getText().toString().isEmpty() && pass.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, ingresar sus datos", Toast.LENGTH_SHORT).show();
        } else {


            fAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Ingreso exitoso, actualiza la UI con la información del usuario
                    //Toast.makeText(MainActivity.this, "Autenticación exitosa.", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = fAuth.getCurrentUser();

                    // Redirecciona a la actividad de Productos
                    Toast.makeText(this, "Ingreso exitoso", Toast.LENGTH_SHORT).show();

                } else {
                    // Si falla el ingreso, muestra un mensaje al usuario
                    Toast.makeText(MainActivity.this, "Falló la autenticación. Verifica tus credenciales.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}