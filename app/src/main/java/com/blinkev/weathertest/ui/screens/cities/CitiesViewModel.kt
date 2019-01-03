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

    fun fetchCities()
    fun addCity(name: String)
    fun onCityItemClick(item: CityItem)
    fun onCityItemRemoveClick(item: CityItem)
}