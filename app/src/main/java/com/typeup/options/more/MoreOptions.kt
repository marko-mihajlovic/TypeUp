package com.typeup.options.more

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.options.common.PolicyDialog
import com.typeup.util.LinkUtil
import com.typeup.util.openUrl

object MoreOptions {

    private enum class Item(val positionInList: Int) {
        DONATE(0),
        FEATURE_FEEDBACK(1),
        POLICY(2),
        SHARE(3),
    }

    fun showDialog(context: Context) {
        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(context.getString(R.string.menuMoreOptionsTitle))
            .setItems(R.array.options_more) { dialog, x ->
                dialog.cancel()

                when (x) {
                    Item.POLICY.positionInList -> PolicyDialog.tryToShow(context, true)
                    Item.SHARE.positionInList -> LinkUtil.shareTypeUpLink(context)
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