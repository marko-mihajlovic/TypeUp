package com.typeup.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.typeup.BuildConfig
import com.typeup.R

/**
 * @author Marko Mihajlovic aka Fybriz
 * @see - Available on Google Play {https://play.google.com/store/apps/details?id=com.typeup}
 */
const val GP_URL = "https://play.google.com/store/apps/details?id="

/** Keyboard **/
fun toggleKeyboard(activity: Activity, view : EditText?, visible : Boolean){
    if(visible){
        view?.post{
            view.requestFocus()
            val inputMethodManager = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }else{
        val imm = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
        view?.text?.clear()
    }
}

/** Share app link */
fun shareTypeUpLink(context: Context){
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
fun openIntent(context: Context, intent: Intent){
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        showToast(context, context.getString(R.string.unableToOpen))
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        showToast(context,  context.getString(R.string.unableToOpen))
    }
}

/** Show UI msg **/
fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(context, message , duration).show()
}

/** Inflater */
fun getInflater(context: Context): LayoutInflater {
    return context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}