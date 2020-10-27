package com.tillster.testpoeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderDetails extends AppCompatActivity {

    EditText pulledOrderID, pulledCustomerName, pulledProduct, pulledShipping;
    Button pull;
    Orders orders;

    private FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        mAuth = FirebaseAuth.getInstance();

        pulledOrderID = findViewById(R.id.txt_pulledOrderID);
        pulledCustomerName = findViewById(R.id.txt_pulledCustomerName);
        pulledProduct = findViewById(R.id.txt_pulledProduct);
        pulledShipping = findViewById(R.id.txt_pulledShipping);

        pull = findViewById(R.id.btn_pull);

        pull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                myRef.child("food today").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {

                        for (DataSnapshot waterValues : snapshot.getChildren())
                        {
                             orders = waterValues.getValue(Orders.class);

                        }

                        pulledCustomerName.setText(orders.getCustomerName());
                        pulledOrderID.setText(orders.getOrderId());
                        pulledProduct.setText(orders.getProductOrderd());
                        pulledShipping.setText(orders.getShippingMethod());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(OrderDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}