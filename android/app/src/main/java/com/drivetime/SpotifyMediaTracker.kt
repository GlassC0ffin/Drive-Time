package com.drivetime

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context

class SpotifyMediaTracker(private val context: Context) {
    fun isSpotifyActive(): Boolean {
        val manager = context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager ?: return false
        val end = System.currentTimeMillis()
        val start = end - 30_000L
        val stats = manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, start, end) ?: return false

        return stats.any { stat ->
            stat.packageName == "com.spotify.music" && stat.lastTimeUsed >= start
        }
    }

    fun getCurrentTrack(): Pair<String, String>? {
        return if (isSpotifyActive()) {
            "Spotify" to "Active"
        } else {
            null
        }
    }

    fun getForegroundPackage(): String? {
        val manager = context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager ?: return null
        val end = System.currentTimeMillis()
        val start = end - 10 * 60 * 1000L
        val usageStats = manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, start, end) ?: return null

        var mostRecent: UsageStats? = null
        for (stat in usageStats) {
            if (mostRecent == null || stat.lastTimeUsed > mostRecent.lastTimeUsed) {
                mostRecent = stat
            }
        }

        return mostRecent?.packageName
    }
}
