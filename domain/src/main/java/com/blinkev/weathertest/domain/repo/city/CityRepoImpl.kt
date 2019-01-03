package com.blinkev.weathertest.domain.repo.city

import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.util.toDataStatus
import io.reactivex.Observable
import javax.inject.Inject

class CityRepoImpl @Inject constructor(
    private val getQuery: GetCityListQuery,
    private val addQuery: AddCityQuery,
    private val removeQuery: RemoveCityQuery
): CityRepo {

    override fun getAll(): Observable<DataStatus<List<City>>> = getQuery.get().toDataStatus()

    override fun addCity(city: City): Observable<DataStatus<Unit>> = addQuery.add(city).toDataStatus()

    override fun removeCity(city: City): Observable<DataStatus<Unit>> = removeQuery.remove(city).toDataStatus()
}