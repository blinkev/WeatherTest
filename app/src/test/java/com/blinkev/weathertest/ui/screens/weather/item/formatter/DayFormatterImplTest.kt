package com.blinkev.weathertest.ui.screens.weather.item.formatter

import com.blinkev.weathertest.ui.R
import com.blinkev.weathertest.ui.util.ResourceProvider
import com.blinkev.weathertest.ui.util.addDays
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class DayFormatterImplTest {

    private lateinit var resProvider: ResourceProvider

    private val todayResString = "Today"
    private val tomorrowResString = "Tomorrow"
    private val firstMondayInJanuary2019: Date = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2019)
        set(Calendar.MONTH, Calendar.JANUARY)
        set(Calendar.DAY_OF_MONTH, 7)
    }.time
    private val firstMondayInJanuary2019Digits = "09.01.2019"

    private lateinit var formatter: DayFormatter

    @Before
    fun setUp() {
        resProvider = mock {
            on { getString(R.string.today_word) }.thenReturn(todayResString)
            on { getString(R.string.tomorrow_word) }.thenReturn(tomorrowResString)
        }

        formatter = DayFormatterImpl(resProvider)
    }

    @Test
    fun `format() - today formatted string should consist of the today word and the day of the week word in parentheses`() {
        val today = firstMondayInJanuary2019
        assertEquals("$todayResString (Monday)", formatter.format(day = today, today = today))
    }

    @Test
    fun `format() - tomorrow formatted string should consist of the tomorrow word and the day of the week word in parentheses`() {
        val today = firstMondayInJanuary2019
        val tomorrow = firstMondayInJanuary2019.addDays(1)
        assertEquals("$tomorrowResString (Tuesday)", formatter.format(day = tomorrow, today = today))
    }

    @Test
    fun `format() - day after tomorrow and other days formatted strings should consist of the day-month-year digits and the day of the week word in parentheses`() {
        val today = firstMondayInJanuary2019
        val dayAfterTomorrow = firstMondayInJanuary2019.addDays(2)
        assertEquals("$firstMondayInJanuary2019Digits (Wednesday)", formatter.format(day = dayAfterTomorrow, today = today))
    }
}