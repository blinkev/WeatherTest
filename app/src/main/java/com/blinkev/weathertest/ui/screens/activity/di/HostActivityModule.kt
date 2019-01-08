package com.blinkev.weathertest.ui.screens.activity.di

import com.blinkev.weathertest.ui.di.scope.ActivityScope
import com.blinkev.weathertest.ui.screens.activity.HostViewModel
import dagger.Module
import dagger.Provides

@Module
class HostActivityModule(private val viewModel: HostViewModel) {

    @ActivityScope
    @Provides
    fun provideViewModel(): HostViewModel = viewModel
}