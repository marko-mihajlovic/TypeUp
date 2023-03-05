package com.typeup.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import com.typeup.R

object AppUtil {

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

    private fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    fun getInflater(context: Context): LayoutInflater {
        return context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

}