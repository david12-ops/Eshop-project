// src/ui/views/DashboardView.js

export function DashboardView({ viewState, handlers }) {
  const { role, capabilities } = viewState;

  const {
    canEnterProductList,
    canEnterCart,
    canEnterOrderHistory,
    canEnterAuthentication,
    canEnterCategoryList,
    canEnterOrderList,
  } = capabilities;

  const {
    onEnterProductList,
    onEnterCart,
    onEnterOrderHistory,
    onEnterAuthentication,
    onEnterCategoryList,
    onEnterOrderList,
    onLogout,
  } = handlers;

  const container = document.createElement("div");

  const title = document.createElement("h2");
  title.textContent = "SportShop";
  container.appendChild(title);

  const roleInfo = document.createElement("p");
  roleInfo.textContent = `Role: ${role}`;
  container.appendChild(roleInfo);

  const nav = document.createElement("section");

  const navTitle = document.createElement("h3");
  navTitle.textContent = "Navigace";
  nav.appendChild(navTitle);

  if (canEnterProductList && onEnterProductList) {
    const btn = document.createElement("button");
    btn.textContent = "Produkty";
    btn.addEventListener("click", onEnterProductList);
    nav.appendChild(btn);
  }

  if (canEnterCart && onEnterCart) {
    const btn = document.createElement("button");
    btn.textContent = "Košík";
    btn.addEventListener("click", onEnterCart);
    nav.appendChild(btn);
  }

  if (canEnterOrderHistory && onEnterOrderHistory) {
    const btn = document.createElement("button");
    btn.textContent = "Historie objednávek";
    btn.addEventListener("click", onEnterOrderHistory);
    nav.appendChild(btn);
  }

  if (canEnterAuthentication && onEnterAuthentication) {
    const btn = document.createElement("button");
    btn.textContent = "Přihlášení";
    btn.addEventListener("click", onEnterAuthentication);
    nav.appendChild(btn);
  }

  if (canEnterCategoryList && onEnterCategoryList) {
    const btn = document.createElement("button");
    btn.textContent = "Kategorie";
    btn.addEventListener("click", onEnterCategoryList);
    nav.appendChild(btn);
  }

  if (canEnterOrderList && onEnterOrderList) {
    const btn = document.createElement("button");
    btn.textContent = "Správa objednávek";
    btn.addEventListener("click", onEnterOrderList);
    nav.appendChild(btn);
  }

  if (role !== "ANONYMOUS" && onLogout) {
    const btn = document.createElement("button");
    btn.textContent = "Odhlásit se";
    btn.addEventListener("click", onLogout);
    nav.appendChild(btn);
  }

  container.appendChild(nav);

  return container;
}