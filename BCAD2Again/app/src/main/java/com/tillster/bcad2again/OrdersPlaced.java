package com.tillster.bcad2again;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class OrdersPlaced extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    EditText et_PulledProdID, et_PulledProdDesc, et_PulledProductShip;
    Button pull;
    Order order;
    String TAG = "Data";



    // to get access to currently logged in user ONLY
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_placed);

        et_PulledProdDesc = findViewById(R.id.et_PulledprodDesc);
        et_PulledProdID = findViewById(R.id.et_PulledprodId);
        et_PulledProductShip = findViewById(R.id.et_PulledprodShipMethod);
        pull = findViewById(R.id.btn_pull);

        mAuth = FirebaseAuth.getInstance();

        pull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());

                myRef.child("orders").addValueEventListener(new ValueEventListener()
                {

                    Order  order = new Order();


                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {

                        for (DataSnapshot data : snapshot.getChildren())

                        {
                            order = data.getValue(Order.class);

                            Log.i(TAG, "onDataChange: " + order) ;
                        }

                        Log.i(TAG, "onDataChange: "+ snapshot.getValue());
                        Log.i(TAG, "onDataChange: " + order.getOrderID()) ;
                        Toast.makeText(OrdersPlaced.this, order.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(OrdersPlaced.this, order.getOrderDesc(), Toast.LENGTH_SHORT).show();
                        et_PulledProdID.setText(order.getOrderID());
                        et_PulledProdDesc.setText(order.getOrderDesc());
                        et_PulledProductShip.setText(order.getOrderShipMethod());

                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        Toast.makeText(OrdersPlaced.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

    }
}