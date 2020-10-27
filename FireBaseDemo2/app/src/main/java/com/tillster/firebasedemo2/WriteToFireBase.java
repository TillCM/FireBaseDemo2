package com.tillster.firebasedemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WriteToFireBase extends AppCompatActivity
{
    EditText numStudents, assignedLecturer;
    Button pusToDB;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ClassNotes");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_to_fire_base);

        numStudents = findViewById(R.id.et_numStudents);
        assignedLecturer = findViewById(R.id.et_assignedLecturer);
        pusToDB = findViewById(R.id.pushToFirebaseRTDB);

        pusToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FirebaseDataModel model = new FirebaseDataModel(numStudents.getText().toString().trim(),
                        assignedLecturer.getText().toString().trim());

                myRef.push().setValue(model);

            }
        });

    }
}