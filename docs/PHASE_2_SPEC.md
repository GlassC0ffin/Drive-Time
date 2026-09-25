# Phase 2 — Android App Feature Specification

## Goal

Drive-Time is an Android-first driving safety app. When driving mode is active, the user should only see the tools that help them stay safe on the road: music, maps, call, and AI assistant. Anything that is not essential should be restricted and redirected through a friction screen.

This phase defines the feature set for the first working Android app version.

## Core product rules

- Only essential driving functions are allowed during an active drive.
- The app must clearly show that Driving Mode is active.
- Restricted apps should trigger intentional friction instead of punishment.
- Emergency contact access should remain available.
- The feature set should stay minimal and easy to understand.
- The app should not shame or manipulate the user.

## Allowed functions during driving

The app should provide four safe, high-value widgets:

1. Music widget
   - opens Spotify or the default music app
   - includes simple playback controls
   - keeps interaction minimal and safe

2. Maps widget
   - opens Google Maps or the default navigation app
   - allows destination search or route guidance
   - keeps the map flow simple and direct

3. Call widget
   - opens the phone call system
   - allows a quick call to saved emergency contacts
   - can also open the dialer for other safe calls

4. AI assistant widget
   - opens Google Assistant or Gemini
   - supports hands-free voice requests
   - examples: "call mom", "play music", "take me home"

## Restricted app categories

During driving mode, the system should restrict distraction-heavy apps, especially social media.

Examples:
- TikTok
- Instagram
- Snapchat
- X / Twitter
- other social media apps
- browser-based distraction apps

The system should classify these by category and block them while driving mode is active.

## Emergency access

Emergency contact access should come from Android system data, such as:
- Google contacts
- emergency contacts in the phone settings
- the user's saved trusted contacts

Examples:
- one-tap call to a parent
- emergency call button
- AI assistant voice fallback for "call mom"

Emergency access should always remain available when the user needs it.

## Driving states

The app should use these states:

- IDLE
- DRIVING
- DISTRACTION
- DRIVE_ENDED

### State behavior

IDLE
- the app is not in a driving session
- the user can start a drive

DRIVING
- the app shows the four safe widgets
- restricted apps are blocked
- the app remains fully focused on driving safety

DISTRACTION
- the user attempted to open a restricted app
- a friction screen appears
- the user is redirected to safe options

DRIVE_ENDED
- the user ends the drive
- the app returns to idle or normal phone behavior

## Screen 1: Start Drive screen

Purpose:
- begin a safe driving session

Contents:
- title: Drive-Time
- large Start Drive button
- optional short helper text: "Only essential tools remain available while driving."

Behavior:
- pressing Start Drive enters DRIVING
- the app transitions to the driving home screen

## Screen 2: Driving home screen

Purpose:
- show the safe driving controls

Contents:
- Driving Mode Active banner
- four large widgets:
  - Music
  - Maps
  - Call
  - Assistant
- emergency shortcut group
- End Drive button

Behavior:
- only safe actions are shown as primary controls
- restricted app access is blocked
- the screen should look calm and clear

## Screen 3: Music widget screen

Purpose:
- open music without turning the app into a full entertainment screen

Contents:
- Spotify or the default music app
- play / pause / next / previous controls
- a visible way to return to the safe driving home

Behavior:
- this should feel like a minimal driving utility, not a media center

## Screen 4: Maps widget screen

Purpose:
- improve navigation access while driving

Contents:
- Google Maps or another navigation app
- destination search or route start
- a back button to the driving home screen

Behavior:
- the route flow should stay quick and direct

## Screen 5: Call widget screen

Purpose:
- enable safe calling while driving

Contents:
- emergency contact list
- call dialer
- one-tap contact shortcuts
- AI assistant fallback for voice call requests

Behavior:
- the user should be able to call emergency contacts or trusted people without confusion

## Screen 6: Assistant widget screen

Purpose:
- provide voice-first help while the driver keeps attention on the road

Contents:
- Assistant or Gemini launch
- voice command support

Examples:
- "Call mom"
- "Take me home"
- "Play music"
- "Find gas station"

Behavior:
- interactions should be fast and hands-free

## Screen 7: Friction screen

Purpose:
- catch a restricted app attempt and redirect the user

Example content:
- "You should be driving."
- "Choose one of these safe options:"

Buttons:
- Music
- Maps
- Assistant
- Return to Home
- End Drive

Behavior:
- this is the intentional-friction step
- it should feel helpful, not shaming
- the user should clearly understand the safe alternatives

## Screen 8: Emergency screen

Purpose:
- give immediate access to safety tools

Actions:
- call emergency contact
- call emergency services
- open assistant for voice help

Behavior:
- this screen should stay accessible even in DRIVING mode

## Screen 9: End Drive confirmation

Purpose:
- confirm the user wants to leave driving mode

Text:
- "Are you sure you want to end driving mode?"

Buttons:
- Yes, end drive
- Stay in driving mode

Behavior:
- prevents accidental shutdowns
- real-world safety check for the user

## Drive detection and start/end logic

### Start drive
The app should use a combination of:
- manual Start Drive button
- gyro / motion detection
- Bluetooth or car connectivity detection
- manual confirmation if sensor data is uncertain

### End drive
The app should support:
- a manual End Drive button with confirmation
- optional motion-based stop detection
- a final clear confirmation step before exit

This keeps the app flexible and realistic, especially for Android devices like the Pixel 9A.

## App usage restriction logic

When driving mode is active:
- compare the current app against a restricted app list
- if the app is social or distracting, redirect to the friction screen
- if the app is one of the four allowed utilities, let it open
- if the app is not on the allowlist, keep the user in the safe flow

## Suggested implementation order

1. Start Drive screen
2. Driving home screen with four widgets
3. Friction screen
4. Emergency contact flow
5. End Drive confirmation
6. Restricted app recognition
7. Drive detection and settings

This is the smallest realistic Android MVP for the app.

## Final product recommendation

The best first version is an Android-first app with four safe widgets, a friction screen for restricted apps, emergency contact access, and a manual start/end drive flow supported by motion-based detection.

This keeps the app practical, aligned with the device you are using, and consistent with the project charter.
