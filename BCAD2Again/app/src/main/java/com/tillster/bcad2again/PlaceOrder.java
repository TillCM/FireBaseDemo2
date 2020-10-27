package com.tillster.bcad2again;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlaceOrder extends AppCompatActivity {
    Order order;
    EditText et_prodId, et_prodDesc, et_prodShip;
    Button push, viewOrder;

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    // to get access to currently logged in user ONLY
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        et_prodId = findViewById(R.id.et_prodId);
        et_prodDesc = findViewById (R.id.et_prodDesc);
        et_prodShip = findViewById(R.id.et_prodShipMethod);
        push = findViewById(R.id.btn_push);
        viewOrder = findViewById(R.id.btn_viewOrders);

        //user Management
        mAuth = FirebaseAuth.getInstance();

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                order = new Order(et_prodId.getText().toString().trim()
                ,et_prodDesc.getText().toString().trim(),
                        et_prodShip.getText().toString().trim());

                DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                myRef.child("orders").push().setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PlaceOrder.this, "Order  successfully stored", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PlaceOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent openPulled = new Intent(PlaceOrder.this,OrdersPlaced.class);
                startActivity(openPulled);
            }
        });
    }


}