package com.typeup

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.typeup.home.AppsDataSource
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestAppsDataSource {

    @Test
    fun test_there_are_apps() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val list = AppsDataSource.get(appContext)
        assertThat(list, `is`(not(empty())))
    }

    @Test
    fun test_some_app_exists() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val list = AppsDataSource.get(appContext)
        assertThat(
            list.firstOrNull { x ->
                x.appName == "Gmail"
            },
            `is`(not(nullValue()))
        )
    }
}