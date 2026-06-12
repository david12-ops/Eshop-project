// src/api/productsHttpApi.js

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

  try {
    return await response.json();
  } catch {
    return {
      status: "ERROR",
      reason: "Neplatná odpověď serveru",
    };
  }
}

export function createProductsHttpApi() {
  return {
    async getProducts() {
      const response = await fetch(buildUrl("/api/products"));

      return handleResponse(response);
    },

    async getProduct(productId) {
      const response = await fetch(
        buildUrl(`/api/products/${productId}`)
      );

      return handleResponse(response);
    },

    async createProduct(payload, token) {
      const response = await fetch(buildUrl("/api/products"), {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });

      return handleResponse(response);
    },

    async updateProduct(productId, payload, token) {
      const response = await fetch(
        buildUrl(`/api/products/${productId}`),
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

    async deleteProduct(productId, token) {
      const response = await fetch(
        buildUrl(`/api/products/${productId}`),
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