package com.blinkev.weathertest.data.query.weather.mapper

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class DateMapperImplTest {

    private lateinit var mapper: DateMapper

    @Before
    fun setUp() {
        mapper = DateMapperImpl()
    }

    @Test
    fun `map() - we should parse year-month-day string from response to date`() {
        val dateToParse = "2019-01-08"
        val expectedParsedDate: Date = Calendar.getInstance().apply {
            clear()
            set(Calendar.YEAR, 2019)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 8)
        }.time

        assertEquals(expectedParsedDate, mapper.map(dateToParse))
    }
}