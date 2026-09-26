package com.drivetime

class DriveStateManager {
    private var currentState: DriveState = DriveState.IDLE

    fun startDrive() {
        currentState = DriveState.DRIVING
    }

    fun onRestrictedAppAttempt() {
        currentState = DriveState.DISTRACTION
    }

    fun endDrive() {
        currentState = DriveState.DRIVE_ENDED
    }

    fun reset() {
        currentState = DriveState.IDLE
    }

    fun setState(state: DriveState) {
        currentState = state
    }

    fun getState(): DriveState = currentState

    fun isDriving(): Boolean = currentState == DriveState.DRIVING
}
