package com.tillster.firebasedemo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{

    EditText email, password;
    Button login, register, signout, opeNext, read;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    LinearLayout loginPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.txt_uName);
        password = findViewById(R.id.txt_pword);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);
        signout = findViewById(R.id.btn_signOut);
        mAuth = FirebaseAuth.getInstance();
        loginPane = findViewById(R.id.login_pane);
        opeNext = findViewById(R.id.btn_openDBActivity);
        read = findViewById(R.id.btn_readfromDB);

        //  get current user
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
        {
            loginPane.setVisibility(View.GONE);
            Toast.makeText(this, "user "+ mAuth.getCurrentUser().getEmail()+
                    "is already signed in ", Toast.LENGTH_SHORT).show();
        }

        else
        {
            loginPane.setVisibility(View.VISIBLE);
        }




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String enteredEmail = email.getText().toString().trim();
                String enteredPassword = password.getText().toString().trim();

                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter values in each field ", Toast.LENGTH_SHORT).show();
                }

                else
                    {
                        mAuth.createUserWithEmailAndPassword(enteredEmail,enteredPassword)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task)
                                    {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(MainActivity.this, " User created", Toast.LENGTH_SHORT).show();
                                        }

                                        else
                                            {
                                                Toast.makeText(MainActivity.this, "User creation failed", Toast.LENGTH_SHORT).show();
                                            }
                                    }
                                }). addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }


            }
        });


        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String enteredEmail = email.getText().toString().trim();
                String enteredPassword = password.getText().toString().trim();

                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter values in each field ", Toast.LENGTH_SHORT).show();
                }

                else

                {
                    mAuth.signInWithEmailAndPassword(enteredEmail,enteredPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(MainActivity.this, "Welcome back "
                                                + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                        loginPane.setVisibility(View.GONE);
                                    }

                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "No Login for you!!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }). addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mAuth.signOut();
                Toast.makeText(MainActivity.this, " bey bey ", Toast.LENGTH_SHORT).show();
                loginPane.setVisibility(View.VISIBLE);

            }
        });

        opeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WriteToFireBase.class);
                startActivity(i);
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this,ReadFromFirebase.class);
                startActivity(i);

            }
        });

    }
}