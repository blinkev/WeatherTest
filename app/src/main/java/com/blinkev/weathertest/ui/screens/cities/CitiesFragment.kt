package com.blinkev.weathertest.ui.screens.cities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.ui.BR
import com.blinkev.weathertest.ui.R
import com.blinkev.weathertest.ui.databinding.ItemCityBinding
import com.blinkev.weathertest.ui.databinding.ItemCommonLoadingBinding
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponentProvider
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentModule
import com.blinkev.weathertest.ui.screens.cities.item.CityItem
import com.blinkev.weathertest.ui.screens.common.item.CommonLoadingItem
import com.blinkev.weathertest.ui.util.addEnum
import com.blinkev.weathertest.ui.util.getEnum
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.StableId
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cities.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CitiesFragment : Fragment() {

    @Inject
    lateinit var viewModel: CitiesViewModel
    @Inject
    lateinit var appContext: Context

    private var firstRunDialog: Dialog? = null

    private var permissionSubscription: Disposable? = null
    private var locationSubscription: Disposable? = null

    private val locationRequest = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(5000)

    private val listData = mutableListOf<StableId>()

    private val adapter = LastAdapter(listData, BR.item, stableIds = true)
        .map<CityItem, ItemCityBinding>(R.layout.item_city) {
            onBind { viewHolder ->
                viewHolder.binding.deleteView.setOnClickListener {
                    viewModel.onCityItemRemoveClick(viewHolder.binding.item as CityItem)
                }
            }
            onClick { viewHolder ->
                val item = viewHolder.binding.item as CityItem
                val action = CitiesFragmentDirections.actionCitiesFragmentToWeatherFragment(item.entity.name)
                view?.findNavController()?.navigate(action)
            }
        }
        .map<CommonLoadingItem, ItemCommonLoadingBinding>(R.layout.item_common_loading)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupComponent()
        retainInstance = true
    }

    private fun setupComponent() {
        val viewModel = ViewModelProviders.of(this).get(CitiesViewModelImpl::class.java)
        val component = viewModel.screenComponent
        if (component == null) {
            (activity as CitiesFragmentComponentProvider).provide(CitiesFragmentModule(viewModel)).apply {
                viewModel.screenComponent = this
                inject(this@CitiesFragment)
                inject(viewModel)
            }
        } else {
            component.inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_cities, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabView.setOnClickListener { showAddCityNameDialog() }

        adapter.into(listView)
        observeCities()
        observeFirstRunSetting()
    }

    private fun showAddCityNameDialog() {
        context?.let { context ->
            val input = EditText(context)
            input.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            val dialog = AlertDialog.Builder(context)
                .setTitle(resources.getString(R.string.add_city_dialog_title))
                .setPositiveButton(resources.getString(R.string.common_add)) { _, _ ->
                    viewModel.addCity(input.text.toString())
                }
                .setNegativeButton(resources.getString(R.string.common_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }

            dialog.setView(input).show()
        }
    }

    private fun observeCities() {
        viewModel.cities.observe(this, Observer {
            when (it) {
                is DataStatus.Data -> {
                    listData.clear()
                    listData.addAll(it.data)
                    adapter.notifyDataSetChanged()
                }
                is DataStatus.Loading -> showLoading()
                is DataStatus.Error -> {
                    hideLoading()
                    showError(it.error)
                }
            }
        })
        if (viewModel.cities.value == null) viewModel.fetchCities()
    }

    private fun observeFirstRunSetting() {
        viewModel.isFirstRunSetting.observe(this, Observer {
            when (it) {
                is DataStatus.Data -> {
                    when {
                        it.data and isPermissionDialogInactive() and isLocationDialogInactive() -> {
                            showAddCurrentLocationDialog()
                        }
                        it.data and isLocationDialogActive() -> showLoading()
                        !it.data -> showFab()
                    }
                }
                is DataStatus.Error -> showError(it.error)
            }
        })
        if (viewModel.isFirstRunSetting.value == null) viewModel.fetchFirstRunSetting()
    }

    private fun isPermissionDialogInactive(): Boolean = permissionSubscription?.isDisposed ?: true

    private fun isLocationDialogInactive(): Boolean = locationSubscription?.isDisposed ?: true

    private fun isLocationDialogActive(): Boolean = !isLocationDialogInactive()

    override fun onDestroyView() {
        super.onDestroyView()

        firstRunDialog?.dismiss()
    }

    private fun showAddCurrentLocationDialog() {
        firstRunDialog = context?.let { context ->
            AlertDialog.Builder(context)
                .setTitle(resources.getString(R.string.add_current_location_dialog_title))
                .setPositiveButton(resources.getString(R.string.common_add)) { _, _ ->
                    requestLocationPermissions()
                }
                .setNegativeButton(resources.getString(R.string.common_cancel)) { dialog, _ ->
                    dialog.dismiss()
                    disableFirstRunSettingAndShowFab()
                }
                .show()
        }
    }

    private fun disableFirstRunSettingAndShowFab() {
        showFab()
        viewModel.disableFirstRunSetting()
    }

    private fun requestLocationPermissions() {
        permissionSubscription = RxPermissions(this)
            .request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribeBy(
                onNext = { granted ->
                    if (granted) {
                        getCurrentLocation()
                    } else {
                        disableFirstRunSettingAndShowFab()
                    }
                },
                onError = ::showError
            )
    }

    @SuppressLint("RestrictedApi")
    private fun showFab() {
        fabView.visibility = View.VISIBLE
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val rxLocation = RxLocation(appContext)
        locationSubscription = rxLocation.settings().checkAndHandleResolution(locationRequest)
            .flatMap {
                if (it) {
                    rxLocation.location().updates(locationRequest)
                        .take(1)
                        .singleOrError()
                        .doOnSubscribe { showLoading() }
                } else {
                    Single.error(LocationSettingsError())
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { disableFirstRunSettingAndShowFab() }
            .subscribeBy(
                onSuccess = {
                    viewModel.detectCity(it.latitude, it.longitude)
                },
                onError = {
                    hideLoading()
                    if (it !is LocationSettingsError) showError(it)
                }
            )
    }

    private fun showLoading() {
        updateList(listOf(CommonLoadingItem(resources.getString(R.string.weather_loading_text))))
    }

    private fun hideLoading() {
        updateList(emptyList())
    }

    private fun updateList(newList: List<StableId>) {
        listData.clear()
        listData.addAll(newList)
        adapter.notifyDataSetChanged()
    }

    private fun showError(error: Throwable) {
        error.printStackTrace()
        Toast.makeText(context, resources.getString(R.string.toast_error_text), Toast.LENGTH_LONG).show()
    }

    private class LocationSettingsError : Throwable()
}