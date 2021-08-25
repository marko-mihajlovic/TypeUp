package com.typeup.model

/**
 * @author Marko Mihajlovic - Fybriz
 * @see - Available on Google Play {https://play.google.com/store/apps/details?id=com.typeup}
 */
data class AppInfo (
    val packageName: String,
    val launcherActivity: String,
    val appName: String,
    val appNameLowercase: String = appName.lowercase()
)