package com.blinkev.weathertest.ui.screens.activity.di

import com.blinkev.weathertest.ui.di.scope.ActivityScope
import com.blinkev.weathertest.ui.screens.activity.HostViewModel
import com.blinkev.weathertest.ui.screens.cities.CitiesRouter
import dagger.Module
import dagger.Provides

@Module
class HostActivityModule(
    private val viewModel: HostViewModel,
    private val citiesRouter: CitiesRouter
) {

    @ActivityScope
    @Provides
    fun provideViewModel(): HostViewModel = viewModel

    @ActivityScope
    @Provides
    fun provideCitiesRouter(): CitiesRouter = citiesRouter
}