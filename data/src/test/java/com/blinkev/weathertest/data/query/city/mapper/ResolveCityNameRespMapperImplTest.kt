package com.blinkev.weathertest.data.query.city.mapper

import com.blinkev.weathertest.data.dto.ResolveCityNameResp
import com.blinkev.weathertest.data.mock
import com.blinkev.weathertest.domain.exception.MapperException
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ResolveCityNameRespMapperImplTest {

    private lateinit var mapper: ResolveCityNameRespMapper

    @Before
    fun setUp() {
        mapper = ResolveCityNameRespMapperImpl()
    }

    @Test
    fun `map() - to create a resolved city entity we should take the first city name in the first result`() {
        val firstCityNameInFirstResult = "first city name"
        val firstResult = ResolveCityNameResp.Result(
            listOf(
                ResolveCityNameResp.StringValueDto(firstCityNameInFirstResult),
                ResolveCityNameResp.StringValueDto("second city name in first result")
            )
        )
        val secondResult = ResolveCityNameResp.Result.mock()
        val responseWithManyCitiesInManyResults = ResolveCityNameResp(
            searchApi = ResolveCityNameResp.SearchApi.mock(
                listOf(
                    firstResult,
                    secondResult
                )
            )
        )

        assertEquals(firstCityNameInFirstResult, mapper.map(responseWithManyCitiesInManyResults).name)
    }

    @Test(expected = MapperException::class)
    fun `map() - when there are no results we should throw the exception`() {
        val responseWithNoResults = ResolveCityNameResp.mock(searchApi = ResolveCityNameResp.SearchApi.mock(
            result = emptyList())
        )

        mapper.map(responseWithNoResults)
    }

    @Test(expected = MapperException::class)
    fun `map() - when there is no resolved city name we should throw the exception`() {
        val responseWithNoResolvedCityName = ResolveCityNameResp.mock(searchApi = ResolveCityNameResp.SearchApi.mock(
            result = listOf(
                ResolveCityNameResp.Result.mock(
                    region = emptyList()
                )
            ))
        )

        mapper.map(responseWithNoResolvedCityName)
    }
}