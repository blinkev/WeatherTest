package com.blinkev.weathertest.ui.screens.weather.item.formatter

import com.blinkev.weathertest.ui.R
import com.blinkev.weathertest.ui.util.ResourceProvider
import com.blinkev.weathertest.ui.util.addDays
import com.blinkev.weathertest.ui.util.isTheSameDayAs
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DayFormatterImpl @Inject constructor(
        private val resProvider: ResourceProvider
) : DayFormatter {

    private val locale = Locale.getDefault()
    private val todayOrTomorrowFormatter = SimpleDateFormat("EEEE", locale)
    private val otherDayFormatter = SimpleDateFormat("dd.MM.yyyy (EEEE)", locale)

    override fun format(day: Date, today: Date): String {
        return when {
            day.isTheSameDayAs(today) -> {
                "${resProvider.getString(R.string.today_word)} (${todayOrTomorrowFormatter.format(day)})"
            }
            day.isTheSameDayAs(today.addDays(1)) -> {
                "${resProvider.getString(R.string.tomorrow_word)} (${todayOrTomorrowFormatter.format(day)})"
            }
            else -> otherDayFormatter.format(day)

        }
    }
}