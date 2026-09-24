// input.js
// Job: capture user interactions and pass them to the state layer.
// This file only listens for raw actions and forwards them.

export function setupInput({ onAction }) {
  function handleClick(event) {
    const actionEl = event.target.closest("[data-action]");

    if (!actionEl) {
      return;
    }

    const action = actionEl.dataset.action;
    const value = actionEl.dataset.value || null;

    onAction(action, value);
  }

  return {
    attachActionHandlers(root) {
      root.addEventListener("click", handleClick);
    },
  };
}
