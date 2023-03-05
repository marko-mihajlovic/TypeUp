package com.typeup.search_apps.data.repo

import com.typeup.search_apps.data.model.AppInfo
import kotlinx.coroutines.flow.Flow

interface InstalledAppsRepo {

    fun get(refresh: Boolean = false): Flow<List<AppInfo>>

    fun getMaxSize(): Int

}