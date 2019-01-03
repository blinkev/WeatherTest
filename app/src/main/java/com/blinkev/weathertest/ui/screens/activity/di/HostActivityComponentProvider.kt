package com.blinkev.weathertest.ui.screens.activity.di

import com.blinkev.weathertest.ui.screens.cities.CitiesRouter

interface HostActivityComponentProvider {
    fun provide(module: HostActivityModule): HostActivityComponent
}