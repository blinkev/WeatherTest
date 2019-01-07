package com.blinkev.weathertest.ui.screens.weather.item.formatter

import javax.inject.Inject

class TemperatureFormatterImpl @Inject constructor() : TemperatureFormatter {

    override fun format(temperature: Int): String = "$temperature˚C"
}