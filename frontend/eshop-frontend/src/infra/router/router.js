// src/infra/router/router.js

export function urlToRoute(url) {
  const hashIndex = url.indexOf("#");
  const path = hashIndex >= 0 ? url.slice(hashIndex + 1) : "";
  return parseUrl(path);
}

export function parseUrl(path) {
  const parts = path.split("/").filter(Boolean);

  // #/
  if (parts.length === 0) {
    return { context: "DASHBOARD" };
  }

  // #/products
  if (parts.length === 1 && parts[0] === "products") {
    return { context: "PRODUCT_LIST" };
  }

  // #/products/:id
  if (parts.length === 2 && parts[0] === "products") {
    return {
      context: "PRODUCT_DETAIL",
      productId: parts[1],
    };
  }

  // #/categories
  if (parts.length === 1 && parts[0] === "categories") {
    return { context: "CATEGORY_LIST" };
  }

  // #/cart
  if (parts.length === 1 && parts[0] === "cart") {
    return { context: "CART" };
  }

  // #/orders
  if (parts.length === 1 && parts[0] === "orders") {
    return { context: "ORDER_LIST" };
  }

  // #/auth
  if (parts.length === 1 && parts[0] === "auth") {
    return { context: "AUTHENTICATION" };
  }

  // #/order-history
  if (parts.length === 1 && parts[0] === "order-history") {
    return { context: "ORDER_HISTORY" };
  }

  return { context: "UNKNOWN" };
}

export function routeToAction(route) {
  switch (route.context) {
    case "DASHBOARD":
      return { type: "ENTER_DASHBOARD" };

    case "ORDER_HISTORY":
      return { type: "ENTER_ORDER_HISTORY" };

    case "PRODUCT_LIST":
      return { type: "ENTER_PRODUCT_LIST" };

    case "PRODUCT_DETAIL":
      return {
        type: "ENTER_PRODUCT_DETAIL",
        payload: {
          productId: route.productId,
        },
      };

    case "CATEGORY_LIST":
      return { type: "ENTER_CATEGORY_LIST" };

    case "CART":
      return { type: "ENTER_CART" };

    case "ORDER_LIST":
      return { type: "ENTER_ORDER_LIST" };

    case "AUTHENTICATION":
      return { type: "ENTER_AUTHENTICATION" };

    case "UNKNOWN":
    default:
      return { type: "ENTER_DASHBOARD" };
  }
}

export function urlToAction(url) {
  const route = urlToRoute(url);
  return routeToAction(route);
}