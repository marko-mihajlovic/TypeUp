package com.typeup.search_apps.ui.list

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.options.*
import com.typeup.search_apps.data.model.AppInfo
import com.typeup.util.AppUtil
import com.typeup.util.LinkUtil

object SelectedAppActions {

    private enum class Item(override val text: String) : EnumWithText {
        INFO("App info"),
        GP("Open in Google Play");

        companion object : CompanionEnumWithText<Item>
    }

    fun openApp(context: Context, appInfo: AppInfo, editText: EditText) {
        editText.text?.clear()

        val name = ComponentName(
            appInfo.appId,
            appInfo.launcherActivity
        )
        val i = Intent(Intent.ACTION_MAIN)

        i.addCategory(Intent.CATEGORY_LAUNCHER)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        i.component = name

        AppUtil.openIntent(context, i)
    }

    fun showAppOptions(context: Context, appInfo: AppInfo) {
        val list = Item.getTexts()

        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(appInfo.appName)
            .setItems(list) { dialog, position ->
                dialog.cancel()

                when (Item.getItemWithText(list[position])) {
                    Item.INFO -> openAppInfo(context, appInfo.appId)
                    Item.GP -> openInGP(context, appInfo.appId)
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