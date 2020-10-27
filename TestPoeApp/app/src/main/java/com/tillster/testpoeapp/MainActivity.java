package com.tillster.testpoeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText txt_email, txt_password;
    Button btn_register,btn_login;
    String email, password;
    final int REQ_CODE =100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_email = findViewById(R.id.txt_email);
        txt_password= findViewById(R.id.txt_password);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

                if (!AppPermissions.hasPermissions(MainActivity.this,permissions))
                {
                    ActivityCompat.requestPermissions(MainActivity.this,permissions,REQ_CODE);
                }




                email = txt_email.getText().toString().trim();
                password= txt_password.getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this, "user created " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult)
                    {
                        Intent i = new Intent(MainActivity.this,Products.class);
                        startActivity(i);

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

                btn_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        email = txt_email.getText().toString().trim();
                        password= txt_password.getText().toString().trim();
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this, "User "
                                            + mAuth.getCurrentUser().getEmail()+ "logged in", Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(MainActivity.this,Products.class);
                                    startActivity(i);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            }
        });





    }
}