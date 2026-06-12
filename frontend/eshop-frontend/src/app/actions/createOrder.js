// src/app/actions/createOrder.js

export async function createOrder({ store, api, payload }) {
    const state = store.getState();

    const customerId = state.auth.userId;

    const cart =
        state.cart.find(
            c => c.userId === customerId
        ) ?? { items: [] };

    const orderData = {
        customerId,
        orderDate: new Date()
            .toISOString()
            .split("T")[0],
        status: "PENDING",
        items: cart.items,
    };

    const { status, order } =
        await api.orders.createOrder(orderData);

    if (status === "SUCCESS") {
        await api.carts.clearCart(customerId);
    }

    store.setState((state) => {
        let orders = state.orders;
        let cart = state.cart;
        let notification = null;

        if (status === "SUCCESS") {
            orders = [...state.orders, order];

            cart = state.cart.map(c =>
                c.userId === state.auth.userId
                    ? { ...c, items: [] }
                    : c
            );

            notification = {
                type: "SUCCESS",
                message: "Objednávka byla vytvořena",
            };
        } else if (status === "REJECTED") {
            notification = {
                type: "WARNING",
                message: "Objednávku nelze vytvořit",
            };
        }

        return {
            ...state,
            orders,
            cart,
            ui: {
                ...state.ui,
                notification,
            },
        };
    });
}