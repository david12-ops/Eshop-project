export function NavigationPanelComponent({ handlers }) {
  const {
    onEnterDashboard,
    onEnterProductList,
    onEnterCategoryList,
    onEnterCart,
    onEnterOrders,
    onEnterAuthentication,
  } = handlers;

  const navigationPanel = document.createElement("nav");

  if (onEnterDashboard) {
    const btn = document.createElement("button");
    btn.textContent = "Dashboard";
    btn.addEventListener("click", onEnterDashboard);
    navigationPanel.appendChild(btn);
  }

  if (onEnterProductList) {
    const btn = document.createElement("button");
    btn.textContent = "Produkty";
    btn.addEventListener("click", onEnterProductList);
    navigationPanel.appendChild(btn);
  }

  if (onEnterCategoryList) {
    const btn = document.createElement("button");
    btn.textContent = "Kategorie";
    btn.addEventListener("click", onEnterCategoryList);
    navigationPanel.appendChild(btn);
  }

  if (onEnterCart) {
    const btn = document.createElement("button");
    btn.textContent = "Košík";
    btn.addEventListener("click", onEnterCart);
    navigationPanel.appendChild(btn);
  }

  if (onEnterOrders) {
    const btn = document.createElement("button");
    btn.textContent = "Objednávky";
    btn.addEventListener("click", onEnterOrders);
    navigationPanel.appendChild(btn);
  }

  if (onEnterAuthentication) {
    const btn = document.createElement("button");
    btn.textContent = "Přihlášení";
    btn.addEventListener("click", onEnterAuthentication);
    navigationPanel.appendChild(btn);
  }

  return navigationPanel;
}