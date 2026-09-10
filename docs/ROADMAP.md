# Drive-Time — Updated Roadmap

## Phase 1 — Confirm the Product Goal
Define the app as a driving-mode safety tool, not a general phone app.

Core mission:
- reduce distracting phone behavior while driving
- allow only essential driving functions
- use clear, respectful friction instead of shame or manipulation

## Phase 2 — Define the Essential Features
Keep the feature list intentionally small.

Allowed functions while driving:
- Music
- Maps/navigation
- Calling
- AI assistant

Restricted functions:
- Messages
- Browser
- Social apps
- Games
- Other non-essential apps

## Phase 3 — Build the Core State Machine
Use explicit states:

- IDLE
- DRIVING
- DISTRACTION
- DRIVE_ENDED

Rules:
- start drive begins the DRIVING state
- restricted apps route into DISTRACTION
- the user can either return to driving or continue through friction
- ending the session moves to DRIVE_ENDED
- after DRIVE_ENDED, the app should stop treating the session as active

## Phase 4 — Build the Interface Flow
Create a minimal set of screens:

- Start screen
- Driving screen with allowed actions
- Restricted app warning
- Friction / choice screen
- Drive-ended screen

Every screen should feel clean, simple, and low-stimulation.

## Phase 5 — Build the Browser Prototype
Create a working prototype that demonstrates the core behavior without pretending to fully control the phone OS.

This prototype should show:
- start drive
- allowed apps visible
- restricted app triggers friction
- user choice to return to driving
- drive end state

## Phase 6 — Define Platform Reality
Document the difference between app types:

- Browser / PWA prototype: easy to install, good for demo
- Android native app: realistic path for true app restrictions
- iPhone app: heavily limited by platform rules

The project should stay honest about what is possible on each platform.

## Phase 7 — Plan Native Mobile Expansion
Map out the next build phase:

- Android app structure
- permission and app-access rules
- emergency call support
- voice assistant shortcut
- speed detection or vehicle detection
- allowlist for essential functions only

## Phase 8 — Add Safety and UX Guardrails
The app must:

- avoid shaming the user
- avoid surveillance
- avoid manipulation
- keep the experience focused on safety
- keep the tool simple and understandable

## Phase 9 — Test the Core Experience
Test the main interaction loop:

1. start drive
2. enter driving mode
3. try a restricted app
4. choose a response
5. return to driving
6. end the drive
7. confirm no driving actions continue after the session ends

## Phase 10 — Create the Demo Build
Prepare a short, usable product demo for presentation.

Show:
- the driving-mode start flow
- allowed apps
- restricted app friction screen
- end-of-drive state
- honest explanation of platform limits

## Phase 11 — Deployment and Documentation
Deploy the browser prototype and confirm the live URL.

Update documentation to include:
- project purpose
- allowed app set
- product limits
- platform reality
- README overview

## Phase 12 — Prepare Final Deliverable
Finalize:

- live working prototype
- GitHub repo link
- README
- technical documentation
- demo video or walkthrough
- explanation of native mobile limitations and next steps

## Phase 13 — Real-World Product Recommendation
The recommended production direction is:

- build the prototype in browser first
- then build a native Android version for real locking and app integration
- treat iPhone as a limited, focus-based companion version rather than full app control

This keeps the product honest, feasible, and aligned with the system charter.