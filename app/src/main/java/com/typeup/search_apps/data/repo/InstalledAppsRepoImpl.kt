package com.typeup.search_apps.data.repo

import com.typeup.options.main.MaxShownItems
import com.typeup.search_apps.data.data_source.InstalledAppsDataSource
import com.typeup.search_apps.data.model.AppInfo
import com.typeup.search_apps.data.model.AppsRepoState
import com.typeup.util.SharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class InstalledAppsRepoImpl @Inject constructor(
    private val dataSource: InstalledAppsDataSource,
) : InstalledAppsRepo {

    private var apps: List<AppInfo> = emptyList()

    override fun get(refresh: Boolean): Flow<AppsRepoState> {
        return flow {
            if (apps.isEmpty() || refresh) {
                val cache = getCachedApps()
                apps = cache

                emit(
                    AppsRepoState(
                        data = cache,
                        isLoading = refresh,
                    )
                )

                if (cache.isEmpty() || refresh) {
                    val installedApps = dataSource.get()
                    apps = installedApps
                    saveCache(installedApps)
                    emit(AppsRepoState(installedApps))
                }
            } else {
                emit(AppsRepoState(apps))
            }
        }
    }

    override fun getMaxSize(): Int {
        return MaxShownItems.getMaxItems()
    }

    private fun saveCache(installedApps: List<AppInfo>) {
        val jsonString = Json.encodeToString(installedApps)
        SharedPref.edit().putString("installed_apps", jsonString).apply()
    }

    private fun getCachedApps(): List<AppInfo> {
        val string = SharedPref.get().getString("installed_apps", "") ?: ""

        return try {
            Json.decodeFromString(string) ?: emptyList()
        } catch (e: IllegalArgumentException) {
            emptyList()
        }
    }

}