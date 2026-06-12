// src/app/actions/loginUser.js

export async function loginUser({ store, api, payload }) {
  const { username, password, email } = payload;

  const result = await api.auth.login({ username, password, email });

  store.setState((state) => {
    let auth = state.auth;
    let notification = null;
    let mode = state.ui.mode;

    if (result.status === "SUCCESS") {
      auth = {
        role: result.role,
        userId: result.userId,
        token: result.token,
      };

      mode = "DASHBOARD";

      notification = {
        type: "SUCCESS",
        message: "Přihlášení proběhlo úspěšně",
      };
    } else {
      notification = {
        type: "WARNING",
        message: "Neplatné přihlašovací údaje",
      };
    }

    return {
      ...state,
      auth,
      ui: {
        ...state.ui,
        mode,
        notification,
      },
    };
  });
}
