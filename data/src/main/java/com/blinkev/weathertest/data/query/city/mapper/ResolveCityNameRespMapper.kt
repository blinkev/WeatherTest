package com.blinkev.weathertest.data.query.city.mapper

import com.blinkev.weathertest.data.dto.ResolveCityNameResp
import com.blinkev.weathertest.domain.entity.City

interface ResolveCityNameRespMapper {
    fun map(resp: ResolveCityNameResp): City
}