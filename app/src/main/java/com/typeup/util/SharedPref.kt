package com.typeup.util

import android.content.Context
import android.content.SharedPreferences

object SharedPref {

    private const val prefKey = "appPref"

    fun get(context: Context): SharedPreferences {
        return context.getSharedPreferences(prefKey, Context.MODE_PRIVATE)
    }

    fun edit(context: Context): SharedPreferences.Editor {
        return get(context).edit()
    }

}

