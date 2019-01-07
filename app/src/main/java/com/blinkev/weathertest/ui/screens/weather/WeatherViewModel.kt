package com.blinkev.weathertest.ui.screens.weather

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.ui.BaseViewModel
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponent
import com.blinkev.weathertest.ui.screens.cities.item.CityItem
import com.blinkev.weathertest.ui.screens.weather.di.WeatherFragmentComponent
import com.github.nitrico.lastadapter.StableId
import java.math.BigDecimal

interface WeatherViewModel : BaseViewModel<WeatherFragmentComponent> {
    val weather: LiveData<DataStatus<List<StableId>>>

    fun fetchWeather()
}