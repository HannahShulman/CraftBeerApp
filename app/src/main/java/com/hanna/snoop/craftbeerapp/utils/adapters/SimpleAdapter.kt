package com.hanna.snoop.craftbeerapp.utils.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

open class SimpleAdapter<T>(
    diffUtil: DiffUtil.ItemCallback<T>,
    private val viewHolderProvider: (parent: ViewGroup) -> BindableViewHolder<T>
) : ListAdapter<T, BindableViewHolder<T>>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder<T> {
        return viewHolderProvider(parent)
    }

    override fun onBindViewHolder(holder: BindableViewHolder<T>, position: Int) {
        holder.bindData(currentList[position])
    }
}