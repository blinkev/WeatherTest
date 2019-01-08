package com.blinkev.weathertest.data.query.city.di

import com.blinkev.weathertest.data.query.city.mapper.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CityRepoMapperModule {

    @Singleton
    @Provides
    fun provideCityListPrefMapper(mapper: CityListPrefMapperImpl): CityListPrefMapper = mapper

    @Singleton
    @Provides
    fun provideLocationMapper(mapper: LocationMapperImpl): LocationMapper = mapper

    @Singleton
    @Provides
    fun provideResolveCityNameRespMapper(mapper: ResolveCityNameRespMapperImpl): ResolveCityNameRespMapper = mapper
}