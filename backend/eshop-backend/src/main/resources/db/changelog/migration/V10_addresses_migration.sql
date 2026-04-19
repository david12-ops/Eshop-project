CREATE TABLE addresses(
    address_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    street_name VARCHAR(100) NOT NULL,
    city_name VARCHAR(100) NOT NULL,
    state_name VARCHAR(100) NOT NULL,
    postal_code VARCHAR(10) NOT NULL,
    country_name VARCHAR(50) NOT NULL,
    region_id INT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by INT NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by INT NOT NULL,
    CONSTRAINT uq_addresses_full_address UNIQUE (country_name, state_name, city_name, street_name, postal_code)
);