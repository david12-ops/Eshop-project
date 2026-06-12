// src/app/actions/enterAuthentication.js

export async function enterAuthentication({ store }) {
    store.setState((state) => ({
        ...state,
        ui: {
            ...state.ui,
            mode: "AUTHENTICATION",
            notification: null,
        },
    }));
}