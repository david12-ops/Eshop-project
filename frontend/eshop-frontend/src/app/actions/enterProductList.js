// src/app/actions/enterProductList.js

export async function enterProductList({ store }) {
    store.setState((state) => ({
        ...state,
        ui: {
            ...state.ui,
            mode: "PRODUCT_LIST",
            selectedProductId: null,
            notification: null,
        },
    }));
}