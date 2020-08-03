package com.tillster.fakestagramvideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    Button login, register, signOut;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    CardView loginCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.btn_loginr);
        register = findViewById(R.id.btn_register);
        currentUser = mAuth.getCurrentUser();
        loginCardView = findViewById(R.id.cardview_login);
        signOut = findViewById(R.id.btn_signOut);






        //if some one is currently logged in
        if (currentUser!=null)
        {
            loginCardView.setVisibility(View.GONE);
            loadFragment();
        }
        else
        {
            //if no one is logged in
            loginCardView.setVisibility(View.VISIBLE);
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String enteredEmail = email.getText().toString().trim();
                String enteredPasssword = email.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(enteredEmail,enteredPasssword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this, "User "
                                            + mAuth.getCurrentUser().getEmail()+ "successfully registered",
                                            Toast.LENGTH_SHORT).show();

                                }

                                else
                                {
                                    Toast.makeText(MainActivity.this, "Boo Boo Occured, no register", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String enteredEmail = email.getText().toString().trim();
                String enteredPasssword = email.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(enteredEmail,enteredPasssword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {

                            loginCardView.setVisibility(View.GONE);

                            Toast.makeText(MainActivity.this, "Logged in "+
                                    mAuth.getCurrentUser().getEmail()+ "successfully", Toast.LENGTH_SHORT).show();
                            loadFragment();

                        }

                        else
                        {
                            Toast.makeText(MainActivity.this, "Boo happened no login for you!!!", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {

                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                mAuth.signOut();
                Toast.makeText(MainActivity.this, "You have signed out", Toast.LENGTH_SHORT).show();
                loginCardView.setVisibility(View.VISIBLE);
            }
        });



    }

    @Override
    protected void onStart()
    {
        super.onStart();



        if (currentUser!=null)
        {
            Toast.makeText(this, "Already logged in " + currentUser.getEmail(), Toast.LENGTH_LONG).show();
        }
    }

    public void loadFragment()
    {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.menu_fragment_placeHolder,MenuFragment.getInstance());
        transaction.commit();
    }


}