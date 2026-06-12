// src/app/actions/enterProductDetail.js

export async function enterProductDetail({ store, payload }) {
    const { productId } = payload;

    store.setState((state) => ({
        ...state,
        ui: {
            ...state.ui,
            mode: "PRODUCT_DETAIL",
            selectedProductId: productId,
            notification: null,
        },
    }));
}