package com.blinkev.weathertest.data.query.city.mapper

import com.blinkev.weathertest.data.dto.ResolveCityNameResp
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.exception.MapperException
import javax.inject.Inject

class ResolveCityNameRespMapperImpl @Inject constructor() : ResolveCityNameRespMapper {

    override fun map(resp: ResolveCityNameResp): City = resp.searchApi.result.firstOrNull()?.region?.firstOrNull()
        ?.value
        ?.let { City(it) }
        ?: throw MapperException()
}