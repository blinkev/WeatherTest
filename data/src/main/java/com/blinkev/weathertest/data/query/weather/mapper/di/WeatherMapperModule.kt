package com.blinkev.weathertest.data.query.weather.mapper.di

import com.blinkev.weathertest.data.query.weather.mapper.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherMapperModule {

    @Singleton
    @Provides
    fun provideDateMapper(mapper: DateMapperImpl): DateMapper = mapper

    @Singleton
    @Provides
    fun provideGetCityWeatherRespToEntityMapper(mapper: GetCityWeatherRespMapperImpl)
            : GetCityWeatherRespMapper = mapper

    @Singleton
    @Provides
    fun provideHourWeatherDtoToEntityMapper(mapper: HourWeatherDtoToEntityMapperImpl): HourWeatherDtoToEntityMapper =
        mapper

    @Singleton
    @Provides
    fun provideTimeMapper(mapper: TimeMapperImpl): TimeMapper = mapper
}