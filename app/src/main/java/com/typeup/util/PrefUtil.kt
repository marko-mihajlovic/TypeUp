package com.typeup.util

import android.content.Context
import android.content.SharedPreferences

const val prefKey = "appPref"

fun getSharedPref(context : Context): SharedPreferences {
    return context.getSharedPreferences(prefKey, Context.MODE_PRIVATE)
}
