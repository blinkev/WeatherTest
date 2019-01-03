package com.blinkev.weathertest.data.di

import com.blinkev.weathertest.data.query.city.di.CityRepoQueriesModule
import com.blinkev.weathertest.data.query.weather.GetCityWeatherQueryImpl
import com.blinkev.weathertest.domain.repo.weather.GetCityWeatherQuery
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [CityRepoQueriesModule::class])
class QueryModule {

    @Singleton
    @Provides
    fun provideGetCityWeatherQuery(query: GetCityWeatherQueryImpl): GetCityWeatherQuery = query
}