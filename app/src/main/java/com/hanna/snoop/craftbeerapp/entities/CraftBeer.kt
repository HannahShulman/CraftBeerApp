package com.hanna.snoop.craftbeerapp.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hanna.snoop.craftbeerapp.datasource.db.converters.FoodPairingListConverter
import com.hanna.snoop.craftbeerapp.datasource.db.converters.IngredientsConverter

@Entity
data class FavoriteBeer(@PrimaryKey val beerId: Int)

data class CraftBeerWithFavouriteState(
    @Embedded var beer: CraftBeer,
    var isFavourite: Boolean = false
)

@Entity
@TypeConverters(FoodPairingListConverter::class, IngredientsConverter::class)
class CraftBeer(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val abv: Double,
    val contributedBy: String,
    val foodPairing: List<String>,
    val ingredients: Ingredients
)//since other fields are not needed for display, I see no reason to serialize them, and store them locally.
// if any fields would be required in the future, I would use room migrations, to change the db scheme, and store the fields too.