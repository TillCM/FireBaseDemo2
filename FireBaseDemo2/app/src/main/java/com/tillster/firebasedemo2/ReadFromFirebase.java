package com.tillster.firebasedemo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReadFromFirebase extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ClassNotes");
    ArrayList<String> pulledData; ;
    ArrayAdapter<String> adapter ;
    ListView listView, arrayListview ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_from_firebase);

        arrayListview = findViewById(R.id.firebaseListView);


        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                pulledData = new ArrayList<>();

                for( DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.d("CHILD", "onDataChange: " + snapshot);
                    Log.d("VALUE", "onDataChange: " + snapshot.getValue());
                    FirebaseDataModel dataModel =snapshot.getValue(FirebaseDataModel.class);
                    pulledData.add(dataModel.toString());

                    Log.d("DATA IN ARRAYLIST", "onDataChange: " + pulledData);
                }


                Log.d("PULLEDATA", "onDataChange: " + pulledData);


                adapter = new ArrayAdapter<>(ReadFromFirebase.this, android.R.layout.simple_list_item_1,pulledData);
                arrayListview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(ReadFromFirebase.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }
}