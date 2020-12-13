package com.hanna.snoop.craftbeerapp.datasource

import android.content.SharedPreferences
import javax.inject.Inject

//Prototypes - V
//Tests - V - (SharedPreferencesContractTest)
class SharedPreferencesContract @Inject constructor(private val sharedPreferences: SharedPreferences){

    @Suppress("PrivatePropertyName")
    private val BEERS_REQUEST_TIME = "beers_request_time"

    var beersLastRequestTime: Long = 0L
    set(value) {
        field = value
        sharedPreferences.edit().putLong(BEERS_REQUEST_TIME, value).apply()
    }
    get() = sharedPreferences.getLong(BEERS_REQUEST_TIME, 0L)

}