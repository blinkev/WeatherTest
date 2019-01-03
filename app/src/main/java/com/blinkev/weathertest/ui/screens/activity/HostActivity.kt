package com.blinkev.weathertest.ui.screens.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.ui.di.UiComponentHolder
import com.blinkev.weathertest.ui.screens.activity.di.HostActivityChildComponentProvider
import com.blinkev.weathertest.ui.screens.activity.di.HostActivityComponent
import com.blinkev.weathertest.ui.screens.activity.di.HostActivityModule
import com.blinkev.weathertest.ui.screens.cities.CitiesFragment
import com.blinkev.weathertest.ui.screens.cities.CitiesRouter
import javax.inject.Inject

class HostActivity : AppCompatActivity(), CitiesRouter, HostActivityChildComponentProvider {

    override lateinit var screenComponent: HostActivityComponent

    @Inject
    lateinit var viewModel: HostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setupComponent()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        if (savedInstanceState == null) showFirstFragment()
    }

    private fun setupComponent() {
        val viewModel = ViewModelProviders.of(this).get(HostViewModelImpl::class.java)
        val component = viewModel.screenComponent
        if (component == null) {
            (application as UiComponentHolder).uiComponent.provide(
                HostActivityModule(
                    viewModel = viewModel,
                    citiesRouter = this
                )
            ).apply {
                screenComponent = this
                viewModel.screenComponent = this
                inject(this@HostActivity)
            }
        } else {
            screenComponent = component
            component.inject(this)
        }
    }

    private fun showFirstFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerView, CitiesFragment())
            .commit()
    }

    override fun routeToCityWeather(city: City) {

    }
}
