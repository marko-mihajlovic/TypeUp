package com.typeup.options.main

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.options.CompanionEnumWithText
import com.typeup.options.EnumWithText
import com.typeup.options.getItemWithText
import com.typeup.options.getTexts
import com.typeup.options.main.more.PolicyDialog
import com.typeup.util.LinkUtil

object MoreOptions {

    private enum class Item(override val text: String) : EnumWithText {
        FEATURE_FEEDBACK("Feedback"),
        POLICY("Privacy Policy"),
        SHARE("Share app link");

        companion object : CompanionEnumWithText<Item>
    }

    fun showDialog(context: Context) {
        val list = Item.getTexts()

        AlertDialog.Builder(context, R.style.Dialog)
            .setTitle(R.string.menuMoreOptionsTitle)
            .setItems(list) { dialog, position ->
                dialog.cancel()

                when (Item.getItemWithText(list[position])) {
                    Item.POLICY -> PolicyDialog.tryToShow(context, true)
                    Item.SHARE -> LinkUtil.shareTypeUpLink(context)
                    Item.FEATURE_FEEDBACK -> LinkUtil.openUrl(
                        context,
                        context.getString(R.string.featureFeedbackUrl)
                    )
                }
            }
            .setNegativeButton(R.string.cancelTxt) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}