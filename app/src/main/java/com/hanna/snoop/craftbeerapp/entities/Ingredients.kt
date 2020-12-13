package com.hanna.snoop.craftbeerapp.entities

import com.google.gson.annotations.SerializedName

data class Ingredients(val malt: List<Malt>, val hops: List<Hops>, val yeast: String)

enum class AmountUnit(val representation: String) {
    @SerializedName("kilograms")
    KILOGRAMS("kg"),

    @SerializedName("grams")
    GRAMS("gr");
}

//before serializing the AmountUnit as an enum, I should have a defined contract with backend, however,
// since I can assume here, that the units will be just kg, and gr, I have implemented as enum.
data class Amount(val value: Double, val unit: AmountUnit) {
    override fun equals(other: Any?): Boolean {
        return this === other || (other != null && other is Amount &&
                this.value == other.value && this.unit == other.unit)
    }

    override fun hashCode(): Int {
        return this.value.plus(unit.ordinal).toInt()
    }
}

sealed class Ingredient(val name: String, val amount: Amount) {
    override fun equals(other: Any?): Boolean {
        val sameIngredientType = other != null && other is Ingredient && this::class == other::class
        other as Ingredient
        return sameIngredientType && this.name == other.name && amount == other.amount
    }

    override fun hashCode(): Int {
        return name.length * amount.hashCode()
    }
}

class Malt(name: String, amount: Amount) : Ingredient(name, amount)

//the attribute should be converted to an enum, but since I have no documentation
// and contract with BE, for now I have left as a string
class Hops(name: String, amount: Amount, val add: String, val attribute: String) :
    Ingredient(name, amount) {

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
                && this.add == (other as? Hops)?.add && this.attribute == other.attribute
    }

    override fun hashCode(): Int {
        return name.length * amount.hashCode().plus(add.length)
    }
}