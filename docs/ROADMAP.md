# Drive-Time — Android-First Roadmap

## Current progress snapshot
- Phase 1: complete
- Phase 2: complete
- Phase 3: complete
- Phase 4: complete
- Phase 5: complete
- Phase 6: in progress — widget-first driving dashboard and app-launch behavior are being refined
- Phase 7+: pending final friction, emergency flow, and launch polish

## Phase 1 — Define the Android app goal
Create a phone app built for Android that reduces distraction while driving.

Current focus:
- build the first version as an Android-first app
- define the exact goal before any UI or code work
- keep the scope narrow: drive safety, not a full app launcher

Core mission:
- when the user is driving, only essential functions stay available
- the app should block or limit non-essential apps and interactions
- the app should help the user stay focused without shaming them

Phase 1 deliverable:
- a clear product statement
- a defined list of allowed functions
- a defined list of restricted functions
- a clear explanation of the user value and safety purpose

This phase is the foundation for the rest of the roadmap.

### Phase 1 product definition
Drive-Time is an Android-first driving safety app designed to reduce phone distraction while a person is driving. When a drive is active, the app keeps only the essential driving tools available: music, maps, calling, and AI assistant. Non-essential apps and distractions are restricted, and the user is shown a respectful friction screen that helps them return to the road without shame or manipulation. The purpose is simple: keep the driver focused, keep the phone useful, and keep the experience minimal.

### Phase 1 constraints
- only essential driving functions remain available during active driving
- the app must clearly communicate when driving mode is active
- restricted functions should trigger intentional friction
- the app must avoid shaming, surveillance, or manipulation
- the feature set must stay minimal and focused on driving safety

This phase is complete once the product goal, allowed functions, restricted functions, and safety boundaries are clearly agreed on.

## Phase 2 — Define the essential allowed apps
The app should allow a small set of Android widgets or quick actions while driving:
- Spotify / music widget
- Maps widget with one-tap navigation
- Call widget for emergency contacts and direct calling
- Google Assistant / Gemini assistant widget

These should behave like quick-access driving tools, not a full app-launcher experience.

### Allowed app behavior
- Music widget opens directly to the music app or playback controls
- Maps widget opens navigation and lets the user search for a destination quickly
- Calling widget connects to emergency contacts or system calling
- AI assistant widget opens Google Assistant or Gemini for hands-free commands

The app should remain focused on a small, usable set of safe actions.

## Phase 2A — Define the emergency system
Emergency contact access should draw from the user's Android phone settings, such as Google contacts or emergency contacts saved in the device.

Examples:
- call parent emergency contact with one tap
- call emergency services if needed
- open assistant and say: "call my mom"

This keeps the system practical without making emergency access confusing.

## Phase 2B — Define restricted apps
Restricted apps should focus on high-distraction categories, especially social media.

Examples:
- TikTok
- Instagram
- Snapchat
- X / Twitter
- other social media apps
- browser-based distraction apps

The app should be able to detect the most-used or most-frequent distracting app and classify it as restricted when driving mode is active.

### Restriction logic
- social media apps are blocked during driving mode
- the system should use the phone's app usage data or recent app list to identify distraction patterns
- if a restricted app is opened, the friction screen appears

## Phase 3 — Design the driving states
Use explicit states:

- IDLE
- DRIVING
- DISTRACTION
- DRIVE_ENDED

Rules:
- starting a drive begins DRIVING
- opening a restricted app enters DISTRACTION
- the user can choose to return to the drive or continue through friction
- ending the drive moves the app to DRIVE_ENDED
- the system should stop accepting driving-mode actions after the drive ends

### Drive start ideas
- start manually from a large button
- detect that the phone is moving in a vehicle using motion sensors
- detect car connectivity or Bluetooth pairing
- use manual confirmation if sensor data is uncertain

### Drive end ideas
- end manually with a confirmation screen
- detect when movement stops and ask for confirmation
- allow user to stop the session after a drive is over

## Phase 4 — Build the Android app structure
Set up the app with a clear structure:

- home / start screen
- active driving screen with four widgets
- friction / restricted app screen
- emergency access screen
- drive ended screen
- settings screen

Keep the interface minimal and easy to understand.

## Phase 5 — Build the Android app shell
Create the first version as a driving dashboard, not a launcher screen.

The interface should feel like a modern car infotainment panel:
- large cards and compact widgets
- strong contrast and clean hierarchy
- voice-first or tap-first actions
- no clutter
- no full-screen app switching during the active drive

The design should feel like a safe driving tool, not a normal phone app.

### Home / driving screen layout
- Start Drive button
- four main widgets for music, maps, call, and assistant
- emergency contacts group
- end drive button
- all core functions live as in-app widgets inside the same driving dashboard

This screen should behave like a car touchscreen where maps and music can be open at the same time without full app transitions.

## Phase 6 — Connect allowed app widgets for Android
Build the allowed functions as embedded in-app widgets and quick actions:
- music widget with play, pause, skip, and volume controls
- maps widget with route and destination quick-access controls
- calling widget with direct emergency contact buttons and quick calls
- assistant widget for voice and text commands like "call mom" or "find a gas station"

This is the main Android-focused functionality for the first version.

### Dashboard flow
- the driving app stays open as the main interface
- music and maps can sit side by side or in stacked cards within the same screen
- the user can tap between widgets without leaving the drive dashboard
- emergency contact access stays available as a safe, minimal action
- if a full app must open for native OS support, it should only happen as a secondary action, not the main driving experience

The default experience should be: dashboard widgets, not app launching.

This matches the car-like UX you want: music and maps visible and usable together inside the same active driving app, just like a modern vehicle touchscreen.

## Phase 7 — Add distraction friction
When a restricted app is opened while driving, the app should:
- show a clear warning
- explain the app is restricted while driving
- show quick options like Music, Maps, AI assistant, or return home
- allow the user to choose a safe action instead of punishing them

This should be respectful and intentional, not punitive.

### Friction screen content
Example:
- "You should be driving."
- "Choose one of these safe options:"
- Music
- Maps
- Assistant
- Return to safe home
- End drive

## Phase 8 — Add emergency access
Add an emergency-safe override:
- emergency call button
- parent or emergency contact shortcut
- one-tap call action using Android contact data
- AI assistant voice command fallback

This keeps the app usable in real-world conditions.

## Phase 9 — Add drive activation logic
Add the logic that decides when the app is active:
- user starts the drive manually
- app checks gyro motion and car connection signals
- phone motion and vehicle detection can help determine driving status
- restricted actions are blocked in DRIVING mode
- drive session ends cleanly when the user stops or confirms they are done driving

### Detection strategy
- gyro / motion sensor detection
- Bluetooth or car connection detection
- manual start button as the primary trigger
- manual end button with confirmation prompt
- stop detection as an optional automatic end condition

## Phase 10 — Add settings and customization
Allow the user to configure:
- music app choice
- maps app choice
- assistant app choice
- emergency contacts list
- restricted app list
- start / end drive behavior
- friction screen wording

Keep the settings simple and focused on driving safety.

## Phase 11 — Test the Android flow
Run through the main loop:

1. start drive
2. driving mode appears with the four widgets
3. user opens a restricted app
4. friction screen appears
5. user chooses a safe tool or returns home
6. user ends the drive with confirmation
7. app returns to a safe idle state

Fix anything that breaks or feels confusing.

## Phase 12 — Build the demo version
Status: Complete

Prepare the Android prototype for presentation:
- four-widget home screen
- safe driving-mode UI
- restricted app detection and friction screen
- emergency contact actions
- clean, minimal visual design

This is the first usable demonstration of the product.

## Phase 13 — Deployment and documentation
Status: Complete

Prepare the project for sharing:

- working Android prototype
- installable or demo version
- README update
- roadmap and prompt log
- final project summary

This milestone is complete once the repo is documented, the main flow is verified, and the product is ready to present as a working Android-first demo.

## Phase 14 — Final product recommendation
The recommended path is:

- build the product as an Android-first app
- allow four core widgets: music, maps, call, assistant
- use a friction screen for restricted apps
- draw emergency contacts from system settings or Google contact data
- use motion detection plus user confirmation for drive start and end
- keep the scope tight and safety-focused

This makes the app practical, realistic, and aligned with the system charter and the Android device you are using.
- ending the drive moves the app to DRIVE_ENDED
- the system should stop accepting driving-mode actions after the drive ends

## Phase 4 — Build the Android app structure
Set up the app with a clear structure:

- onboarding or intro screen
- start drive screen
- active driving screen
- restricted app / friction screen
- drive ended screen

Keep the interface minimal and easy to understand.

## Phase 5 — Build the Android app shell
Create the first version with:
- large buttons
- simple layout
- strong contrast
- voice-first or tap-first actions
- no clutter

The design should feel like a safe driving tool, not a normal phone app.

## Phase 6 — Connect allowed apps for Android
Build the real integrations for the allowed functions:
- launch music app
- launch maps/navigation
- open calling system
- open AI assistant

This is the main Android-focused functionality for the first version.

## Phase 7 — Add distraction friction
When a restricted app is opened while driving, the app should:
- show a clear warning
- explain the app is restricted while driving
- give the user a simple choice
- encourage them to return to driving

This should be respectful and intentional, not punitive.

## Phase 8 — Add emergency access
Add an emergency-safe override:
- emergency call button
- emergency contact shortcut
- a way to reach safety tools without confusion

This keeps the app usable in real-world conditions.

## Phase 9 — Add drive activation logic
Add the logic that decides when the app is active:
- user starts the drive manually
- app checks driving status
- restricted actions are blocked in DRIVING mode
- drive session ends cleanly when the user stops

## Phase 10 — Add settings and customization
Allow the user to configure:
- allowed apps list
- emergency favorites
- start drive button behavior
- end drive flow
- voice assistant shortcut

Keep the settings simple and focused on driving safety.

## Phase 11 — Test the Android flow
Run through the main loop:

1. start drive
2. active driving mode appears
3. user tries a restricted app
4. friction screen appears
5. user returns to driving
6. user ends the drive
7. app returns to a safe idle state

Fix anything that breaks or feels confusing.

## Phase 12 — Build the demo version
Prepare the Android prototype for presentation:
- clean screens
- strong focus on driving safety
- limited app set
- friction for distractions
- emergency access

This is the first usable demonstration of the product.

## Phase 13 — Deployment and documentation
Prepare the project for sharing:

- working Android prototype
- live demo or installable build
- README update
- roadmap and prompt log
- final project summary

## Phase 14 — Final product recommendation
The recommended path is:

- build the product as an Android-first app
- keep the scope tight and safety-focused
- do not promise full iPhone app locking as a first version
- treat iPhone restrictions as a separate future challenge

This keeps the product practical and aligned with the system charter.