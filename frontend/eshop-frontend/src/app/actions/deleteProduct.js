// src/app/actions/deleteProduct.js

export async function deleteProduct({ store, api, payload }) {
    const token = store.getState().auth.token;
    const { productId } = payload;

    const { status } =
        await api.products.deleteProduct(productId, token);

    store.setState((state) => {
        if (status === "SUCCESS") {
            return {
                ...state,
                products: state.products.filter(
                    p => p.id !== productId
                ),
                ui: {
                    ...state.ui,
                    mode: "PRODUCT_LIST",
                    selectedProductId: null,
                    notification: {
                        type: "SUCCESS",
                        message: "Produkt byl smazán",
                    },
                },
            };
        }

        return {
            ...state,
            ui: {
                ...state.ui,
                notification: {
                    type: "WARNING",
                    message: "Produkt nelze smazat",
                },
            },
        };
    });
}