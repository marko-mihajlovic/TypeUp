package com.typeup.model

import kotlinx.serialization.Serializable

@Serializable
data class AppInfo (
    val packageName: String,
    val launcherActivity: String,
    val appName: String,
    val appNameLowercase: String = appName.lowercase()
)