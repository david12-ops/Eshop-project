CREATE TYPE method_type_enum AS ENUM (
    'cash',
    'credit_card',
    'debit_card',
    'paypal',
    'bank_transfer',
    'apple_pay',
    'other'
);

CREATE TABLE payment_methods (
    payment_method_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    method_name VARCHAR(50) UNIQUE NOT NULL,
    method_type method_type_enum NOT NULL,
    method_description TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by INT NOT NULL,
    updated_by INT NOT NULL
);

-- je model