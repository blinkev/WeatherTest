package com.blinkev.weathertest.ui.screens.activity

import com.blinkev.weathertest.ui.BaseViewModelImpl
import com.blinkev.weathertest.ui.screens.activity.di.HostActivityComponent
import com.blinkev.weathertest.ui.screens.cities.CitiesViewModel
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponent
import javax.inject.Inject

class HostViewModelImpl @Inject constructor() : BaseViewModelImpl<HostActivityComponent>(), HostViewModel