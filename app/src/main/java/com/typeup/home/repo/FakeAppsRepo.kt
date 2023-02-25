package com.typeup.home.repo

import com.typeup.home.data_source.FakeInstalledAppsDataSource
import com.typeup.model.AppInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAppsRepo : InstalledAppsRepo {

    override fun get(): Flow<List<AppInfo>> {
        val fakeDataSource = FakeInstalledAppsDataSource()

        return flow {
            emit(emptyList())

            emit(fakeDataSource.get())
        }
    }

}