package com.blinkev.weathertest.ui.screens.weather.item.formatter.di

import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.ui.di.scope.ActivityScope
import com.blinkev.weathertest.ui.di.scope.FragmentScope
import com.blinkev.weathertest.ui.screens.cities.CitiesViewModel
import com.blinkev.weathertest.ui.screens.cities.item.CitiesUiMapper
import com.blinkev.weathertest.ui.screens.cities.item.CitiesUiMapperImpl
import com.blinkev.weathertest.ui.screens.cities.item.CityEntityMapper
import com.blinkev.weathertest.ui.screens.cities.item.CityEntityMapperImpl
import com.blinkev.weathertest.ui.screens.weather.WeatherViewModel
import com.blinkev.weathertest.ui.screens.weather.item.formatter.*
import dagger.Module
import dagger.Provides
import java.time.format.DateTimeFormatter

@Module
class WeatherFormatterModule {

    @FragmentScope
    @Provides
    fun provideDayFormatter(formatter: DayFormatterImpl): DayFormatter = formatter

    @FragmentScope
    @Provides
    fun provideHourFormatter(formatter: HourFormatterImpl): HourFormatter = formatter


    @FragmentScope
    @Provides
    fun provideTemperatureFormatter(formatter: TemperatureFormatterImpl): TemperatureFormatter =
            formatter
}