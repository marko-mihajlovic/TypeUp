package com.typeup.options.main.more

import android.content.Context
import android.content.SharedPreferences
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.typeup.R
import com.typeup.util.SharedPref

object PolicyDialog {

    private const val hasAcceptedPolicyKey = "hasAcceptedPrivacyPolicy-v2"

    fun tryToShow(context: Context, forceShow: Boolean) {
        val appPref = SharedPref.get(context)
        val hasAcceptedPP = appPref.getBoolean(hasAcceptedPolicyKey, false)

        if (!hasAcceptedPP || forceShow)
            showDialog(context, appPref, hasAcceptedPP)
    }

    private fun showDialog(context: Context, appPref: SharedPreferences, hasAcceptedPP: Boolean) {
        AlertDialog.Builder(context, R.style.Dialog)
            .setCancelable(hasAcceptedPP)
            .setTitle(R.string.privacyPolicyTxt)
            .setMessage(R.string.privacyLongTxt)
            .setPositiveButton(
                if (hasAcceptedPP)
                    R.string.ok
                else
                    R.string.accept,
            ) { d, _ ->
                appPref.edit().putBoolean(hasAcceptedPolicyKey, true).apply()
                d.dismiss()
            }
            .show()
            .also { x ->
                x.findViewById<TextView>(android.R.id.message)?.movementMethod =
                    LinkMovementMethod.getInstance()
            }
    }


}