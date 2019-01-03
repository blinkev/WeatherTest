package com.blinkev.weathertest.ui.di

import com.blinkev.weathertest.domain.di.DomainProvider
import com.blinkev.weathertest.ui.screens.activity.di.HostActivityComponent
import com.blinkev.weathertest.ui.screens.activity.di.HostActivityComponentProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [DomainProvider::class])
interface UiComponent : HostActivityComponentProvider