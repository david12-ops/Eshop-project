export async function removeFromCart({ store, api, payload }) {
    const user = store.getState().auth;
    const { productId } = payload;

    const {
        status,
        cart: updatedCart,
    } = await api.carts.removeItem(
        user.userId,
        productId
    );

    store.setState((state) => {
        let cart = state.cart;
        let notification = null;

        if (status === "SUCCESS") {
            cart = state.cart.map(c =>
                c.userId === user.userId
                    ? updatedCart
                    : c
            );
        } else {
            notification = {
                type: "WARNING",
                message: "Produkt nelze odebrat z košíku",
            };
        }

        return {
            ...state,
            cart,
            ui: {
                ...state.ui,
                notification,
            },
        };
    });
}