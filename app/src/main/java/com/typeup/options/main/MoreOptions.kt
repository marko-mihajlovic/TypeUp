package com.typeup.options.main

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.options.main.more.PolicyDialog
import com.typeup.util.LinkUtil

object MoreOptions {

    private enum class Item(val positionInList: Int) {
        FEATURE_FEEDBACK(0),
        POLICY(1),
        SHARE(2),
    }

    fun showDialog(context: Context) {
        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(R.string.menuMoreOptionsTitle)
            .setItems(R.array.options_more) { dialog, x ->
                dialog.cancel()

                when (x) {
                    Item.POLICY.positionInList -> PolicyDialog.tryToShow(context, true)
                    Item.SHARE.positionInList -> LinkUtil.shareTypeUpLink(context)
                    Item.FEATURE_FEEDBACK.positionInList -> LinkUtil.openUrl(
                        context,
                        context.getString(R.string.featureFeedbackUrl)
                    )
                    else -> {}
                }
            }
            .setNegativeButton(R.string.cancelTxt) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}