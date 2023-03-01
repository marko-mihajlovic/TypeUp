package com.typeup.home.model

import kotlinx.serialization.Serializable

@Serializable
data class AppInfo(
    val appId: String,
    val launcherActivity: String,
    val appName: String,
    val appNameLowercase: String = appName.lowercase()
)