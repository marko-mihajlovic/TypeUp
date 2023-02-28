package com.typeup.home.repo

import com.typeup.home.model.AppInfo
import kotlinx.coroutines.flow.Flow

interface InstalledAppsRepo {

    fun get(refresh: Boolean = false): Flow<List<AppInfo>>

    fun getMaxSize(): Int

}