package com.typeup.search_apps.data.data_source.test

import com.typeup.search_apps.data.data_source.InstalledAppsDataSource
import com.typeup.search_apps.data.model.AppInfo

class FakeInstalledAppsDataSource2 : InstalledAppsDataSource {

    override fun get(): List<AppInfo> {
        return listOf(
            "Bar123",
            "Bar",
            "Buzz123",
            "Buzz",
            "Zzz",
        ).map { x ->
            getSingleAppInfo(x)
        }.shuffled()
    }

    private fun getSingleAppInfo(appName: String): AppInfo {
        return AppInfo(
            appId = "com.example.app",
            launcherActivity = "com.example.app.Activity",
            appName = appName
        )
    }

}