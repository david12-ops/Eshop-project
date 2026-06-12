export function AuthenticationView({ viewState, handlers }) {
  const { capabilities } = viewState;

  const {
    canLogin,
    canRegister,
  } = capabilities;

  const {
    onLogin,
    onRegister,
  } = handlers;

  const container = document.createElement("div");

  const title = document.createElement("h2");
  title.textContent = "Přihlášení / Registrace";

  container.appendChild(title);

  const form = document.createElement("form");

  const usernameInput = document.createElement("input");
  usernameInput.type = "text";
  usernameInput.placeholder = "Uživatelské jméno";
  form.appendChild(usernameInput);

  const emailInput = document.createElement("input");
  emailInput.type = "email";
  emailInput.placeholder = "Email";
  form.appendChild(emailInput);

  const passwordInput = document.createElement("input");
  passwordInput.type = "password";
  passwordInput.placeholder = "Heslo";
  form.appendChild(passwordInput);

  const getCredentials = () => ({
    username: usernameInput.value.trim(),
    email: emailInput.value.trim(),
    password: passwordInput.value,
  });

  if (canLogin && onLogin) {
    const loginButton = document.createElement("button");

    loginButton.type = "button";
    loginButton.textContent = "Přihlásit se";

    loginButton.addEventListener("click", () => {
      onLogin(getCredentials());
    });

    form.appendChild(loginButton);
  }

  if (canRegister && onRegister) {
    const registerButton = document.createElement("button");

    registerButton.type = "button";
    registerButton.textContent = "Registrovat";

    registerButton.addEventListener("click", () => {
      onRegister(getCredentials());
    });

    form.appendChild(registerButton);
  }

  container.appendChild(form);

  return container;
}