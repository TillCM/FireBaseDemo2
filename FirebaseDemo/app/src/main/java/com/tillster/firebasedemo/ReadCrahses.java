package com.tillster.firebasedemo;

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

public class ReadCrahses extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("appCrashes");
    ListView listView;
    ArrayList<String> pulledValues;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_crahses);
        listView = findViewById(R.id.craslListView);

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                pulledValues = new ArrayList<>();
                for(DataSnapshot pulledData : dataSnapshot.getChildren())
                {
                    Crash crash = pulledData.getValue(Crash.class);
                    pulledValues.add(crash.ToString());
                }
                adapter = new ArrayAdapter<>(ReadCrahses.this, android.R.layout.simple_list_item_1, pulledValues);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

                Toast.makeText(ReadCrahses.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }




}