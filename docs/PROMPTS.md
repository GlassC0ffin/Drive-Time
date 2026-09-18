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
> Request: Build a driving safety app prototype matching the project charter.
> Prompt: "The app should do this; read the System_Charter.md."
> Notes: Followed the Drive-Time rules: one clear purpose, allowed functions limited to music, maps, calling, and AI assistant, intentional friction for restricted actions, no shame or surveillance, minimal feature set.

> Date: 2026-09-24
> Request: Update the project README and explain whether the app can run on a phone and link to native apps.
> Prompt: "I need you to change the Readme.md and I is it possible to have this as and app on your phone? and i also want to link it to the your phone apps"
> Notes: Kept the project within the charter: minimal feature scope, honest platform constraints, browser prototype plus native mobile pathway, no misleading claims about complete app locking on iPhone.

> Date: 2026-09-24
> Request: Rewrite the project roadmap to match the current app direction and product constraints.
> Prompt: "okay i need you to remake the road map to what we are discusing right now"
> Notes: Aligned the roadmap to the current product direction: driving-mode safety app, allowed functions limited to music, maps, calling, and AI assistant, browser prototype first, Android native app as realistic next step, and clear acknowledgment of iPhone limitations.
