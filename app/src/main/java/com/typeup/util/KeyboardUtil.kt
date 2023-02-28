package com.typeup.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

object KeyboardUtil {

    fun show(context: Context, view: EditText?) {
        view?.post {
            view.requestFocus()
            getInputMethodManager(context).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hide(activity: Activity, view: EditText?) {
        getInputMethodManager(activity).hideSoftInputFromWindow(
            activity.window.decorView.windowToken,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )

        view?.text?.clear()
    }

    private fun getInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    }

}