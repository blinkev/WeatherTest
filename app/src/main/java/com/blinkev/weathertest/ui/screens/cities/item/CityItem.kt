package com.blinkev.weathertest.ui.screens.cities.item

import com.blinkev.weathertest.domain.entity.City
import com.github.nitrico.lastadapter.StableId

data class CityItem(val name: String, val entity: City) : StableId {

    override val stableId = name.hashCode().toLong()
}