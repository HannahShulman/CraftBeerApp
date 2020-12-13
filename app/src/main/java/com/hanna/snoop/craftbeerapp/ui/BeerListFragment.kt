package com.hanna.snoop.craftbeerapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.hanna.snoop.craftbeerapp.R
import com.hanna.snoop.craftbeerapp.datasource.network.Status
//import com.hanna.snoop.craftbeerapp.di.DaggerInjectHelper
import com.hanna.snoop.craftbeerapp.extensions.provideViewModel
import com.hanna.snoop.craftbeerapp.utils.adapters.BeerListAdapter
import com.hanna.snoop.craftbeerapp.viewmodel.BeersViewModel
import com.hanna.snoop.craftbeerapp.viewmodel.BeersViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_beer_list.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class BeerListFragment : Fragment(R.layout.fragment_beer_list) {

    @Inject
    lateinit var factory: BeersViewModelFactory

    val viewModel: BeersViewModel by provideViewModel { factory }

    private val adapter = BeerListAdapter(::onBeerSelected)

    private val viewStates: ViewStates by lazy {
        ViewStates(requireView())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        beer_list.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchAllBeers().collect { resource ->
                val state = when (resource.status) {
                    Status.LOADING -> ViewStates.ViewState.LOADING.takeIf { resource.data.isNullOrEmpty() }
                        ?: ViewStates.ViewState.MAIN
                    Status.SUCCESS -> ViewStates.ViewState.MAIN
                    Status.ERROR -> ViewStates.ViewState.ERROR.also {
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                viewStates.setState(state)
                resource.data?.let {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun onBeerSelected(beeIdr: Int) {
        parentFragmentManager.commit {
            setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                0,
                R.anim.exit_to_right
            )
            addToBackStack(null)
            replace(R.id.fragment_container, BeerDetailsFragment.newInstance(beeIdr))
        }
    }
}