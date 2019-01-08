package com.blinkev.weathertest.data.query.city.mapper

import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.util.empty
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CityListPrefMapperImplTest {

    private lateinit var mapper: CityListPrefMapper

    @Before
    fun setUp() {
        mapper = CityListPrefMapperImpl()
    }

    @Test
    fun `map() - empty string should be mapped to empty list`() {
        assertEquals(emptyList<City>(), mapper.mapFromPref(String.empty()))
    }

    @Test
    fun `map() - empty list should be mapped to empty string`() {
        assertEquals(String.empty(), mapper.mapToPref(emptyList()))
    }

    @Test
    fun `map() - mapped city list should be restored as the same list`() {
        val cityList = listOf(
            City("city1"),
            City("city2")
        )

        val mappedList = mapper.mapToPref(cityList)
        val restoredList = mapper.mapFromPref(mappedList)

        assertEquals(cityList, restoredList)
    }
}