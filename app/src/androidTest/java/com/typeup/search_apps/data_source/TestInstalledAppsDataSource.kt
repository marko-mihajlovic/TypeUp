package com.typeup.search_apps.data_source

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.typeup.search_apps.data.data_source.InstalledAppsDataSourceImpl
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestInstalledAppsDataSource {

    @Test
    fun test_there_are_apps() {
        val list = InstalledAppsDataSourceImpl().get()
        assertThat(list, `is`(not(empty())))
    }

    @Test
    fun test_some_app_exists() {
        val list = InstalledAppsDataSourceImpl().get()
        assertThat(
            list.firstOrNull { x ->
                x.appName == "Gmail"
            },
            `is`(not(nullValue()))
        )
    }
}