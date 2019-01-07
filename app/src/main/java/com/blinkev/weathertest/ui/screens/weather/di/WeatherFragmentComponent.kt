package com.blinkev.weathertest.ui.screens.weather.di

import com.blinkev.weathertest.ui.di.scope.ActivityScope
import com.blinkev.weathertest.ui.di.scope.FragmentScope
import com.blinkev.weathertest.ui.screens.cities.CitiesFragment
import com.blinkev.weathertest.ui.screens.cities.CitiesViewModelImpl
import com.blinkev.weathertest.ui.screens.weather.WeatherFragment
import com.blinkev.weathertest.ui.screens.weather.WeatherViewModelImpl
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [WeatherFragmentModule::class])
interface WeatherFragmentComponent {
    fun inject(fragment: WeatherFragment)
    fun inject(viewModel: WeatherViewModelImpl)
}