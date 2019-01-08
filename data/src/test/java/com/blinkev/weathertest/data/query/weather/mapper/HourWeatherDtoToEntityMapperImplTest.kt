package com.blinkev.weathertest.data.query.weather.mapper

import com.blinkev.weathertest.data.dto.GetCityWeatherResp
import com.blinkev.weathertest.data.mock
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class HourWeatherDtoToEntityMapperImplTest {

    private lateinit var timeMapper: TimeMapper

    private lateinit var mapper: HourWeatherDtoToEntityMapper

    @Before
    fun setUp() {
        timeMapper = mock {}

        mapper = HourWeatherDtoToEntityMapperImpl(timeMapper)
    }

    @Test
    fun `map() - in created entity we should use mapped value as an hour`() {
        val mappedHour = Date(100500)
        whenever(timeMapper.map(day = any(), time = any())).thenReturn(mappedHour)

        val mappingDay = Date(1)
        mapper.map(day = mappingDay, dto = GetCityWeatherResp.HourWeatherDto.mock()).apply {
            assertEquals(mappedHour, hour)
        }
    }
}