package com.tillster.firebasedemobcad2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Product prodcut;
    Button push;
    EditText productID, productName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();// please connect to the web and get my firebase project
    DatabaseReference myRef = database.getReference(); // use that project to connnet to my db
    // orders - is kind of like a table.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        push = findViewById(R.id.push);
        productID = findViewById(R.id.productID);
        productName = findViewById(R.id.productName);

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productIDValue = productID.getText().toString().trim();
                String productNameValue = productName.getText().toString().trim();

                // instance of DTO
                prodcut = new Product(productIDValue,productNameValue);


// push to DB
                myRef.push().setValue(prodcut).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Record Saved", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "boo happened ", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }
}