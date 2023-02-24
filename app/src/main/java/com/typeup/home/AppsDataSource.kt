package com.typeup.home

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import com.typeup.model.AppInfo

/**
 *  Returns all installed apps using PackageManager
 */
object AppsDataSource {

    public fun get(context: Context): List<AppInfo> {
        return getInstalledApps(context).map { x ->
            AppInfo(
                packageName =  x.activityInfo.applicationInfo.packageName,
                launcherActivity = x.activityInfo.name,
                appName = x.loadLabel(context.packageManager).toString()
            )
        }
    }

    private fun getInstalledApps(context: Context): List<ResolveInfo> {
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

}