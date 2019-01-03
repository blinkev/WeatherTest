package com.blinkev.weathertest.ui.screens.activity.di

import com.blinkev.weathertest.ui.di.scope.ActivityScope
import com.blinkev.weathertest.ui.screens.activity.HostActivity
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponentProvider
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [HostActivityModule::class])
interface HostActivityComponent : CitiesFragmentComponentProvider {
    fun inject(activity: HostActivity)
}