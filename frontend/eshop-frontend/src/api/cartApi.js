export function createCartApi(db) {
    return {
        async getCarts() {
            return {
                status: "SUCCESS",
                carts: db.carts,
            };
        },

        async getCart(userId) {
            const cart =
                db.carts.find((c) => c.userId === userId) ??
                {
                    userId,
                    items: [],
                };

            return {
                status: "SUCCESS",
                cart,
            };
        },

        async addItem(userId, productId, quantity = 1) {
            let cart = db.carts.find(
                (c) => c.userId === userId
            );

            if (!cart) {
                cart = {
                    userId,
                    items: [],
                };

                db.carts.push(cart);
            }

            const existingItem = cart.items.find(
                (i) => i.productId === productId
            );

            if (existingItem) {
                existingItem.quantity += quantity;
            } else {
                cart.items.push({
                    productId,
                    quantity,
                });
            }

            return {
                status: "SUCCESS",
                cart,
            };
        },

        async removeItem(userId, productId) {
            const cart = db.carts.find(
                c => c.userId === userId
            );

            if (!cart) {
                return {
                    status: "ERROR",
                    reason: "Cart not found",
                };
            }

            cart.items = cart.items.filter(
                item => item.productId !== productId
            );

            return {
                status: "SUCCESS",
                cart,
            };
        },

        async clearCart(userId) {
            const cart = db.carts.find(
                (c) => c.userId === userId
            );

            if (cart) {
                cart.items = [];
            }

            return {
                status: "SUCCESS",
            };
        },
    };
}