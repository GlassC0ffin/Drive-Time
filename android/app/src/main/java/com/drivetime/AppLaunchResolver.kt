package com.drivetime

class AppLaunchResolver {
    fun candidatesForMusic(): List<String> = listOf(
        "com.spotify.music",
        "com.google.android.music",
        "com.android.music",
        "com.amazon.mp3"
    )

    fun candidatesForMaps(): List<String> = listOf(
        "com.google.android.apps.maps",
        "com.google.android.apps.navigate",
        "com.waze",
        "com.google.android.apps.mapslite"
    )

    fun candidatesForAssistant(): List<String> = listOf(
        "com.google.android.apps.googleassistant",
        "com.google.android.googlequicksearchbox",
        "com.google.android.apps.assistant"
    )

    fun resolvePackage(candidates: List<String>, installedPackages: Set<String>): String? {
        return candidates.firstOrNull { installedPackages.contains(it) }
    }
}
