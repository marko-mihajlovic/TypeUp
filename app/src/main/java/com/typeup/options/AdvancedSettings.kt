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
    enum class Item(
        override val id: Int,
        override val text: String,
        override var bool: Boolean
    ) : EnumWithText, EnumWithBool, EnumWithId {

        AUTO_CLEAR_ON_APP_CLICK(5, "Auto clear search on app click", true),
        AUTO_CLEAR_ON_APP_OPTION_CLICK(6, "Auto clear search on app option click", true),
        INCLUDE_APP_ID(7, "Also search by application ID (package name)", false);

        fun getSavedBool(): Boolean {
            val items = getSavedOrDefault()
            return items.firstOrNull { x ->
                this.id == x.id
            }?.bool ?: this.bool
        }

        companion object :
            CompanionEnumWithText<Item>,
            CompanionEnumWithBool<Item>,
            CompanionEnumWithId<Item>
    }


    fun showDialog(context: Context) {
        val items = getSavedOrDefault()

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

    private fun getSavedOrDefault(): Array<Item> {
        val jsonString = SharedPref.get().getString("advanced_settings", "") ?: "";
        if (jsonString.isEmpty())
            return Item.values()

        try {
            return Json.decodeFromString(jsonString)
        } catch (e: IllegalArgumentException) {
            return Item.values()
        }
    }
}
