package com.blinkev.weathertest.data.query.weather.mapper

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class TimeMapperImplTest {

    private lateinit var mapper: TimeMapper

    @Before
    fun setUp() {
        mapper = TimeMapperImpl()
    }

    @Test
    fun `map() - mapped time should have the same year, month and day as a provided day`() {
        val providedYear = 2019
        val providedMonth = Calendar.JANUARY
        val providedDay = 8
        val providedDate: Date = Calendar.getInstance().apply {
            clear()
            set(Calendar.YEAR, providedYear)
            set(Calendar.MONTH, providedMonth)
            set(Calendar.DAY_OF_MONTH, providedDay)
        }.time

        val mappedDate = mapper.map(day = providedDate, time = "1200")

        Calendar.getInstance().apply {
            time = mappedDate

            assertEquals(providedYear, get(Calendar.YEAR))
            assertEquals(providedMonth, get(Calendar.MONTH))
            assertEquals(providedDay, get(Calendar.DAY_OF_MONTH))
        }
    }

    private fun checkResult(result: Date, expectingHour: Int, expectingMinute: Int) {
        Calendar.getInstance().apply {
            time = result

            assertEquals(expectingHour, get(Calendar.HOUR_OF_DAY))
            assertEquals(expectingMinute, get(Calendar.MINUTE))
        }
    }

    @Test
    fun `map() - 0 should be mapped as 0-00 am`() {
        checkResult(
            result = mapper.map(day = Date(), time = "0"),
            expectingHour = 0,
            expectingMinute = 0
        )
    }

    @Test
    fun `map() - 600 should be mapped as 6-00 am`() {
        checkResult(
            result = mapper.map(day = Date(), time = "600"),
            expectingHour = 6,
            expectingMinute = 0
        )
    }

    @Test
    fun `map() - 1200 should be mapped as 12-00 pm`() {
        checkResult(
            result = mapper.map(day = Date(), time = "1200"),
            expectingHour = 12,
            expectingMinute = 0
        )
    }
}