package com.hanna.snoop.craftbeerapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.bumptech.glide.Glide
import com.hanna.snoop.craftbeerapp.R
import com.hanna.snoop.craftbeerapp.entities.CraftBeerWithFavouriteState
import com.hanna.snoop.craftbeerapp.utils.adapters.BeerIngredientsViewHolder
import com.hanna.snoop.craftbeerapp.utils.adapters.SimpleAdapter
import com.hanna.snoop.craftbeerapp.utils.adapters.SimpleStringViewHolder
import com.hanna.snoop.craftbeerapp.utils.adapters.SimpleStringViewHolder.Companion.textDiffUtil
import com.hanna.snoop.craftbeerapp.viewmodel.BeersViewModel
import com.hanna.snoop.craftbeerapp.viewmodel.BeersViewModelFactory
import com.hanna.snoop.craftbeerapp.viewmodel.FavoriteBeerAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_beer_details.*
import javax.inject.Inject

@AndroidEntryPoint
class BeerDetailsFragment : Fragment(R.layout.fragment_beer_details) {

    @Inject
    lateinit var factory: BeersViewModelFactory
    private val viewModel: BeersViewModel by viewModels { factory }

    private val beerId: Int by lazy {
        arguments?.getInt(BEER_ID_KEY)
            ?: throw Throwable("$this must be instantiated using the newInstance method, for id purposes")
    }

    //adapters for food pairing
    private val foodPairingHeaderAdapter =
        SimpleAdapter(
            textDiffUtil, SimpleStringViewHolder.create(
                SimpleStringViewHolder.TextType.MAIN_TITLE
            )
        )
    private val foodPairingAdapter =
        SimpleAdapter(textDiffUtil, SimpleStringViewHolder.create())

    //adapters for ingredients
    private val ingredientsHeaderAdapter: SimpleAdapter<String> by lazy {
        SimpleAdapter(
            textDiffUtil,
            SimpleStringViewHolder.create(SimpleStringViewHolder.TextType.MAIN_TITLE)
        ).apply {
            //this is based on the assumption, there is no beer without recorded ingredients.
            submitList(listOf(getString(R.string.ingredients)))
        }
    }

    //ingredients header
    //malt header
    private val maltHeaderAdapter =
        SimpleAdapter(
            textDiffUtil,
            SimpleStringViewHolder.create(SimpleStringViewHolder.TextType.SUB_TITLE)
        )

    //malt ingredients
    private val maltInfoAdapter =
        SimpleAdapter(
            BeerIngredientsViewHolder.ingredientDiffUtil,
            BeerIngredientsViewHolder.create
        )

    //hops header
    private val hopsHeaderAdapter =
        SimpleAdapter(
            textDiffUtil,
            SimpleStringViewHolder.create(SimpleStringViewHolder.TextType.SUB_TITLE)
        )

    //hops ingredients
    private val hopsInfoAdapter =
        SimpleAdapter(
            BeerIngredientsViewHolder.ingredientDiffUtil,
            BeerIngredientsViewHolder.create
        )

    //yeast header - will always have the yeast ingredient
    private val yeastHeaderAdapter: SimpleAdapter<String> by lazy {
        SimpleAdapter(
            textDiffUtil,
            SimpleStringViewHolder.create(SimpleStringViewHolder.TextType.SUB_TITLE)
        ).also {
            it.submitList(listOf(getString(R.string.yeast_header)))
        }
    }

    //yeast content
    private val yeastInfoAdapter =
        SimpleAdapter(
            textDiffUtil,
            SimpleStringViewHolder.create()
        )

    private val concatAdapter: ConcatAdapter by lazy {
        ConcatAdapter(
            foodPairingHeaderAdapter,
            foodPairingAdapter,
            ingredientsHeaderAdapter,
            maltHeaderAdapter,
            maltInfoAdapter,
            hopsHeaderAdapter,
            hopsInfoAdapter, yeastHeaderAdapter, yeastInfoAdapter
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.beerById.observe(viewLifecycleOwner, { beer ->
            bindBeerData(beer)
        })
        viewModel._beerId.value = beerId

        favourite.setOnCheckedChangeListener { _, checked ->
            viewModel.handleFavouriteBeer(beerId,
                FavoriteBeerAction.ADD_TO_FAVORITE.takeIf { checked }
                    ?: FavoriteBeerAction.REMOVE_FROM_FAVORITES
            )
        }

        favourite_cover.setOnClickListener {//this is a layer to the start icon, to avoid the check-change listener from invoking
            favourite.toggle()
        }
    }

    private fun bindBeerData(craftBeerWithFavouriteState: CraftBeerWithFavouriteState) {
        favourite.isChecked = craftBeerWithFavouriteState.isFavourite
        craftBeerWithFavouriteState.beer.let {
            Glide.with(beer_image).load(it.imageUrl).placeholder(R.drawable.beer_placeholder)
                .into(beer_image)
            beer_name.text = it.name
            contributed_by.text = getString(R.string.contributed_by, it.contributedBy)
            description.text = it.description
            data_list.adapter = concatAdapter
            //here I am taking into account, that there may not be any suggestions
            if (it.foodPairing.isNotEmpty()) {
                foodPairingHeaderAdapter.submitList(listOf(getString(R.string.goes_well_with)))
                foodPairingAdapter.submitList(it.foodPairing)
            } else {
                concatAdapter.removeAdapter(foodPairingHeaderAdapter)
                concatAdapter.removeAdapter(foodPairingAdapter)
            }
            //as mentioned, assuming there will always be ingredients
            it.ingredients.let { ingredients ->//as mentioned, assuming there will always be ingredients
                maltHeaderAdapter.submitList(listOf("Malt").takeIf { ingredients.hops.isNotEmpty() })
                maltInfoAdapter.submitList(ingredients.malt)

                hopsHeaderAdapter.submitList(listOf("Hops").takeIf { ingredients.hops.isNotEmpty() })
                hopsInfoAdapter.submitList(ingredients.hops)

                yeastInfoAdapter.submitList(listOf(ingredients.yeast))
            }
        }

    }

    companion object {

        const val BEER_ID_KEY = "beer_id_key"

        fun newInstance(beerId: Int): BeerDetailsFragment {
            return BeerDetailsFragment().apply {
                arguments = bundleOf(BEER_ID_KEY to beerId)
            }
        }
    }
}