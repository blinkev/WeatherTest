package com.blinkev.weathertest.data.query.city

import com.blinkev.weathertest.data.api.WeatherApi
import com.blinkev.weathertest.data.query.city.mapper.LocationMapper
import com.blinkev.weathertest.data.query.city.mapper.ResolveCityNameRespMapper
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.repo.city.ResolveCityQuery
import io.reactivex.Observable
import javax.inject.Inject

class ResolveCityQueryImpl @Inject constructor(
    private val api: WeatherApi,
    private val locationMapper: LocationMapper,
    private val resolveCityRespMapper: ResolveCityNameRespMapper
) : ResolveCityQuery {

    override fun resolve(lat: Double, lon: Double): Observable<City> = api
        .resolveCityName(locationMapper.map(lat, lon))
        .map(resolveCityRespMapper::map)
}