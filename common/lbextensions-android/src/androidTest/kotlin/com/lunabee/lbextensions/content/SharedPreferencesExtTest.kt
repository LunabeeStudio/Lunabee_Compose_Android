package com.lunabee.lbextensions.content

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import kotlin.test.assertTrue

class SharedPreferencesExtTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Test
    fun putAndGetData() {
        val data = byteArrayOf(1, 2, 3, 4, 5, 6)
        sharedPreferences.edit {
            putData("data_key", data)
        }
        assertTrue { data.contentEquals(sharedPreferences.getData("data_key", null)) }
    }
}
