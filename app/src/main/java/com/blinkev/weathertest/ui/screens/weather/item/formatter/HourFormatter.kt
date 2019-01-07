package com.blinkev.weathertest.ui.screens.weather.item.formatter

import java.util.*

interface HourFormatter {
    fun format(hour: Date): String
}