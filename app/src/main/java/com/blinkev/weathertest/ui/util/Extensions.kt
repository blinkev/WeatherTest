package com.blinkev.weathertest.ui.util

import android.os.Bundle
import java.util.*

fun Bundle.addString(key: String, value: String): Bundle {
    this.putString(key, value)
    return this
}

fun Date.isTheSameDayAs(otherDate: Date): Boolean {
    val otherDateCalendar = Calendar.getInstance().apply { time = otherDate }
    val thisCalendar = Calendar.getInstance().apply { time = this@isTheSameDayAs }

    return thisCalendar.get(Calendar.YEAR) == otherDateCalendar.get(Calendar.YEAR)
            && thisCalendar.get(Calendar.MONTH) == otherDateCalendar.get(Calendar.MONTH)
            && thisCalendar.get(Calendar.DAY_OF_MONTH) == otherDateCalendar.get(Calendar.DAY_OF_MONTH)
}

fun Date.addDays(count: Int): Date {
    val calendar = Calendar.getInstance().apply { time = this@addDays }
    return calendar.apply { add(Calendar.DAY_OF_MONTH, count) }.time
}

fun <T : Enum<T>> Bundle.addEnum(key: String, clazz: T): Bundle {
    this.putString(key, clazz.name)
    return this
}

inline fun <reified T : Enum<T>> Bundle.getEnum(key: String): T? {
    val typeStr: String? = this.getString(key)
    return enumValues<T>().find { it.name == typeStr }
}