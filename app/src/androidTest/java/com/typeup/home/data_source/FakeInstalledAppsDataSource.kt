package com.typeup.home.data_source

import com.typeup.model.AppInfo

class FakeInstalledAppsDataSource : InstalledAppsDataSource {

    override fun get(): List<AppInfo> {
        return listOf(
            AppInfo(
                packageName = "com.example.app",
                launcherActivity = "com.example.app.SplashActivity",
                appName = "Some App"
            ),
            AppInfo(
                packageName = "com.different.app",
                launcherActivity = "com.package.app.deep.Activity",
                appName = "App2"
            )
        )
    }

}