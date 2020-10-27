package com.tillster.fakestagram.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.tillster.fakestagram.Models.ImageModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper
{
    Context context;
    private static final String DATABASENAME="images.db";
    public static final int DATABASEVERSION=1;
    private ByteArrayOutputStream convertBitmapToByteArray;
    private byte [] imageInBytes;

    private static String createTableQuery ="create table imageStore(imageName TEXT ,imageBitmap BLOB)";


    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        try
        {
            db.execSQL(createTableQuery);
            Toast.makeText(context, "Table created", Toast.LENGTH_SHORT).show();
        }

        catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void storeImageLocal(ImageModel imageModel)
    {
        try
        {
            SQLiteDatabase imageDatabase = this.getWritableDatabase();
            Bitmap imageToStore = imageModel.getImageBitmap();
            convertBitmapToByteArray = new ByteArrayOutputStream();
            imageToStore.compress(Bitmap.CompressFormat.JPEG,100,convertBitmapToByteArray);
            imageInBytes =convertBitmapToByteArray.toByteArray();
            ContentValues contentValues = new ContentValues();
            contentValues.put("imageName", imageModel.getImageName());
            contentValues.put("imageBitmap",imageInBytes);

             long checkIfQueryRuns =  imageDatabase.insert("imageStore",null,contentValues);

             if (checkIfQueryRuns!=-1)
                 {
                     Toast.makeText(context, "Image saved", Toast.LENGTH_SHORT).show();
                     imageDatabase.close();
                 }
             else
                 {
                     Toast.makeText(context, "Unable to save Image", Toast.LENGTH_SHORT).show();
                 }

        }

        catch (Exception e)
            {
                Log.i("SAVE TO DB ", "storeImageLocal: " +e.getMessage());
            }

    }

    public ArrayList<ImageModel> displayImages()
    {

        try
            {
                SQLiteDatabase imagDatabase = this.getReadableDatabase();
                ArrayList<ImageModel>dbImages = new ArrayList<>();
                Cursor cursor = imagDatabase.rawQuery("select * from imageStore",
                        null);

              if(cursor.getCount()!=0)
                  {
                      while(cursor.moveToNext())
                      {
                          String imageName = cursor.getString(0);
                          byte [] image = cursor.getBlob(1);

                          Bitmap imageBitmap = BitmapFactory.decodeByteArray(image,0 ,
                                  image.length);

                          dbImages.add(new ImageModel(imageName,imageBitmap));
                      }


                      Toast.makeText(context, "Loading Images", Toast.LENGTH_SHORT).show();
                      return  dbImages;
                  }
              else
                  {
                      Toast.makeText(context, "No Images Found ", Toast.LENGTH_SHORT).show();
                      return null;
                  }


            }
        catch (Exception e)
            {
                Log.i("SAVE TO DB ", "storeImageLocal: " +e.getMessage());
                return null;
            }


    }
}
