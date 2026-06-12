/* viewState ma tvar:
 * {
 *   type: 'PRODUCT_LIST',
 *   products,
 *   capabilities: {
 *     canEnterDetail: true,
 *     canCreateProduct: true,
 *   }
 * }
 */
export function productListHandlers(dispatch, viewState) {
    const { capabilities } = viewState;
    const { canEnterDetail, canCreateProduct } = capabilities;

    const handlers = {};

    if (canEnterDetail) {
        handlers.onEnterDetail = (productId) =>
            dispatch({
                type: "ENTER_PRODUCT_DETAIL",
                payload: { productId },
            });
    }

    if (canCreateProduct) {
        handlers.onCreateProduct = (data) =>
            dispatch({
                type: "CREATE_PRODUCT",
                payload: data,
            });
    }

    return handlers;
}