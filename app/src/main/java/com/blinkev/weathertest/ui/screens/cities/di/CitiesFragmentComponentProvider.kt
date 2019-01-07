package com.blinkev.weathertest.ui.screens.cities.di

import com.blinkev.weathertest.ui.screens.weather.di.WeatherFragmentComponent
import com.blinkev.weathertest.ui.screens.weather.di.WeatherFragmentModule

interface CitiesFragmentComponentProvider {
    fun provide(module: CitiesFragmentModule): CitiesFragmentComponent
    fun provide(module: WeatherFragmentModule): WeatherFragmentComponent
}