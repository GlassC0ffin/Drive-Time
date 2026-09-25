# Drive-Time Android app skeleton

This folder contains a lightweight Android implementation blueprint for the Drive-Time app.

## Purpose

This is not a full Android Studio project yet. It is a concrete starter structure for the real app work described in the roadmap and the Phase 2 specification.

## Target structure

```text
android/
  app/
    src/
      main/
        java/com/drivetime/
          DriveState.kt
          DriveStateManager.kt
          AppRestrictionChecker.kt
          DrivingDetector.kt
          EmergencyContactService.kt
          SafeHomeController.kt
          FrictionController.kt
          MainActivity.kt
```

## Next step

Open this folder in Android Studio and convert the skeleton classes into a real Android app project with the proper Gradle files, manifests, and UI screens.

## Product rule

The app must remain focused on a short list of safe functions while driving:

- music
- maps
- calling
- AI assistant

Everything else is restricted or redirected through friction.
