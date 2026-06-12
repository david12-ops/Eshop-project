/* viewState ma tvar:
 * {
 *   type: 'ORDER_HISTORY',
 *   orders,
 *   capabilities: {
 *     canEnterDetail: true,
 *     canBackToDashboard: true,
 *   }
 * }
 */
export function orderHistoryHandlers(dispatch, viewState) {
    const { capabilities } = viewState;
    const { canEnterDetail, canBackToDashboard } = capabilities;

    const handlers = {};

    if (canEnterDetail) {
        handlers.onEnterDetail = (orderId) =>
            dispatch({
                type: "ENTER_ORDER_DETAIL",
                payload: { orderId },
            });
    }

    if (canBackToDashboard) {
        handlers.onBackToDashboard = () =>
            dispatch({
                type: "ENTER_DASHBOARD",
            });
    }

    return handlers;
}