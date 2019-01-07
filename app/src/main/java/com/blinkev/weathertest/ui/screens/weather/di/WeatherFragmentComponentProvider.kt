package com.blinkev.weathertest.ui.screens.weather.di

interface WeatherFragmentComponentProvider {
    fun provide(module: WeatherFragmentModule): WeatherFragmentComponent
}