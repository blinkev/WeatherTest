package com.blinkev.weathertest.ui.screens.activity.di

interface HostActivityComponentProvider {
    fun provide(module: HostActivityModule): HostActivityComponent
}