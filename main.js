import { setupInput } from "./src/input/input.js";

const appEl = document.getElementById("app");

const state = {
  mode: "IDLE",
  attemptedApp: "Messages",
  lastMessage: "Ready for your next drive.",
};

const allowedApps = [
  { name: "Music", icon: "♫" },
  { name: "Maps", icon: "▣" },
  { name: "Call", icon: "☎" },
  { name: "AI Assistant", icon: "✦" },
];

const input = setupInput({
  onAction: (type, payload) => {
    if (type === "startDrive") {
      state.mode = "DRIVING";
      state.lastMessage = "Driving Mode is active. Keep your focus on the road.";
      render();
      return;
    }

    if (type === "allowed") {
      state.lastMessage = `${payload} is available while driving.`;
      render();
      return;
    }

    if (type === "restricted") {
      state.mode = "DISTRACTION";
      state.attemptedApp = payload;
      state.lastMessage = `${payload} is restricted while the vehicle is in motion.`;
      render();
      return;
    }

    if (type === "returnToDriving") {
      state.mode = "DRIVING";
      state.lastMessage = "You returned to the drive.";
      render();
      return;
    }

    if (type === "continueAnyway") {
      state.mode = "DRIVING";
      state.lastMessage = "Intentional friction was shown. Please stay focused on driving.";
      render();
      return;
    }

    if (type === "endDrive") {
      state.mode = "DRIVE_ENDED";
      state.lastMessage = "Drive ended. Safe driving continues off the road.";
      render();
    }
  },
});

input.attachActionHandlers(document);
render();

function render() {
  if (state.mode === "IDLE") {
    appEl.innerHTML = `
      <div class="screen-card">
        <div>
          <div class="topline">
            <span class="badge">Ready</span>
          </div>
          <h1 class="title">Drive-Time</h1>
          <p class="subtitle">A focused mode that keeps the drive clear and the phone useful without distraction.</p>
        </div>

        <div class="actions">
          <button class="primary-btn" data-action="startDrive">Start Drive</button>
          <button class="ghost-btn" data-action="restricted" data-value="Messages">Try Restricted App</button>
        </div>
      </div>
    `;
    return;
  }

  if (state.mode === "DRIVING") {
    appEl.innerHTML = `
      <div class="screen-card">
        <div>
          <div class="topline">
            <span class="badge">Driving</span>
            <button class="ghost-btn" data-action="endDrive">End</button>
          </div>

          <h1 class="title">Stay on the road</h1>
          <p class="subtitle">Only essential driving tools remain visible.</p>

          <div class="grid">
            ${allowedApps
              .map(
                (app) => `
                  <button class="allowed-app" data-action="allowed" data-value="${app.name}">
                    <div>${app.icon}</div>
                    <span>${app.name}</span>
                  </button>
                `,
              )
              .join("")}
          </div>
        </div>

        <div>
          <div class="note">${state.lastMessage}</div>
          <div class="actions">
            <button class="utility-btn" data-action="restricted" data-value="Messages">Messages</button>
            <button class="utility-btn" data-action="restricted" data-value="Browser">Browser</button>
          </div>
        </div>
      </div>
    `;
    return;
  }

  if (state.mode === "DISTRACTION") {
    appEl.innerHTML = `
      <div class="screen-card">
        <div>
          <div class="topline">
            <span class="badge warning">Friction</span>
          </div>

          <h1 class="title">Restricted</h1>
          <p class="subtitle">${state.attemptedApp} is not allowed while driving.</p>
          <div class="note">The system slows the action down and asks for a clear choice instead of hiding it.</div>
        </div>

        <div class="actions">
          <button class="primary-btn" data-action="returnToDriving">Return to driving</button>
          <button class="secondary-btn" data-action="continueAnyway">Continue through friction</button>
        </div>
      </div>
    `;
    return;
  }

  appEl.innerHTML = `
    <div class="screen-card">
      <div>
        <div class="topline">
          <span class="badge">Ended</span>
        </div>

        <h1 class="title">Drive ended</h1>
        <p class="subtitle">You are no longer in a driving session. The phone is fully available again.</p>
      </div>

      <div class="actions">
        <button class="primary-btn" data-action="startDrive">Start another drive</button>
      </div>
    </div>
  `;
}
