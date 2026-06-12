// src/app/actionHandlers/createHandlers.js

import { dashboardHandlers } from "./dashboardHandlers.js";
import { authenticationHandlers } from "./authenticationHandlers.js";

import { productListHandlers } from "./productListHandlers.js";
import { productDetailHandlers } from "./productDetailHandlers.js";

import { cartHandlers } from "./cartHandlers.js";
import { orderHistoryHandlers } from "./orderHistoryHandlers.js";

import { errorHandlers } from "./errorHandlers.js";

export function createHandlers(dispatch, viewState) {
  switch (viewState.type) {

    // dashboard
    case "DASHBOARD":
      return dashboardHandlers(dispatch, viewState);

    // login / register
    case "AUTHENTICATION":
      return authenticationHandlers(dispatch, viewState);

    // produkty
    case "PRODUCT_LIST":
      return productListHandlers(dispatch, viewState);

    case "PRODUCT_DETAIL":
      return productDetailHandlers(dispatch, viewState);

    // košík
    case "CART":
      return cartHandlers(dispatch, viewState);

    // objednávky
    case "ORDER_HISTORY":
      return orderHistoryHandlers(dispatch, viewState);

    // chyby
    case "ERROR":
      return errorHandlers(dispatch);

    default:
      return {};
  }
}