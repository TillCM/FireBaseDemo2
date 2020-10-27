package com.tillster.betterweatherv2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tillster.betterweatherv2.Adapters.ForecastAdapter;
import com.tillster.betterweatherv2.Common.Common;
import com.tillster.betterweatherv2.Models.ForecastResult;
import com.tillster.betterweatherv2.Retrofit.IOpenWeather;
import com.tillster.betterweatherv2.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class five_day_forecast extends Fragment
{
    TextView city;
    RecyclerView five_day_recycler;
    CompositeDisposable compositeDisposable;
    IOpenWeather service;

    private static five_day_forecast instance;

    public static five_day_forecast getInstance()
    {
        if(instance == null)
            return new five_day_forecast();
        return instance;

    }

    public five_day_forecast()
    {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        service = retrofit.create(IOpenWeather.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View itemview = inflater.inflate(R.layout.five_day_forecast_fragment, container, false);

        city = itemview.findViewById(R.id.txt_five_day_city);
        five_day_recycler = itemview.findViewById(R.id.recycler_five_day);

        five_day_recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        five_day_recycler.setHasFixedSize(true);

        getFiveDayForecastInfo();

        return itemview;
    }

    private void getFiveDayForecastInfo()
    {
        compositeDisposable.add(service.getForecastWithLatandLon(String.valueOf(Common.currentLocation.getLatitude()),
                String.valueOf(Common.currentLocation.getLongitude()),Common.APP_ID, "metric")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<ForecastResult>() {
            @Override
            public void accept(ForecastResult forecastResult) throws Exception
            {
                displayView(forecastResult);

            }
        }, new Consumer<Throwable>()
        {
            @Override
            public void accept(Throwable throwable) throws Exception
            {
                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error in Five Day JSON", "accept: " + throwable.getMessage());
            }
        }));
    }

    private void displayView(ForecastResult forecastResult)
    {
        city.setText(forecastResult.city.name);

        ForecastAdapter adapter = new ForecastAdapter(getContext(),forecastResult);
        five_day_recycler.setAdapter(adapter);

    }
}
