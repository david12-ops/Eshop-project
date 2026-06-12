// src/api/ordersHttpApi.js

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

export function createOrdersHttpApi() {
    return {
        async getOrders(token) {
            const response = await fetch(buildUrl("/api/orders"), {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            return handleResponse(response);
        },

        async getOrder(orderId, token) {
            const response = await fetch(
                buildUrl(`/api/orders/${orderId}`),
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            return handleResponse(response);
        },

        async createOrder(payload, token) {
            const response = await fetch(buildUrl("/api/orders"), {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(payload),
            });

            return handleResponse(response);
        },

        async cancelOrder(orderId, token) {
            const response = await fetch(
                buildUrl(`/api/orders/${orderId}/cancel`),
                {
                    method: "POST",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            return handleResponse(response);
        },
    };
}