package com.blinkev.weathertest.ui

import android.app.Application
import com.blinkev.weathertest.data.di.DaggerDataComponent
import com.blinkev.weathertest.domain.di.DaggerDomainComponent
import com.blinkev.weathertest.ui.di.DaggerUiComponent
import com.blinkev.weathertest.ui.di.UiComponent
import com.blinkev.weathertest.ui.di.UiComponentHolder

class App : Application(), UiComponentHolder {

    override val uiComponent: UiComponent by lazy {
        val dataComponent = DaggerDataComponent.builder()
            .appContext(applicationContext)
            .build()

        val domainComponent = DaggerDomainComponent.builder()
                .domainDependencies(dataComponent)
                .build()

        DaggerUiComponent.builder()
                .domainProvider(domainComponent)
                .build()
    }
}