package com.blinkev.weathertest.ui.screens.cities.item

import com.blinkev.weathertest.domain.entity.City
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CitiesUiMapperImplTest {

    private lateinit var mapper: CitiesUiMapper

    @Before
    fun setUp() {
        mapper = CitiesUiMapperImpl()
    }

    @Test
    fun `map() - we should use entity city name and entity itself to create city list item`() {
        val cityName = "city name"
        val city = City(cityName)

        mapper.map(listOf(city)).apply {
            assertEquals(1, size)
            assertEquals(cityName, get(0).name)
            assertEquals(city, get(0).entity)
        }
    }
}