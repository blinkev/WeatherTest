package com.blinkev.weathertest.ui.screens.cities.di

import com.blinkev.weathertest.ui.di.scope.ActivityScope
import com.blinkev.weathertest.ui.di.scope.FragmentScope
import com.blinkev.weathertest.ui.screens.cities.CitiesFragment
import com.blinkev.weathertest.ui.screens.cities.CitiesViewModelImpl
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [CitiesFragmentModule::class])
interface CitiesFragmentComponent {
    fun inject(fragment: CitiesFragment)
    fun inject(viewModel: CitiesViewModelImpl)
}