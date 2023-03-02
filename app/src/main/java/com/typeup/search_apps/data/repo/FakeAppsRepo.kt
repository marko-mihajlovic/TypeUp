package com.typeup.search_apps.data.repo

import com.typeup.options.main.MaxShownItems
import com.typeup.search_apps.data.data_source.FakeInstalledAppsDataSource
import com.typeup.search_apps.data.model.AppInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAppsRepo(
    private val maxSize: Int = MaxShownItems.default
) : InstalledAppsRepo {

    override fun get(refresh: Boolean): Flow<List<AppInfo>> {
        val fakeDataSource = FakeInstalledAppsDataSource()

        return flow {
            emit(emptyList())

            emit(fakeDataSource.get())
        }
    }

    override fun getMaxSize(): Int {
        return maxSize
    }

}