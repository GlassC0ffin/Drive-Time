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
      if (payload === "Music") {
        state.mode = "MUSIC";
        state.lastMessage = "Music mode active.";
        render();
        return;
      }

      if (payload === "Maps") {
        state.mode = "MAPS";
        state.lastMessage = "Maps mode active.";
        render();
        return;
      }

      if (payload === "AI Assistant") {
        state.mode = "ASSISTANT";
        state.lastMessage = "AI assistant is ready to help.";
        render();
        return;
      }

      if (payload === "Call") {
        state.mode = "CALLING";
        state.lastMessage = "Call mode is active.";
        render();
        return;
      }

      if (payload === "Emergency") {
        state.mode = "EMERGENCY";
        state.lastMessage = "Emergency contact ready.";
        render();
        return;
      }

      state.lastMessage = `${payload} is available while driving.`;
      render();
      return;
    }

    if (type === "music") {
      state.mode = "MUSIC";
      state.lastMessage = "Music mode active.";
      render();
      return;
    }

    if (type === "maps") {
      state.mode = "MAPS";
      state.lastMessage = "Maps mode active.";
      render();
      return;
    }

    if (type === "assistant") {
      state.mode = "ASSISTANT";
      state.lastMessage = "AI assistant is ready to help.";
      render();
      return;
    }

    if (type === "emergency") {
      state.mode = "EMERGENCY";
      state.lastMessage = "Emergency contact ready.";
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
      <div class="drive-hud-screen screen-card">
        <div class="drive-header-row">
          <span class="drive-number">02 - Drive Mode</span>
        </div>

        <div class="drive-title-wrap">
          <h1 class="drive-title">DRIVE MODE</h1>
          <div class="live-status"><span class="live-dot"></span> ACTIVE</div>
        </div>

        <div class="drive-grid">
          <button class="cyber-tile maps-tile" data-action="allowed" data-value="Maps">
            <span>MAPS</span>
          </button>

          <button class="cyber-tile music-tile" data-action="music" data-value="Music">
            <span>MUSIC</span>
          </button>

          <button class="cyber-tile assistant-tile" data-action="allowed" data-value="AI Assistant">
            <span>AI ASSISTANT</span>
          </button>

          <button class="cyber-tile emergency-tile" data-action="restricted" data-value="Emergency">
            <span>EMERGENCY</span>
          </button>
        </div>

        <button class="restricted-pill" data-action="restricted" data-value="Restricted App">
          RESTRICTED APP
        </button>

        <button class="end-drive-pill" data-action="endDrive">End Drive</button>
      </div>
    `;
    return;
  }

  if (state.mode === "MUSIC") {
    appEl.innerHTML = `
      <div class="music-screen screen-card">
        <div class="music-header">
          <div class="music-title">MUSIC</div>
          <div class="music-subtitle">DRIVE MODE</div>
        </div>

        <div class="vinyl-panel">
          <div class="vinyl-disc is-spinning">
            <div class="vinyl-label">
              <span>DR</span>
            </div>
          </div>
        </div>

        <div class="track-block">
          <div class="track-name">Midnight Drive</div>
          <div class="track-artist">The Open Road</div>
        </div>

        <div class="progress-bar">
          <span class="progress-fill"></span>
        </div>

        <div class="player-controls">
          <button class="mini-btn" aria-label="Previous">◀</button>
          <button class="mini-btn play-btn" aria-label="Pause">⏸</button>
          <button class="mini-btn" aria-label="Next">▶</button>
        </div>

        <button class="drive-mode-pill" data-action="returnToDriving">← DRIVE MODE</button>
      </div>
    `;
    return;
  }

  if (state.mode === "MAPS") {
    appEl.innerHTML = `
      <div class="maps-screen screen-card">
        <div class="maps-header">MAPS</div>

        <div class="map-card">
          <div class="map-grid"></div>
        </div>

        <div class="turn-box">
          <div class="turn-label">Next turn</div>
          <div class="turn-text">Turn right in 500 ft</div>
          <div class="turn-meta">12 min remaining</div>
        </div>

        <button class="drive-mode-pill maps-pill" data-action="returnToDriving">← DRIVE MODE</button>
      </div>
    `;
    return;
  }

  if (state.mode === "ASSISTANT") {
    appEl.innerHTML = `
      <div class="assistant-screen screen-card">
        <div class="cyber-header-row">
          <span class="micro-pill">AI ASSISTANT</span>
        </div>

        <div class="cyber-hero">
          <div class="cyber-kicker">Drive voice</div>
          <h1 class="cyber-title">HOW CAN I HELP?</h1>
        </div>

        <div class="assistant-panel">
          <div class="assistant-row"><span>Route to home</span><strong>Ready</strong></div>
          <div class="assistant-row"><span>Call mom</span><strong>Queued</strong></div>
          <div class="assistant-row"><span>Play playlist</span><strong>On deck</strong></div>
        </div>

        <div class="action-stack">
          <button class="primary-btn cyber-btn" data-action="returnToDriving">Back to drive</button>
          <button class="secondary-btn cyber-btn" data-action="allowed" data-value="Maps">Open maps</button>
        </div>
      </div>
    `;
    return;
  }

  if (state.mode === "EMERGENCY") {
    appEl.innerHTML = `
      <div class="emergency-screen screen-card">
        <div class="cyber-header-row">
          <span class="micro-pill emergency-pill">EMERGENCY</span>
        </div>

        <div class="cyber-hero emergency-hero">
          <div class="cyber-kicker">Contact support</div>
          <h1 class="cyber-title">CALL SAFE CONTACTS</h1>
        </div>

        <div class="emergency-card">
          <div class="contact-row"><span>Mom</span><strong>Ready</strong></div>
          <div class="contact-row"><span>Dad</span><strong>Ready</strong></div>
        </div>

        <div class="action-stack">
          <button class="primary-btn cyber-btn danger-btn" data-action="returnToDriving">Return to drive</button>
          <button class="secondary-btn cyber-btn" data-action="allowed" data-value="AI Assistant">Use AI assistant</button>
        </div>
      </div>
    `;
    return;
  }

  if (state.mode === "DISTRACTION") {
    appEl.innerHTML = `
      <div class="friction-screen screen-card">
        <div class="cyber-header-row">
          <span class="micro-pill warning-pill">FRICTION</span>
        </div>

        <div class="cyber-hero">
          <div class="cyber-kicker">Stay safe</div>
          <h1 class="cyber-title">${state.attemptedApp.toUpperCase()}</h1>
        </div>

        <p class="subtitle friction-copy">This app is paused while the car is in motion. Choose a safer option below.</p>

        <div class="friction-grid">
          <button class="cyber-tile maps-tile" data-action="allowed" data-value="Maps"><span>MAPS</span></button>
          <button class="cyber-tile music-tile" data-action="allowed" data-value="Music"><span>MUSIC</span></button>
          <button class="cyber-tile assistant-tile" data-action="allowed" data-value="AI Assistant"><span>AI ASSISTANT</span></button>
          <button class="cyber-tile emergency-tile" data-action="allowed" data-value="Emergency"><span>EMERGENCY</span></button>
        </div>

        <div class="action-stack">
          <button class="primary-btn cyber-btn" data-action="returnToDriving">Return to driving</button>
          <button class="secondary-btn cyber-btn" data-action="continueAnyway">Continue through friction</button>
        </div>
      </div>
    `;
    return;
  }

  appEl.innerHTML = `
    <div class="end-screen screen-card">
      <div class="cyber-header-row">
        <span class="micro-pill">ENDED</span>
      </div>

      <div class="cyber-hero">
        <div class="cyber-kicker">Drive complete</div>
        <h1 class="cyber-title">SAFE DRIVE</h1>
      </div>

      <p class="subtitle friction-copy">You are no longer in a driving session. The phone is fully available again.</p>

      <div class="action-stack">
        <button class="primary-btn cyber-btn" data-action="startDrive">Start another drive</button>
      </div>
    </div>
  `;
}
