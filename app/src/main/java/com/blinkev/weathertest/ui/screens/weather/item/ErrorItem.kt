package com.blinkev.weathertest.ui.screens.weather.item

import com.github.nitrico.lastadapter.StableId

/**
 * Created by egor.kharenko
on 11/09/2018.
 */

data class ErrorItem(val text: String): StableId {

    override val stableId = hashCode().toLong()
}