package com.drivetime

class DriveStateManager {
    private var currentState: DriveState = DriveState.IDLE

    fun canActivateDrive(): Boolean = currentState == DriveState.IDLE || currentState == DriveState.DRIVE_ENDED

    fun startDrive() {
        if (currentState == DriveState.IDLE || currentState == DriveState.DRIVE_ENDED) {
            currentState = DriveState.DRIVING
        }
    }

    fun onRestrictedAppAttempt() {
        if (currentState == DriveState.DRIVING) {
            currentState = DriveState.DISTRACTION
        }
    }

    fun confirmEndDrive(): Boolean {
        if (currentState == DriveState.DRIVING || currentState == DriveState.DISTRACTION) {
            currentState = DriveState.DRIVE_ENDED
            return true
        }
        return false
    }

    fun endDrive() {
        if (currentState == DriveState.DRIVING || currentState == DriveState.DISTRACTION) {
            currentState = DriveState.DRIVE_ENDED
        }
    }

    fun reset() {
        currentState = DriveState.IDLE
    }

    fun forceExitDrive() {
        currentState = DriveState.IDLE
    }

    fun setState(state: DriveState) {
        currentState = state
    }

    fun getState(): DriveState = currentState

    fun isDriving(): Boolean = currentState == DriveState.DRIVING
}
