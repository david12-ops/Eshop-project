// src/app/state.js
/*
UI state contract
=================
state.ui = {
  status: 'READY' | 'LOADING' | 'ERROR',
  message: null | string,
};
READY -> LOADING -> READY
READY -> ERROR
*/

// FP
// funkce bez vedlejších efektů
// žádná globální proměnná
export function createInitialState() {
  return {
    // ====== domain data =======
    products: [],
    categories: [],
    cart: [],
    orders: [],

    // ====== identity ======
    auth: {
      role: "ANONYMOUS",
      userId: null,
      token: null,
    },

    // ====== UI state ======
    ui: {
      mode: "DASHBOARD",

      selectedProductId: null,
      selectedOrderId: null,

      status: "LOADING",
      message: null,
      notification: null,
    },
  };
}