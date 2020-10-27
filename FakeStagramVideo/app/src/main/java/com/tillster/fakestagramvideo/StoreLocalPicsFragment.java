package com.tillster.fakestagramvideo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tillster.fakestagramvideo.Databases.LocalDatabaseHelper;
import com.tillster.fakestagramvideo.Models.ImageModel;
import com.tillster.fakestagramvideo.Permissions.PicPermissions;

import java.io.IOException;
import java.net.SocketTimeoutException;


public class StoreLocalPicsFragment extends Fragment
{
    ImageView localImagePane, saveToLocal, chooseLocalImage;
    EditText imageName;
    Bitmap imageToStoreLocally;
    private final static int REQUEST_CODE =100;
    private final static int PICK_CODE =103;
    LocalDatabaseHelper localDatabaseHelper;

   private static StoreLocalPicsFragment instance;

   public static StoreLocalPicsFragment getInstance()
   {
       if(instance == null)
           return new StoreLocalPicsFragment();
       return instance;
   }

    public StoreLocalPicsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View view  =  inflater.inflate(R.layout.fragment_store_local_pics, container, false);

        localImagePane = view.findViewById(R.id.img_localImagePane);
        saveToLocal = view.findViewById(R.id.img_saveLocalPic);
        chooseLocalImage = view.findViewById(R.id.img_chooseLocalPic);
        imageName = view.findViewById(R.id.et_nameLocalImage);
        localDatabaseHelper = new LocalDatabaseHelper(getContext());

        chooseLocalImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE};

                if (!PicPermissions.hasPermissions(getContext(),permissions))
                {
                    ActivityCompat.requestPermissions(getActivity(),permissions,REQUEST_CODE);
                }

                else
                {
                    chooseImage();
                }

            }
        });

        saveToLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE};

                if (!PicPermissions.hasPermissions(getContext(),permissions))
                {
                    ActivityCompat.requestPermissions(getActivity(),permissions,REQUEST_CODE);
                }

                else
                {
                    saveImage();
                }

            }
        });


        return view;
    }



    private void chooseImage()
    {
        Intent chooseImage  = new Intent(Intent.ACTION_GET_CONTENT);
        chooseImage.setType("image/*");
        startActivityForResult(Intent.createChooser(chooseImage,"Pick an image"),PICK_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CODE && data !=null)
        {
            Uri imageData = data.getData();

            try
            {
                imageToStoreLocally = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageData);
            }
            catch (IOException e)
            {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            localImagePane.setImageBitmap(imageToStoreLocally);
        }
    }

    private void saveImage()
    {
        if (imageName.getText().toString()!= null && imageToStoreLocally!=null)
        {
            localDatabaseHelper.storeImageLocal(new ImageModel(imageName.getText().toString().trim(),imageToStoreLocally));
            localImagePane.setImageResource(R.drawable.photo);
            imageName.getText().clear();

        }

        else
        {
            Toast.makeText(getContext(), "Please choose an Image and assign a name", Toast.LENGTH_SHORT).show();
        }

    }
}