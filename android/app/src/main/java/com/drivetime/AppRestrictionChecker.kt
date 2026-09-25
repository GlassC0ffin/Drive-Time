package com.drivetime

class AppRestrictionChecker {
    private val allowedPackages = setOf(
        "com.spotify.music",
        "com.google.android.apps.maps",
        "com.google.android.dialer",
        "com.google.android.apps.googleassistant"
    )

    private val restrictedKeywords = listOf(
        "instagram",
        "tiktok",
        "snapchat",
        "twitter",
        "browser"
    )

    fun isAllowed(packageName: String): Boolean {
        return allowedPackages.contains(packageName)
    }

    fun isRestricted(packageName: String): Boolean {
        return restrictedKeywords.any { packageName.contains(it, ignoreCase = true) }
    }
}
