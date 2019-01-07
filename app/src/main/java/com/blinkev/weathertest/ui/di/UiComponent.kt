package com.blinkev.weathertest.ui.di

import android.content.Context
import com.blinkev.weathertest.domain.di.DomainProvider
import com.blinkev.weathertest.ui.screens.activity.di.HostActivityComponentProvider
import com.blinkev.weathertest.ui.util.ResourceProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [DomainProvider::class], modules = [UtilModule::class])
interface UiComponent : HostActivityComponentProvider {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(appContext: Context): Builder

        fun domainProvider(provider: DomainProvider): Builder

        fun build(): UiComponent
    }
}