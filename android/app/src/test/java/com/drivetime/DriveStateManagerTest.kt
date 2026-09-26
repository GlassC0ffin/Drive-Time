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
}
