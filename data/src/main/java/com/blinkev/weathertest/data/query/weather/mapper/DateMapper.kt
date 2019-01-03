package com.blinkev.weathertest.data.query.weather.mapper

import java.util.*

interface DateMapper {
    fun map(date: String): Date
}