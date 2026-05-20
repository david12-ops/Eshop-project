CREATE TYPE discount_type_enum AS ENUM ('percent', 'fixed');

CREATE TABLE discounts (
    discount_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    discount_name VARCHAR(50) UNIQUE NOT NULL,
    discount_code VARCHAR(25) UNIQUE NOT NULL,
    discount_type discount_type_enum NOT NULL,
    discount_value NUMERIC(10,2) NOT NULL,
    discount_description TEXT,
    valid_from DATE,
    valid_to DATE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by INT NOT NULL,
    updated_by INT NOT NULL
);

CREATE TABLE order_discounts(
	order_id INT REFERENCES orders(order_id),
	discount_id INT REFERENCES discounts(discount_id),
	PRIMARY KEY(order_id, discount_id)
);

CREATE TABLE invoice_discounts(
	invoice_id INT REFERENCES invoices(invoice_id),
	discount_id INT REFERENCES discounts(discount_id),
	PRIMARY KEY(invoice_id, discount_id)
);

-- je model