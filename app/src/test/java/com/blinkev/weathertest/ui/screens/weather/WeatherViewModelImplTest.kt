package com.blinkev.weathertest.ui.screens.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.entity.CityWeather
import com.blinkev.weathertest.domain.repo.weather.CityWeatherRepo
import com.blinkev.weathertest.ui.*
import com.blinkev.weathertest.ui.screens.cities.CitiesViewModel
import com.blinkev.weathertest.ui.screens.cities.CitiesViewModelImpl
import com.blinkev.weathertest.ui.screens.cities.item.CityItem
import com.blinkev.weathertest.ui.screens.weather.item.mapper.WeatherMapper
import com.github.nitrico.lastadapter.StableId
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherViewModelImplTest {

    private lateinit var mockWeatherRepo: CityWeatherRepo
    private lateinit var mockWeatherMapper: WeatherMapper

    private lateinit var weatherObserver: Observer<DataStatus<List<StableId>>>

    private lateinit var viewModel: WeatherViewModel

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        prepareRxSchedulers()

        mockWeatherRepo = mock {}
        mockWeatherMapper = mock {}

        weatherObserver = mock {}

        viewModel = WeatherViewModelImpl().apply {
            weatherRepo = mockWeatherRepo
            mapper = mockWeatherMapper
        }

        viewModel.weather.observeForever(weatherObserver)
    }

    @After
    fun tearDown() {
        resetRxSchedulers()
    }

    @Test
    fun `fetchWeather() - when repo succeeds fetching weather we should apply mapped weather to live data object`() {
        val data = CityWeather.mock()
        whenever(mockWeatherRepo.getWeather(any())).thenReturn(listOf(data).toObservableDataStatus())
        val mappedData = mock<StableId>()
        whenever(mockWeatherMapper.map(listOf(data))).thenReturn(listOf(mappedData))

        viewModel.fetchWeather(City("city"))

        verify(weatherObserver).onChanged(listOf(mappedData).toDataStatus())
    }

    @Test
    fun `fetchWeather() - when repo fails fetching weather we should apply error to live data object`() {
        val error = Throwable()
        whenever(mockWeatherRepo.getWeather(any())).thenReturn(error.toObservableDataStatus())

        viewModel.fetchWeather(City("city"))

        verify(weatherObserver).onChanged(error.toDataStatus())
    }
}