package com.blinkev.weathertest.ui.screens.activity.di

import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponent
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponentProvider
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentModule

interface HostActivityChildComponentProvider : CitiesFragmentComponentProvider {

    val screenComponent: HostActivityComponent

    override fun provide(module: CitiesFragmentModule): CitiesFragmentComponent =
            screenComponent.provide(module)
}