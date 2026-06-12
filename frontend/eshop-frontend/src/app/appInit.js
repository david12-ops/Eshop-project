import { urlToAction } from "../infra/router/router.js";

// první akce aplikace
export async function appInit({ store, api, dispatch }) {

  const token = localStorage.getItem("token");

  store.setState((state) => ({
    ...state,
    ui: {
      ...state.ui,
      status: "LOADING",
      message: null,
    },
  }));

  // autentizace
  let auth = {
    role: "ANONYMOUS",
    userId: null,
    token: null,
  };

  if (token) {
    const whoResult = await api.auth.whoAmI(token);

    if (whoResult.status === "SUCCESS") {
      auth = {
        role: whoResult.role,
        userId: whoResult.userId,
        token,
      };
    }
  }

  // načtení dat e-shopu
  const [productsResult, categoriesResult, ordersResult, cartResult] = await Promise.all([
    api.products.getProducts(),
    api.categories.getCategories(),
    api.orders.getOrders(),
    api.carts.getCarts(),
  ]);

  if (
    productsResult.status !== "SUCCESS" ||
    categoriesResult.status !== "SUCCESS" ||
    ordersResult.status !== "SUCCESS" ||
    cartResult.status !== "SUCCESS"
  ) {
    store.setState((state) => ({
      ...state,
      auth,
      ui: {
        ...state.ui,
        status: "ERROR",
        message: "Nepodařilo se načíst data",
      },
    }));

    return;
  }

  const { products } = productsResult;
  const { categories } = categoriesResult;
  const { orders } = ordersResult;
  const { carts } = cartResult;

  store.setState((state) => ({
    ...state,

    auth,

    products: [...products],
    categories: [...categories],
    orders: [...orders],
    cart: [...carts],

    ui: {
      ...state.ui,
      status: "READY",
      message: null,
    },
  }));

  // první navigace
  const initialAction = urlToAction(window.location.href);

  if (initialAction) {
    dispatch(initialAction);
  } else {
    dispatch({ type: "ENTER_DASHBOARD" });
  }
}