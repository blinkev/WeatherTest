package com.blinkev.weathertest.domain.di

import com.blinkev.weathertest.domain.repo.city.CityRepo
import com.blinkev.weathertest.domain.repo.city.CityRepoImpl
import com.blinkev.weathertest.domain.repo.settings.SettingsRepo
import com.blinkev.weathertest.domain.repo.settings.SettingsRepoImpl
import com.blinkev.weathertest.domain.repo.weather.CityWeatherRepo
import com.blinkev.weathertest.domain.repo.weather.CityWeatherRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideCityRepo(repo: CityRepoImpl): CityRepo = repo

    @Singleton
    @Provides
    fun provideCityWeatherRepo(repo: CityWeatherRepoImpl): CityWeatherRepo = repo

    @Singleton
    @Provides
    fun provideSettingsRepo(repo: SettingsRepoImpl): SettingsRepo = repo
}