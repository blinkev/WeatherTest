package com.blinkev.weathertest.ui.screens.cities

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.ui.BaseViewModel
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponent
import com.blinkev.weathertest.ui.screens.cities.item.CityItem
import java.math.BigDecimal

interface CitiesViewModel : BaseViewModel<CitiesFragmentComponent> {
    val cities: LiveData<DataStatus<List<CityItem>>>
    val isFirstRunSetting: LiveData<DataStatus<Boolean>>

    fun fetchFirstRunSetting()
    fun disableFirstRunSetting()

    fun detectCity(lat: Double, lon: Double)

    fun fetchCities()
    fun addCity(name: String)
    fun onCityItemRemoveClick(item: CityItem)
}