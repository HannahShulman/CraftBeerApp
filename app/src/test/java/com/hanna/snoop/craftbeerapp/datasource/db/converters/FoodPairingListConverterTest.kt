package com.hanna.snoop.craftbeerapp.datasource.db.converters

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FoodPairingListConverterTest {

    private val converter = FoodPairingListConverter()
    private val list = listOf("First product", "Second Product")

    @Test
    fun `convert food pairing list to string`() {
        val convertedList = converter.listToString(list)
        assertThat(convertedList).isEqualTo("[\"First product\",\"Second Product\"]")
    }

    @Test
    fun `convert string to list of food pairing`() {
        val convertedString =
            converter.stringToFoodPairingList("[\"First product\",\"Second Product\"]")
        assertThat(convertedString).isEqualTo(list)
    }


    @Test
    fun `convert empty food pairing list to string`() {
        val convertedList = converter.listToString(emptyList())
        assertThat(convertedList).isEqualTo("[]")
    }


    @Test
    fun `convert string of empty list, to  list of food pairing`() {
        val convertedString = converter.stringToFoodPairingList("[]")
        assertThat(convertedString).isEqualTo(emptyList<String>())
    }
}