export function ProductListView({ viewState, handlers }) {
    const { products, capabilities } = viewState;

    const {
        canEnterDetail,
        canCreateProduct,
    } = capabilities;

    const {
        onEnterDetail,
        onCreateProduct,
    } = handlers;

    const container = document.createElement("div");

    const title = document.createElement("h2");
    title.textContent = "Produkty";
    container.appendChild(title);

    if (canCreateProduct && onCreateProduct) {
        const btn = document.createElement("button");
        btn.textContent = "Vytvořit produkt";

        btn.addEventListener("click", () =>
            onCreateProduct({
                name: "Nový produkt",
                price: 0,
            })
        );

        container.appendChild(btn);
    }

    const list = document.createElement("ul");

    products.forEach((product) => {
        const item = document.createElement("li");

        item.textContent = `${product.name} (${product.price} Kč)`;

        if (canEnterDetail && onEnterDetail) {
            const detailBtn = document.createElement("button");
            detailBtn.textContent = "Detail";
            detailBtn.addEventListener("click", () => (
                onEnterDetail(product.id)
            ));

            item.appendChild(detailBtn);
        }

        list.appendChild(item);
    });

    container.appendChild(list);

    return container;
}