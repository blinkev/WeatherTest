package com.blinkev.weathertest.domain.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [DomainDependencies::class], modules = [RepoModule::class])
interface DomainComponent : DomainProvider