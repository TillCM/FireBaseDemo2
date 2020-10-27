package com.tillster.betterweatherv2.Retrofit;

import com.tillster.betterweatherv2.Models.ForecastResult;
import com.tillster.betterweatherv2.Models.WeatherResults;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather
{
    @GET("weather")
    Observable<WeatherResults> getWeatherWithLatandLon(@Query("lat") String lat,
                                                       @Query("lon") String lon,
                                                       @Query("appid") String appid,
                                                       @Query("units") String units);


    @GET("weather")
    Observable<WeatherResults> getWeatherByCity(@Query("q") String cityName,
                                                       @Query("appid") String appid,
                                                       @Query("units") String units);


    @GET("forecast")
    Observable<ForecastResult> getForecastWithLatandLon(@Query("lat") String lat,
                                                        @Query("lon") String lon,
                                                        @Query("appid") String appid,
                                                        @Query("units") String units);


}
