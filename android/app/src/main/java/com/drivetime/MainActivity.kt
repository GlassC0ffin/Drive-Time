package com.drivetime

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    val context = LocalContext.current
    val stateManager = remember { DriveStateManager() }
    var driveState by remember { mutableStateOf(DriveState.IDLE) }

    fun updateState(state: DriveState) {
        driveState = state
        stateManager.setState(state)
    }

    fun launchApp(packageName: String?, uri: Uri? = null) {
        val intent = if (packageName != null) {
            context.packageManager.getLaunchIntentForPackage(packageName)
        } else if (uri != null) {
            Intent(Intent.ACTION_VIEW, uri)
        } else {
            null
        }

        if (intent != null) {
            context.startActivity(intent)
        }
    }

    val bgStart = Color(0xFF070B17)
    val bgEnd = Color(0xFF121A2E)
    val surface = Color(0xFF111B2F)
    val surfaceAlt = Color(0xFF192843)
    val border = Color(0x6A7DD3FC)
    val accent = Color(0xFF7DD3FC)
    val accent2 = Color(0xFFB794F4)
    val accentWarm = Color(0xFFF59E0B)
    val textPrimary = Color.White
    val textSecondary = Color(0xFFBFCDE8)
    val faded = Color(0xFF8EA3C3)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(bgStart, bgEnd)))
                .padding(22.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(accentWarm),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "D",
                            color = Color(0xFF0B1020),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Surface(
                        color = Color(0xFF10213B),
                        shape = RoundedCornerShape(999.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, border)
                    ) {
                        Text(
                            text = when (driveState) {
                                DriveState.IDLE -> "Ready"
                                DriveState.DRIVING -> "Driving"
                                DriveState.DISTRACTION -> "Focus"
                                DriveState.DRIVE_ENDED -> "Ended"
                            },
                            color = accent,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = when (driveState) {
                        DriveState.IDLE -> "Drive-Time"
                        DriveState.DRIVING -> "Drive dashboard"
                        DriveState.DISTRACTION -> "Stay on the road"
                        DriveState.DRIVE_ENDED -> "Drive complete"
                    },
                    color = textPrimary,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = when (driveState) {
                        DriveState.IDLE -> "Start a drive and keep only the essentials in reach."
                        DriveState.DRIVING -> "Music, maps, calls, and assistant stay in one safe dashboard."
                        DriveState.DISTRACTION -> "A distraction was detected. Choose a safe action below."
                        DriveState.DRIVE_ENDED -> "Your trip has ended. Start again when you are ready."
                    },
                    color = textSecondary,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (driveState == DriveState.IDLE) {
                    Button(
                        onClick = { updateState(DriveState.DRIVING) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(62.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accentWarm),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text(
                            text = "Start Drive",
                            color = Color(0xFF111827),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }

                if (driveState == DriveState.DRIVING) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFF101D34),
                        shape = RoundedCornerShape(22.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, border)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Now playing",
                                    color = faded,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 0.8.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Ariana Grande · 2:14",
                                    color = textPrimary,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(accent),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "▶",
                                    color = Color(0xFF0B1020),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DashboardTile(
                            title = "Music",
                            subtitle = "Queue · 2 tracks",
                            actionText = "Play",
                            accentColor = accent,
                            modifier = Modifier.weight(1f),
                            onClick = { launchApp("com.spotify.music") }
                        )
                        DashboardTile(
                            title = "Maps",
                            subtitle = "Home · 8 min",
                            actionText = "Nav",
                            accentColor = accent2,
                            modifier = Modifier.weight(1f),
                            onClick = { launchApp("com.google.android.apps.maps") }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DashboardTile(
                            title = "Assistant",
                            subtitle = """"Call mom""",
                            actionText = "Ask",
                            accentColor = accent,
                            modifier = Modifier.weight(1f),
                            onClick = { launchApp("com.google.android.apps.googleassistant") }
                        )
                        DashboardTile(
                            title = "Call",
                            subtitle = "Emergency contacts",
                            actionText = "Dial",
                            accentColor = accentWarm,
                            modifier = Modifier.weight(1f),
                            onClick = { context.startActivity(Intent(Intent.ACTION_DIAL)) }
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = surface,
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, border)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Drive status",
                                    color = faded,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Safe mode active",
                                    color = textPrimary,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Button(
                                onClick = { updateState(DriveState.DRIVE_ENDED) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF312E81)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("End Drive", color = textPrimary)
                            }
                        }
                    }
                }

                if (driveState == DriveState.DISTRACTION) {
                    Surface(
                        color = Color(0xFF1A243B),
                        shape = RoundedCornerShape(22.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFB7185)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "You should be driving.",
                                color = textPrimary,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Choose a safe option below instead of opening a distraction.",
                                color = textSecondary,
                                fontSize = 15.sp,
                                lineHeight = 22.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                DashboardTile(
                                    title = "Music",
                                    subtitle = "Play now",
                                    actionText = "Open",
                                    accentColor = accent,
                                    modifier = Modifier.weight(1f),
                                    onClick = { launchApp("com.spotify.music") }
                                )
                                DashboardTile(
                                    title = "Maps",
                                    subtitle = "Quick nav",
                                    actionText = "Open",
                                    accentColor = accent2,
                                    modifier = Modifier.weight(1f),
                                    onClick = { launchApp("com.google.android.apps.maps") }
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = { updateState(DriveState.DRIVING) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = surfaceAlt),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text("Return to safe home", color = textPrimary)
                            }
                        }
                    }
                }

                if (driveState == DriveState.DRIVE_ENDED) {
                    Button(
                        onClick = { updateState(DriveState.IDLE) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accent),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text(
                            text = "Start Again",
                            color = Color(0xFF0B1020),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardTile(
    title: String,
    subtitle: String,
    actionText: String,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .height(146.dp),
        color = Color(0xFF18263F),
        shape = RoundedCornerShape(22.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x6A7DD3FC))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(accentColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title.take(1),
                        color = Color(0xFF0B1020),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = actionText,
                    color = accentColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }

            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = subtitle,
                color = Color(0xFFBFCDE8),
                fontSize = 12.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Start
            )
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
