export function dashboardHandlers(dispatch, viewState) {
  const { capabilities } = viewState;

  const {
    canLogout,
    canEnterDashboard,
    canEnterProductList,
    canEnterCategoryList,
    canEnterOrderList,
    canEnterCart,
  } = capabilities;

  const handlers = {};

  if (canLogout) {
    handlers.onLogout = () =>
      dispatch({
        type: "LOGOUT",
      });
  }

  if (canEnterDashboard) {
    handlers.onEnterDashboard = () =>
      dispatch({
        type: "ENTER_DASHBOARD",
      });
  }

  if (canEnterProductList) {
    handlers.onEnterProductList = () =>
      dispatch({
        type: "ENTER_PRODUCT_LIST",
      });
  }

  if (canEnterCategoryList) {
    handlers.onEnterCategoryList = () =>
      dispatch({
        type: "ENTER_CATEGORY_LIST",
      });
  }

  if (canEnterOrderList) {
    handlers.onEnterOrderList = () =>
      dispatch({
        type: "ENTER_ORDER_LIST",
      });
  }

  if (canEnterCart) {
    handlers.onEnterCart = () =>
      dispatch({
        type: "ENTER_CART",
      });
  }

  return handlers;
}