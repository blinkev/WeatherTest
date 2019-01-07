package com.blinkev.weathertest.ui.screens.activity.di

import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponent
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponentProvider
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentModule
import com.blinkev.weathertest.ui.screens.weather.di.WeatherFragmentComponent
import com.blinkev.weathertest.ui.screens.weather.di.WeatherFragmentComponentProvider
import com.blinkev.weathertest.ui.screens.weather.di.WeatherFragmentModule

interface HostActivityChildComponentProvider : CitiesFragmentComponentProvider,
        WeatherFragmentComponentProvider {

    val screenComponent: HostActivityComponent

    override fun provide(module: CitiesFragmentModule): CitiesFragmentComponent =
            screenComponent.provide(module)

    override fun provide(module: WeatherFragmentModule): WeatherFragmentComponent =
            screenComponent.provide(module)
}