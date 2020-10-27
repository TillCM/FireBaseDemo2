package com.tillster.betterweatherv2;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tillster.betterweatherv2.Common.Common;
import com.tillster.betterweatherv2.Common.ProcessImage;
import com.tillster.betterweatherv2.Models.WeatherResults;
import com.tillster.betterweatherv2.Permissions.BetterPermissions;
import com.tillster.betterweatherv2.Retrofit.IOpenWeather;
import com.tillster.betterweatherv2.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.tillster.betterweatherv2.MainActivity.PERM_REQEUSTCODE;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class current_weather extends Fragment {

    String TAG = "JSONERROR";
    final int  REQUEST_CODE=102;
    CompositeDisposable compositeDisposable;
    IOpenWeather service;
    ImageView weather_image, instagram;
    TextView city_name, wind_speed, pressure, humidity, sunrise, sunset, location, weather_value;
    ProcessImage processImage;
    View rootview;



     static current_weather instance;

    public static current_weather  getInstance()
    {
        if(instance == null)
            return new current_weather();
        return instance;
    }

    public current_weather()
    {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        service = retrofit.create(IOpenWeather.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.current_weather_fragment,container,false);

        city_name = view.findViewById(R.id.current_city_name);
        weather_value = view.findViewById(R.id.weather_value);
        wind_speed = view.findViewById(R.id.wind_speed);
        pressure = view.findViewById(R.id.pressure);
        humidity = view.findViewById(R.id.humidity);
        sunrise = view.findViewById(R.id.sunrise);
        sunset = view.findViewById(R.id.sunset);
        location = view.findViewById(R.id.location);
        weather_image = view.findViewById(R.id.weather_img);
        instagram = view.findViewById(R.id.btn_instagram);
        rootview = getActivity().getWindow().getDecorView().getRootView();
        processImage = new ProcessImage();

        getWeatherInformation();

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String [] Permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                if (!BetterPermissions.hasPermissions(getContext(),Permissions))
                {
                    ActivityCompat.requestPermissions(getActivity(), Permissions, REQUEST_CODE);
                }

               Bitmap  currentScreenshot =  processImage.takeScreenshot(rootview);

                processImage.storeScreenshot(getContext(),currentScreenshot,"Today Weather");

                processImage.pushToInstagram(getContext(),"/Today Weather");

            }
        });



        return view;
    }

    private void getWeatherInformation()
    {
        compositeDisposable.add(service.getWeatherWithLatandLon(String.valueOf(Common.currentLocation.getLatitude()),
                String.valueOf(Common.currentLocation.getLongitude()),Common.APP_ID,"metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResults>()
                           {
                               @Override
                               public void accept(WeatherResults weatherResults) throws Exception
                               {
                                   Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                           .append(weatherResults.getWeather().get(0).getIcon())
                                   .append(".png").toString()).into(weather_image);
                                   weather_value.setText(String.valueOf(weatherResults.getMain().getTemp()));

                                   pressure.setText(new StringBuilder(String.valueOf(weatherResults
                                           .getMain().getPressure())).append(" hpa").toString());


                                   wind_speed.setText(new StringBuilder(String.valueOf(weatherResults
                                   .getWind().getSpeed())).append(" m/s").toString());

                                   humidity.setText(new StringBuilder(String.valueOf(weatherResults
                                           .getMain().getHumidity())).append(" %").toString());

                                   sunrise.setText(Common.convertTime(weatherResults.getSys().getSunrise()));

                                   sunset.setText(Common.convertTime(weatherResults.getSys().getSunrise()));

                                   location.setText(new StringBuilder("Lat an& Lon" ).append(weatherResults.getCoord()));


                               }


                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception
                               {
                                   Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();

                               }
                           }
                ));



    }


}
