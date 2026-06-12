// src/app/actions/enterCart.js

export async function enterCart({ store }) {
    store.setState((state) => ({
        ...state,
        ui: {
            ...state.ui,
            mode: "CART",
            notification: null,
        },
    }));
}