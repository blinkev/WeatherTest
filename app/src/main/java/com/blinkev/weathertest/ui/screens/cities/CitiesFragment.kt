package com.blinkev.weathertest.ui.screens.cities

import android.os.Bundle
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
import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.ui.BR
import com.blinkev.weathertest.ui.databinding.ItemCityBinding
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentComponentProvider
import com.blinkev.weathertest.ui.screens.cities.di.CitiesFragmentModule
import com.blinkev.weathertest.ui.screens.cities.item.CityItem
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.StableId
import kotlinx.android.synthetic.main.fragment_cities.*
import javax.inject.Inject

class CitiesFragment : Fragment() {

    @Inject
    lateinit var viewModel: CitiesViewModel

    private val listData = mutableListOf<CityItem>()

    /*private val adapter = LastAdapter(listData, BR.item, stableIds = true)
        .map<CityItem, ItemCityBinding>(R.layout.item_city) {
            onBind { viewHolder ->
                viewHolder.binding.deleteView.setOnClickListener {
                    viewModel.onCityItemRemoveClick(viewHolder.binding.item as CityItem)
                }
            }
            onClick { viewHolder ->
                viewModel.onCityItemClick(viewHolder.binding.item as CityItem)
            }
        }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupComponent()
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

        //adapter.into(listView)
        observeCities()
        if (viewModel.cities.value == null) viewModel.fetchCities()
    }

    private fun showAddCityNameDialog() {
        context?.let { context ->
            val input = EditText(context)
            input.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)

            val dialog = AlertDialog.Builder(context)
                .setTitle(resources.getString(R.string.order_address_dialog_title))
                .setPositiveButton(resources.getString(R.string.order_address_dialog_positive_button_text)) { _, _ ->
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
                    //adapter.notifyDataSetChanged()
                }
                is DataStatus.Error -> {
                    it.error.printStackTrace()
                    Toast.makeText(context, resources.getString(R.string.toast_error_text), Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}