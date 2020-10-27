package com.tillster.fakestagramvideo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class LocalPics extends AppCompatActivity {

    ImageView viewLocalPic, storeLocalPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_pics);

       storeLocalPic = findViewById(R.id.img_storeLocalImages);
       viewLocalPic = findViewById(R.id.img_viewLocalImages);

       storeLocalPic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               FragmentManager manager = getSupportFragmentManager();
               FragmentTransaction transaction = manager.beginTransaction();
               transaction.replace(R.id.load_localImagePlaceHolder,StoreLocalPicsFragment.getInstance());
               transaction.commitAllowingStateLoss();

           }
       });

       viewLocalPic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               FragmentManager manager = getSupportFragmentManager();
               FragmentTransaction transaction = manager.beginTransaction();
               transaction.replace(R.id.load_localImagePlaceHolder,ViewLocalPicsFragment.getInstance());
               transaction.commitAllowingStateLoss();

           }
       });



    }
}