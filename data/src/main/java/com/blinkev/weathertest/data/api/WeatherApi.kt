package com.blinkev.weathertest.data.api

import android.os.Build
import com.blinkev.weathertest.data.BuildConfig
import com.blinkev.weathertest.data.dto.GetCityWeatherResp
import com.blinkev.weathertest.data.dto.ResolveCityNameResp
import com.blinkev.weathertest.domain.repo.weather.GetCityWeatherQuery
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("premium/v1/weather.ashx?format=json&num_of_days=5")
    fun getCityWeather(
        @Query("q") cityName: String,
        @Query("key") apiKey: String = BuildConfig.WEATHER_API_KEY
    ): Observable<GetCityWeatherResp>

    @GET("premium/v1/search.ashx?num_of_result=1&format=json")
    fun resolveCityName(
        @Query("query") location: String,
        @Query("key") apiKey: String = BuildConfig.WEATHER_API_KEY
    ): Observable<ResolveCityNameResp>
}