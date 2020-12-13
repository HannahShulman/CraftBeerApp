package com.hanna.snoop.craftbeerapp.datasource.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.hanna.snoop.craftbeerapp.entities.CraftBeer
import com.hanna.snoop.craftbeerapp.entities.CraftBeerWithFavouriteState
import com.hanna.snoop.craftbeerapp.entities.FavoriteBeer
import kotlinx.coroutines.flow.Flow

//Prototypes - N/A
//Tests - N/A
@Dao
interface CraftBeerDao {

    @Insert(onConflict = REPLACE)
    fun insertCraftBeer(list: CraftBeer)

    @Insert(onConflict = IGNORE)
    suspend fun addBeerToFavorites(id: FavoriteBeer)

    @Query("delete from FavoriteBeer where beerId =:id")
    fun removeBeerFromFavorites(id: Int)

    @Insert(onConflict = REPLACE)
    fun insertCraftBeers(list: List<CraftBeer>)

    @Query(
        """SELECT CraftBeer.*, CraftBeer.id as id, FavoriteBeer.beerId,  case when (beerId is NULL) Then 0
            ELSE 1 END as isFavourite FROM CraftBeer LEFT JOIN FavoriteBeer on FavoriteBeer.beerId = CraftBeer.id"""
    )
    fun getAllCraftBeers(): Flow<List<CraftBeerWithFavouriteState>>

    @Query("SELECT * FROM CraftBeer WHERE id =:id")
    fun getCraftBeerById(id: Int): LiveData<CraftBeer>

    @Query("""SELECT CraftBeer.*, 
            CASE WHEN (SELECT NOT EXISTS (SELECT beerId FROM FavoriteBeer WHERE beerId = :id)) THEN 0  
            ELSE 1  END AS isFavourite FROM CraftBeer WHERE CraftBeer.id =:id""")
    fun getCraftBeerWithFavouriteById(id: Int): LiveData<CraftBeerWithFavouriteState>

    @Query("SELECT EXISTS(SELECT * FROM FavoriteBeer WHERE beerId = :id)")
    suspend fun isFavourite(id: Int): Boolean

    @Query("DELETE FROM CraftBeer")
    fun deleteAllCraftBeers()

    @Transaction
    fun saveAllCraftBeers(list: List<CraftBeer>) {
        deleteAllCraftBeers()
        insertCraftBeers(list)
    }
}