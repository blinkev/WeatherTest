package com.blinkev.weathertest.data.query.settings.di

import com.blinkev.weathertest.data.query.settings.SettingsRepoQueries
import com.blinkev.weathertest.data.query.settings.SettingsRepoQueriesImpl
import com.blinkev.weathertest.domain.repo.settings.GetFirstRunQuery
import com.blinkev.weathertest.domain.repo.settings.SetFirstRunQuery
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsRepoQueriesModule {

    // Combined query implementation. Used to provide separate queries (see other provides below)
    @Singleton
    @Provides
    fun provideSettingsRepoQueries(query: SettingsRepoQueriesImpl): SettingsRepoQueries = query

    @Singleton
    @Provides
    fun provideGetFirstRunQuery(query: SettingsRepoQueries): GetFirstRunQuery = query

    @Singleton
    @Provides
    fun provideSetFirstRunQuery(query: SettingsRepoQueries): SetFirstRunQuery = query
}