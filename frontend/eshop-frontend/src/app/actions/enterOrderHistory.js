// src/app/actions/enterOrderHistory.js

export async function enterOrderHistory({ store }) {
    store.setState((state) => ({
        ...state,
        ui: {
            ...state.ui,
            mode: "ORDER_HISTORY",
            selectedOrderId: null,
            notification: null,
        },
    }));
}