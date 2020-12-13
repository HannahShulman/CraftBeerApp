package com.hanna.snoop.craftbeerapp.ui

import android.os.Build
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.hanna.snoop.craftbeerapp.R
import com.hanna.snoop.craftbeerapp.datasource.network.Resource
import com.hanna.snoop.craftbeerapp.entities.CraftBeer
import com.hanna.snoop.craftbeerapp.entities.CraftBeerWithFavouriteState
import com.hanna.snoop.craftbeerapp.entities.Ingredients
import com.hanna.snoop.craftbeerapp.extensions.replace
import com.hanna.snoop.craftbeerapp.viewmodel.BeersViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class BeerListFragmentTest {

    lateinit var scenario: FragmentScenario<BeerListFragment>
    lateinit var factory: BeerListFragmentTestFactory
    private val initListResource = Resource.success(
        listOf(CraftBeerWithFavouriteState(
                beer = CraftBeer(
                    id = 0,
                    name = "Beer Name",
                    description = "Beer description",
                    imageUrl = "ImageUrl",
                    abv = 8.8,
                    contributedBy = "Myself",
                    foodPairing = listOf(),
                    ingredients = Ingredients(
                        listOf(), listOf(), yeast = "Yeast Name"
                    )
                ),
                isFavourite = true))
    )
    private val listValue = MutableStateFlow(initListResource)
    private val viewModelMock: BeersViewModel = mock {
        onBlocking { fetchAllBeers() } doReturn listValue
    }

    @Before
    fun setupScenario() {
        factory = BeerListFragmentTestFactory(viewModelMock)
        scenario = FragmentScenario.launchInContainer(
            BeerListFragment::class.java,
            null, R.style.Theme_AppCompat_NoActionBar, factory
        )
    }

    @Test
    fun `when beer list resource is successful, list adapter item count, is equals to beer list`() {
        listValue.value = initListResource
        scenario.onFragment {
            val rv = it.view!!.findViewById<RecyclerView>(R.id.beer_list)
            assertThat(rv!!.adapter?.itemCount).isEqualTo(1)
        }
    }

    @Test
    fun `when beer list resource is successful, ViewStates_MAIN resource id is visible`() {
        listValue.value = initListResource
        scenario.onFragment {
            val mainView = it.view!!.findViewById<View>(R.id.main_view)
            assertThat(mainView.isVisible).isTrue()
        }
    }

    @Test
    fun `when beer list resource is loading with data, ViewStates_MAIN resource id is visible`() {
        listValue.value = Resource.loading(initListResource.data)
        scenario.onFragment {
            val mainView = it.view!!.findViewById<View>(R.id.main_view)
            assertThat(mainView.isVisible).isTrue()
        }
    }

    @Test
    fun `when beer list resource is loading with no data, ViewStates_LOADING resource id is visible`() {
        listValue.value = Resource.loading(null)
        scenario.onFragment {
            val mainView = it.view!!.findViewById<View>(R.id.loading_view)
            assertThat(mainView.isVisible).isTrue()
        }
    }

    @Test
    fun `when beer list resource is error with no data, ViewStates_LOADING resource id is visible`() {
        listValue.value = Resource.error("An error has occurred", null)
        scenario.onFragment {
            assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("An error has occurred")
        }
    }

}

class BeerListFragmentTestFactory constructor(
    var viewModelMock: BeersViewModel
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        BeerListFragment().apply {
            replace(BeerListFragment::viewModel, viewModelMock)
        }
}