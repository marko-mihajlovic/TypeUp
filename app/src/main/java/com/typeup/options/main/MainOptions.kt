package com.typeup.options.main

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.options.more.MoreOptions

object MainOptions {

    private enum class Item(val positionInList: Int) {
        MAX_NUM(0),
        THEME(1),
        MORE(2),
    }

    fun showDialog(context: Context) {
        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(context.getString(R.string.menuOptionsTitle))
            .setItems(R.array.options) { dialog, x ->
                dialog.cancel()

                when (x) {
                    Item.THEME.positionInList -> ThemeSettings.showDialog(context)
                    Item.MAX_NUM.positionInList -> MaxShownItems.showDialog(context)
                    Item.MORE.positionInList -> MoreOptions.showDialog(context)
                    else -> {}
                }
            }
            .setNegativeButton(context.getString(R.string.cancelTxt)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}
