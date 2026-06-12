// src/api/httpApi.js

import { createAuthHttpApi } from "./authHttpApi.js";
import { createProductsHttpApi } from "./productHttpApi.js";
import { createCategoriesHttpApi } from "./categoryHttpApi.js";
import { createOrdersHttpApi } from "./ordersHttpApi.js";
import { createCartHttpApi } from "./cartHttpApi.js";

export function createHttpApi() {
  return {
    auth: createAuthHttpApi(),
    products: createProductsHttpApi(),
    categories: createCategoriesHttpApi(),
    orders: createOrdersHttpApi(),
    carts: createCartHttpApi(),
  };
}
