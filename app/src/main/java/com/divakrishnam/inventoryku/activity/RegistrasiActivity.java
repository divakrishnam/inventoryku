package com.divakrishnam.inventoryku.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.divakrishnam.inventoryku.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrasiActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnSignUp;
    private TextView tvSignIn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        firebaseAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.tvSignIn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (email.isEmpty()) {
                    etEmail.setError("Provide your Email first!");
                    etEmail.requestFocus();
                } else if (password.isEmpty()) {
                    etPassword.setError("Set your password");
                    etPassword.requestFocus();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrasiActivity.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "SignUp unsuccessful: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
