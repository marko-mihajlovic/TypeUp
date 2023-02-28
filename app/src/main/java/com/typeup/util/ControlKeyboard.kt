package com.typeup.util

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

object ControlKeyboard {

    fun toggle(activity: Activity, view: EditText?, visible: Boolean) {
        if (visible) {
            view?.post {
                view.requestFocus()
                val inputMethodManager =
                    activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        } else {
            val imm =
                activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                activity.window.decorView.windowToken,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
            view?.text?.clear()
        }
    }

}