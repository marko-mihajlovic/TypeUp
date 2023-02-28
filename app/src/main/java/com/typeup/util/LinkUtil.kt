package com.typeup.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.typeup.BuildConfig

object LinkUtil {

    const val GP_URL = "https://play.google.com/store/apps/details?id="

    fun shareTypeUpLink(context: Context) {
        val txt = GP_URL + BuildConfig.APPLICATION_ID

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, txt)
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share TypeUp link")
        openIntent(context, shareIntent)
    }

    fun openUrl(context: Context, url: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        openIntent(context, intent)
    }

}