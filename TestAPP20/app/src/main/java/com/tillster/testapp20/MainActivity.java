package com.tillster.testapp20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

      Button clickMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickMe = findViewById(R.id.clickMe);

        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        clickMe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Boo ", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this,OrderDetails.class);
            }
        });
    }



}