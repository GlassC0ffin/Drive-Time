package com.drivetime

import org.junit.Assert.assertEquals
import org.junit.Test

class DriveStateManagerTest {
    @Test
    fun `startDrive transitions to driving state`() {
        val manager = DriveStateManager()

        manager.startDrive()

        assertEquals(DriveState.DRIVING, manager.getState())
    }

    @Test
    fun `restrictedAppAttempt transitions to distraction state`() {
        val manager = DriveStateManager()

        manager.startDrive()
        manager.onRestrictedAppAttempt()

        assertEquals(DriveState.DISTRACTION, manager.getState())
    }

    @Test
    fun `endDrive transitions to drive ended state`() {
        val manager = DriveStateManager()

        manager.startDrive()
        manager.endDrive()

        assertEquals(DriveState.DRIVE_ENDED, manager.getState())
    }

    @Test
    fun `drive activation only starts from idle and blocks duplicate starts`() {
        val manager = DriveStateManager()

        assertEquals(true, manager.canActivateDrive())
        manager.startDrive()
        assertEquals(DriveState.DRIVING, manager.getState())

        manager.startDrive()
        assertEquals(DriveState.DRIVING, manager.getState())
    }

    @Test
    fun `end drive confirmation only completes an active session`() {
        val manager = DriveStateManager()

        assertEquals(false, manager.confirmEndDrive())

        manager.startDrive()
        assertEquals(true, manager.confirmEndDrive())
        assertEquals(DriveState.DRIVE_ENDED, manager.getState())
    }

    @Test
    fun `friction safe actions use clear returning options`() {
        val controller = FrictionController()

        assertEquals(
            listOf("Music", "Maps", "Assistant", "Return to Safe Home", "End Drive"),
            controller.getSafeActions()
        )
    }

    @Test
    fun `default drive settings include the core essentials`() {
        val settings = DriveSettings()

        assertEquals("Spotify", settings.musicApp)
        assertEquals("Google Maps", settings.mapsApp)
        assertEquals("Google Assistant", settings.assistantApp)
        assertEquals(listOf("Mom", "Dad"), settings.emergencyContacts)
    }

    @Test
    fun `app launch resolver picks the first installed package from fallback options`() {
        val resolver = AppLaunchResolver()
        val installedPackages = setOf(
            "com.google.android.apps.maps",
            "com.spotify.music"
        )

        assertEquals("com.spotify.music", resolver.resolvePackage(resolver.candidatesForMusic(), installedPackages))
        assertEquals("com.google.android.apps.maps", resolver.resolvePackage(resolver.candidatesForMaps(), installedPackages))
    }
}
