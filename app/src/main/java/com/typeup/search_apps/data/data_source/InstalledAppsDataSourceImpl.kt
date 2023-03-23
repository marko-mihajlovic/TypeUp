package com.typeup.search_apps.data.data_source

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import com.typeup.BuildConfig
import com.typeup.R
import com.typeup.TypeUpApp
import com.typeup.search_apps.data.model.AppInfo
import javax.inject.Inject

/**
 *  Returns all installed apps using PackageManager
 */
class InstalledAppsDataSourceImpl : InstalledAppsDataSource {

    private val context = TypeUpApp.appContext

    override fun get(): List<AppInfo> {
        val list: MutableList<AppInfo> = getInstalledApps().map { x ->
            AppInfo(
                appId = x.activityInfo.applicationInfo.packageName,
                launcherActivity = x.activityInfo.name,
                appName = x.loadLabel(context.packageManager).toString()
            )
        }.toMutableList()

        list.add(getThisApp())

        return list
    }

    private fun getInstalledApps(): List<ResolveInfo> {
        val main = Intent(Intent.ACTION_MAIN, null)
        main.addCategory(Intent.CATEGORY_LAUNCHER)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.queryIntentActivities(
                main,
                PackageManager.ResolveInfoFlags.of(
                    PackageManager.MATCH_ALL.toLong()
                )
            )
        } else {
            context.packageManager.queryIntentActivities(main, 0)
        }
    }

    private fun getThisApp(): AppInfo {
        return AppInfo(
            appName = context.getString(R.string.app_name_label),
            appId = BuildConfig.APPLICATION_ID,
            launcherActivity = context.getString(R.string.app_launcher_activity)
        )
    }
}