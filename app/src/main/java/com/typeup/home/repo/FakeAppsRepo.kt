package com.typeup.home.repo

import com.typeup.home.data_source.FakeInstalledAppsDataSource
import com.typeup.home.model.AppInfo
import com.typeup.options.main.MaxShownItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAppsRepo(
    private val maxSize: Int = MaxShownItems.default
) : InstalledAppsRepo {

    override fun get(): Flow<List<AppInfo>> {
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