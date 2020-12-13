package com.hanna.snoop.craftbeerapp.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hanna.snoop.craftbeerapp.entities.CraftBeer
import com.hanna.snoop.craftbeerapp.entities.FavoriteBeer

//Prototypes - N/A
//Tests - N/A
@Database(entities = [CraftBeer::class, FavoriteBeer::class], version = 1)
abstract class AppDb: RoomDatabase(){
    abstract fun craftBeerDao(): CraftBeerDao
}