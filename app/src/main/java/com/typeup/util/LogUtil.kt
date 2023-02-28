package com.typeup.util

import android.util.Log

object LogUtil {

    private const val showLog = true
    fun print(logMsg: String) {
        if (showLog)
            Log.d("com.typeup_logMessage", logMsg)
    }

}

