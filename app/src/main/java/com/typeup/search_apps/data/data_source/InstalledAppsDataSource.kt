package com.typeup.search_apps.data.data_source

import com.typeup.search_apps.data.model.AppInfo

interface InstalledAppsDataSource {

    fun get(): List<AppInfo>

}