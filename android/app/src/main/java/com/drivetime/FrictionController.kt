package com.drivetime

class FrictionController {
    fun showFrictionScreen() {
        // Respectful, intentional friction to redirect the driver back to safe driving actions.
    }

    fun getSafetyMessage(): String = "You should be driving."

    fun getSafeActions(): List<String> {
        return listOf("Music", "Maps", "Assistant", "Return to safe home", "End drive")
    }
}
