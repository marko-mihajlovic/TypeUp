package com.typeup.util

import android.util.Log

private const val showLog = true
fun logMessage(logMsg: String) {
    if (showLog)
        Log.d("com.typeup_logMessage", logMsg)
}

