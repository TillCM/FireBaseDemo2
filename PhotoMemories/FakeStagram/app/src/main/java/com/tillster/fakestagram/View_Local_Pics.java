package com.tillster.fakestagram;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tillster.fakestagram.Adapter.PhotoViewAdapter;
import com.tillster.fakestagram.Database.DatabaseHandler;
import com.tillster.fakestagram.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class View_Local_Pics extends Fragment
{
    RecyclerView recyclerView;
    DatabaseHandler imageDb;

    public View_Local_Pics() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_view__local__pics, container, false);

        try
            {
                recyclerView= view.findViewById(R.id.local_img_Recycler);
                imageDb = new DatabaseHandler(getContext());
                getData();


            }
        catch (Exception e)
            {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        recyclerView = view.findViewById(R.id.local_img_Recycler);


        return view;
    }

    private void getData()
    {
        try
        {

             if (imageDb.displayImages()!= null)
             {
                 PhotoViewAdapter photoViewAdapter = new PhotoViewAdapter(imageDb.displayImages(), getContext());
                 recyclerView.setHasFixedSize(true);
                 recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                 recyclerView.setAdapter(photoViewAdapter);
             }

             else
             {
                 Toast.makeText(getContext(), "No Images found", Toast.LENGTH_SHORT).show();
             }


        }

        catch (Exception e)
        {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
