// src/app/actions/addToCart.js

export async function addToCart({
    store,
    api,
    payload,
}) {
    const token = store.getState().auth.token;
    const user = store.getState().auth;
    const { productId } = payload;

    const { status, cart } =
        await api.carts.addItem(
            user.userId,
            productId
        );

    store.setState((state) => {
        const existingCart = state.cart.find(
            c => c.userId === user.userId
        );

        const newCart = existingCart
            ? state.cart.map(c =>
                c.userId === user.userId
                    ? cart
                    : c
            )
            : [...state.cart, cart];

        return {
            ...state,
            cart: newCart,
            ui: {
                ...state.ui,
                notification: {
                    type:
                        status === "SUCCESS"
                            ? "SUCCESS"
                            : "WARNING",
                    message:
                        status === "SUCCESS"
                            ? "Produkt přidán do košíku"
                            : "Produkt nelze přidat do košíku",
                },
            },
        };
    });
}