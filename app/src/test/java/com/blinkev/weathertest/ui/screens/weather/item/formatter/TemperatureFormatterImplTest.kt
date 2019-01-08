package com.blinkev.weathertest.ui.screens.weather.item.formatter

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TemperatureFormatterImplTest {

    private lateinit var formatter: TemperatureFormatter

    @Before
    fun setUp() {
        formatter = TemperatureFormatterImpl()
    }

    @Test
    fun `format() - should format negative temperature as digits with sign and celsius symbol`() {
        assertEquals("-100˚C", formatter.format(-100))
    }

    @Test
    fun `format() - should format positive temperature as digits without sign and with celsius symbol`() {
        assertEquals("100˚C", formatter.format(100))
    }
}