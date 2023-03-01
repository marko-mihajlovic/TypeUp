package com.typeup.home.repo

import android.content.Context
import com.typeup.home.data_source.InstalledAppsDataSource
import com.typeup.home.model.AppInfo
import com.typeup.options.main.MaxShownItems
import com.typeup.util.SharedPref
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

    override fun get(refresh: Boolean): Flow<List<AppInfo>> {
        return flow {
            if (apps.isEmpty() || refresh) {
                val cache = getCachedApps()
                apps = cache
                emit(cache)

                if (cache.isEmpty() || refresh) {
                    val installedApps = dataSource.get()
                    apps = installedApps
                    saveCache(installedApps)
                    emit(installedApps)
                }
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
        SharedPref.edit(context).putString("installed_apps", jsonString).apply()
    }

    private fun getCachedApps(): List<AppInfo> {
        val string = SharedPref.get(context).getString("installed_apps", "") ?: ""

        return try {
            Json.decodeFromString(string) ?: emptyList()
        } catch (e: IllegalArgumentException) {
            emptyList()
        }
    }

}