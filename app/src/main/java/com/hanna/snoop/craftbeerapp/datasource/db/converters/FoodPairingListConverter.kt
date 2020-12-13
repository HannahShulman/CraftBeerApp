package com.hanna.snoop.craftbeerapp.datasource.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//Prototypes - V
//Tests - V - (FoodPairingListConverterTest)
class FoodPairingListConverter {

    @TypeConverter
    fun listToString(foodPairingList: List<String>): String = Gson().toJson(foodPairingList)

    @TypeConverter
    fun stringToFoodPairingList(value: String): List<String> =
        Gson().fromJson(value, object : TypeToken<ArrayList<String>>() {}.type)
}