package com.blinkev.weathertest.ui.screens.cities

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.repo.city.CityRepo
import com.blinkev.weathertest.domain.repo.settings.SettingsRepo
import com.blinkev.weathertest.domain.util.changeGeneric
import com.blinkev.weathertest.domain.util.flatMapData
import com.blinkev.weathertest.domain.util.mapData
import com.blinkev.weathertest.ui.BaseViewModelImpl
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponent
import com.blinkev.weathertest.ui.screens.cities.item.CitiesUiMapper
import com.blinkev.weathertest.ui.screens.cities.item.CityEntityMapper
import com.blinkev.weathertest.ui.screens.cities.item.CityItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CitiesViewModelImpl @Inject constructor() : BaseViewModelImpl<CitiesFragmentComponent>(),
        CitiesViewModel {

    companion object {
        private const val CITY_GET_OPERATION = "CITY_GET_OPERATION"
        private const val CITY_ADD_OPERATION = "CITY_ADD_OPERATION"
        private const val CITY_REMOVE_OPERATION = "CITY_REMOVE_OPERATION"
        private const val CITY_RESOLVE_OPERATION = "CITY_RESOLVE_OPERATION"

        private const val SETTINGS_GET_OPERATION = "SETTINGS_GET_OPERATION"
        private const val SETTINGS_SET_OPERATION = "SETTINGS_SET_OPERATION"
    }

    @Inject
    lateinit var cityRepo: CityRepo
    @Inject
    lateinit var settingsRepo: SettingsRepo
    @Inject
    lateinit var uiMapper: CitiesUiMapper
    @Inject
    lateinit var entityMapper: CityEntityMapper

    override val cities = MutableLiveData<DataStatus<List<CityItem>>>()
    override val isFirstRunSetting = MutableLiveData<DataStatus<Boolean>>()

    override fun fetchCities() {
        cityRepo.getAll()
            .mapData(uiMapper::map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .trackAndSubscribe(operationId = CITY_GET_OPERATION.hashCode()) {
                cities.value = it
            }
    }

    override fun addCity(name: String) {
        cityRepo.addCity(entityMapper.map(name))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .trackAndSubscribe(operationId = CITY_ADD_OPERATION.hashCode()) {
                when (it) {
                    is DataStatus.Data -> fetchCities()
                    is DataStatus.Loading -> cities.value = it.changeGeneric()
                    is DataStatus.Error -> cities.value = it.changeGeneric()
                }
            }
    }

    override fun onCityItemRemoveClick(item: CityItem) {
        cityRepo.removeCity(item.entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .trackAndSubscribe(operationId = CITY_REMOVE_OPERATION.hashCode()) {
                when (it) {
                    is DataStatus.Data -> fetchCities()
                    is DataStatus.Loading -> cities.value = it.changeGeneric()
                    is DataStatus.Error -> cities.value = it.changeGeneric()
                }
            }
    }

    override fun fetchFirstRunSetting() {
        settingsRepo.getFirstRunSetting()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .trackAndSubscribe(operationId = SETTINGS_GET_OPERATION.hashCode()) {
                isFirstRunSetting.value = it
            }
    }

    override fun disableFirstRunSetting() {
        settingsRepo.setFirstRunSettings(false)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .trackAndSubscribe(operationId = SETTINGS_SET_OPERATION.hashCode()) {
                when (it) {
                    is DataStatus.Data -> fetchFirstRunSetting()
                    is DataStatus.Loading -> isFirstRunSetting.value = it.changeGeneric()
                    is DataStatus.Error -> isFirstRunSetting.value = it.changeGeneric()
                }
            }
    }

    override fun detectCity(lat: Double, lon: Double) {
        cityRepo.resolveCity(lat, lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .trackAndSubscribe(operationId = CITY_RESOLVE_OPERATION.hashCode()) {
                when (it) {
                    is DataStatus.Data -> addCity(it.data.name)
                    is DataStatus.Loading -> cities.value = it.changeGeneric()
                    is DataStatus.Error -> cities.value = it.changeGeneric()
                }
            }
    }
}