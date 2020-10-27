package com.tillster.fakestagram.Models;

import android.graphics.Bitmap;

public class ImageModel
{
    String imageName;
    Bitmap imageBitmap;
    String imageUrl;
    String userID;

    public ImageModel(){}

    public ImageModel(String imageName, Bitmap imageBitmap)
    {
        this.imageName = imageName;
        this.imageBitmap = imageBitmap;
    }

    public ImageModel(String imageName, String imageUrl) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public ImageModel(String imageName, String imageUrl, String userID) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.userID = userID;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
