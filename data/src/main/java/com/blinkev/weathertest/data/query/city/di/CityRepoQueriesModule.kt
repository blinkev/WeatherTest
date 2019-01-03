package com.blinkev.weathertest.data.query.city.di

import com.blinkev.weathertest.data.query.city.CityRepoQueries
import com.blinkev.weathertest.data.query.city.CityRepoQueriesImpl
import com.blinkev.weathertest.domain.repo.city.AddCityQuery
import com.blinkev.weathertest.domain.repo.city.GetCityListQuery
import com.blinkev.weathertest.domain.repo.city.RemoveCityQuery
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CityRepoQueriesModule {

    // Combined query implementation. Used to provide separate queries (see other provides below)
    @Singleton
    @Provides
    fun provideCityRepoQueries(query: CityRepoQueriesImpl): CityRepoQueries = query

    @Singleton
    @Provides
    fun provideGetCityListQuery(query: CityRepoQueries): GetCityListQuery = query

    @Singleton
    @Provides
    fun provideAddCityQuery(query: CityRepoQueries): AddCityQuery = query

    @Singleton
    @Provides
    fun provideRemoveCityQuery(query: CityRepoQueries): RemoveCityQuery = query
}