export function navigationPanelHandlers(dispatch, viewState) {
    const { capabilities } = viewState;

    const handlers = {};

    handlers.onEnterDashboard = () =>
        dispatch({
            type: "ENTER_DASHBOARD",
        });

    if (capabilities.canEnterProductList) {
        handlers.onEnterProductList = () =>
            dispatch({
                type: "ENTER_PRODUCT_LIST",
            });
    }

    if (capabilities.canEnterCategoryList) {
        handlers.onEnterCategoryList = () =>
            dispatch({
                type: "ENTER_CATEGORY_LIST",
            });
    }

    if (capabilities.canEnterCart) {
        handlers.onEnterCart = () =>
            dispatch({
                type: "ENTER_CART",
            });
    }

    // CUSTOMER
    if (capabilities.canEnterOrderHistory) {
        handlers.onEnterOrders = () =>
            dispatch({
                type: "ENTER_ORDER_HISTORY",
            });
    }

    // ADMIN
    if (capabilities.canEnterOrderList) {
        handlers.onEnterOrders = () =>
            dispatch({
                type: "ENTER_ORDER_LIST",
            });
    }

    if (capabilities.canEnterAuthentication) {
        handlers.onEnterAuthentication = () =>
            dispatch({
                type: "ENTER_AUTHENTICATION",
            });
    }

    if (capabilities.canLogout) {
        handlers.onLogout = () =>
            dispatch({
                type: "LOGOUT",
            });
    }

    return handlers;
}