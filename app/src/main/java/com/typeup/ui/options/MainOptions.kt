package com.typeup.ui.options

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.ui.MainViewModel
import com.typeup.util.openUrl
import com.typeup.util.shareTypeUpLink

/**
 * @author Marko Mihajlovic aka Fybriz
 * @see - Available on Google Play {https://play.google.com/store/apps/details?id=com.typeup}
 */
object MainOptions {

    private enum class Item(val positionInList: Int) {
        MAX_NUM(0),
        THEME(1),
        DONATE(2),
        FEATURE_FEEDBACK(3),
        POLICY(4),
        SHARE(5),
    }

    fun showDialog(context: Context, mainViewModel: MainViewModel) {
        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(context.getString(R.string.menuOptionsTitle))
            .setItems(R.array.options) { dialog, x ->
                dialog.cancel()

                when (x) {
                    Item.THEME.positionInList -> ThemeSettings.showDialog(context)
                    Item.POLICY.positionInList -> PolicyDialog.tryToShow(context, true)
                    Item.MAX_NUM.positionInList -> MaxShownItems.showDialog(context, mainViewModel)

                    Item.SHARE.positionInList -> shareTypeUpLink(context)

                    Item.FEATURE_FEEDBACK.positionInList -> openUrl(context, context.getString(R.string.featureFeedbackUrl))
                    Item.DONATE.positionInList -> openUrl(context, context.getString(R.string.donateUrl))
                    else -> { }
                }
            }
            .setNegativeButton(context.getString(R.string.cancelTxt)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}
