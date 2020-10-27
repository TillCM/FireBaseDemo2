package com.tillster.opsc7311shopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button clickMe;
    TextView Boo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickMe = findViewById(R.id.clickMe);
        Boo = findViewById(R.id.txt_boo);

        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent openNewActivity = new Intent(MainActivity.this,Reciever.class);
                startActivity(openNewActivity);
            }
        });

        Boo.setOnClickListener(new On);


    }
}