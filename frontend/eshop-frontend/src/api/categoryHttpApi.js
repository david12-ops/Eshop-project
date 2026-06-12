// src/api/categoriesHttpApi.js

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

export function createCategoriesHttpApi() {
    return {
        async getCategories() {
            const response = await fetch(buildUrl("/api/categories"));

            return handleResponse(response);
        },

        async createCategory(payload, token) {
            const response = await fetch(buildUrl("/api/categories"), {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(payload),
            });

            return handleResponse(response);
        },

        async updateCategory(categoryId, payload, token) {
            const response = await fetch(
                buildUrl(`/api/categories/${categoryId}`),
                {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                    body: JSON.stringify(payload),
                }
            );

            return handleResponse(response);
        },

        async deleteCategory(categoryId, token) {
            const response = await fetch(
                buildUrl(`/api/categories/${categoryId}`),
                {
                    method: "DELETE",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            return handleResponse(response);
        },
    };
}