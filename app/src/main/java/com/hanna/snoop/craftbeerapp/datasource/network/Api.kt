package com.hanna.snoop.craftbeerapp.datasource.network

import com.hanna.snoop.craftbeerapp.entities.CraftBeer
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET


//Prototypes - N/A
//Tests - N/A
interface Api {

    @GET("beers")
    fun getAllBeers(): Flow<ApiResponse<List<CraftBeer>>>
}