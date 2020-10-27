package com.tillster.bcad2again;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    //call in our UI

    EditText et_email, et_password;
    Button btn_register, btn_login;

    String email, password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        //initialise Auth
        mAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                email = et_email.getText().toString().trim();
                password = et_password.getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {

                                if (task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this, "User " +
                                            mAuth.getCurrentUser().getEmail()+ " was created successfully", Toast.LENGTH_SHORT).show();


                                }

                            }
                        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult)
                    {


                        Toast.makeText(MainActivity.this, " welcome " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        //tell my why auth fails
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                email = et_email.getText().toString().trim();
                password = et_password.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, " Welcome Back "
                                    +mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult)
                    {
                        Toast.makeText(MainActivity.this, "User " +
                                mAuth.getCurrentUser().getEmail()+" has successfully logged in"
                                , Toast.LENGTH_SHORT).show();

                        Intent openPlaceOrder = new Intent(MainActivity.this,PlaceOrder.class);
                        startActivity(openPlaceOrder);

                    }
                }). addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}