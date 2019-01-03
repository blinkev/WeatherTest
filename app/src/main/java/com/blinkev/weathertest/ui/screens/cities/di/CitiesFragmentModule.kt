package com.blinkev.weathertest.ui.screens.cities.di

import com.blinkev.weathertest.ui.di.scope.ActivityScope
import com.blinkev.weathertest.ui.di.scope.FragmentScope
import com.blinkev.weathertest.ui.screens.cities.CitiesViewModel
import com.blinkev.weathertest.ui.screens.cities.item.CitiesUiMapper
import com.blinkev.weathertest.ui.screens.cities.item.CitiesUiMapperImpl
import com.blinkev.weathertest.ui.screens.cities.item.CityEntityMapper
import com.blinkev.weathertest.ui.screens.cities.item.CityEntityMapperImpl
import dagger.Module
import dagger.Provides

@Module
class CitiesFragmentModule(
    private val viewModel: CitiesViewModel
) {

    @FragmentScope
    @Provides
    fun provideViewModel(): CitiesViewModel = viewModel

    @FragmentScope
    @Provides
    fun provideCitiesUiMapper(mapper: CitiesUiMapperImpl): CitiesUiMapper = mapper

    @FragmentScope
    @Provides
    fun provideCityEntityMapper(mapper: CityEntityMapperImpl): CityEntityMapper = mapper
}