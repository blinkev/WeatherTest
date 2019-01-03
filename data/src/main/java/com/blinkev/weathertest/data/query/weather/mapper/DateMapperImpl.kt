package com.blinkev.weathertest.data.query.weather.mapper

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateMapperImpl @Inject constructor() : DateMapper {

    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun map(date: String): Date = formatter.parse(date)
}