# Drive-Time Android Implementation Blueprint

## Goal

Drive-Time is an Android-first driving safety app. During an active trip, the app should keep only four essential experiences available: music, maps, calling, and AI assistant. Everything else should be restricted or redirected through a simple, respectful friction screen.

## Product constraints

These constraints come directly from the project charter:

- one clear purpose: reduce distracted phone use while driving
- only essential driving functions remain available during active driving
- restricted functions should trigger friction, not shame or punishment
- emergency access must remain available at all times
- the feature set must stay minimal and user-centered
- Android is the focus; iPhone remains secondary and limited

## Android app structure

A realistic first Android build should look like this:

```text
app/
  src/
    main/
      java/com/drivetime/
        app/
          DriveTimeApplication.kt
          DriveState.kt
          DriveStateManager.kt
          AppRestrictionChecker.kt
          DrivingDetector.kt
          EmergencyContactService.kt
          SafeHomeController.kt
          FrictionController.kt
        ui/
          MainActivity.kt
          StartDriveScreen.kt
          SafeHomeScreen.kt
          FrictionScreen.kt
          EmergencyScreen.kt
          EndDriveDialog.kt
        data/
          AppRule.kt
          ContactModel.kt
          DriveSession.kt
        services/
          MusicLaunchService.kt
          MapsLaunchService.kt
          CallingService.kt
          AssistantService.kt
        receiver/
          AppUsageReceiver.kt
```

## Core state machine

Use a clear state model so behavior never drifts:

```kotlin
enum class DriveState {
    IDLE,
    DRIVING,
    DISTRACTION,
    DRIVE_ENDED
}
```

### `DriveStateManager`

Responsibilities:

- start and end the drive session
- store the current state
- keep the active screen and current reason for friction
- expose a single point of truth for the app

Example behavior:

```kotlin
class DriveStateManager {
    private var currentState = DriveState.IDLE

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

    fun getState(): DriveState = currentState
}
```

## Allowed operations during driving

The app should present a safe home screen that contains four large quick actions:

1. Music
   - open Spotify or default music app
   - small controls only
   - no full media-center experience

2. Maps
   - open Google Maps or default navigation app
   - quick route and destination flow

3. Call
   - open default dialer
   - one-tap emergency contact calls
   - trusted contacts from Android contact data

4. AI assistant
   - open Google Assistant or Gemini
   - voice-first commands
   - examples: “call mom,” “play music,” “take me home”

## Restricted app handling

`AppRestrictionChecker` decides whether an app is allowed during driving mode.

### High-level logic

- allow: music, maps, dialer, assistant
- restrict: TikTok, Instagram, Snapchat, X, browser, social apps, other obvious distractions
- if a restricted app is opened while driving, switch to `DISTRACTION`
- show a friction screen with safe alternatives

Example:

```kotlin
class AppRestrictionChecker {
    private val allowedPackages = setOf(
        "com.spotify.music",
        "com.google.android.apps.maps",
        "com.google.android.dialer",
        "com.google.android.apps.googleassistant"
    )

    private val restrictedKeywords = listOf(
        "instagram",
        "tiktok",
        "snapchat",
        "twitter",
        "browser"
    )

    fun isAllowed(packageName: String): Boolean {
        return allowedPackages.contains(packageName)
    }

    fun isRestricted(packageName: String): Boolean {
        return restrictedKeywords.any { packageName.contains(it, ignoreCase = true) }
    }
}
```

## Friction flow

The friction screen should be simple and respectful.

Example message:

- “You should be driving.”
- “Choose a safe option:”

Buttons:

- Music
- Maps
- Assistant
- Return to safe home
- End drive

The flow should always give the user a way back to a safe app, never a dead end.

## Emergency access

Emergency actions should remain available in every drive state.

`EmergencyContactService` should:

- load trusted contacts from Google/Android contact data
- expose favorites such as parent or emergency contacts
- support one-tap dial flows
- support voice-driven assistant fallback

Example:

```kotlin
class EmergencyContactService {
    fun getEmergencyContacts(): List<ContactModel> {
        return listOf(
            ContactModel("Mom", "+15550000001"),
            ContactModel("Dad", "+15550000002")
        )
    }

    fun callEmergencyContact(contact: ContactModel) {
        // launch dialer / system call flow
    }
}
```

## Driving detection

Use a hybrid approach for the first version:

- manual Start Drive button as the primary trigger
- motion detection with gyroscope or accelerometer as a secondary signal
- optional Bluetooth or car-connection hints
- manual confirmation when ending a driving session

`DrivingDetector` should detect when the phone is likely in a vehicle and whether the user is still driving.

Example:

```kotlin
class DrivingDetector {
    fun shouldStartDrive(): Boolean {
        // use motion + manual trigger + optional vehicle signal
        return true
    }

    fun shouldEndDrive(): Boolean {
        // user confirms end or motion indicates idle state
        return true
    }
}
```

## Screen plan

### 1. Start screen

- title: Drive-Time
- large Start Drive button
- short helper text: “Only essential tools remain available while driving.”

### 2. Safe home screen

- banner: Driving Mode Active
- four large action cards
- emergency contact shortcut row
- End Drive button

### 3. Friction screen

- message explaining the user should be driving
- safe alternatives
- back to home
- end drive option

### 4. Emergency screen

- one-tap contact buttons
- emergency services option
- assistant fallback

### 5. “End drive” confirmation

- “Are you sure you want to end driving mode?”
- confirm / stay in driving mode

## Implementation order

### Phase 1

- Build the state machine and drive lifecycle
- create the safe home screen
- add the friction screen flow
- implement restricted app detection

### Phase 2

- add emergency contacts and emergency call actions
- wire in maps, music, and assistant launchers
- add end-drive confirmation flow

### Phase 3

- add motion detection and driving heuristics
- connect app usage signals or app category data
- small UI tuning based on real device testing

## Suggested class responsibilities

### `DriveStateManager`
Controls the app state and session lifecycle.

### `AppRestrictionChecker`
Determines whether an app should be allowed during active driving.

### `EmergencyContactService`
Loads and handles emergency-call-friendly contacts.

### `DrivingDetector`
Uses motion signals and user actions to decide drive state.

### `SafeHomeController`
Handles the safe home experience and route selection.

### `FrictionController`
Handles blocked app attempts and redirects to safe actions.

## Recommended first build

Start with the following before adding advanced device detection:

1. state machine
2. safe home screen
3. restricted app check
4. friction flow
5. emergency action
6. end-drive confirmation

This gives a stable MVP without overbuilding too early.

## Build outcome

The first working Android version should feel like a simple, safe, calm dashboard for the driver—not a launcher, not a social app, and not a guilt-based system.

The system should be clear, useful, and minimal: the user can drive, open the essentials, and get help quickly when needed.
