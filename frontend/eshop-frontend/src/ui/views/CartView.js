export function CartView({ viewState, handlers }) {
    const { cart, capabilities } = viewState;

    const {
        canRemoveFromCart,
        canCreateOrder,
    } = capabilities;

    const {
        onRemoveFromCart,
        onCreateOrder,
    } = handlers;

    const container = document.createElement("div");

    const title = document.createElement("h2");
    title.textContent = "Košík";
    container.appendChild(title);

    const list = document.createElement("ul");

    cart.forEach((product) => {
        const item = document.createElement("li");

        item.textContent = `${product.name} (${product.price} Kč)`;

        if (canRemoveFromCart && onRemoveFromCart) {
            const btn = document.createElement("button");
            btn.textContent = "Odebrat";

            btn.addEventListener("click", () =>
                onRemoveFromCart(product.id)
            );

            item.appendChild(btn);
        }

        list.appendChild(item);
    });

    container.appendChild(list);

    if (canCreateOrder && onCreateOrder) {
        const btn = document.createElement("button");
        btn.textContent = "Vytvořit objednávku";

        btn.addEventListener("click", onCreateOrder);

        container.appendChild(btn);
    }

    return container;
}