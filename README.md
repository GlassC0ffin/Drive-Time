# Drive-Time

Drive-Time is a focused driving-mode prototype designed to reduce phone distraction while a person is driving.

## Purpose

When a drive is active, the system limits access to distracting phone behavior while keeping only the most essential driving tools available:

- Music
- Maps/navigation
- Calling
- AI assistant

The app is intentionally minimal. It does not shame the user, it does not surveil the user, and it does not manipulate the user. It uses clear friction instead.

## What this prototype does

This version demonstrates the core interaction loop:

- IDLE
- DRIVING
- DISTRACTION
- DRIVE_ENDED

The user can:

- start a drive
- use allowed driving functions
- attempt a restricted app
- see a friction screen that asks them to return to driving
- end the drive

## Files

- `main.js` — app state and screen flow
- `src/input/input.js` — input handling and action forwarding
- `src/canvas/` — unused in this prototype and kept for future visual or animation experiments
- `docs/SYSTEM_CHARTER.md` — project constraints and behavior rules
- `docs/ROADMAP.md` — development plan
- `docs/PROMPTS.md` — AI prompt log and project rules

## Run locally

From the project root, run:

```bash
python3 -m http.server 8000
```

Then open:

```text
http://localhost:8000
```

## Can this be an app on your phone?

Yes, but there are two different versions:

1. Mobile web app / PWA
   - It can be installed on a phone like an app
   - It works well for a prototype and demo
   - It can be saved to the home screen

2. Native phone app
   - This is the version that can better control phone behavior
   - Android allows more real app-level restrictions and integrations
   - iPhone is much more limited because Apple restricts deep app locking and app-to-app control

## Can it be linked to your phone apps?

Partially.

- On Android, it can open apps like Maps, Music, Phone, and Assistant through platform APIs or intents.
- On iPhone, linking to native apps is heavily limited by Apple’s sandboxing and app restrictions.
- A full lock-screen or allowlist system is much more realistic as a native app rather than a browser app.

So the best path is:

- prototype in the browser first
- native Android app next for real driving-mode restrictions
- iPhone support as limited, focus-based behavior rather than full phone locking

## Behavior integrity

This project keeps the feature set narrow and intentional:

- one clear purpose: reduce distracted phone use while driving
- only essential functions remain available
- no shaming or surveillance
- intentional friction instead of hidden manipulation

## Current status

This project is now at the demo-ready Android dashboard milestone.

Completed:

- safe driving dashboard with music, maps, assistant, and call widgets
- friction flow for restricted apps while driving
- emergency contact access and safe fallback actions
- settings/default drive configuration
- final roadmap and documentation pass for Phase 12 and Phase 13

## Next steps

Future improvements could include:

- real GPS speed detection
- emergency override button
- allowed contacts and emergency calls
- app allowlist for Android
- full native mobile build
- PWA install support and icon setup
