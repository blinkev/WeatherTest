package com.blinkev.weathertest.ui.screens.cities.item

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CityEntityMapperImplTest {

    private lateinit var mapper: CityEntityMapper

    @Before
    fun setUp() {
        mapper = CityEntityMapperImpl()
    }

    @Test
    fun `map() - mapped entity should have the same city name`() {
        val cityName = "city name"

        assertEquals(cityName, mapper.map(cityName).name)
    }
}