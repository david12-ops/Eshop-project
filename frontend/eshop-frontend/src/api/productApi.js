// src/api/productApi.js

import { delay } from "./utils.js";

export function createProductApi(db) {
    return {
        async getProducts() {
            await delay();

            return {
                status: "SUCCESS",
                products: structuredClone(db.products),
            };
        },

        async getProduct(productId) {
            await delay();

            const product = db.products.find(
                (p) => p.id === productId
            );

            if (!product) {
                return {
                    status: "REJECTED",
                    reason: "Produkt neexistuje",
                };
            }

            return {
                status: "SUCCESS",
                product: structuredClone(product),
            };
        },

        async createProduct(payload, token) {
            await delay();

            const user = db.users.find(
                (u) => u.token === token
            );

            if (!user) {
                return {
                    status: "REJECTED",
                    reason: "Neplatný token",
                };
            }

            if (user.role !== "ADMIN") {
                return {
                    status: "REJECTED",
                    reason: "Nedostatečná oprávnění",
                };
            }

            const product = {
                id: crypto.randomUUID(),
                ...payload,
            };

            db.products.push(product);

            return {
                status: "SUCCESS",
                product: structuredClone(product),
            };
        },

        async updateProduct(productId, data, token) {
            await delay();

            const user = db.users.find(
                (u) => u.token === token
            );

            if (!user || user.role !== "ADMIN") {
                return {
                    status: "REJECTED",
                    reason: "Nedostatečná oprávnění",
                };
            }

            const product = db.products.find(
                (p) => p.id === productId
            );

            if (!product) {
                return {
                    status: "REJECTED",
                    reason: "Produkt neexistuje",
                };
            }

            Object.assign(product, data);

            return {
                status: "SUCCESS",
                product: structuredClone(product),
            };
        },

        async deleteProduct(productId, token) {
            await delay();

            const user = db.users.find(
                (u) => u.token === token
            );

            if (!user || user.role !== "ADMIN") {
                return {
                    status: "REJECTED",
                    reason: "Nedostatečná oprávnění",
                };
            }

            const product = db.products.find(
                (p) => p.id === productId
            );

            if (!product) {
                return {
                    status: "REJECTED",
                    reason: "Produkt neexistuje",
                };
            }

            db.products = db.products.filter(
                (p) => p.id !== productId
            );

            return {
                status: "SUCCESS",
                product: structuredClone(product),
            };
        },
    };
}