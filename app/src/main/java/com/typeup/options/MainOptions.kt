package com.typeup.options

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.options.main.MaxShownItems
import com.typeup.options.main.MoreOptions
import com.typeup.options.main.ThemeSettings
import com.typeup.search_apps.ui.OnRefreshEvent

object MainOptions {

    private enum class Item(override val text: String) : EnumWithText {
        MAX_NUM("Max items"),
        THEME("Theme"),
        REFRESH("Refresh"),
        MORE("More…");

        companion object : CompanionEnumWithText<Item>
    }


    fun showDialog(context: Context) {
        val list = Item.getTexts()

        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(R.string.menuOptionsTitle)
            .setItems(list) { dialog, position ->
                dialog.cancel()

                when (Item.getItemWithText(list[position])) {
                    Item.MAX_NUM -> MaxShownItems.showDialog(context)
                    Item.THEME -> ThemeSettings.showDialog(context)
                    Item.REFRESH -> {
                        if (context is OnRefreshEvent)
                            context.onRefresh(true)
                    }
                    Item.MORE -> MoreOptions.showDialog(context)
                }
            }
            .setNegativeButton(R.string.cancelTxt) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}


