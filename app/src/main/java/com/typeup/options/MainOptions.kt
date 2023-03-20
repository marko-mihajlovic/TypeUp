package com.typeup.options

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.options.main.MaxShownItems
import com.typeup.options.main.MoreOptions
import com.typeup.options.main.ThemeSettings

object MainOptions {

    private enum class Item(override val text: String) : EnumWithText {
        MAX_NUM("Max items"),
        THEME("Theme"),
        REFRESH("Refresh"),
        MORE("More…");

        companion object : EnumCompanion<Item>
    }


    fun showDialog(context: Context, onRefresh: (refresh: Boolean) -> Unit) {
        val list: Array<String> = Item.values().map { it.text }.toTypedArray()

        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(R.string.menuOptionsTitle)
            .setItems(list) { dialog, position ->
                dialog.cancel()

                when (Item.getItemWithText(list[position])) {
                    Item.MAX_NUM -> MaxShownItems.showDialog(context, onRefresh)
                    Item.THEME -> ThemeSettings.showDialog(context)
                    Item.REFRESH -> onRefresh(true)
                    Item.MORE -> MoreOptions.showDialog(context)
                }
            }
            .setNegativeButton(R.string.cancelTxt) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}


