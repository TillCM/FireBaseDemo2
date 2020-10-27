package com.tillster.opsc7311shopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Reciever extends AppCompatActivity {
    
    Button clickMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever);
        
        clickMe = findViewById(R.id.clickMe);
        
        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Reciever.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}