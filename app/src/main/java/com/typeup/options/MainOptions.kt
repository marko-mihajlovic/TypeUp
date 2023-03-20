package com.typeup.options

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.options.main.MaxShownItems
import com.typeup.options.main.MoreOptions
import com.typeup.options.main.ThemeSettings

object MainOptions {

    private enum class Item(val text: String) {
        MAX_NUM("Max items"),
        THEME("Theme"),
        REFRESH("Refresh"),
        MORE("More…");

        companion object {
            fun getItemWithText(text: String): Item {
                return Item.values().first { x ->
                    x.text == text
                }
            }
        }
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


