package com.blinkev.weathertest.data.query.weather.mapper

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TimeMapperImpl @Inject constructor() : TimeMapper {

    private val locale = Locale.getDefault()
    private val formatter = SimpleDateFormat("HHmm", locale)

    override fun map(day: Date, time: String): Date {
        if (time.length == 1) return day

        val (hour, minute) = getHourAndMinute(time)

        return Calendar.getInstance(locale).apply {
            this.time = day
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }.time
    }

    private fun getHourAndMinute(hourAndMinute: String): Pair<Int, Int> {

        val calendar = Calendar.getInstance(locale).apply {
            time = formatter.parse(hourAndMinute.addLeadingZeroIfNeeded())
        }

        return Pair(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
    }

    private fun String.addLeadingZeroIfNeeded(): String = if (this.length == 3) {
        "0" + this
    } else {
        this
    }
}