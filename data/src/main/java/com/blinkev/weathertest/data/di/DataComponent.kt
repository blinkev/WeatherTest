package com.blinkev.weathertest.data.di

import android.content.Context
import com.blinkev.weathertest.domain.di.DomainDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, MapperModule::class, RetrofitModule::class, QueryModule::class])
interface DataComponent : DomainDependencies {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(appContext: Context): Builder

        fun build(): DataComponent
    }
}