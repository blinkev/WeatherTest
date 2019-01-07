package com.blinkev.weathertest.data.di

import com.blinkev.weathertest.data.query.city.di.CityRepoMapperModule
import com.blinkev.weathertest.data.query.city.mapper.CityListPrefMapper
import com.blinkev.weathertest.data.query.city.mapper.CityListPrefMapperImpl
import com.blinkev.weathertest.data.query.weather.mapper.di.WeatherMapperModule
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [WeatherMapperModule::class, CityRepoMapperModule::class])
class MapperModule