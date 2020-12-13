package com.hanna.snoop.craftbeerapp.datasource.db.converters

import com.google.common.truth.Truth.assertThat
import com.hanna.snoop.craftbeerapp.entities.*
import org.junit.Test

class IngredientsConverterTest {

    private val converter = IngredientsConverter()
    private val maltList = listOf(Malt("malt1", Amount(2.3, AmountUnit.KILOGRAMS)))
    private val hopsList =
        listOf(Hops("hops1", Amount(3.4, AmountUnit.KILOGRAMS), "start", "bitter"))

    @Test
    fun `convert ingredients to string`() {
        val convertedList =
            converter.ingredientsToString(Ingredients(maltList, hopsList, "yeast name"))
        assertThat(convertedList)
            .isEqualTo("{\"malt\":[{\"name\":\"malt1\",\"amount\":{\"value\":2.3,\"unit\":\"kilograms\"}}],\"hops\":[{\"add\":\"start\",\"attribute\":\"bitter\",\"name\":\"hops1\",\"amount\":{\"value\":3.4,\"unit\":\"kilograms\"}}],\"yeast\":\"yeast name\"}")
    }

    @Test
    fun `convert string to ingredients, converts with correct data`() {
        val ingredients =
            converter.stringToIngredients("{\"malt\":[{\"name\":\"malt1\",\"amount\":{\"value\":2.3,\"unit\":\"kilograms\"}}],\"hops\":[{\"add\":\"start\",\"attribute\":\"bitter\",\"name\":\"hops1\",\"amount\":{\"value\":3.4,\"unit\":\"kilograms\"}}],\"yeast\":\"yeast name\"}")
        assertThat(ingredients.malt.size).isEqualTo(1)
        assertThat(ingredients.malt[0].name).isEqualTo("malt1")
        assertThat(ingredients.malt[0].amount.unit).isEqualTo(AmountUnit.KILOGRAMS)
        assertThat(ingredients.malt[0].amount.value).isEqualTo(2.3)

        assertThat(ingredients.hops.size).isEqualTo(1)
        assertThat(ingredients.hops[0].name).isEqualTo("hops1")
        assertThat(ingredients.hops[0].amount.unit).isEqualTo(AmountUnit.KILOGRAMS)
        assertThat(ingredients.hops[0].amount.value).isEqualTo(3.4)

        assertThat(ingredients.yeast).isEqualTo("yeast name")
    }

}