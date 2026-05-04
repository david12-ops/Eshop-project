CREATE OR REPLACE FUNCTION public.insert_and_get_currency
(
    p_currency_code CHAR(3) ,
    p_currency_name VARCHAR(50),
    p_symbol symbol_currency_type_enum,
    p_created_at TIMESTAMP WITH TIME ZONE,
    p_created_by INT,
    p_updated_at TIMESTAMP WITH TIME ZONE,
    p_updated_by INT
)
RETURNS CHAR(3)
LANGUAGE plpgsql
AS $$
    DECLARE 
        curr_code CHAR(3);

    BEGIN
        INSERT INTO currencies(currency_code, currency_name, symbol, created_at, created_by, updated_at, updated_by)
        VALUES (p_currency_code, p_currency_name, p_symbol, p_created_at, p_created_by, p_updated_at, p_updated_by)
        ON CONFLICT (currency_code) DO NOTHING
        RETURNING currency_code INTO curr_code;

        IF curr_code IS NULL THEN
            SELECT c.currency_code INTO curr_code FROM currencies c WHERE c.currency_code = p_currency_code;
        END IF;

        RETURN curr_code;
    END;
$$;

CREATE OR REPLACE FUNCTION public.insert_and_get_address
(
    p_street_name VARCHAR(50),
    p_city_name VARCHAR(50),
    p_state_name VARCHAR(50),
    p_postal_code VARCHAR(10),
    p_country_name VARCHAR(50),
    p_created_by INT,
    p_updated_by INT,
    p_created_at TIMESTAMP WITH TIME ZONE,
    p_updated_at TIMESTAMP WITH TIME ZONE,
    p_region_name VARCHAR(50),
    p_currency_code CHAR(3),
    p_currency_name VARCHAR(50),
    p_symbol symbol_currency_type_enum
)
RETURNS INT
LANGUAGE plpgsql
AS $$
    DECLARE 
        reg_id INT;
        addr_id INT;
        curr_code CHAR(3);

    BEGIN
        curr_code := insert_and_get_currency(p_currency_code, p_currency_name, p_symbol, p_created_at, p_created_by, p_updated_at, p_updated_by);

        INSERT INTO regions(region_name, currency_code, created_at, created_by, updated_at, updated_by)
        VALUES (p_region_name, curr_code, p_created_at, p_created_by, p_updated_at, p_updated_by)
        ON CONFLICT (region_name) DO NOTHING
        RETURNING regions.region_id INTO reg_id;

        IF reg_id IS NULL THEN
            SELECT r.region_id INTO reg_id FROM regions r WHERE r.region_name = p_region_name AND r.currency_code = curr_code;
        END IF;
 
        INSERT INTO addresses(street_name, city_name, state_name, postal_code, country_name, region_id, created_at, created_by, updated_at, updated_by)
        VALUES (p_street_name, p_city_name, p_state_name, p_postal_code, p_country_name, reg_id, p_created_at, p_created_by, p_updated_at, p_updated_by)
        ON CONFLICT (country_name, state_name, city_name, street_name, postal_code) DO NOTHING
        RETURNING address_id INTO addr_id;

        IF addr_id IS NULL THEN
            SELECT a.address_id INTO addr_id FROM addresses a WHERE a.country_name = p_country_name AND a.state_name = p_state_name AND a.city_name = p_city_name AND a.street_name = p_street_name AND a.postal_code = p_postal_code AND a.region_id = reg_id;
        END IF;

        RETURN addr_id;
    END;
$$;

CREATE OR REPLACE PROCEDURE public.customer_registration
(
    p_branch_name VARCHAR(50),
    p_first_name VARCHAR(50),
    p_last_name VARCHAR(50),
    p_birth_date DATE,
    p_phone_number VARCHAR(20),
    p_email VARCHAR(50),
    p_created_at TIMESTAMPTZ,
    p_updated_at TIMESTAMPTZ,
    p_created_by INT,
    p_updated_by INT,
    p_street_name VARCHAR(50),
    p_city_name VARCHAR(50),
    p_state_name VARCHAR(50),
    p_postal_code VARCHAR(10),
    p_country_name VARCHAR(50),
    p_region_name VARCHAR(50),
    p_currency_code CHAR(3),
    p_currency_name VARCHAR(50),
    p_symbol symbol_currency_type_enum
)
LANGUAGE plpgsql
AS $$
    DECLARE 
        addr_id INT;

    BEGIN
        IF p_branch_name IS NULL AND (p_first_name IS NULL OR p_last_name IS NULL) THEN
            RAISE EXCEPTION 'Either branch_name OR first_name + last_name must be provided';
        END IF;

        IF EXISTS (SELECT 1 FROM customers WHERE email = p_email OR phone_number = p_phone_number) THEN
            RAISE EXCEPTION 'Customer with the same email or phone number already exists';
        END IF;

        addr_id := public.insert_and_get_address(p_street_name, p_city_name, p_state_name, p_postal_code, p_country_name, p_created_by, p_updated_by, p_created_at, p_updated_at, p_region_name, p_currency_code, p_currency_name, p_symbol);
       
        INSERT INTO customers(branch_name, first_name, last_name, birth_date, phone_number, email, address_id, created_at, updated_at, created_by, updated_by)
        VALUES (p_branch_name, p_first_name, p_last_name, p_birth_date, p_phone_number, p_email, addr_id, p_created_at, p_updated_at, p_created_by, p_updated_by);

        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE;
    END;
$$;