package com.hanna.snoop.craftbeerapp.datasource.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.hanna.snoop.craftbeerapp.entities.Ingredients

//Prototypes - V
//Tests - V (IngredientsConverterTest)
class IngredientsConverter {

    @TypeConverter
    fun ingredientsToString(ingredients: Ingredients): String = Gson().toJson(ingredients)

    @TypeConverter
    fun stringToIngredients(value: String): Ingredients =
        Gson().fromJson(value, Ingredients::class.java)
}
