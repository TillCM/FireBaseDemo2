package com.tillster.fakestagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class LocalPics extends AppCompatActivity {

    View_Local_Pics view = new View_Local_Pics();
    Store_Local_Pics store =new Store_Local_Pics();

    ImageView img_viewPhotos, img_storePhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_pics);
        img_viewPhotos = findViewById(R.id.img_viewImages);
        img_storePhotos=findViewById(R.id.img_storeImages);

       img_viewPhotos.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               FragmentManager manager = getSupportFragmentManager();
               FragmentTransaction transaction = manager.beginTransaction();

               transaction.replace(R.id.load_image_Place_holder, view);
               transaction.commitAllowingStateLoss();
           }
       });

       img_storePhotos.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               FragmentManager manager = getSupportFragmentManager();
               FragmentTransaction transaction = manager.beginTransaction();

               transaction.replace(R.id.load_image_Place_holder, store);
               transaction.commitAllowingStateLoss();

           }
       });


    }
}
