package com.typeup.home.data_source

import com.typeup.home.model.AppInfo

class FakeInstalledAppsDataSource : InstalledAppsDataSource {

    override fun get(): List<AppInfo> {
        return listOf(
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
        ) + listOf(
            "last_abc",
            "abc",
            "salty_bacon",
            "abc_bacon",
            "bacon",
            "bob",
            "abc_johnny",
            "abc2"
        ).map { x ->
            getSingleAppInfo(x)
        }
    }

    private fun getSingleAppInfo(appName: String): AppInfo {
        return AppInfo(
            appId = "com.example.app",
            launcherActivity = "com.example.app.Activity",
            appName = appName
        )
    }

}