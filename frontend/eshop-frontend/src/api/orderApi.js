export function createOrderApi(db) {
    return {
        async getOrders() {
            return {
                status: "SUCCESS",
                orders: db.orders,
            };
        },

        async getOrder(id) {
            const order = db.orders.find(
                (o) => o.id === id
            );

            if (!order) {
                return {
                    status: "ERROR",
                    reason: "Order not found",
                };
            }

            return {
                status: "SUCCESS",
                order,
            };
        },

        async createOrder(orderData) {
            const order = {
                id: `order-${Date.now()}`,
                ...orderData,
            };

            db.orders.push(order);

            return {
                status: "SUCCESS",
                order,
            };
        },
    };
}