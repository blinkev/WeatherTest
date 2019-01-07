package com.blinkev.weathertest.ui.screens.weather.item

import com.blinkev.weathertest.domain.entity.City
import com.github.nitrico.lastadapter.StableId

data class DayHeaderItem(val text: String) : StableId {

    override val stableId: Long = text.hashCode().toLong()
}