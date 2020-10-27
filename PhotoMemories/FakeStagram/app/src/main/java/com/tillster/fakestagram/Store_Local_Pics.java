package com.tillster.fakestagram;

import android.Manifest;
import android.content.ContentResolver;
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

import com.squareup.picasso.Picasso;
import com.tillster.fakestagram.Database.DatabaseHandler;
import com.tillster.fakestagram.Models.ImageModel;
import com.tillster.fakestagram.Permissions.Permission;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Store_Local_Pics extends Fragment
{
    private static final int REQUEST_CODE = 100;
    ImageView pickImage, storeImage, imagepPane;
    EditText imageName;
    final int PICK_CODE= 101;
    Bitmap imageToStore;
    DatabaseHandler imagedb ;

    public Store_Local_Pics()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_store__local__pics, container, false);
        pickImage = view.findViewById(R.id.img_choose_SQL_Image);
        storeImage = view.findViewById(R.id.img_save_SQL_Image);
        imagepPane = view.findViewById(R.id.img_ImagePane);
        imagedb = new DatabaseHandler(getActivity());
        imageName = view.findViewById(R.id.txt_SqlImageName);

        pickImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String [] Permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if(!Permission.hasPermissions(getContext(),Permissions))
                {
                    ActivityCompat.requestPermissions(getActivity(), Permissions, REQUEST_CODE);
                }

                else
                {

                    chooseImage();

                }

            }
        });

        storeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    storeImage();
            }
        });


        return view;
    }

    public void chooseImage()
    {
        Intent chooseImage = new Intent(Intent.ACTION_GET_CONTENT);
        chooseImage.setType("image/*");
        startActivityForResult(Intent.createChooser(chooseImage,"Pick and Image "),PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

        if (requestCode == PICK_CODE && data !=null)
        {
            Uri imageData = data.getData();

            try
                {
                    imageToStore = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageData);
                }
            catch (IOException e)
                {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            imagepPane.setImageBitmap(imageToStore);

        }

    }

    public void storeImage()
    {

        if (imageName.getText().toString()!=null && imagepPane !=null && imageToStore !=null)
            {
                imagedb.storeImageLocal(new ImageModel(imageName.getText().toString(),imageToStore));
                imagepPane.setImageResource(R.drawable.photo);
            }
        else
            {
                Toast.makeText(getContext(), "Please select and Image and Enter an Image name",
                        Toast.LENGTH_SHORT).show();
            }
    }


}
