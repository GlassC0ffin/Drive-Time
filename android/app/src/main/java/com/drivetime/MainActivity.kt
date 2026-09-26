package com.drivetime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DriveTimeTheme {
                DriveTimeApp()
            }
        }
    }
}

@Composable
fun DriveTimeApp() {
    val stateManager = remember { DriveStateManager() }
    var driveState by remember { mutableStateOf(DriveState.IDLE) }

    fun updateState(state: DriveState) {
        driveState = state
        stateManager.setState(state)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111827)),
        color = Color(0xFF111827)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = when (driveState) {
                    DriveState.IDLE -> "Drive-Time"
                    DriveState.DRIVING -> "Driving Mode Active"
                    DriveState.DISTRACTION -> "You should be driving."
                    DriveState.DRIVE_ENDED -> "Drive ended"
                },
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = when (driveState) {
                    DriveState.IDLE -> "Only essential tools stay available while driving."
                    DriveState.DRIVING -> "Use only the safe driving tools."
                    DriveState.DISTRACTION -> "Choose a safe option below."
                    DriveState.DRIVE_ENDED -> "Your session is over. Start again when you are ready."
                },
                color = Color(0xFFD1D5DB),
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (driveState == DriveState.IDLE) {
                Button(
                    onClick = { updateState(DriveState.DRIVING) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Drive")
                }
            }

            if (driveState == DriveState.DRIVING || driveState == DriveState.DISTRACTION) {
                listOf("Music", "Maps", "Call", "AI Assistant").forEach { action ->
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        Text(action)
                    }
                }

                if (driveState == DriveState.DISTRACTION) {
                    Button(
                        onClick = { updateState(DriveState.DRIVING) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        Text("Return to Safe Home")
                    }
                }

                Button(
                    onClick = { updateState(DriveState.DRIVE_ENDED) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("End Drive")
                }
            }

            if (driveState == DriveState.DRIVE_ENDED) {
                Button(
                    onClick = { updateState(DriveState.IDLE) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Again")
                }
            }

            if (driveState == DriveState.DRIVING) {
                Button(
                    onClick = { updateState(DriveState.DISTRACTION) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Text("Open Restricted App")
                }
            }
        }
    }
}

@Composable
fun DriveTimeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme,
        content = content
    )
}
