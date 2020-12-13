package com.hanna.snoop.craftbeerapp.extensions

fun Double.removeTrailingZeros(): String {
    return toString().removeSuffix(".0")
}