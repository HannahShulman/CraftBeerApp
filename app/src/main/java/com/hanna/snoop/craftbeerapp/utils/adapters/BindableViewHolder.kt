package com.hanna.snoop.craftbeerapp.utils.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class  BindableViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindData(data: T)
}