package com.blinkev.weathertest.domain.di

import com.blinkev.weathertest.domain.repo.city.AddCityQuery
import com.blinkev.weathertest.domain.repo.city.GetCityListQuery
import com.blinkev.weathertest.domain.repo.city.RemoveCityQuery
import com.blinkev.weathertest.domain.repo.city.ResolveCityQuery
import com.blinkev.weathertest.domain.repo.settings.GetFirstRunQuery
import com.blinkev.weathertest.domain.repo.settings.SetFirstRunQuery
import com.blinkev.weathertest.domain.repo.weather.GetCityWeatherQuery

interface DomainDependencies {
    val getCityListQuery: GetCityListQuery
    val addCityQuery: AddCityQuery
    val removeCityQuery: RemoveCityQuery
    val resolveCityQuery: ResolveCityQuery

    val getFirstRunQuery: GetFirstRunQuery
    val setFirstRunQuery: SetFirstRunQuery

    val getCityWeatherQuery: GetCityWeatherQuery
}