package com.hanna.snoop.craftbeerapp.datasource.db.converters

import com.google.common.truth.Truth.assertThat
import com.hanna.snoop.craftbeerapp.entities.Amount
import com.hanna.snoop.craftbeerapp.entities.AmountUnit
import com.hanna.snoop.craftbeerapp.entities.Malt
import org.junit.Test

class IngredientEntityTest {

    @Test
    fun `gram amount unit representation is gr`() {
        val amountUnit = AmountUnit.GRAMS
        assertThat(amountUnit.representation).isEqualTo("gr")
    }

    @Test
    fun `kilogram amount unit representation is kilogram`() {
        val amountUnit = AmountUnit.KILOGRAMS
        assertThat(amountUnit.representation).isEqualTo("kg")
    }

    @Test
    fun `ingredient are the same, between 2 malts, areTheSame is true`() {
        val amount = Amount(1.2, AmountUnit.KILOGRAMS)
        val malt1 = Malt("malt1", amount)
        val malt2 = Malt("malt1", amount)
        assertThat(malt1.areTheSame(malt2)).isTrue()
    }

    @Test
    fun `ingredient are not the same, between 2 malts, areTheSame is False`() {
        val amount = Amount(1.2, AmountUnit.KILOGRAMS)
        val malt1 = Malt("malt1", amount)
        val malt2 = Malt("malt2", amount)
        assertThat(malt1.areTheSame(malt2)).isFalse()
    }
}