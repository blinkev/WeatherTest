package com.blinkev.weathertest.data.query.weather.mapper

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TimeMapperImpl @Inject constructor() : TimeMapper {

    private val locale = Locale.getDefault()
    private val formatter = SimpleDateFormat("Hmm", locale)

    override fun map(day: Date, time: String): Date {
        if (time.length == 1) return day

        val (hour, minute) = getHourAndMinute(time)

        return Calendar.getInstance(locale).apply {
            this.time = day
            set(Calendar.HOUR, hour)
            set(Calendar.MINUTE, minute)
        }.time
    }

    private fun getHourAndMinute(hourAndMinute: String): Pair<Int, Int> {

        val calendar = Calendar.getInstance(locale).apply {
            time = formatter.parse(hourAndMinute)
        }

        return Pair(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE))
    }
}