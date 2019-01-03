package com.blinkev.weathertest.data.query.weather.mapper

import java.util.*

interface TimeMapper {
    fun map(day: Date, time: String): Date
}