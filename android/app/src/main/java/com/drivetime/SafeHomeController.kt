package com.drivetime

class SafeHomeController {
    private val driveStateManager = DriveStateManager()

    fun openMusic() {
        // Launch Spotify or default music app.
    }

    fun openMaps() {
        // Launch Google Maps or navigation app.
    }

    fun openCall() {
        // Launch call UI.
    }

    fun openAssistant() {
        // Launch assistant voice flow.
    }

    fun getState(): DriveState = driveStateManager.getState()
}
