export function errorHandlers(dispatch) {
  const handlers = {
    onContinue: () =>
      dispatch({
        type: "ENTER_DASHBOARD",
      }),
  };

  return handlers;
}