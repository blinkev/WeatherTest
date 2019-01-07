package com.blinkev.weathertest.ui.screens.common.item

import com.github.nitrico.lastadapter.StableId

/**
 * Created by egor.kharenko
on 11/09/2018.
 */

data class CommonLoadingItem(val text: String): StableId {

    override val stableId = hashCode().toLong()
}