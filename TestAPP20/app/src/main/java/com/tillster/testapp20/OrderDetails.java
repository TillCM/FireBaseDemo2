package com.tillster.testapp20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class OrderDetails extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);



        Toast.makeText(OrderDetails.this, "", Toast.LENGTH_SHORT).show();
    }
}