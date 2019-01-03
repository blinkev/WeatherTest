package com.blinkev.weathertest.ui.screens.activity

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.ui.BaseViewModel
import com.blinkev.weathertest.ui.screens.activity.di.HostActivityComponent
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponent
import com.blinkev.weathertest.ui.screens.cities.item.CityItem
import java.math.BigDecimal

interface HostViewModel : BaseViewModel<HostActivityComponent>