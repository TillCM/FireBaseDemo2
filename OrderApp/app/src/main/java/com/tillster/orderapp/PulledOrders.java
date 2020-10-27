package com.tillster.orderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PulledOrders extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("orders");
    List<String> ordersList;
    ArrayAdapter adapter;
    Orders orders;

    ListView ordersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulled_orders);
        ordersListView = findViewById(R.id.lv_orders);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                orders = new Orders();
                ordersList = new ArrayList<String>();
                for (DataSnapshot orderFromFirebase : snapshot.getChildren())
                {
                    orders = orderFromFirebase.getValue(Orders.class);
                    ordersList.add(orders.ToString());
                }

               adapter = new ArrayAdapter(PulledOrders.this,android.R.layout.simple_list_item_1,ordersList);

                ordersListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(PulledOrders.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}