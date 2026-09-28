package com.drivetime

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.graphicsLayer
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
    val frictionController = remember { FrictionController() }
    val emergencyService = remember { EmergencyContactService() }
    val emergencyContacts = remember { emergencyService.getEmergencyContacts() }
    var driveState by remember { mutableStateOf(DriveState.IDLE) }
    var settings by remember { mutableStateOf(DriveSettings()) }
    var showSettings by remember { mutableStateOf(false) }
    var vehicleConnected by remember { mutableStateOf(false) }
    var activeWidget by remember { mutableStateOf<String?>(null) }
    var mediaPlaying by remember { mutableStateOf(false) }

    val mediaTitle = "All They Wanted"
    val mediaArtist = "Panchiko"

    fun updateState(state: DriveState) {
        driveState = state
        stateManager.setState(state)
    }

    val appLaunchResolver = remember { AppLaunchResolver() }

    fun launchApp(packageName: String?, uri: Uri? = null, action: String? = null) {
        val resolvedPackage = if (packageName != null) {
            val installedPackages = context.packageManager.getInstalledApplications(0).map { it.packageName }.toSet()
            appLaunchResolver.resolvePackage(listOf(packageName), installedPackages) ?: packageName
        } else null

        val intent = when {
            resolvedPackage != null -> {
                val launchIntent = context.packageManager.getLaunchIntentForPackage(resolvedPackage)
                if (launchIntent != null) {
                    launchIntent
                } else {
                    Intent(Intent.ACTION_VIEW).apply {
                        setPackage(resolvedPackage)
                        data = uri ?: when (resolvedPackage) {
                            "com.spotify.music" -> Uri.parse("spotify:")
                            "com.google.android.apps.maps" -> Uri.parse("geo:0,0?q=home")
                            "com.google.android.apps.googleassistant" -> Uri.parse("https://www.google.com")
                            else -> null
                        }
                    }
                }
            }
            uri != null -> Intent(Intent.ACTION_VIEW, uri)
            action != null -> Intent(action)
            else -> null
        }

        if (intent == null) {
            if (action == null && packageName != null) {
                val fallbackIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("market://details?id=$packageName")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(fallbackIntent)
            }
            return
        }

        if (intent.resolveActivity(context.packageManager) != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } else if (packageName != null) {
            val fallbackIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=$packageName")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(fallbackIntent)
        }
    }

    fun openMusic() {
        activeWidget = "music"
    }

    fun openMaps() {
        activeWidget = "maps"
    }

    fun openAssistant() {
        activeWidget = "assistant"
    }

    fun openFullApp(widgetType: String) {
        val installedPackages = context.packageManager.getInstalledApplications(0).map { it.packageName }.toSet()
        val packageName = when (widgetType) {
            "music" -> appLaunchResolver.resolvePackage(appLaunchResolver.candidatesForMusic(), installedPackages)
            "maps" -> appLaunchResolver.resolvePackage(appLaunchResolver.candidatesForMaps(), installedPackages)
            "assistant" -> appLaunchResolver.resolvePackage(appLaunchResolver.candidatesForAssistant(), installedPackages)
            else -> null
        }

        when (widgetType) {
            "music" -> if (packageName != null) launchApp(packageName)
            "maps" -> if (packageName != null) launchApp(packageName)
            "assistant" -> {
                val assistIntent = Intent(Intent.ACTION_ASSIST).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (assistIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(assistIntent)
                } else if (packageName != null) {
                    launchApp(packageName)
                }
            }
        }
    }

    fun callEmergency() {
        val contact = emergencyContacts.firstOrNull()
        if (contact != null) {
            emergencyService.callEmergencyContact(context, contact)
        } else {
            launchApp(null, action = Intent.ACTION_DIAL)
        }
    }

    val bgStart = Color(0xFF050B16)
    val bgEnd = Color(0xFF121B39)
    val surface = Color(0xFF101B2D)
    val surfaceAlt = Color(0xFF162644)
    val border = Color(0xFF7DD3FC)
    val accent = Color(0xFF6EE7F9)
    val accent2 = Color(0xFFB794F4)
    val accentWarm = Color(0xFFFFB703)
    val textPrimary = Color(0xFFF8FBFF)
    val textSecondary = Color(0xFFB9C9E8)
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
                            .background(Brush.linearGradient(listOf(accentWarm, accent2)))
                            .border(1.dp, accent, RoundedCornerShape(16.dp)),
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
                        color = Color(0xFF0D1830),
                        shape = RoundedCornerShape(999.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, accent)
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

                    Button(
                        onClick = { showSettings = !showSettings },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF111F39)),
                        shape = RoundedCornerShape(999.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, border)
                    ) {
                        Text(
                            text = "Settings",
                            color = accent,
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

                if (showSettings) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        color = Color(0xFF111E35),
                        shape = RoundedCornerShape(22.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, border),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp)
                        ) {
                            Text(
                                text = "Drive settings",
                                color = textPrimary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            SettingsRow(label = "Music", value = settings.musicApp, labelColor = textSecondary, valueColor = textPrimary)
                            SettingsRow(label = "Maps", value = settings.mapsApp, labelColor = textSecondary, valueColor = textPrimary)
                            SettingsRow(label = "Assistant", value = settings.assistantApp, labelColor = textSecondary, valueColor = textPrimary)
                            SettingsRow(label = "Emergency", value = settings.emergencyContacts.joinToString(", "), labelColor = textSecondary, valueColor = textPrimary)

                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = { showSettings = false },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = accent),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text("Done", color = Color(0xFF0B1020), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

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

                if (driveState == DriveState.DRIVING && vehicleConnected) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        color = Color(0xFF101E35),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, border),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Vehicle connected",
                                color = textSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Color(0xFF34D399), RoundedCornerShape(50))
                                )
                                Text(
                                    text = "Live",
                                    color = accent,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (driveState == DriveState.IDLE) {
                    Button(
                        onClick = {
                            if (stateManager.canActivateDrive()) {
                                stateManager.startDrive()
                                updateState(stateManager.getState())
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(62.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6EE7F9)
                        ),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text(
                            text = "Start Drive",
                            color = Color(0xFF0B1020),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }

                if (driveState == DriveState.DRIVING) {
                    if (activeWidget == "music" && mediaPlaying) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color(0xFF101D34),
                            shape = RoundedCornerShape(22.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, border)
                        ) {
                            val infiniteTransition = rememberInfiniteTransition(label = "album-spin")
                            val rotation by infiniteTransition.animateFloat(
                                initialValue = 0f,
                                targetValue = 360f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(14000, easing = LinearEasing)
                                ),
                                label = "spin"
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
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
                                            text = "$mediaArtist · $mediaTitle",
                                            color = textPrimary,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(Brush.linearGradient(listOf(accent, accent2)))
                                            .graphicsLayer { rotationZ = rotation },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "D",
                                            color = Color(0xFF0B1020),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 22.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Button(
                                        onClick = { mediaPlaying = !mediaPlaying },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = surfaceAlt),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(if (mediaPlaying) "Pause" else "Play", color = textPrimary)
                                    }
                                    Button(
                                        onClick = { openFullApp("music") },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = accent),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text("Open app", color = Color(0xFF0B1020), fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    if (activeWidget == "maps") {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color(0xFF101D34),
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, accent2)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text("Maps", color = textPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text("Home · 8 min", color = textSecondary, fontSize = 13.sp)
                                Button(
                                    onClick = { openFullApp("maps") },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(containerColor = accent2),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("Open maps", color = Color(0xFF0B1020), fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    if (activeWidget == "assistant") {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color(0xFF101D34),
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, accent)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text("Gemini assistant", color = textPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text("Ready to help with calls, routes, and quick prompts.", color = textSecondary, fontSize = 13.sp)
                                Button(
                                    onClick = { openFullApp("assistant") },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(containerColor = accent),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("Launch assistant", color = Color(0xFF0B1020), fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }

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
                            onClick = { openMusic(); mediaPlaying = true }
                        )
                        DashboardTile(
                            title = "Maps",
                            subtitle = "Home · 8 min",
                            actionText = "Nav",
                            accentColor = accent2,
                            modifier = Modifier.weight(1f),
                            onClick = { openMaps() }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DashboardTile(
                            title = "Assistant",
                            subtitle = "Call mom",
                            actionText = "Ask",
                            accentColor = accent,
                            modifier = Modifier.weight(1f),
                            onClick = { openAssistant() }
                        )
                        DashboardTile(
                            title = "Call",
                            subtitle = "Emergency contacts",
                            actionText = "Dial",
                            accentColor = accentWarm,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                emergencyService.callEmergencyContact(context, emergencyContacts.first())
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFF101E35),
                        shape = RoundedCornerShape(18.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, border)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp)
                        ) {
                            Text(
                                text = "Emergency contacts",
                                color = faded,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                emergencyContacts.forEach { contact ->
                                    Surface(
                                        onClick = {
                                            emergencyService.callEmergencyContact(context, contact)
                                        },
                                        color = Color(0xFF18263F),
                                        shape = RoundedCornerShape(12.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, border)
                                    ) {
                                        Text(
                                            text = contact.name,
                                            color = textPrimary,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
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
                                onClick = {
                                    if (stateManager.confirmEndDrive()) {
                                        updateState(stateManager.getState())
                                    }
                                },
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
                                text = frictionController.getSafetyMessage(),
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
                                    onClick = { openMusic() }
                                )
                                DashboardTile(
                                    title = "Maps",
                                    subtitle = "Quick nav",
                                    actionText = "Open",
                                    accentColor = accent2,
                                    modifier = Modifier.weight(1f),
                                    onClick = { openMaps() }
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = {
                                    stateManager.setState(DriveState.DRIVING)
                                    updateState(stateManager.getState())
                                },
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
                        onClick = {
                            stateManager.reset()
                            updateState(stateManager.getState())
                        },
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
private fun SettingsRow(
    label: String,
    value: String,
    labelColor: Color = Color(0xFFBFCDE8),
    valueColor: Color = Color.White
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = labelColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = valueColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
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
        modifier = modifier.height(146.dp),
        color = Color(0xFF13233D),
        shape = RoundedCornerShape(22.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, accentColor)
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
