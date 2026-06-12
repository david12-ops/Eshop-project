/* viewState ma tvar:
 * {
 *   type: 'CART',
 *   cart,
 *   capabilities: {
 *     canRemoveFromCart: true,
 *     canCreateOrder: true,
 *   }
 * }
 */
export function cartHandlers(dispatch, viewState) {
    const { capabilities } = viewState;
    const { canRemoveFromCart, canCreateOrder } = capabilities;

    const handlers = {};

    if (canRemoveFromCart) {
        handlers.onRemoveFromCart = (productId) =>
            dispatch({
                type: "REMOVE_FROM_CART",
                payload: { productId },
            });
    }

    if (canCreateOrder) {
        handlers.onCreateOrder = () =>
            dispatch({
                type: "CREATE_ORDER",
            });
    }

    return handlers;
}