package com.tillster.fakestagram.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tillster.fakestagram.Models.ImageModel;
import com.tillster.fakestagram.R;

import java.util.ArrayList;
import java.util.List;

public class CloudPhotoViewAdapter extends RecyclerView.Adapter<CloudPhotoViewAdapter.MyViewHolder> {

    List<ImageModel> photolist = new ArrayList<>();
    ImageModel imageModel;
    Context context;

    public CloudPhotoViewAdapter(List<ImageModel> photolist, Context context) {
        this.photolist = photolist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(context).inflate(R.layout.photo_itmes,parent,false);

        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        ImageModel model = photolist.get(position);
        holder.localImageName.setText(model.getImageName());
        Picasso.get().load(model.getImageUrl()).fit()
                .centerCrop().into(holder.localImage);

        holder.localImageName.setText(model.getImageName());




    }

        @Override
        public int getItemCount() {
            return photolist.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder
        {

            ImageView localImage;
            TextView localImageName;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            localImage = itemView.findViewById(R.id.img_viewImagePane);
            localImageName = itemView.findViewById(R.id.txt_SqlDisplayImageName);




        }
    }
}
