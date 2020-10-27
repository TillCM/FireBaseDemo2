package com.tillster.betterweatherv2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tillster.betterweatherv2.Common.Common;
import com.tillster.betterweatherv2.Models.ForecastResult;
import com.tillster.betterweatherv2.R;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {

    Context context;
    ForecastResult forecastResult;

    public ForecastAdapter(Context context, ForecastResult forecastResult) {
        this.context = context;
        this.forecastResult = forecastResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemview = LayoutInflater.from(context).inflate(R.layout.five_day_weather_items
        ,parent,false);


        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
        .append(forecastResult.list.get(position).weather.get(0).getIcon())
        .append(".png").toString()).into(holder.img_five_day_weather);

        holder.five_day_temp.setText(new StringBuilder(String.valueOf(forecastResult.list.get(position).main.getTemp()))
        .append("°C"));

        holder.five_day_description.setText(new StringBuilder(String.valueOf(forecastResult.list.get(position)
        .weather.get(0).getDescription())));

        holder.five_day_date.setText(new StringBuilder(Common.convertTime(forecastResult.list.get(position)
        .getDt())));
    }

    @Override
    public int getItemCount()
    {
        return forecastResult.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_five_day_weather;
        TextView five_day_temp, five_day_description, five_day_date;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            img_five_day_weather = itemView.findViewById(R.id.img_five_day_weather);
            five_day_temp = itemView.findViewById(R.id.five_day_temp);
            five_day_description = itemView.findViewById(R.id.five_day_description);
            five_day_date = itemView.findViewById(R.id.five_date);

        }
    }
}
