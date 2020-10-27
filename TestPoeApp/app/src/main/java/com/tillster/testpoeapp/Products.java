package com.tillster.testpoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Products extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseAuth mAuth;
    Orders orders ;
    EditText orderID, customerName, productOrdered, shippingMethod;
    Button btn_order, waterIntake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        orderID = findViewById(R.id.txt_orderID);
        customerName = findViewById(R.id.txt_customerName);
        productOrdered = findViewById(R.id.txt_product);
        shippingMethod = findViewById(R.id.txt_shipping);



        btn_order = findViewById(R.id.btn_push);
        waterIntake = findViewById(R.id.btn_push2);

        // to get access to currently logged on user
        mAuth = FirebaseAuth.getInstance();


        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String orderIDvalue = orderID.getText().toString().trim();
                String customerNamevalue = customerName.getText().toString().trim();
                String productOrdervalue = productOrdered.getText().toString().trim();
                String ship = shippingMethod.getText().toString().trim();
                DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                Orders orders = new Orders(orderIDvalue,customerNamevalue,productOrdervalue,ship);
                myRef.child("food today")
                        .push().setValue(orders);

            }
        });

        waterIntake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                //Orders orders = new Orders(orderIDvalue,customerNamevalue,productOrdervalue,ship);
                myRef.child("Water Intake")
                        .push().setValue("WATEERRRR");
            }
        });
    }
}