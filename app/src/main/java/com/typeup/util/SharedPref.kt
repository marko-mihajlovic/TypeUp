package com.typeup.util

import android.content.Context
import android.content.SharedPreferences
import com.typeup.TypeUpApp

object SharedPref {

    private const val prefKey = "appPref"

    fun get(): SharedPreferences {
        return TypeUpApp.appContext.getSharedPreferences(prefKey, Context.MODE_PRIVATE)
    }

    fun edit(): SharedPreferences.Editor {
        return get().edit()
    }

}

