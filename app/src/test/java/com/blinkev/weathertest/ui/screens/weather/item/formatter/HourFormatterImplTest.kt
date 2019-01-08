package com.blinkev.weathertest.ui.screens.weather.item.formatter

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class HourFormatterImplTest {

    private lateinit var formatter: HourFormatter

    @Before
    fun setUp() {
        formatter = HourFormatterImpl()
    }

    @Test
    fun `format() - should format am hour in 0-23 range with two digits, minutes as two digits and a colon between hours and minutes`() {
        val nineAmAndTenMinues: Date = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 10)
        }.time

        assertEquals("09:10", formatter.format(nineAmAndTenMinues))
    }

    @Test
    fun `format() - should format pm hour in 0-23 range with two digits, minutes as two digits and a colon between hours and minutes`() {
        val ninePmAndTenMinues: Date = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 21)
            set(Calendar.MINUTE, 10)
        }.time

        assertEquals("21:10", formatter.format(ninePmAndTenMinues))
    }
}