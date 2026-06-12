export async function updateProduct({ store, api, payload }) {
    const token = store.getState().auth.token;
    const { productId, ...data } = payload;

    const { status, reason, product } =
        await api.products.updateProduct(productId, data, token);

    store.setState((state) => {
        let { products } = state;
        let notification = null;

        if (status === "SUCCESS") {
            products = state.products.map((p) =>
                p.id === product.id ? product : p
            );
        } else if (status === "REJECTED") {
            notification = {
                type: "WARNING",
                message: "Produkt nelze upravit",
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