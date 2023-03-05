package com.typeup.search_apps.data.data_source.test

import com.typeup.search_apps.data.data_source.InstalledAppsDataSource
import com.typeup.search_apps.data.model.AppInfo

class FakeInstalledAppsDataSource : InstalledAppsDataSource {

    override fun get(): List<AppInfo> {
        val list = mutableListOf(
            AppInfo(
                appId = "com.example.app",
                launcherActivity = "com.example.app.SplashActivity",
                appName = "Some App"
            ),
            AppInfo(
                appId = "com.different.app",
                launcherActivity = "com.package.app.deep.Activity",
                appName = "App2"
            ),
        )

        list.addAll(
            listOf(
                "Last_abc",
                "Abc",
                "Salty_bacon",
                "Abc_bacon",
                "Bacon",
                "Bob",
                "Abc_johnny",
                "Abc2"
            ).map { x ->
                getSingleAppInfo(x)
            }
        )

        return list.shuffled()
    }

    private fun getSingleAppInfo(appName: String): AppInfo {
        return AppInfo(
            appId = "com.example.app",
            launcherActivity = "com.example.app.Activity",
            appName = appName
        )
    }

}