package com.blinkev.weathertest.ui.di

import android.content.Context
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.ui.di.scope.ActivityScope
import com.blinkev.weathertest.ui.di.scope.FragmentScope
import com.blinkev.weathertest.ui.screens.cities.CitiesViewModel
import com.blinkev.weathertest.ui.screens.cities.item.CitiesUiMapper
import com.blinkev.weathertest.ui.screens.cities.item.CitiesUiMapperImpl
import com.blinkev.weathertest.ui.screens.cities.item.CityEntityMapper
import com.blinkev.weathertest.ui.screens.cities.item.CityEntityMapperImpl
import com.blinkev.weathertest.ui.screens.weather.WeatherViewModel
import com.blinkev.weathertest.ui.screens.weather.item.formatter.di.WeatherFormatterModule
import com.blinkev.weathertest.ui.screens.weather.item.mapper.WeatherMapper
import com.blinkev.weathertest.ui.screens.weather.item.mapper.WeatherMapperImpl
import com.blinkev.weathertest.ui.util.ResourceProvider
import com.blinkev.weathertest.ui.util.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule {

    @Singleton
    @Provides
    fun provideResourceProvider(appContext: Context): ResourceProvider =
            ResourceProviderImpl(appContext.resources)
}