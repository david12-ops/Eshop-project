// src/app/actions/createProduct.js

export async function createProduct({ store, api, payload }) {
    const token = store.getState().auth.token;

    const { status, reason, product } =
        await api.products.createProduct(payload, token);

    store.setState((state) => {
        let products = state.products;
        let notification = null;

        if (status === "SUCCESS") {
            products = [...state.products, product];

            notification = {
                type: "SUCCESS",
                message: "Produkt byl vytvořen",
            };
        } else if (status === "REJECTED") {
            notification = {
                type: "WARNING",
                message: "Produkt nelze vytvořit",
            };
        }

        return {
            ...state,
            products,
            ui: {
                ...state.ui,
                notification,
            },
        };
    });
}