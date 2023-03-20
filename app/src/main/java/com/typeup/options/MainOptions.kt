package com.typeup.options

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.options.main.MaxShownItems
import com.typeup.options.main.MoreOptions
import com.typeup.options.main.ThemeSettings

object MainOptions {

    private enum class Item(val positionInList: Int) {
        MAX_NUM(0),
        THEME(1),
        REFRESH(2),
        MORE(3),
    }

    fun showDialog(context: Context, onRefresh: (refresh: Boolean) -> Unit) {
        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(R.string.menuOptionsTitle)
            .setItems(R.array.options) { dialog, x ->
                dialog.cancel()

                when (x) {
                    Item.MAX_NUM.positionInList -> MaxShownItems.showDialog(context, onRefresh)
                    Item.THEME.positionInList -> ThemeSettings.showDialog(context)
                    Item.REFRESH.positionInList -> onRefresh(true)
                    Item.MORE.positionInList -> MoreOptions.showDialog(context)
                    else -> {}
                }
            }
            .setNegativeButton(R.string.cancelTxt) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}
