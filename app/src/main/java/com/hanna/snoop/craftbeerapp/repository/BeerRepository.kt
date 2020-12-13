package com.hanna.snoop.craftbeerapp.repository

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.hanna.snoop.craftbeerapp.datasource.SharedPreferencesContract
import com.hanna.snoop.craftbeerapp.datasource.db.CraftBeerDao
import com.hanna.snoop.craftbeerapp.datasource.network.*
import com.hanna.snoop.craftbeerapp.entities.CraftBeer
import com.hanna.snoop.craftbeerapp.entities.CraftBeerWithFavouriteState
import com.hanna.snoop.craftbeerapp.entities.FavoriteBeer
import com.hanna.snoop.craftbeerapp.viewmodel.FavoriteBeerAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

interface BeerRepository {
    fun getBeers(): Flow<Resource<List<CraftBeerWithFavouriteState>>>
    fun getBeerById(id: Int): LiveData<CraftBeerWithFavouriteState>
    suspend fun handleFavouriteBeer(beerId: Int, action: FavoriteBeerAction)
}

class BeerRepositoryImpl @Inject constructor(
    val api: Api,
    val dao: CraftBeerDao,
    val sp: SharedPreferencesContract
) : BeerRepository {

    override fun getBeers(): Flow<Resource<List<CraftBeerWithFavouriteState>>> {
        return object :
            FlowNetworkBoundResource<List<CraftBeerWithFavouriteState>, List<CraftBeer>>() {

            override suspend fun saveNetworkResult(item: List<CraftBeer>) {
                withContext(Dispatchers.Default) {
                    sp.beersLastRequestTime = Date().time
                    dao.saveAllCraftBeers(item)
                }
            }

            override fun shouldFetch(): Boolean {
                return Date().time.minus(sp.beersLastRequestTime) > DateUtils.DAY_IN_MILLIS
            }

            override suspend fun loadFromDb(): Flow<List<CraftBeerWithFavouriteState>> {
                return dao.getAllCraftBeers()
            }

            override suspend fun fetchFromNetwork(): Flow<ApiResponse<List<CraftBeer>>> {
                return api.getAllBeers()
            }

        }.asFlow()
    }

    //here I am assuming I have all the data locally, and I have no way of accessing the data,
    // besides from the list, hence getting data just from persistancy, if in future will require deep links,
    // I would just implement using the bound resource..
    override fun getBeerById(id: Int): LiveData<CraftBeerWithFavouriteState> {
        return dao.getCraftBeerWithFavouriteById(id)
    }

    override suspend fun handleFavouriteBeer(beerId: Int, action: FavoriteBeerAction) {
        when (action) {
            FavoriteBeerAction.ADD_TO_FAVORITE ->
                withContext(Dispatchers.Default) {
                    dao.addBeerToFavorites(FavoriteBeer(beerId))
                }
            FavoriteBeerAction.REMOVE_FROM_FAVORITES -> withContext(Dispatchers.Default) {
                dao.removeBeerFromFavorites(beerId)
            }
        }
    }
}