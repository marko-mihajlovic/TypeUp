package com.typeup.home.data_source

import com.typeup.home.model.AppInfo

interface InstalledAppsDataSource {

    fun get(): List<AppInfo>

}