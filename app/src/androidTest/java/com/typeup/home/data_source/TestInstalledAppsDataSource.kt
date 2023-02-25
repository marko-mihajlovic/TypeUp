package com.typeup.home.data_source

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestInstalledAppsDataSource {

    @Test
    fun test_there_are_apps() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val list = InstalledAppsDataSourceImpl(appContext).get()
        assertThat(list, `is`(not(empty())))
    }

    @Test
    fun test_some_app_exists() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val list = InstalledAppsDataSourceImpl(appContext).get()
        assertThat(
            list.firstOrNull { x ->
                x.appName == "Gmail"
            },
            `is`(not(nullValue()))
        )
    }
}