package com.blinkev.weathertest.data.di

import com.blinkev.weathertest.data.api.WeatherApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)
}