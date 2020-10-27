package com.tillster.fakestagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CloudImages extends AppCompatActivity
{
    ImageView loadImages, storeImages;
    View_Cloud_Pics view_cloud_pics = new View_Cloud_Pics();
    Store_Cloud_Pics store_cloud_pics = new Store_Cloud_Pics();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_images);

        loadImages = findViewById(R.id.img_viewCloudImages);
        storeImages = findViewById(R.id.img_storeCloudImages);

        storeImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.load_cloud_image_Place_holder, store_cloud_pics);
                transaction.commitAllowingStateLoss();
            }


        });

        loadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.load_cloud_image_Place_holder, view_cloud_pics);
                transaction.commitAllowingStateLoss();

            }
        });




    }
}
