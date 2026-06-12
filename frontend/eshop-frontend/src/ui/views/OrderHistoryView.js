export function OrderHistoryView({ viewState, handlers }) {
    const { orders, capabilities } = viewState;

    const {
        canBackToDashboard,
    } = capabilities;

    const {
        onBackToDashboard,
    } = handlers;

    const container = document.createElement("div");

    const title = document.createElement("h2");
    title.textContent = "Historie objednávek";

    container.appendChild(title);

    const list = document.createElement("ul");

    orders.forEach((order) => {
        const item = document.createElement("li");

        item.textContent =
            `Objednávka #${order.id}`;

        list.appendChild(item);
    });

    container.appendChild(list);

    if (canBackToDashboard && onBackToDashboard) {
        const btn = document.createElement("button");

        btn.textContent = "Zpět na dashboard";

        btn.addEventListener("click", onBackToDashboard);

        container.appendChild(btn);
    }

    return container;
}