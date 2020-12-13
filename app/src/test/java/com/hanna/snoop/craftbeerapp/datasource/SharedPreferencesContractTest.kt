package com.hanna.snoop.craftbeerapp.datasource

import android.os.Build
import androidx.preference.PreferenceManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.hanna.snoop.craftbeerapp.datasource.SharedPreferencesContract
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SharedPreferencesContractTest {
    private lateinit var sharedPreferencesContract: SharedPreferencesContract

    @Before
    fun reset() {
        sharedPreferencesContract = SharedPreferencesContract(//re-init sp for each test to start with clear data
            PreferenceManager.getDefaultSharedPreferences(
                InstrumentationRegistry.getInstrumentation().context
            )
        )
    }

    @Test
    fun `get last queried time, for first time returns 0`() {
        val lastFetched = sharedPreferencesContract.beersLastRequestTime
        assertThat(lastFetched).isEqualTo(0)
    }

    @Test
    fun `get last queried time, after been set, returns set time ms`() {
        val ms: Long by lazy {
            Date().time
        }
        sharedPreferencesContract.beersLastRequestTime = ms
        val lastFetched = sharedPreferencesContract.beersLastRequestTime
        assertThat(lastFetched).isEqualTo(ms)
    }
}