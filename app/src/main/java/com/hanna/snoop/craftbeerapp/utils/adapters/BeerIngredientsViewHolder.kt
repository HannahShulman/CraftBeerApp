package com.hanna.snoop.craftbeerapp.utils.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.hanna.snoop.craftbeerapp.R
import com.hanna.snoop.craftbeerapp.entities.Hops
import com.hanna.snoop.craftbeerapp.entities.Ingredient
import com.hanna.snoop.craftbeerapp.entities.Malt
import com.hanna.snoop.craftbeerapp.extensions.inflate
import com.hanna.snoop.craftbeerapp.extensions.removeTrailingZeros

class BeerIngredientsViewHolder(itemView: View) : BindableViewHolder<Ingredient>(itemView) {

    companion object {
        val create: (parent: ViewGroup) -> BindableViewHolder<Ingredient> = {
            BeerIngredientsViewHolder(it.inflate(R.layout.beer_ingredient_info_layout))
        }

        val ingredientDiffUtil = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
                return oldItem == newItem
            }
        }
    }

    private val context = itemView.context
    private val ingredientName: TextView = itemView.findViewById(R.id.ingredient_name)
    private val ingredientAmount: TextView = itemView.findViewById(R.id.ingredient_amount)
    private val additionalInfo: TextView = itemView.findViewById(R.id.additional_info)
    override fun bindData(data: Ingredient) {
        ingredientName.text = data.name
        ingredientAmount.text = context.getString(R.string.amount_representation, data.amount.value.removeTrailingZeros(), data.amount.unit.representation)
        additionalInfo.isVisible = data is Hops
        when (data) {
            is Malt -> { }
            is Hops -> {
                additionalInfo.text = context.getString(R.string.formatted_hops_additional_info, data.add, data.attribute)
            }
        }
    }
}