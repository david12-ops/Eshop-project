export function authenticationHandlers(dispatch, viewState) {
  const { capabilities } = viewState;
  const { canLogin, canRegister } = capabilities;

  const handlers = {};

  if (canLogin) {
    handlers.onLogin = (credentials) =>
      dispatch({
        type: "LOGIN",
        payload: {
          username: credentials.username,
          password: credentials.password,
          email: credentials.email,
        },
      });
  }

  if (canRegister) {
    handlers.onRegister = (credentials) =>
      dispatch({
        type: "REGISTER",
        payload: {
          username: credentials.username,
          password: credentials.password,
          email: credentials.email,
        },
      });
  }

  return handlers;
}