package com.typeup.ui.options

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.model.AppInfo
import com.typeup.util.GP_URL
import com.typeup.util.openIntent

/**
 * @author Marko Mihajlovic aka Fybriz
 * @see - Available on Google Play {https://play.google.com/store/apps/details?id=com.typeup}
 */
class SelectedAppActions(
    val context: Context
) {

    private enum class Item(val positionInList: Int) {
        INFO(0),
        GP(1),
    }

    fun openSelectedApp(appInfo : AppInfo){
        val name = ComponentName(
            appInfo.packageName,
            appInfo.launcherActivity
        )
        val i = Intent(Intent.ACTION_MAIN)

        i.addCategory(Intent.CATEGORY_LAUNCHER)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        i.component = name

        openIntent(context, i)
    }


    fun showLongClickOptions(appInfo : AppInfo){
        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(appInfo.appName)
            .setItems(R.array.selectedAppOptions) { dialog, x ->
                dialog.cancel()

                when (x) {
                    Item.INFO.positionInList -> openAppInfo(appInfo.packageName)
                    Item.GP.positionInList -> openInGP(appInfo.packageName)
                    else -> { }
                }
            }
            .setNegativeButton(context.getString(R.string.cancelTxt)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun openAppInfo(packageName : String){
        val uri = Uri.fromParts("package", packageName, null)
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
        openIntent(context, intent)
    }

    private fun openInGP(packageName : String){
        val uri = Uri.parse(GP_URL + packageName)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        openIntent(context, intent)
    }



}