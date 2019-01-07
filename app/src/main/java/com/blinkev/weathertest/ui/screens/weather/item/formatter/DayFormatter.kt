package com.blinkev.weathertest.ui.screens.weather.item.formatter

import java.util.*

interface DayFormatter {
    fun format(day: Date): String
}