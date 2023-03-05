package com.typeup.search_apps.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AppInfo(
    val appId: String,
    val launcherActivity: String,
    val appName: String,
    val appNameLowercase: String = appName.lowercase()
)