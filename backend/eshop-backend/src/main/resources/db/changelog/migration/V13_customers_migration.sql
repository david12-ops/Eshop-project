CREATE TABLE customers (
    customer_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    branch_name VARCHAR(50) UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    birth_date DATE NOT NULL,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    address_id INT NOT NULL,
    user_id INT UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by INT NOT NULL,
    updated_by INT NOT NULL,
    CONSTRAINT uq_customers_person UNIQUE (first_name, last_name, birth_date)
);