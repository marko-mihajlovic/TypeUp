package com.typeup.search_apps.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.typeup.search_apps.data.data_source.FakeInstalledAppsDataSource
import com.typeup.search_apps.data.repo.InstalledAppsRepoImpl
import com.typeup.util.SharedPref
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestInstalledAppsRepo {

    @Test
    fun test_repo() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val fakeDataSource = FakeInstalledAppsDataSource()
        val repo = InstalledAppsRepoImpl(context, fakeDataSource)

        // Clear cache
        SharedPref.edit(context).remove("installed_apps").commit()

        // Check empty cache is emitted
        assertThat(repo.get().firstOrNull(), `is`(empty()))

        // Check installed apps are emitted
        assertThat(repo.get().lastOrNull(), `is`(not(empty())))

        // Wait for cache to be saved, because of sharedPref.apply()
        // https://developer.android.com/reference/android/content/SharedPreferences.Editor.html?hl=en#apply()
        delay(100)

        // Check cache is now available
        assertThat(repo.get().firstOrNull(), `is`(not(empty())))
    }


}