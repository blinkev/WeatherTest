package com.blinkev.weathertest.ui.screens.weather.di

import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.ui.di.scope.ActivityScope
import com.blinkev.weathertest.ui.di.scope.FragmentScope
import com.blinkev.weathertest.ui.screens.cities.CitiesViewModel
import com.blinkev.weathertest.ui.screens.cities.item.CitiesUiMapper
import com.blinkev.weathertest.ui.screens.cities.item.CitiesUiMapperImpl
import com.blinkev.weathertest.ui.screens.cities.item.CityEntityMapper
import com.blinkev.weathertest.ui.screens.cities.item.CityEntityMapperImpl
import com.blinkev.weathertest.ui.screens.weather.WeatherViewModel
import com.blinkev.weathertest.ui.screens.weather.item.formatter.di.WeatherFormatterModule
import com.blinkev.weathertest.ui.screens.weather.item.mapper.WeatherMapper
import com.blinkev.weathertest.ui.screens.weather.item.mapper.WeatherMapperImpl
import dagger.Module
import dagger.Provides

@Module(includes = [WeatherFormatterModule::class])
class WeatherFragmentModule(private val viewModel: WeatherViewModel) {

    @FragmentScope
    @Provides
    fun provideViewModel(): WeatherViewModel = viewModel

    @FragmentScope
    @Provides
    fun provideMapper(mapper: WeatherMapperImpl): WeatherMapper = mapper
}