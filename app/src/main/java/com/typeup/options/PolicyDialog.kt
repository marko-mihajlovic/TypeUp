package com.typeup.options

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.util.getSharedPref

object PolicyDialog {

    private const val hasAcceptedPolicyKey = "hasAcceptedPrivacyPolicy-v1-t4"

    fun tryToShow(context: Context, forceShow: Boolean) {
        val appPref = getSharedPref(context)
        val hasAcceptedPP = appPref.getBoolean(hasAcceptedPolicyKey, false)

        if (!hasAcceptedPP || forceShow)
            showDialog(context, appPref, hasAcceptedPP)
    }

    private fun showDialog(context: Context, appPref: SharedPreferences, hasAcceptedPP: Boolean) {
        AlertDialog.Builder(context, R.style.Dialog)
            .setCancelable(hasAcceptedPP)
            .setTitle(context.getString(R.string.privacyPolicyTxt))
            .setMessage(context.getString(R.string.privacyLongTxt))
            .setPositiveButton(
                if (hasAcceptedPP)
                    context.getString(R.string.ok)
                else
                    context.getString(R.string.accept),
            ) { d, _ ->
                appPref.edit().putBoolean(hasAcceptedPolicyKey, true).apply()
                d.dismiss()
            }
            .show()
    }


}