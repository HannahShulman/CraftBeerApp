package com.hanna.snoop.craftbeerapp.utils.adapters

import com.hanna.snoop.craftbeerapp.entities.CraftBeerWithFavouriteState

//This adapter extends the simple adapter to add itemClick functionality.
class BeerListAdapter(onItemSelected: (beerId: Int) -> Unit) :
    SimpleAdapter<CraftBeerWithFavouriteState>(
        BeerViewHolder.diffUtil,
        BeerViewHolder.create(onItemSelected)
    )

