package com.blinkev.weathertest.ui.screens.weather

import androidx.lifecycle.MutableLiveData
import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.repo.weather.CityWeatherRepo
import com.blinkev.weathertest.domain.util.mapData
import com.blinkev.weathertest.ui.BaseViewModelImpl
import com.blinkev.weathertest.ui.screens.weather.di.WeatherFragmentComponent
import com.blinkev.weathertest.ui.screens.weather.item.mapper.WeatherMapper
import com.github.nitrico.lastadapter.StableId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherViewModelImpl @Inject constructor() : BaseViewModelImpl<WeatherFragmentComponent>(),
        WeatherViewModel {

    @Inject
    lateinit var weatherRepo: CityWeatherRepo
    @set:Inject
    var city: City? = null
    @Inject
    lateinit var mapper: WeatherMapper

    override val weather = MutableLiveData<DataStatus<List<StableId>>>()

    override fun fetchWeather() {
        city?.let { city ->
            weatherRepo.getWeather(city)
                    .mapData(mapper::map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .trackAndSubscribe(operationId = weatherRepo.hashCode()) {
                        weather.value = it
                    }
        }
    }
}