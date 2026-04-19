CREATE TABLE invoices (
	invoice_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	invoice_code VARCHAR(25) UNIQUE NOT NULL,
	order_id INT NOT NULL,
	issue_date DATE NOT NULL,
	due_date DATE NOT NULL,
	payment_date DATE NULL,
	payment_method_id INT NOT NULL,
	billing_address_id INT NOT NULL,
	currency_code CHAR(3) NOT NULL,
	subtotal NUMERIC(10,2) NOT NULL,
	tax_amount NUMERIC(10,2) NOT NULL,
	total_amount NUMERIC(10,2) NOT NULL,
	notes TEXT,
	created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
   	updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);