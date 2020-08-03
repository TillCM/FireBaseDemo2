package com.tillster.fakestagramvideo.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tillster.fakestagramvideo.Models.ImageModel;
import com.tillster.fakestagramvideo.R;

import java.util.ArrayList;
import java.util.List;

public class LocalPicAdapter extends RecyclerView.Adapter<LocalPicAdapter.MyViewHolder> {

    Context context;
    List<ImageModel> photoList = new ArrayList<>();

    public LocalPicAdapter(){}

    public LocalPicAdapter(Context context, List<ImageModel> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View itemview = LayoutInflater.from(context).inflate(R.layout.local_pics_items,parent,
               false);

        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {

        ImageModel imageModel =photoList.get(position);

        holder.localImageName.setText(imageModel.getImageName());


    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView localImage;
        TextView localImageName;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            localImage = itemView.findViewById(R.id.adapter_image);
            localImageName = itemView.findViewById(R.id.adapter_imageName);


        }
    }
}
