CREATE TABLE regions(
    region_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    region_name VARCHAR(50) UNIQUE NOT NULL,
    currency_code VARCHAR(3) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by INT NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by INT NOT NULL
);

-- je model