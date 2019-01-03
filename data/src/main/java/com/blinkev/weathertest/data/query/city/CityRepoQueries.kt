package com.blinkev.weathertest.data.query.city

import com.blinkev.weathertest.domain.repo.city.AddCityQuery
import com.blinkev.weathertest.domain.repo.city.GetCityListQuery
import com.blinkev.weathertest.domain.repo.city.RemoveCityQuery

interface CityRepoQueries : GetCityListQuery, AddCityQuery, RemoveCityQuery