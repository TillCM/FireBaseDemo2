package com.tillster.fakestagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText txt_uName, txt_pword;
    Button register ,login;

    menu_fragment menu = new menu_fragment();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        txt_uName = findViewById(R.id.txt_uName);
        txt_pword = findViewById(R.id.txt_pword);
        register =findViewById(R.id.btn_register);
        login =findViewById(R.id.btn_login);

      register.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v)

          {
              final String userName = txt_uName.getText().toString().trim();
              String passWord = txt_pword.getText().toString().trim();

           mAuth.createUserWithEmailAndPassword(userName, passWord)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                   {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task)
                       {
                           if (task.isSuccessful())
                           {
                               Toast.makeText(MainActivity.this, userName, Toast.LENGTH_SHORT).show();
                           }

                           else
                           {
                               Toast.makeText(MainActivity.this, "Boo Boo Happened when registering ", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });

          }
      });


    }

    @Override
    protected void onStart()
    {
        super.onStart();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                final String userName = txt_uName.getText().toString().trim();
                String passWord = txt_pword.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(userName,passWord)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this, "You have signed in" +mAuth.getCurrentUser(), Toast.LENGTH_SHORT).show();
                                    loadFragment();

                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Boo Boo Happened when logging in", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });




    }

        public void loadFragment()
        {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.menu_fragment_place_holder, menu);
            transaction.commit();
        }

}
