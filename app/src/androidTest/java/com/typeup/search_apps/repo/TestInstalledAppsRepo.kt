package com.typeup.search_apps.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.typeup.search_apps.data.data_source.test.FakeInstalledAppsDataSource
import com.typeup.search_apps.data.data_source.test.FakeInstalledAppsDataSource2
import com.typeup.search_apps.data.repo.InstalledAppsRepoImpl
import com.typeup.util.SharedPref
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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

        // Clear cache
        SharedPref.edit(context).remove("installed_apps").commit()

        // Check memory cache is still available
        assertThat(repo.get().firstOrNull(), `is`(not(empty())))

        // baseline check for later tests
        val repoAppNameList1a = repo.get().firstOrNull()?.map { it.appName }
        val fakeAppNameList1a = fakeDataSource.get().map { it.appName }
        assertThat(repoAppNameList1a?.size, `is`(fakeAppNameList1a.size))
        assertThat(repoAppNameList1a, containsInAnyOrder(*fakeAppNameList1a.toTypedArray()))

        // Change cache with different data source
        val fakeList2 = FakeInstalledAppsDataSource2().get()
        val jsonString = Json.encodeToString(fakeList2)
        SharedPref.edit(context).putString("installed_apps", jsonString).apply()

        // Check cache still shows old data, because refresh is false by default
        val repoAppNameList1b = repo.get().firstOrNull()?.map { it.appName }
        val fakeAppNameList1b = fakeDataSource.get().map { it.appName }
        assertThat(repoAppNameList1b?.size, `is`(fakeAppNameList1b.size))
        assertThat(repoAppNameList1b, containsInAnyOrder(*fakeAppNameList1b.toTypedArray()))

        // Check refresh works
        // Check new data is emitted when we request refresh
        val repoAppNameList2 = repo.get(refresh = true).firstOrNull()?.map { it.appName }
        val fakeAppNameList2 = fakeList2.map { it.appName }
        assertThat(repoAppNameList2?.size, `is`(fakeAppNameList2.size))
        assertThat(repoAppNameList2, containsInAnyOrder(*fakeAppNameList2.toTypedArray()))
    }


}