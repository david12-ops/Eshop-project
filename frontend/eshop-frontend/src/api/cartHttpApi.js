// src/api/cartHttpApi.js

import { API_BASE_URL } from "./config.js";

function buildUrl(path) {
    return `${API_BASE_URL}${path}`;
}

async function handleResponse(response) {
    if (!response.ok) {
        return {
            status: "ERROR",
            reason: response.statusText,
        };
    }

    return response.json();
}

export function createCartHttpApi() {
    return {
        async getCart(token) {
            const response = await fetch(buildUrl("/api/cart"), {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            return handleResponse(response);
        },

        async addItem(productId, quantity, token) {
            const response = await fetch(
                buildUrl("/api/cart/items"),
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                    body: JSON.stringify({
                        productId,
                        quantity,
                    }),
                }
            );

            return handleResponse(response);
        },

        async removeItem(productId, token) {
            const response = await fetch(
                buildUrl(`/api/cart/items/${productId}`),
                {
                    method: "DELETE",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            return handleResponse(response);
        },

        async clearCart(token) {
            const response = await fetch(buildUrl("/api/cart"), {
                method: "DELETE",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            return handleResponse(response);
        },
    };
}