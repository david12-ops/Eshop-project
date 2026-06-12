export function ProductDetailView({ viewState, handlers }) {
    const { product, capabilities } = viewState;

    const {
        canAddToCart,
        canUpdateProduct,
        canDeleteProduct,
        canBackToList,
    } = capabilities;

    const {
        onAddToCart,
        onUpdateProduct,
        onDeleteProduct,
        onBackToList,
    } = handlers;

    const container = document.createElement("div");

    if (!product) {
        container.textContent = "Produkt nebyl nalezen.";
        return container;
    }

    const title = document.createElement("h2");
    title.textContent = product.name;
    container.appendChild(title);

    const price = document.createElement("p");
    price.textContent = `Cena: ${product.price} Kč`;
    container.appendChild(price);

    if (canAddToCart && onAddToCart) {
        const btn = document.createElement("button");
        btn.textContent = "Přidat do košíku";

        btn.addEventListener("click", () =>
            onAddToCart(product.id)
        );

        container.appendChild(btn);
    }

    if (canUpdateProduct && onUpdateProduct) {
        const btn = document.createElement("button");
        btn.textContent = "Upravit";

        btn.addEventListener("click", () =>
            onUpdateProduct(product.id, {
                name: product.name,
                price: product.price,
            })
        );

        container.appendChild(btn);
    }

    if (canDeleteProduct && onDeleteProduct) {
        const btn = document.createElement("button");
        btn.textContent = "Smazat";

        btn.addEventListener("click", () => (
            onDeleteProduct(product.id)
        ));

        container.appendChild(btn);
    }

    if (canBackToList && onBackToList) {
        const btn = document.createElement("button");
        btn.textContent = "Zpět";

        btn.addEventListener("click", onBackToList);

        container.appendChild(btn);
    }

    return container;
}