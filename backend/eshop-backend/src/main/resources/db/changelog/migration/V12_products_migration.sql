CREATE TABLE products (
    product_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_name VARCHAR(50) UNIQUE NOT NULL,
    product_code VARCHAR(25) UNIQUE NOT NULL,
    product_description TEXT,
    product_image_url TEXT NOT NULL,
    recommended_price NUMERIC(10,2) NOT NULL,
    unit_price NUMERIC(10,2) NOT NULL,
    tax_rate NUMERIC(5,2) NOT NULL,
    category_id INT,
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by INT NOT NULL,
    updated_by INT NOT NULL
);

--je model