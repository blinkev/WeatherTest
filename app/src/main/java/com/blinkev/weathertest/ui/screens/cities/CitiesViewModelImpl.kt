package com.blinkev.weathertest.ui.screens.cities

import androidx.lifecycle.MutableLiveData
import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.repo.city.CityRepo
import com.blinkev.weathertest.domain.util.changeGeneric
import com.blinkev.weathertest.domain.util.flatMapData
import com.blinkev.weathertest.domain.util.mapData
import com.blinkev.weathertest.ui.BaseViewModelImpl
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponent
import com.blinkev.weathertest.ui.screens.cities.item.CitiesUiMapper
import com.blinkev.weathertest.ui.screens.cities.item.CityEntityMapper
import com.blinkev.weathertest.ui.screens.cities.item.CityItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CitiesViewModelImpl @Inject constructor() : BaseViewModelImpl<CitiesFragmentComponent>(),
        CitiesViewModel {

    companion object {
        private const val GET_OPERATION = "GET_OPERATION"
        private const val ADD_OPERATION = "ADD_OPERATION"
        private const val REMOVE_OPERATION = "REMOVE_OPERATION"
    }

    @Inject
    lateinit var cityRepo: CityRepo
    @Inject
    lateinit var router: CitiesRouter
    @Inject
    lateinit var uiMapper: CitiesUiMapper
    @Inject
    lateinit var entityMapper: CityEntityMapper

    override val cities = MutableLiveData<DataStatus<List<CityItem>>>()

    override fun fetchCities() {
        cityRepo.getAll()
            .mapData(uiMapper::map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .trackAndSubscribe(operationId = GET_OPERATION.hashCode()) {
                cities.value = it
            }
    }

    override fun addCity(name: String) {
        cityRepo.addCity(entityMapper.map(name))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .trackAndSubscribe(operationId = ADD_OPERATION.hashCode()) {
                when (it) {
                    is DataStatus.Data -> fetchCities()
                    is DataStatus.Loading -> cities.value = it.changeGeneric()
                    is DataStatus.Error -> cities.value = it.changeGeneric()
                }
            }
    }

    override fun onCityItemClick(item: CityItem) {
        router.routeToCityWeather(item.entity)
    }

    override fun onCityItemRemoveClick(item: CityItem) {
        cityRepo.removeCity(item.entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .trackAndSubscribe(operationId = REMOVE_OPERATION.hashCode()) {
                when (it) {
                    is DataStatus.Data -> fetchCities()
                    is DataStatus.Loading -> cities.value = it.changeGeneric()
                    is DataStatus.Error -> cities.value = it.changeGeneric()
                }
            }
    }
}