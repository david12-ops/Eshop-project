/* viewState ma tvar:
 * {
 *   type: 'PRODUCT_DETAIL',
 *   product,
 *   capabilities: {
 *     canAddToCart: true,
 *     canUpdateProduct: true,
 *     canDeleteProduct: true,
 *     canBackToList: true,
 *   }
 * }
 */
export function productDetailHandlers(dispatch, viewState) {
    const { capabilities } = viewState;

    const {
        canAddToCart,
        canUpdateProduct,
        canDeleteProduct,
        canBackToList,
    } = capabilities;

    const handlers = {};

    if (canAddToCart) {
        handlers.onAddToCart = (productId) =>
            dispatch({
                type: "ADD_TO_CART",
                payload: { productId },
            });
    }

    if (canUpdateProduct) {
        handlers.onUpdateProduct = (productId, data) =>
            dispatch({
                type: "UPDATE_PRODUCT",
                payload: {
                    productId,
                    ...data,
                },
            });
    }

    if (canDeleteProduct) {
        handlers.onDeleteProduct = (productId) =>
            dispatch({
                type: "DELETE_PRODUCT",
                payload: { productId },
            });
    }

    if (canBackToList) {
        handlers.onBackToList = () =>
            dispatch({
                type: "ENTER_PRODUCT_LIST",
            });
    }

    return handlers;
}