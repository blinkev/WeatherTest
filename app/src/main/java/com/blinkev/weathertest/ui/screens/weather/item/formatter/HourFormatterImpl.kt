package com.blinkev.weathertest.ui.screens.weather.item.formatter

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HourFormatterImpl @Inject constructor(): HourFormatter {

    private val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun format(hour: Date): String = formatter.format(hour)
}