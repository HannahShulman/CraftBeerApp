package com.hanna.snoop.craftbeerapp.utils.adapters

import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.hanna.snoop.craftbeerapp.R
import com.hanna.snoop.craftbeerapp.extensions.inflate

class SimpleStringViewHolder(itemView: View, private val textType: TextType) :
    BindableViewHolder<String>(itemView) {

    enum class TextType(val textSize: Float, val textStyle: Int = Typeface.NORMAL) {
        MAIN_TITLE(22F, Typeface.BOLD),
        SUB_TITLE(16F, Typeface.BOLD),
        REG_TEXT(16F)
    }

    companion object {
        fun create(textType: TextType = TextType.REG_TEXT): (parent: ViewGroup) -> BindableViewHolder<String> =
            {
                SimpleStringViewHolder(it.inflate(R.layout.beer_info_text_layout), textType)
            }

        val textDiffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    val tv: TextView = itemView.findViewById(R.id.text_view)
    override fun bindData(data: String) {
        with(tv) {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, textType.textSize)
            setTypeface(tv.typeface, textType.textStyle)
            text = data
        }
    }
}