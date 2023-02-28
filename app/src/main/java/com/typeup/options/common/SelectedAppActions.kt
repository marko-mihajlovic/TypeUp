package com.typeup.options.common

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.home.model.AppInfo
import com.typeup.util.AppUtil
import com.typeup.util.LinkUtil

object SelectedAppActions{

    private enum class Item(val positionInList: Int) {
        INFO(0),
        GP(1),
    }

    fun openApp(context: Context, appInfo: AppInfo) {
        val name = ComponentName(
            appInfo.packageName,
            appInfo.launcherActivity
        )
        val i = Intent(Intent.ACTION_MAIN)

        i.addCategory(Intent.CATEGORY_LAUNCHER)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        i.component = name

        AppUtil.openIntent(context, i)
    }


    fun showAppOptions(context: Context, appInfo: AppInfo) {
        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(appInfo.appName)
            .setItems(R.array.selectedAppOptions) { dialog, x ->
                dialog.cancel()

                when (x) {
                    Item.INFO.positionInList -> openAppInfo(context, appInfo.packageName)
                    Item.GP.positionInList -> openInGP(context, appInfo.packageName)
                    else -> {}
                }
            }
            .setNegativeButton(R.string.cancelTxt) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun openAppInfo(context: Context, packageName: String) {
        val uri = Uri.fromParts("package", packageName, null)
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
        AppUtil.openIntent(context, intent)
    }

    private fun openInGP(context: Context, packageName: String) {
        val uri = Uri.parse(LinkUtil.GP_URL + packageName)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        AppUtil.openIntent(context, intent)
    }


}