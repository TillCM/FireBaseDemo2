package com.tillster.betterweatherv2;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.label305.asynctask.SimpleAsyncTask;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;
import com.tillster.betterweatherv2.Common.Common;
import com.tillster.betterweatherv2.Models.WeatherResults;
import com.tillster.betterweatherv2.Retrofit.IOpenWeather;
import com.tillster.betterweatherv2.Retrofit.RetrofitClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class city_weather extends Fragment {

    private List<String> cityNames;
    ImageView imgCityWeather;
    TextView cityTemp, cityDesc, cityDate, cityName;
    MaterialSearchBar citySearchbar;
    CardView  cityCardView;
    CompositeDisposable compositeDisposable;
    IOpenWeather service;

    static city_weather instance;

    public static city_weather  getInstance()
    {
        if(instance == null)
            return new city_weather();
        return instance;
    }


    public city_weather() 
    {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        service = retrofit.create(IOpenWeather.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_city_weather, container, false);
        imgCityWeather = view.findViewById(R.id.img_city_weather);
        cityTemp = view.findViewById(R.id.city_temp);
        cityDesc = view.findViewById(R.id.city_description);
        cityDate = view.findViewById(R.id.city_date);
        cityName = view.findViewById(R.id.current_city_name);
        citySearchbar = view.findViewById(R.id.mt_searchbar_city);
        cityCardView = view.findViewById(R.id.cardV_city);
        
        cityCardView.setVisibility(View.GONE);

        new LoadCites().execute();

        return view;
    }

    private class LoadCites extends SimpleAsyncTask<List<String>>
    {



        @Override
        protected List<String> doInBackgroundSimple()
        {
           cityNames = new ArrayList<>();

           try
               {
                   StringBuilder builder = new StringBuilder();
                   // get the zipped file from the raw folder
                   InputStream in = getResources().openRawResource(R.raw.city_list);
                   //unzip the file
                   GZIPInputStream gzipInputStream = new GZIPInputStream(in);
                   //read the file
                   InputStreamReader reader = new InputStreamReader(gzipInputStream);
                   //buffer the file read action because this is a large file
                   BufferedReader finalReader = new BufferedReader(reader);

                   String cities;

                   while ((cities = finalReader.readLine())!=null)
                   {
                       builder.append(cities);

                       Log.d("JSON", "doInBackgroundSimple: " + cities);
                       cityNames = new Gson().fromJson(builder.toString(), new TypeToken<List<String>>(){

                       }.getType());
                   }


               }

           catch (Exception e)
               {
                   Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
               }


            return cityNames;
        }


        @Override
        protected void onSuccess(final List<String> cityNames)
        {
            super.onSuccess(cityNames);
            
            citySearchbar.setEnabled(true);
            
            //add TextChanged Listener
            
            citySearchbar.addTextChangeListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) 
                {
                    // do nothing 
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    List<String> suggetionsList  = new ArrayList<>();
                    
                    for (String search : cityNames)
                    {
                        if(search.toLowerCase().contains(citySearchbar.getText().toLowerCase()))
                        {
                            suggetionsList.add(search);
                        }
                    }

                }

                @Override
                public void afterTextChanged(Editable s) 
                {

                }
            });
            
            citySearchbar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                @Override
                public void onSearchStateChanged(boolean enabled) 
                {
                  // do nothing   
                }

                @Override
                public void onSearchConfirmed(CharSequence text) 
                {
                    cityCardView.setVisibility(View.VISIBLE);
                    getWeatherbyCity(text.toString());

                }

                @Override
                public void onButtonClicked(int buttonCode) 
                {

                }
            });
            
            citySearchbar.setLastSuggestions(cityNames);
            
        }
    }

    private void getWeatherbyCity(String city)
    {
        compositeDisposable.add(service.getWeatherByCity(city, Common.APP_ID,"metric")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<WeatherResults>() {
                       @Override
                       public void accept(WeatherResults weatherResults) throws Exception
                       {

                           // get weather icon
                           Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                   .append(weatherResults.getWeather().get(0).getIcon())
                                   .append(".png").toString()).into(imgCityWeather);

                           cityTemp.setText(String.valueOf(weatherResults.getMain().getTemp()));
                           cityDesc.setText(String.valueOf(new StringBuilder( String.valueOf(weatherResults.getWeather()
                           .get(0).getDescription()))));
                           cityDate.setText(new StringBuilder(Common.convertTime(weatherResults
                                   .getDt())));

                       }
                   }
                , new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception
                    {
                        Log.d("CityError", "accept: " + throwable.getMessage());
                        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));

    }


}