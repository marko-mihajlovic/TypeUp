package com.typeup.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.widget.Toast
import com.typeup.BuildConfig
import com.typeup.R

const val GP_URL = "https://play.google.com/store/apps/details?id="

/** Share app link */
fun shareTypeUpLink(context: Context) {
    val txt = "Minimalistic App Search Tool: " + GP_URL + BuildConfig.APPLICATION_ID

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, txt)
    }

    val shareIntent = Intent.createChooser(sendIntent, "Share TypeUp link")
    openIntent(context, shareIntent)
}


/** Opening URL **/
fun openUrl(context: Context, url: String?) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    openIntent(context, intent)
}

/** Open intent **/
fun openIntent(context: Context, intent: Intent) {
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        showToast(context, context.getString(R.string.unableToOpen))
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        showToast(context, context.getString(R.string.unableToOpen))
    }
}

/** Show UI msg **/
fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

/** Inflater */
fun getInflater(context: Context): LayoutInflater {
    return context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}