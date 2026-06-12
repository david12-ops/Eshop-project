// src/infra/store/selectors.js
// předpokládáme, že selectViewState je jediné místo rozhodování o stavo-pohledu aplikace

export function selectProductById(state) {
  const productId = state.ui.selectedProductId;

  if (!productId) {
    return null;
  }

  return state.products.find(
    (p) => p.id === productId
  ) ?? null;
}

export function canCreateProduct(state) {
  return state.auth.role === "ADMIN";
}

export function canUpdateProduct(state) {
  return state.auth.role === "ADMIN";
}

export function canDeleteProduct(state) {
  return state.auth.role === "ADMIN";
}

export function canAddToCart(state) {
  return state.auth.role === "CUSTOMER";
}

export function canCreateOrder(state) {
  const cart =
    state.cart.find(
      c => c.userId === state.auth.userId
    ) ?? { items: [] };

  return (
    state.auth.role === "CUSTOMER" &&
    cart.items.length > 0
  );
}

export function selectProductListView(state) {
  return {
    type: "PRODUCT_LIST",
    products: state.products,

    capabilities: {
      canEnterDetail: true,
      canCreateProduct: canCreateProduct(state),
    },
  };
}

export function selectProductDetailView(state) {
  const product = selectProductById(state);

  return {
    type: "PRODUCT_DETAIL",
    product,

    capabilities: {
      canBackToList: true,
      canAddToCart: canAddToCart(state),
      canUpdateProduct: canUpdateProduct(state),
      canDeleteProduct: canDeleteProduct(state),
    },
  };
}

export function selectCartView(state) {
  const cart =
    state.cart.find(
      c => c.userId === state.auth.userId
    ) ?? { items: [] };

  const products = cart.items.map(item => {
    const product = state.products.find(
      p => p.id === item.productId
    );

    return {
      ...product,
      quantity: item.quantity,
    };
  });

  return {
    type: "CART",
    cart: products,

    capabilities: {
      canRemoveFromCart:
        products.length > 0,
      canCreateOrder:
        canCreateOrder(state),
    },
  };
}

export function selectOrderHistoryView(state) {
  return {
    type: "ORDER_HISTORY",
    orders: state.orders.filter(
      o => o.customerId === state.auth.userId
    ),

    capabilities: {
      canBackToDashboard: true,
    },
  };
}

export function selectDashboardView(state) {
  const { role } = state.auth;

  return {
    type: "DASHBOARD",
    role,

    capabilities: {
      canLogout: role !== "ANONYMOUS",
      canEnterProductList: true,
      canEnterCart: role === "CUSTOMER",
      canEnterOrderHistory: role === "CUSTOMER",
      canEnterAuthentication: role === "ANONYMOUS",
      canEnterCategoryList: role === "ADMIN",
      canEnterOrderList: role === "ADMIN",
    },
  };
}

export function selectAuthenticationView(state) {
  const { role, userId } = state.auth;

  const isLoggedIn =
    role !== "ANONYMOUS" &&
    role != null &&
    userId != null;

  return {
    type: "AUTHENTICATION",
    isLoggedIn,
    role,

    capabilities: {
      canLogin: !isLoggedIn,
      canRegister: !isLoggedIn,
      canLogout: isLoggedIn,
    },
  };
}

/*
 ** vrací objekt ve tvaru
 ** {
 **   type: 'LOADING' | 'ERROR' | 'EXAM_TERM_LIST' | 'EXAM_TERM_DETAIL' | 'EXAM_TERM_ADMINISTRATION',
 **   message?: string ,
 **   exam?: ExamTerm,
 **   exams?: ExamTerm[],
 **   capabilities?: {
 **     canEnterDetail: boolean,
 **     canEnterAdministration: boolean,
 **     canBackToList: boolean,
 **     canCreateExam: boolean,
 **     canRegister: boolean,
 **     canUnregister: boolean,
 **     canPublish: boolean,
 **     canUnpublish: boolean,
 **     canCancel: boolean,
 **     canDelete: boolean,
 **     canUpdateCapacity: boolean,
 **     canUpdate: boolean
 **   },
 ** }
 */
export function selectViewState(state) {
  const { status, message, mode } = state.ui;

  if (status === "LOADING") {
    return { type: "LOADING" };
  }

  if (status === "ERROR") {
    return { type: "ERROR", message };
  }

  if (status !== "READY") {
    return {
      type: "ERROR",
      message: `Unknown ui status: ${status}`,
    };
  }

  switch (mode) {
    case "AUTHENTICATION":
      return selectAuthenticationView(state);

    case "DASHBOARD":
      return selectDashboardView(state);

    case "PRODUCT_LIST":
      return selectProductListView(state);

    case "PRODUCT_DETAIL":
      return selectProductDetailView(state);

    case "CART":
      return selectCartView(state);

    case "ORDER_HISTORY":
      return selectOrderHistoryView(state);

    default:
      return {
        type: "ERROR",
        message: `Unknown ui mode: ${mode}`,
      };
  }
}
