package com.blinkev.weathertest.ui.screens.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.blinkev.weathertest.ui.R
import com.blinkev.weathertest.ui.di.UiComponentHolder
import com.blinkev.weathertest.ui.screens.activity.di.HostActivityChildComponentProvider
import com.blinkev.weathertest.ui.screens.activity.di.HostActivityComponent
import com.blinkev.weathertest.ui.screens.activity.di.HostActivityModule
import javax.inject.Inject

class HostActivity : AppCompatActivity(), HostActivityChildComponentProvider {

    override lateinit var screenComponent: HostActivityComponent

    @Inject
    lateinit var viewModel: HostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setupComponent()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_host)
    }

    private fun setupComponent() {
        val viewModel = ViewModelProviders.of(this).get(HostViewModelImpl::class.java)
        val component = viewModel.screenComponent
        if (component == null) {
            (application as UiComponentHolder).uiComponent.provide(HostActivityModule(viewModel)).apply {
                screenComponent = this
                viewModel.screenComponent = this
                inject(this@HostActivity)
            }
        } else {
            screenComponent = component
            component.inject(this)
        }
    }
}
