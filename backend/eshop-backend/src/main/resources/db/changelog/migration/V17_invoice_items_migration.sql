CREATE TABLE invoice_items(
    invoice_item_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    invoice_id INT NOT NULL,
    product_id INT NOT NULL,
    line_total NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL,
    notes TEXT,
    unit_price NUMERIC(10,2) NOT NULL,
    CONSTRAINT uq_invoice_item UNIQUE (invoice_id, product_id) 
);

-- je model
