package com.tillster.fakestagramvideo;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tillster.fakestagramvideo.Adapters.CloudPicAdapter;
import com.tillster.fakestagramvideo.Models.ImageModel;

import java.util.ArrayList;
import java.util.List;


public class VIewCouldImageFragment extends Fragment {
    RecyclerView cloudRecycler;
    CloudPicAdapter cloudPicAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private List<ImageModel> cloudPicsList;
    FirebaseAuth mAuth;


    private static VIewCouldImageFragment instance;

    public static VIewCouldImageFragment getInstance() {
        if (instance == null)
            return new VIewCouldImageFragment();
        return instance;
    }

    public VIewCouldImageFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_v_iew_could_image, container, false);

        mAuth = FirebaseAuth.getInstance();

        //only access the data / images for the current user
        DatabaseReference myRef = database.getReference("PhotoMemories").child(mAuth.getCurrentUser().getUid());
        cloudRecycler = view.findViewById(R.id.cloudImage_recycler);
        cloudRecycler.setHasFixedSize(true);
        cloudRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        cloudPicsList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot cloudImages : dataSnapshot.getChildren())
                {

                    ImageModel imageModel = cloudImages.getValue(ImageModel.class);

                    cloudPicsList.add(imageModel);
                }

                cloudPicAdapter = new CloudPicAdapter(getContext(),cloudPicsList);
                cloudRecycler.setAdapter(cloudPicAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

}
