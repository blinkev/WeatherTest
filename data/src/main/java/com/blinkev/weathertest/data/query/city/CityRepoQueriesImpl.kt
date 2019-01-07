package com.blinkev.weathertest.data.query.city

import android.content.Context
import com.blinkev.weathertest.data.api.WeatherApi
import com.blinkev.weathertest.data.query.city.mapper.CityListPrefMapper
import com.blinkev.weathertest.data.query.city.mapper.LocationMapper
import com.blinkev.weathertest.data.query.city.mapper.ResolveCityNameRespMapper
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.exception.StorageException
import com.blinkev.weathertest.domain.repo.city.AddCityQuery
import com.blinkev.weathertest.domain.repo.city.GetCityListQuery
import com.blinkev.weathertest.domain.repo.city.RemoveCityQuery
import com.blinkev.weathertest.domain.util.empty
import io.reactivex.Observable
import javax.inject.Inject

class CityRepoQueriesImpl @Inject constructor(
    private val appContext: Context,
    private val cityListMapper: CityListPrefMapper,
    private val api: WeatherApi,
    private val locationMapper: LocationMapper,
    private val resolveCityRespMapper: ResolveCityNameRespMapper
) : CityRepoQueries {

    companion object {
        private const val CITY_LIST_PREF_NAME = "CITY_LIST_PREF_NAME"
        private const val CITY_LIST = "CITY_LIST"
    }

    override fun add(city: City): Observable<Unit> =
        Observable.fromCallable {
            synchronized(this@CityRepoQueriesImpl) {
                saveCitiesToPref(getCitiesFromPref() + listOf(city))
            }
        }

    override fun get(): Observable<List<City>> =
        Observable.fromCallable {
            synchronized(this@CityRepoQueriesImpl) {
                getCitiesFromPref()
            }
        }

    override fun remove(city: City): Observable<Unit> =
        Observable.fromCallable {
            saveCitiesToPref(
                getCitiesFromPref().filterNot { it == city }
            )
        }

    override fun resolve(lat: Double, lon: Double): Observable<City> = api
        .resolveCityName(locationMapper.map(lat, lon))
        .map(resolveCityRespMapper::map)

    private fun getCitiesFromPref(): List<City> = appContext
        .getSharedPreferences(CITY_LIST_PREF_NAME, Context.MODE_PRIVATE)
        .getString(CITY_LIST, String.empty())
        ?.let(cityListMapper::mapFromPref)
        ?: emptyList()

    private fun saveCitiesToPref(cities: List<City>) {
        val result = appContext
            .getSharedPreferences(CITY_LIST_PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(CITY_LIST, cityListMapper.mapToPref(cities))
            .commit()

        if (!result) throw StorageException()
    }
}