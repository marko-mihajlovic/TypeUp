package com.typeup.search_apps.data.repo

import com.typeup.search_apps.data.model.AppsRepoState
import kotlinx.coroutines.flow.Flow

interface InstalledAppsRepo {

    fun get(refresh: Boolean = false): Flow<AppsRepoState>

    fun getMaxSize(): Int

}