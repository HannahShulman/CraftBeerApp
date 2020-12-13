package com.hanna.snoop.craftbeerapp.viewmodel

import androidx.lifecycle.*
import com.hanna.snoop.craftbeerapp.OpenForTesting
import com.hanna.snoop.craftbeerapp.datasource.network.Resource
import com.hanna.snoop.craftbeerapp.entities.CraftBeerWithFavouriteState
import com.hanna.snoop.craftbeerapp.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OpenForTesting
class BeersViewModel(private val beersRepository: BeerRepository) : ViewModel() {

    val _beerId = MutableLiveData<Int>()

    fun fetchAllBeers(): Flow<Resource<List<CraftBeerWithFavouriteState>>> =
        beersRepository.getBeers()

    val beerById = Transformations.switchMap(_beerId) {
        return@switchMap beersRepository.getBeerById(it)
    }

    fun handleFavouriteBeer(beerId: Int, action: FavoriteBeerAction) {
        viewModelScope.launch {
            beersRepository.handleFavouriteBeer(beerId, action)
        }
    }
}

enum class FavoriteBeerAction {
    ADD_TO_FAVORITE, REMOVE_FROM_FAVORITES
}

class BeersViewModelFactory @Inject constructor(private val beersRepository: BeerRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BeersViewModel(beersRepository) as T
    }

}