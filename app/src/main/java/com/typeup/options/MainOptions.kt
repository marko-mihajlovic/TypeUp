package com.typeup.options

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.util.openUrl
import com.typeup.util.shareTypeUpLink

class MainOptions {

    private enum class Item(val positionInList: Int) {
        MAX_NUM(0),
        THEME(1),
        DONATE(2),
        FEATURE_FEEDBACK(3),
        POLICY(4),
        SHARE(5),
    }

    fun showDialog(context: Context) {
        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(context.getString(R.string.menuOptionsTitle))
            .setItems(R.array.options) { dialog, x ->
                dialog.cancel()

                when (x) {
                    Item.THEME.positionInList -> ThemeSettings.showDialog(context)
                    Item.POLICY.positionInList -> PolicyDialog.tryToShow(context, true)
                    Item.MAX_NUM.positionInList -> MaxShownItems.showDialog(context)

                    Item.SHARE.positionInList -> shareTypeUpLink(context)

                    Item.FEATURE_FEEDBACK.positionInList -> openUrl(
                        context,
                        context.getString(R.string.featureFeedbackUrl)
                    )
                    Item.DONATE.positionInList -> openUrl(
                        context,
                        context.getString(R.string.donateUrl)
                    )
                    else -> {}
                }
            }
            .setNegativeButton(context.getString(R.string.cancelTxt)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}
