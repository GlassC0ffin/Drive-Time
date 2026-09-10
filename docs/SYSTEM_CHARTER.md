# System Charter — Drive-Time

## Purpose

Drive-Time is a phone-based tool designed to reduce distracting phone use while a person is driving.

When a drive is active, the tool limits access to non-essential phone functions while keeping essential driving functions available.

The core purpose is to create intentional friction between the driver and distracting phone behaviors.

## Constraints

1. While driving, the phone should restrict access to distracting apps and functions.

2. The driver should still be able to access essential driving functions:
   - Music
   - Maps/navigation
   - Calling
   - AI assistant

3. The system must clearly communicate when Driving Mode is active.

4. The system must not shame, punish, or surveil the driver.

5. The system should use intentional friction rather than making distracting actions completely mysterious or confusing.

6. The tool should have one clear purpose: reducing unnecessary phone interaction while driving.

7. The prototype should keep the number of features minimal and focused on the core driving experience.

## Core State

The system has four primary states:

- IDLE — no drive is active
- DRIVING — driving mode is active
- DISTRACTION — the user attempts to access a restricted function
- DRIVE_ENDED — the driving session has ended

## Allowed Functions During Driving

When the system is in the DRIVING state, the prototype allows access to:

- Music
- Maps/navigation
- Calling
- AI assistant

Other distracting phone functions should be restricted.

## Choice Point

When the user attempts to access a restricted function, Drive-Time should create a clear choice:

- Return to the driving experience
- Continue through intentional friction

The choice should inform the user without using shame, guilt, or manipulation.

## Input

The primary input is the current driving state.

Additional user interactions are limited to:

- Starting a drive
- Selecting an allowed function
- Attempting to access a restricted function
- Ending a drive

## Processing

The system determines what the user is allowed to access based on the current state.

Example:

DRIVING + restricted function
→ DISTRACTION
→ CHOICE / FRICTION

DRIVING + allowed function
→ ALLOWED FUNCTION

DRIVING + end drive
→ DRIVE_ENDED

## Output

The system communicates the current state through the interface.

Examples:

- Driving Mode active
- Allowed functions
- Restricted function warning
- Choice / friction screen
- Drive Ended

## Tensions

### Safety vs. Convenience

The system should reduce unnecessary interaction without preventing access to essential driving functions.

### Friction vs. Freedom

The system intentionally makes distracting actions less immediate while allowing the user to make a clear choice.

### Simplicity vs. Function

The tool should provide only the functions necessary for the driving experience.

## Behavior Integrity

Drive-Time should:

- Avoid shame
- Avoid surveillance
- Avoid manipulation
- Avoid unnecessary data collection
- Make its behavior understandable
- Use intentional friction
- Keep the experience focused on driving