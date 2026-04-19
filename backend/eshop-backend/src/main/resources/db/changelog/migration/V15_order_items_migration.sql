CREATE TABLE order_items(
    order_item_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    line_total NUMERIC(10,2) NOT NULL,
    notes TEXT,
    unit_price NUMERIC(10,2) NOT NULL,
    CONSTRAINT uq_order_item UNIQUE (order_id, product_id)
);