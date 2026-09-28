package com.drivetime

data class DriveSettings(
    val musicApp: String = "Spotify",
    val mapsApp: String = "Google Maps",
    val assistantApp: String = "Gemini",
    val emergencyContacts: List<String> = listOf("Mom", "Dad"),
    val restrictedApps: List<String> = listOf(
        "com.instagram.android",
        "com.zhiliao.musically",
        "com.snapchat.android",
        "com.twitter.android",
        "com.facebook.katana"
    )
)

val knownRestrictedApps = listOf(
    "com.instagram.android",
    "com.zhiliao.musically",
    "com.snapchat.android",
    "com.twitter.android",
    "com.facebook.katana",
    "com.google.android.youtube",
    "com.android.chrome"
)