package com.hanna.snoop.craftbeerapp.utils.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.hanna.snoop.craftbeerapp.R
import com.hanna.snoop.craftbeerapp.entities.CraftBeerWithFavouriteState
import com.hanna.snoop.craftbeerapp.extensions.inflate
import com.hanna.snoop.craftbeerapp.extensions.removeTrailingZeros

class BeerViewHolder(itemView: View, val onItemSelected: (beerId: Int) -> Unit) :
    BindableViewHolder<CraftBeerWithFavouriteState>(itemView) {
    private val title: TextView = itemView.findViewById(R.id.beer_name)
    private val abv: TextView = itemView.findViewById(R.id.beer_abv)
    private val image: ImageView = itemView.findViewById(R.id.image)
    private val favorite: CheckBox = itemView.findViewById(R.id.favorite)

    override fun bindData(data: CraftBeerWithFavouriteState) {
        val context = itemView.context
        favorite.isChecked = data.isFavourite
        itemView.setOnClickListener {
            onItemSelected(data.beer.id)
        }

        title.text = context.getString(R.string.beer_name, data.beer.name)
        abv.text = context.getString(R.string.beer_abv_format, data.beer.abv.removeTrailingZeros())
        Glide.with(itemView).load(data.beer.imageUrl).placeholder(R.drawable.beer_placeholder)
            .into(image)
    }

    companion object {
        fun create(onItemSelected: (beerId: Int) -> Unit): (parent: ViewGroup) -> BindableViewHolder<CraftBeerWithFavouriteState> =
            {
                BeerViewHolder(it.inflate(R.layout.single_beer_layout), onItemSelected)
            }

        val diffUtil = object : DiffUtil.ItemCallback<CraftBeerWithFavouriteState>() {
            override fun areItemsTheSame(
                oldItem: CraftBeerWithFavouriteState,
                newItem: CraftBeerWithFavouriteState
            ): Boolean {
                return oldItem.beer.id == newItem.beer.id
            }

            override fun areContentsTheSame(
                oldItem: CraftBeerWithFavouriteState,
                newItem: CraftBeerWithFavouriteState
            ): Boolean {
                return oldItem.beer.name == newItem.beer.name &&
                        oldItem.beer.abv == newItem.beer.abv &&
                        oldItem.beer.imageUrl == newItem.beer.imageUrl && oldItem.isFavourite == newItem.isFavourite
            }
        }
    }
}