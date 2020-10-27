package com.tillster.fakestagram;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tillster.fakestagram.Models.ImageModel;
import com.tillster.fakestagram.Permissions.Permission;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Store_Cloud_Pics extends Fragment {
    private static final int PICK_CODE = 103;
    private static final int REQUEST_CODE = 104;
    private StorageReference mStorageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("PhotoMemories");
    Uri imageData;
    String storedImageUri;
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    ImageView chooseImage, storeImage, imagePane;
    EditText imageName;
    Bitmap imageToStore;

    public Store_Cloud_Pics() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store__cloud__pics, container, false);

        chooseImage = view.findViewById(R.id.img_choose_Cloud_Image);
        storeImage = view.findViewById(R.id.img_save_Cloud_Image);
        imagePane = view.findViewById(R.id.img_couldImagePane);
        imageName = view.findViewById(R.id.txt_CloudImageName);

        mStorageRef = FirebaseStorage.getInstance().getReference("PhotoMemories");
        mAuth = FirebaseAuth.getInstance();

        chooseImage.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v)
            {
                String[] Permissions ={Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

                if (!Permission.hasPermissions(getContext(),Permissions))
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
                String[] Permissions ={Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                if (!Permission.hasPermissions(getContext(),Permissions))
                {
                    ActivityCompat.requestPermissions(getActivity(), Permissions, REQUEST_CODE);
                }
                else
                {
                    setStoreImage();
                }


            }
        });


        return view;
    }

    public void chooseImage() {
        Intent chooseImage = new Intent(Intent.ACTION_GET_CONTENT);
        chooseImage.setType("image/*");
        startActivityForResult(Intent.createChooser(chooseImage, "Pick and Image "), PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PICK_CODE && data != null) {
            imageData = data.getData();

            try {
                imageToStore = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageData);
            } catch (IOException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            imagePane.setImageBitmap(imageToStore);


        }
    }

    private String getFileExtensions(Uri uri)
    {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void setStoreImage()
    {

        try
            {
                if (imageData!=null)
                    {
                        final StorageReference fileRef = mStorageRef.child(System.currentTimeMillis()+
                                "."+getFileExtensions(imageData));

                     UploadTask  uploadTask = fileRef.putFile(imageData);

                     Task<Uri> urlTask =uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                         @Override
                         public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                         {

                                return fileRef.getDownloadUrl();
                         }
                     }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                         @Override
                         public void onComplete(@NonNull Task<Uri> task)
                         {
                             if(task.isSuccessful())
                             {
                                 currentUser = mAuth.getCurrentUser();
                                storedImageUri = task.getResult().toString();
                                
                                if (storedImageUri!= null)
                                {
                                    
                                

                                 ImageModel imageModel = new ImageModel(imageName.getText().toString().trim()
                                         ,storedImageUri);
                                 String uploadID= myRef.push().getKey();
                                 myRef.child(currentUser.getUid()).child(uploadID).setValue(imageModel);

                                 Toast.makeText(getContext(), "Photo loaded to the cloud :-)", Toast.LENGTH_SHORT).show();
                                 imagePane.setImageResource(R.drawable.photo);
                                }
                                
                                else
                                {
                                    Toast.makeText(getContext(), "Please select an Image", Toast.LENGTH_SHORT).show();
                                }

                             }

                         }
                     });





                    }

                else
                    {
                        Toast.makeText(getContext(), "Please select an Image ", Toast.LENGTH_SHORT).show();

                    }
            }
        catch (Exception e)
            {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

    }

}
