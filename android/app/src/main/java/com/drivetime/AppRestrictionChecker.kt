package com.drivetime

class AppRestrictionChecker(
    private val restrictedPackages: Set<String> = DEFAULT_RESTRICTED_PACKAGES
) {
    private val allowedPackages = setOf(
        "com.spotify.music",
        "com.google.android.apps.maps",
        "com.google.android.dialer",
        "com.google.android.apps.googleassistant"
    )

    companion object {
        val DEFAULT_RESTRICTED_PACKAGES = setOf(
            "com.instagram.android",
            "com.zhiliao.musically",
            "com.snapchat.android",
            "com.twitter.android",
            "com.facebook.katana"
        )
    }

    fun isAllowed(packageName: String): Boolean {
        return allowedPackages.contains(packageName)
    }

    fun isRestricted(packageName: String): Boolean {
        val normalized = packageName.trim()
        if (normalized.isBlank()) return false
        return restrictedPackages.any { it.equals(normalized, ignoreCase = true) }
            || restrictedPackages.any { normalized.contains(it, ignoreCase = true) }
    }
}
