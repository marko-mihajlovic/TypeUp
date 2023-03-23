package com.typeup.options

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.util.SharedPref
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object AdvancedSettings {

    @Serializable
    private enum class Item(
        override val text: String,
        override var bool: Boolean
    ) : EnumWithText, EnumWithBool {

        AUTO_CLEAR_ON_APP_CLICK("Auto clear search on app click", true),
        AUTO_CLEAR_ON_APP_OPTION_CLICK("Auto clear search on app option click", true),
        INCLUDE_APP_ID("Also search by application ID (package name)", false);

        companion object : CompanionEnumWithText<Item>, CompanionEnumWithBool<Item>
    }


    fun showDialog(context: Context) {
        val items = getSavedOrDefault(Item.values())

        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle("Advanced Settings")
            .setMultiChoiceItems(items.getTexts(), items.getBools()) { dialog, position, checked ->
                items[position].bool = checked
            }
            .setPositiveButton(R.string.saveTxt) { dialog, _ ->
                save(items)
            }
            .setNegativeButton(R.string.cancelTxt) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun save(items: Array<Item>) {
        val jsonString = Json.encodeToString(items)
        SharedPref.edit().putString("advanced_settings", jsonString).apply()
    }

    private fun getSavedOrDefault(default: Array<Item>): Array<Item> {
        val jsonString = SharedPref.get().getString("advanced_settings", "") ?: "";
        if (jsonString.isEmpty())
            return default

        try {
            return Json.decodeFromString(jsonString)
        } catch (e: IllegalArgumentException) {
            return default
        }
    }
}
