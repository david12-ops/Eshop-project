// src/app/actions/logoutUser.js

export async function logoutUser({ store, api }) {
  await api.auth.logout(store.getState().auth.token);

  store.setState((state) => ({
    ...state,

    auth: {
      role: "ANONYMOUS",
      userId: null,
      token: null,
    },

    ui: {
      ...state.ui,
      mode: "AUTHENTICATION",
      selectedProductId: null,
      selectedOrderId: null,

      notification: {
        type: "SUCCESS",
        message: "Byli jste odhlášeni",
      },
    },
  }));
}
