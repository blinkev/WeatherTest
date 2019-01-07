package com.blinkev.weathertest.ui.screens.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.ui.BR
import com.blinkev.weathertest.ui.R
import com.blinkev.weathertest.ui.databinding.*
import com.blinkev.weathertest.ui.screens.common.item.CommonLoadingItem
import com.blinkev.weathertest.ui.screens.weather.di.WeatherFragmentComponentProvider
import com.blinkev.weathertest.ui.screens.weather.di.WeatherFragmentModule
import com.blinkev.weathertest.ui.screens.weather.item.DayHeaderItem
import com.blinkev.weathertest.ui.screens.weather.item.ErrorItem
import com.blinkev.weathertest.ui.screens.weather.item.HourWeatherItem
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.StableId
import kotlinx.android.synthetic.main.fragment_cities.*
import javax.inject.Inject

class WeatherFragment : Fragment() {

    @Inject
    lateinit var viewModel: WeatherViewModel

    private val listData = mutableListOf<StableId>()

    private val adapter = LastAdapter(listData, BR.item)
            .map<DayHeaderItem, ItemWeatherDayHeaderBinding>(R.layout.item_weather_day_header)
            .map<HourWeatherItem, ItemWeatherHourWeatherBinding>(R.layout.item_weather_hour_weather)
            .map<CommonLoadingItem, ItemCommonLoadingBinding>(R.layout.item_common_loading)
            .map<ErrorItem, ItemWeatherErrorBinding>(R.layout.item_weather_error) {
                onBind { viewHolder ->
                    viewHolder.binding.centerLayout.setOnClickListener {
                        viewModel.fetchWeather()
                    }
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupComponent()
    }

    private fun createCityEntity(): City? = WeatherFragmentArgs.fromBundle(arguments).cityName?.let { name -> City(name) }

    private fun setupComponent() {
        val viewModel = ViewModelProviders.of(this).get(WeatherViewModelImpl::class.java)
        val component = viewModel.screenComponent
        if (component == null) {
            (activity as WeatherFragmentComponentProvider).provide(
                    WeatherFragmentModule(
                            viewModel = viewModel,
                            city = createCityEntity()
                    )
            ).apply {
                viewModel.screenComponent = this
                inject(this@WeatherFragment)
                inject(viewModel)
            }
        } else {
            component.inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_weather, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.into(listView)
        observeWeather()
        if (viewModel.weather.value == null) viewModel.fetchWeather()
    }

    private fun observeWeather() {
        viewModel.weather.observe(this, Observer {
            when (it) {
                is DataStatus.Loading -> {
                    updateList(listOf(CommonLoadingItem(resources.getString(R.string.weather_loading_text))))
                }
                is DataStatus.Data -> updateList(it.data)
                is DataStatus.Error -> {
                    it.error.printStackTrace()
                    updateList(listOf(ErrorItem(resources.getString(R.string.weather_error_text))))
                }
            }
        })
    }

    private fun updateList(newList: List<StableId>) {
        listData.clear()
        listData.addAll(newList)
        adapter.notifyDataSetChanged()
    }
}