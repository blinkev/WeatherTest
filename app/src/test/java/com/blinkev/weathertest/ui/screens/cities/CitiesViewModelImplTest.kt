package com.blinkev.weathertest.ui.screens.cities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.repo.city.CityRepo
import com.blinkev.weathertest.domain.repo.settings.SettingsRepo
import com.blinkev.weathertest.ui.prepareRxSchedulers
import com.blinkev.weathertest.ui.resetRxSchedulers
import com.blinkev.weathertest.ui.screens.cities.item.CitiesUiMapper
import com.blinkev.weathertest.ui.screens.cities.item.CityEntityMapper
import com.blinkev.weathertest.ui.screens.cities.item.CityItem
import com.blinkev.weathertest.ui.toDataStatus
import com.blinkev.weathertest.ui.toObservableDataStatus
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CitiesViewModelImplTest {

    private lateinit var mockCityRepo: CityRepo
    private lateinit var mockSettingsRepo: SettingsRepo
    private lateinit var mockUiMapper: CitiesUiMapper
    private lateinit var mockEntityMapper: CityEntityMapper

    private lateinit var citiesObserver: Observer<DataStatus<List<CityItem>>>
    private lateinit var firstRunObserver: Observer<DataStatus<Boolean>>

    private lateinit var viewModel: CitiesViewModel

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        prepareRxSchedulers()

        mockCityRepo = mock {}
        mockSettingsRepo = mock {}
        mockUiMapper = mock {}
        mockEntityMapper = mock {
            on { map(any()) }.thenReturn(City("some city"))
        }

        citiesObserver = mock {}
        firstRunObserver = mock {}

        viewModel = CitiesViewModelImpl().apply {
            cityRepo = mockCityRepo
            settingsRepo = mockSettingsRepo
            uiMapper = mockUiMapper
            entityMapper = mockEntityMapper
        }

        viewModel.cities.observeForever(citiesObserver)
        viewModel.isFirstRunSetting.observeForever(firstRunObserver)
    }

    @After
    fun tearDown() {
        resetRxSchedulers()
    }

    @Test
    fun `fetchCities() - when repo succeeds fetching cities we should apply mapped cities to live data object`() {
        val data = City("city")
        whenever(mockCityRepo.getAll()).thenReturn(listOf(data).toObservableDataStatus())
        val mappedData = CityItem(name = "city", entity = data)
        whenever(mockUiMapper.map(listOf(data))).thenReturn(listOf(mappedData))

        viewModel.fetchCities()

        verify(citiesObserver).onChanged(listOf(mappedData).toDataStatus())
    }

    @Test
    fun `fetchCities() - when repo fails fetching cities we should apply error to live data object`() {
        val error = Throwable()
        whenever(mockCityRepo.getAll()).thenReturn(error.toObservableDataStatus())

        viewModel.fetchCities()

        verify(citiesObserver).onChanged(error.toDataStatus())
    }

    @Test
    fun `addCity() - when requesting repo to add new city we should use mapped entity as an argument`() {
        val cityName = "name"
        val mappedEntity = City(cityName)
        whenever(mockEntityMapper.map(cityName)).thenReturn(mappedEntity)
        whenever(mockCityRepo.addCity(mappedEntity)).thenReturn(Observable.never())

        viewModel.addCity(cityName)

        verify(mockCityRepo).addCity(mappedEntity)
    }

    @Test
    fun `addCity() - when repo fails to add city we should apply only error data to live data object`() {
        val error = Throwable()
        whenever(mockCityRepo.addCity(any())).thenReturn(error.toObservableDataStatus())

        viewModel.addCity("city")

        verify(citiesObserver).onChanged(error.toDataStatus())
        verifyNoMoreInteractions(citiesObserver)
    }

    @Test
    fun `addCity() - when repo succeeds adding city we should request updated cities and apply them to live data object`() {
        whenever(mockCityRepo.addCity(any())).thenReturn(Unit.toObservableDataStatus())
        val updatedData = City("city")
        whenever(mockCityRepo.getAll()).thenReturn(listOf(updatedData).toObservableDataStatus())
        val mappedData = CityItem(name = "city", entity = updatedData)
        whenever(mockUiMapper.map(listOf(updatedData))).thenReturn(listOf(mappedData))

        viewModel.addCity("city")

        val order = inOrder(mockCityRepo)
        order.verify(mockCityRepo).addCity(any())
        order.verify(mockCityRepo).getAll()
        verify(citiesObserver).onChanged(listOf(mappedData).toDataStatus())
    }

    @Test
    fun `onCityItemRemoveClick() - when repo fails to remove city we should apply only error data to live data object`() {
        val error = Throwable()
        whenever(mockCityRepo.removeCity(any())).thenReturn(error.toObservableDataStatus())

        viewModel.onCityItemRemoveClick(CityItem(name = "city", entity = City("city")))

        verify(citiesObserver).onChanged(error.toDataStatus())
        verifyNoMoreInteractions(citiesObserver)
    }

    @Test
    fun `onCityItemRemoveClick() - when repo succeeds removing city we should request updated cities and apply them to live data object`() {
        val removingCity = City("removing city")
        whenever(mockCityRepo.removeCity(any())).thenReturn(Unit.toObservableDataStatus())
        val updatedData = City("left city")
        whenever(mockCityRepo.getAll()).thenReturn(listOf(updatedData).toObservableDataStatus())
        val mappedData = CityItem(name = "left city", entity = updatedData)
        whenever(mockUiMapper.map(listOf(updatedData))).thenReturn(listOf(mappedData))

        viewModel.onCityItemRemoveClick(CityItem(name = "removing city", entity = removingCity))

        val order = inOrder(mockCityRepo)
        order.verify(mockCityRepo).removeCity(removingCity)
        order.verify(mockCityRepo).getAll()
        verify(citiesObserver).onChanged(listOf(mappedData).toDataStatus())
    }

    @Test
    fun `fetchFirstRunSetting() - when repo succeeds fetching first run setting we should apply that setting to live data object`() {
        val data = true
        whenever(mockSettingsRepo.getFirstRunSetting()).thenReturn(data.toObservableDataStatus())

        viewModel.fetchFirstRunSetting()

        verify(firstRunObserver).onChanged(data.toDataStatus())
    }

    @Test
    fun `fetchFirstRunSetting() - when repo fails fetching first run setting we should apply error to live data object`() {
        val error = Throwable()
        whenever(mockSettingsRepo.getFirstRunSetting()).thenReturn(error.toObservableDataStatus())

        viewModel.fetchFirstRunSetting()

        verify(firstRunObserver).onChanged(error.toDataStatus())
    }

    @Test
    fun `disableFirstRunSetting() - we should set first run setting to false`() {
        whenever(mockSettingsRepo.setFirstRunSettings(any())).thenReturn(Observable.never())

        viewModel.disableFirstRunSetting()

        verify(mockSettingsRepo).setFirstRunSettings(false)
    }

    @Test
    fun `disableFirstRunSetting() - when repo succeeds disabling first run setting we should request updated setting and apply it to live data object`() {
        whenever(mockSettingsRepo.setFirstRunSettings(any())).thenReturn(Unit.toObservableDataStatus())
        val updatedSetting = false
        whenever(mockSettingsRepo.getFirstRunSetting()).thenReturn(updatedSetting.toObservableDataStatus())

        viewModel.disableFirstRunSetting()

        val order = inOrder(mockSettingsRepo)
        order.verify(mockSettingsRepo).setFirstRunSettings(updatedSetting)
        order.verify(mockSettingsRepo).getFirstRunSetting()
        verify(firstRunObserver).onChanged(updatedSetting.toDataStatus())
    }

    @Test
    fun `disableFirstRunSetting() - when repo fails disabling first run setting we should apply only error data to live data object`() {
        val error = Throwable()
        whenever(mockSettingsRepo.setFirstRunSettings(any())).thenReturn(error.toObservableDataStatus())

        viewModel.disableFirstRunSetting()

        verify(firstRunObserver).onChanged(error.toDataStatus())
        verifyNoMoreInteractions(firstRunObserver)
    }

    @Test
    fun `resolveCity() - when repo succeeds resolving city we should add that city to repo and then get updated cities from repo`() {
        val unknownCityLat = 100500.0
        val unknownCityLon = 200500.0
        val resolvedCityName = "resolved city"
        val resolvedCity = City("resolved city")
        whenever(mockCityRepo.resolveCity(unknownCityLat, unknownCityLon)).thenReturn(resolvedCity.toObservableDataStatus())
        whenever(mockEntityMapper.map(resolvedCityName)).thenReturn(resolvedCity)
        whenever(mockCityRepo.addCity(resolvedCity)).thenReturn(Unit.toObservableDataStatus())
        whenever(mockCityRepo.getAll()).thenReturn(listOf(resolvedCity).toObservableDataStatus())
        val mappedData = CityItem(name = "resolved city", entity = resolvedCity)
        whenever(mockUiMapper.map(listOf(resolvedCity))).thenReturn(listOf(mappedData))

        viewModel.resolveCity(unknownCityLat, unknownCityLon)

        val order = inOrder(mockCityRepo)
        order.verify(mockCityRepo).resolveCity(unknownCityLat, unknownCityLon)
        order.verify(mockCityRepo).addCity(resolvedCity)
        order.verify(mockCityRepo).getAll()
        verify(citiesObserver).onChanged(listOf(mappedData).toDataStatus())
    }

    @Test
    fun `resolveCity() - when repo fails resolving city we should apply only error data to live data object`() {
        val error = Throwable()
        whenever(mockCityRepo.resolveCity(any(), any())).thenReturn(error.toObservableDataStatus())

        viewModel.resolveCity(100500.0, 200500.0)

        verify(citiesObserver).onChanged(error.toDataStatus())
        verifyNoMoreInteractions(citiesObserver)
    }
}