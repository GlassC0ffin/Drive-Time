# Prompts

Copilot (or any AI assistant) only gets used inside the constraints
already written in `SYSTEM_CHARTER.md`. I'm not asking it for ideas —
I'm asking it for specific technical help.

## Context block

Paste this at the top of a chat/session before asking anything:

> I'm building a Canvas-based studio engine. Files: `main.js` wires
> things together; `/src/canvas/setupCanvas.js` handles HiDPI setup;
> `/src/canvas/loop.js` owns the animation loop and behavior;
> `/src/input/input.js` captures raw input signals;
> `/src/utils/math.js` holds small numeric helpers. No external
> libraries in the template sketch — pure Canvas only. Keep each file
> to one job.

## Reusable prompt templates

**1. Canvas draw loop**
> Given this signal->parameter mapping: [describe it], write the
> body of a `requestAnimationFrame` loop that draws [shape] whose
> [visual property] responds to [parameter]. Keep it framework-free.

**2. Input mapping**
> I have a raw browser event (e.g. mousemove, scroll, keydown). Help
> me capture it in `input.js` as a simple getter function, without
> putting any drawing or mapping logic in that file.

**3. Debugging**
> Here is my loop function: [paste code]. The behavior I expect is
> [describe]. What I'm actually seeing is [describe]. What's the
> likely cause, and what's the smallest fix?

## Rule

Every AI response gets followed by my own explanation, in plain
language, of what changed and why — written as a code comment or a
changelog line. No paste-dumps without annotation.

## Prompt logging rule

Whenever I submit a new prompt or ask for a new kind of technical
help, I must add a short record to this file so the project keeps a
clear log of the exact use cases and constraints being applied.

Add the new prompt under a section called "Prompt Log" in this format:

> Date: [date]
> Request: [short summary]
> Prompt: [exact prompt or condensed version]
> Notes: [what constraint it followed]

This keeps the project traceable and ensures each AI interaction stays
inside the rules already defined in `SYSTEM_CHARTER.md`.

### Prompt Log

> Date: 2026-09-24
> Request: Initial app concept and product goal.
> Prompt: "I want to make a phone app that locks you phone while driving and but you can only use is your music app, maps, calling system, and Ai asistant"
> Notes: Established the core idea: Android-first driving safety app with only music, maps, calling, and AI assistant allowed while driving.

> Date: 2026-09-24
> Request: Require prompt tracking for every user request.
> Prompt: "okay every time I promnt somthing in I need you to add that to promts.md"
> Notes: Added the requirement that every future prompt must be logged here for traceability and project continuity.

> Date: 2026-09-24
> Request: Align the app to the system charter.
> Prompt: "okay the app should do this read the System_character.md"
> Notes: Followed the project charter: one clear purpose, essential functions only, intentional friction for restricted actions, no shaming or surveillance.

> Date: 2026-09-24
> Request: Update the project README and clarify phone-app possibilities.
> Prompt: "I need you to change the Readme.md and I is it possible to have this as and app on your phone? and i also want to link it to the your phone apps"
> Notes: Kept the project within the charter and clarified the real platform constraints, especially between Android and iPhone behavior.

> Date: 2026-09-24
> Request: Create meaningful project commits.
> Prompt: "can you do commits?"
> Notes: Created a small commit history representing the meaningful project steps and documentation updates.

> Date: 2026-09-24
> Request: Review project status and roadmap progress.
> Prompt: "yes that will be fine but i also want to know what we have done and what is left for the road map?"
> Notes: Reviewed the current milestones and identified completed work versus remaining project tasks.

> Date: 2026-09-24
> Request: Mark completed roadmap items and log the prompt.
> Prompt: "yes that is fime and make sure to strike out things on the road map and add the promnts ot the .md file"
> Notes: Updated the roadmap to show completed items and added this prompt to the project log.

> Date: 2026-09-24
> Request: Reset the roadmap to the current Android-first direction.
> Prompt: "woah okay remeber this every time ADD EVERY PROMT OF MINE TO THE .MD FILE AND RIGHT NOW I DONT SEE ALL OF THEM SO ADD THEM ALL AND WE ARE GOING TO RESTART FROM THE TOP OF THE ROADMAP. AND WE ARE GOING TO FOCOUS ON AN ANDROID PHONE FOR NOW"
> Notes: Rebuilt the roadmap from the top with an Android-first focus and ensured the prompt log contains the full request history.

> Date: 2026-09-24
> Request: Start the Android-first first roadmap phase.
> Prompt: "okay fisrt we should start with that is in the first phase of the road map"
> Notes: Began the first phase by clarifying the Android goal, the allowed functions, the restricted functions, and the foundation deliverable before moving to screens or code.

> Date: 2026-09-24
> Request: Convert the first phase into a concrete product definition and prepare for phase 2.
> Prompt: "okay do that and get ready for phase 2"
> Notes: Finished the Phase 1 definition with a product statement, scope constraints, and a clear foundation checkpoint so Phase 2 can move into feature planning with no ambiguity.

> Date: 2026-09-24
> Request: Define the Phase 2 Android widget model and restricted app behavior.
> Prompt: "yes that work four allowed apps I would say would be more like widgets for like Spotify, your music and like a button that's like for your Google Assistant or Gemini assistant and then the emergency context is one that could be pulled from the system since I'm using Google pixel 9A I could set emergency contacts as my parents and I could use that information from the Google settings to allow me to call them or use my AI assistant to call them and make maps like a widget too like one click and you have the full maps and what you or what or where you wanna go and for restricted amps I'd say like social media apps in general or like if you could go to, or if you could use your data from time screen and see if the the most used app would be like example of TikTok that goes like block it and like other social media apps, and like friction screen, it could be like a screen that says you should be driving and give you options of what you need like you could go to your Music or your Maps or your AI assistant or back to home screen where it has all the four and to end the drive and to start it I'd say we could use. It's a Gyro phone Gyro to detect if it's being used inside a car or while driving or if it's connected to any cars and to end it manually ended it, but it's gonna ask you before that if you're sure you want to end it or it could detect when you stop moving."
> Notes: This clarifies the Phase 2 feature set: four Android widgets for music, maps, calls, and AI assistant; emergency contacts pulled from Android settings; social media blocked as high-distraction apps; friction screen with safe alternatives; and drive detection using gyro, car connectivity, or manual start/end confirmation.

> Date: 2026-09-24
> Request: Clarify when code changes happen and how the Android app logic will work.
> Prompt: "yes and does when whill there be a code chage to how does the app code work?"
> Notes: Confirmed the next step is actual implementation work. The app code will be structured around state management, safe app widgets, restricted app detection, friction flow, and drive start/end logic; the current browser prototype is only the planning prototype, not the final Android production code.

> Date: 2026-09-25
> Request: Turn the Phase 2 feature plan into a concrete screen-by-screen Android app blueprint.
> Prompt: "yes do that for me"
> Notes: Created the Phase 2 blueprint around four safe widgets, emergency contact access, social media restrictions, friction flow, and drive start/end detection, keeping the design within the charter and Android-first scope.

> Date: 2026-09-25
> Request: Convert the Phase 2 blueprint into an actual Android app file structure and implementation plan.
> Prompt: "yes do that and also update the promts .md"
> Notes: Mapped the app into a realistic Android architecture: DriveStateManager, AppRestrictionChecker, EmergencyContactService, DrivingDetector, SafeHomeController, and FrictionController, while keeping the project aligned with the system charter and Android-first focus.

> Date: 2026-09-25
> Request: Confirm the app architecture is helpful and move forward with the next implementation step.
> Prompt: "yes that would be most helpfull"
> Notes: Confirmed the Android app structure and logic plan were useful; the next step is to move from architecture into the actual screen and class implementation plan.

> Date: 2026-09-25
> Request: Prepare the live UI preview and commit the current project state.
> Prompt: "okay yeah do do and while you are at it is there like a page where i can see how it's curently looking like? and do a comit too"
> Notes: Verified the browser prototype page was running locally and created a project commit to record the latest app preview and planning work.

> Date: 2026-09-25
> Request: Create the Phase 2 screen-by-screen spec document.
> Prompt: "yes please do that"
> Notes: Produced the Phase 2 screen-by-screen specification and recorded the implementation direction in the project docs.

> Date: 2026-09-25
> Request: Ensure the project log contains the complete set of user prompts without omissions.
> Prompt: "wait you forgot some of my ealrier promts remeber to always put them in add them all in"
> Notes: Corrected the prompt log to include earlier and recent user requests so the full conversation history is complete and traceable.

> Date: 2026-09-25
> Request: Turn the Android-first plan into actual project files and implementation structure.
> Prompt: "yes okay can you do that?"
> Notes: Created the Android implementation blueprint, app skeleton directory, and core class files for DriveState, DriveStateManager, AppRestrictionChecker, DrivingDetector, EmergencyContactService, SafeHomeController, and FrictionController so the project has a real build-ready foundation.
