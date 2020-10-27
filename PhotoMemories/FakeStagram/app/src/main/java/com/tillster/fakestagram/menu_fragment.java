package com.tillster.fakestagram;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class menu_fragment extends Fragment {

    ImageView localImages, cloudImages;

    public menu_fragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view  = inflater.inflate(R.layout.fragment_menu_fragment, container, false);

        localImages = view.findViewById(R.id.img_localImages);
        cloudImages = view.findViewById(R.id.img_cloudImages);

        localImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent openLocalImages  = new Intent(getContext(),LocalPics.class);
                startActivity(openLocalImages);

            }
        });

        cloudImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent openCloudImages = new Intent(getContext(),CloudImages.class);
                startActivity(openCloudImages);

            }
        });

        return  view;
    }
}
