package com.blinkev.weathertest.data.query.weather.mapper.di

import com.blinkev.weathertest.data.api.WeatherApi
import com.blinkev.weathertest.data.query.weather.mapper.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class WeatherMapperModule {

    @Singleton
    @Provides
    fun provideDateMapper(mapper: DateMapperImpl): DateMapper = mapper

    @Singleton
    @Provides
    fun provideGetCityWeatherRespToEntityMapper(mapper: GetCityWeatherRespToEntityMapperImpl)
            : GetCityWeatherRespToEntityMapper = mapper

    @Singleton
    @Provides
    fun provideHourWeatherDtoToEntityMapper(mapper: HourWeatherDtoToEntityMapperImpl): HourWeatherDtoToEntityMapper =
        mapper

    @Singleton
    @Provides
    fun provideTimeMapper(mapper: TimeMapperImpl): TimeMapper = mapper
}