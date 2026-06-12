// src/app/dispatch.js
// Předpoklady v chování: uživatel nekliká zběsile rychle, nevolá REGISTER_FOR_EXAM5x za sebou - skutečné aplikace by to řešily

import { appInit } from "./appInit.js";

// autentizace
import { loginUser } from "./actions/loginUser.js";
import { registerUser } from "./actions/registerUser.js";
import { logoutUser } from "./actions/logoutUser.js";

// navigace
import { enterDashboard } from "./actions/enterDashboard.js";
import { enterAuthentication } from "./actions/enterAuthentication.js";

import { enterProductList } from "./actions/enterProductList.js";
import { enterProductDetail } from "./actions/enterProductDetail.js";
import { enterCart } from "./actions/enterCart.js";
import { enterOrderHistory } from "./actions/enterOrderHistory.js";

// produkty
import { createProduct } from "./actions/createProduct.js";
import { updateProduct } from "./actions/updateProduct.js";
import { deleteProduct } from "./actions/deleteProduct.js";

// košík
import { addToCart } from "./actions/addToCart.js";
import { removeFromCart } from "./actions/removeFromCart.js";

// objednávky
import { createOrder } from "./actions/createOrder.js";

/**
 * Vytvoří funkci dispatch, která podle typu akce vrací výsledek výkonného kódu akce.
 * Příklad:
 * pro typ akce 'ENTER_EXAM_TERM_DETAIL'
   bude funke dispatch vracet výsledek funkce enterExamTermDetail({ store, payload: action.payload })
 *
 */
export function createDispatcher(store, api) {
  return async function dispatch(action) {
    // výchozí hodnota payload bude {}, pokud payload není v action definován
    // pokud action obsahuje payload, bude payload = action.payload
    // pokud action je null nebo undefined, použijeme se místo ní {}
    const { type, payload = {} } = action ?? {};

    switch (type) {
      // inicializace
      case "APP_INIT":
        return appInit({ store, api, dispatch });

      // autentizační akce
      case "LOGIN":
        return loginUser({ store, api, payload });

      case "REGISTER":
        return registerUser({ store, api, payload });

      case "LOGOUT":
        return logoutUser({ store, api });

      // navigační akce
      case "ENTER_DASHBOARD":
        return enterDashboard({ store });

      case "ENTER_AUTHENTICATION":
        return enterAuthentication({ store });

      case "ENTER_PRODUCT_LIST":
        return enterProductList({ store });

      case "ENTER_PRODUCT_DETAIL":
        return enterProductDetail({ store, payload });

      case "ENTER_CART":
        return enterCart({ store });

      case "ENTER_ORDER_HISTORY":
        return enterOrderHistory({ store });

      // doménové akce
      case "CREATE_PRODUCT":
        return createProduct({ store, api, payload });

      case "UPDATE_PRODUCT":
        return updateProduct({ store, api, payload });

      case "DELETE_PRODUCT":
        return deleteProduct({ store, api, payload });

      case "ADD_TO_CART":
        return addToCart({ store, api, payload });

      case "REMOVE_FROM_CART":
        return removeFromCart({ store, api, payload });

      case "CREATE_ORDER":
        return createOrder({ store, api, payload });

      default:
        console.warn(`Unknown action type: ${type}`);
    }
  };
}
