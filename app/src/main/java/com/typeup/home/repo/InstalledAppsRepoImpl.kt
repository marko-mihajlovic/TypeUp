package com.typeup.home.repo

import android.content.Context
import com.typeup.home.data_source.InstalledAppsDataSource
import com.typeup.home.model.AppInfo
import com.typeup.options.MaxShownItems
import com.typeup.util.getSharedPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class InstalledAppsRepoImpl @Inject constructor(
    private val context: Context,
    private val dataSource: InstalledAppsDataSource,
) : InstalledAppsRepo {

    private var apps: List<AppInfo> = emptyList()

    public override fun get(): Flow<List<AppInfo>> {
        return flow {
            if (apps.isEmpty()) {
                val cache = getCachedApps()
                apps = cache
                emit(cache)

                val installedApps = dataSource.get()
                apps = installedApps
                saveCache(installedApps)
                emit(installedApps)
            } else {
                emit(apps)
            }
        }
    }

    override fun getMaxSize(): Int {
        return MaxShownItems.getMaxItems(context)
    }

    private fun saveCache(installedApps: List<AppInfo>) {
        val jsonString = Json.encodeToString(installedApps)
        getSharedPref(context).edit().putString("installed_apps", jsonString).apply()
    }

    private fun getCachedApps(): List<AppInfo> {
        val string = getSharedPref(context).getString("installed_apps", "") ?: ""

        return try {
            Json.decodeFromString(string) ?: emptyList()
        } catch (e: IllegalArgumentException) {
            emptyList()
        }
    }

}