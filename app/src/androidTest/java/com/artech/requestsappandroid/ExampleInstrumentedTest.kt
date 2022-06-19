package com.artech.requestsappandroid

import android.provider.SyncStateContract
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.domain.use_case.get_repair_part.GetRepairPartUseCase
import com.artech.requestsappandroid.presentation.ui.screens.change_password.ChangePasswordViewModel
import com.artech.requestsappandroid.utils.Constants

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals(Constants.SharedPreferences.APP_NAME, appContext.packageName)
    }
}