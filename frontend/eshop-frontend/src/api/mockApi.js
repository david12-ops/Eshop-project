import { createMockDatabase } from "./data.js";

import { createAuthApi } from "./authApi.js";
import { createProductApi } from "./productApi.js";
import { createCategoryApi } from "./categoryApi.js";
import { createOrderApi } from "./orderApi.js";
import { createCartApi } from "./cartApi.js";

export function createApi() {
  const db = createMockDatabase();

  return {
    auth: createAuthApi(db),
    products: createProductApi(db),
    categories: createCategoryApi(db),
    orders: createOrderApi(db),
    carts: createCartApi(db),
  };
}