// src/app/actions/enterDashboard.js

export async function enterDashboard({ store }) {
    store.setState((state) => ({
        ...state,
        ui: {
            ...state.ui,
            mode: "DASHBOARD",
            selectedProductId: null,
            selectedOrderId: null,
            notification: null,
        },
    }));
}