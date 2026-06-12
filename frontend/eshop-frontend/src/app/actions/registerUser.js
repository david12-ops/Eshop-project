// src/app/actions/registerUser.js

export async function registerUser({ store, api, payload }) {
  const { username, password, email } = payload;
  const role = "CUSTOMER";

  const result = await api.auth.register({
    username,
    password,
    email,
    role,
  });

  store.setState((state) => {
    let auth = state.auth;
    let notification = null;
    let mode = state.ui.mode;

    if (result.status === "SUCCESS") {
      auth = {
        role: result.role ?? "CUSTOMER",
        userId: result.userId,
        token: result.token,
      };

      mode = "DASHBOARD";

      notification = {
        type: "SUCCESS",
        message: "Registrace proběhla úspěšně",
      };

    } else {
      notification = {
        type: "WARNING",
        message: "Registraci se nepodařilo dokončit",
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

