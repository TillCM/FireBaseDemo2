package com.tillster.fakestagram.Models;

import android.graphics.Bitmap;

public class ImageModel
{
    String imageName;
    Bitmap imageBitmap;

    public ImageModel(String imageName, Bitmap imageBitmap)
    {
        this.imageName = imageName;
        this.imageBitmap = imageBitmap;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}
