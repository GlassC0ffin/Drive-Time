package com.drivetime

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var titleText: TextView
    private lateinit var statusText: TextView
    private lateinit var startDriveButton: Button
    private lateinit var musicButton: Button
    private lateinit var mapsButton: Button
    private lateinit var callButton: Button
    private lateinit var assistantButton: Button
    private lateinit var restrictedAccessButton: Button
    private lateinit var safeHomeButton: Button
    private lateinit var endDriveButton: Button

    private val driveStateManager = DriveStateManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleText = findViewById(R.id.titleText)
        statusText = findViewById(R.id.statusText)
        startDriveButton = findViewById(R.id.startDriveButton)
        musicButton = findViewById(R.id.musicButton)
        mapsButton = findViewById(R.id.mapsButton)
        callButton = findViewById(R.id.callButton)
        assistantButton = findViewById(R.id.assistantButton)
        restrictedAccessButton = findViewById(R.id.restrictedAccessButton)
        safeHomeButton = findViewById(R.id.safeHomeButton)
        endDriveButton = findViewById(R.id.endDriveButton)

        startDriveButton.setOnClickListener {
            driveStateManager.startDrive()
            updateUi()
        }

        musicButton.setOnClickListener {
            statusText.text = "Opening music controls."
        }

        mapsButton.setOnClickListener {
            statusText.text = "Opening navigation."
        }

        callButton.setOnClickListener {
            statusText.text = "Opening call options."
        }

        assistantButton.setOnClickListener {
            statusText.text = "Opening AI assistant."
        }

        restrictedAccessButton.setOnClickListener {
            driveStateManager.onRestrictedAppAttempt()
            updateUi()
        }

        safeHomeButton.setOnClickListener {
            driveStateManager.startDrive()
            updateUi()
        }

        endDriveButton.setOnClickListener {
            driveStateManager.endDrive()
            updateUi()
        }

        driveStateManager.reset()
        updateUi()
    }

    private fun updateUi() {
        when (driveStateManager.getState()) {
            DriveState.IDLE -> {
                titleText.text = "Drive-Time"
                statusText.text = "Only essential tools stay available while driving."
                startDriveButton.visibility = View.VISIBLE
                musicButton.visibility = View.GONE
                mapsButton.visibility = View.GONE
                callButton.visibility = View.GONE
                assistantButton.visibility = View.GONE
                restrictedAccessButton.visibility = View.GONE
                safeHomeButton.visibility = View.GONE
                endDriveButton.visibility = View.GONE
            }

            DriveState.DRIVING -> {
                titleText.text = "Driving Mode Active"
                statusText.text = "Use only the safe driving tools."
                startDriveButton.visibility = View.GONE
                musicButton.visibility = View.VISIBLE
                mapsButton.visibility = View.VISIBLE
                callButton.visibility = View.VISIBLE
                assistantButton.visibility = View.VISIBLE
                restrictedAccessButton.visibility = View.VISIBLE
                safeHomeButton.visibility = View.GONE
                endDriveButton.visibility = View.VISIBLE
            }

            DriveState.DISTRACTION -> {
                titleText.text = "You should be driving."
                statusText.text = "Choose a safe option below."
                startDriveButton.visibility = View.GONE
                musicButton.visibility = View.VISIBLE
                mapsButton.visibility = View.VISIBLE
                callButton.visibility = View.GONE
                assistantButton.visibility = View.VISIBLE
                restrictedAccessButton.visibility = View.GONE
                safeHomeButton.visibility = View.VISIBLE
                endDriveButton.visibility = View.VISIBLE
            }

            DriveState.DRIVE_ENDED -> {
                titleText.text = "Drive ended"
                statusText.text = "Your session is over. Start again when you are ready."
                startDriveButton.visibility = View.VISIBLE
                musicButton.visibility = View.GONE
                mapsButton.visibility = View.GONE
                callButton.visibility = View.GONE
                assistantButton.visibility = View.GONE
                restrictedAccessButton.visibility = View.GONE
                safeHomeButton.visibility = View.GONE
                endDriveButton.visibility = View.GONE
            }
        }
    }
}
