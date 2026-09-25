package com.drivetime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val driveStateManager = DriveStateManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start the app in the idle state. The real UI will be implemented next.
        driveStateManager.reset()
    }
}
