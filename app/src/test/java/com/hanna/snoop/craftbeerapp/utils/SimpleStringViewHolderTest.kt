package com.hanna.snoop.craftbeerapp.utils

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.hanna.snoop.craftbeerapp.R
import com.hanna.snoop.craftbeerapp.utils.adapters.BindableViewHolder
import com.hanna.snoop.craftbeerapp.utils.adapters.SimpleAdapter
import com.hanna.snoop.craftbeerapp.utils.adapters.SimpleStringViewHolder
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SimpleStringViewHolderTest {

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return true
        }

    }

    private fun provideViewHolder(): (parent: ViewGroup) -> BindableViewHolder<String> {
        return SimpleStringViewHolder.create()
    }

    private val adapter = SimpleAdapter(diffUtil, provideViewHolder()).apply {
        submitList(listOf("abcdefg"))
    }

    private lateinit var context: Context

    private val layout: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.beer_info_text_layout, null)
    }

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }


    @Test
    fun `bind view holder sets correct text`() {
        val holder = SimpleStringViewHolder(layout, SimpleStringViewHolder.TextType.REG_TEXT)
        adapter.bindViewHolder(holder, 0)
        assertThat(holder.tv.text).isEqualTo("abcdefg")
    }

    @Test
    fun `bind view holder sets correct text size`() {

    }

    @Test
    fun `bind view holder sets correct text style`() {

    }

}