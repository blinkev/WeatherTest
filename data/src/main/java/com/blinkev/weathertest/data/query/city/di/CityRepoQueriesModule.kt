package com.blinkev.weathertest.data.query.city.di

import com.blinkev.weathertest.data.query.city.CityRepoGetAddRemoveQueries
import com.blinkev.weathertest.data.query.city.CityRepoGetAddRemoveQueriesImpl
import com.blinkev.weathertest.data.query.city.ResolveCityQueryImpl
import com.blinkev.weathertest.domain.repo.city.AddCityQuery
import com.blinkev.weathertest.domain.repo.city.GetCityListQuery
import com.blinkev.weathertest.domain.repo.city.RemoveCityQuery
import com.blinkev.weathertest.domain.repo.city.ResolveCityQuery
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CityRepoQueriesModule {

    @Singleton
    @Provides
    fun provideResolveCityQuery(query: ResolveCityQueryImpl): ResolveCityQuery = query

    // Combined query implementation. Used to provide separate queries (see other provides below)
    @Singleton
    @Provides
    fun provideCityRepoQueries(query: CityRepoGetAddRemoveQueriesImpl): CityRepoGetAddRemoveQueries = query

    @Singleton
    @Provides
    fun provideGetCityListQuery(query: CityRepoGetAddRemoveQueries): GetCityListQuery = query

    @Singleton
    @Provides
    fun provideAddCityQuery(query: CityRepoGetAddRemoveQueries): AddCityQuery = query

    @Singleton
    @Provides
    fun provideRemoveCityQuery(query: CityRepoGetAddRemoveQueries): RemoveCityQuery = query
}