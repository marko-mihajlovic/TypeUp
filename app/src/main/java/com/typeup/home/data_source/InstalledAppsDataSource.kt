package com.typeup.home.data_source

import com.typeup.model.AppInfo

interface InstalledAppsDataSource {

    fun get(): List<AppInfo>

}