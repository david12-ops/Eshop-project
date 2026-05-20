DROP PROCEDURE IF EXISTS public.customer_registration;

DROP FUNCTION IF EXISTS public.insert_and_get_address;

DROP FUNCTION IF EXISTS public.insert_and_get_currency;

DROP VIEW IF EXISTS public.vw_orders_with_items CASCADE;
DROP VIEW IF EXISTS public.vw_customers_with_orders CASCADE;
DROP VIEW IF EXISTS public.vw_invoices_with_items CASCADE;

ALTER TABLE currencies
    ALTER COLUMN symbol TYPE VARCHAR(8)
    USING symbol::text;

DROP TYPE IF EXISTS symbol_currency_type_enum;

-- nejdřív odstranit CHECK constraint
ALTER TABLE discounts
    DROP CONSTRAINT IF EXISTS chk_discounts_discount_value;

-- převod enum -> text
ALTER TABLE discounts
    ALTER COLUMN discount_type TYPE TEXT
    USING discount_type::text;

-- smazání starého enumu
DROP TYPE IF EXISTS discount_type_enum;

-- vytvoření nového enumu
CREATE TYPE discount_type_enum AS ENUM (
    'PERCENTAGE',
    'FIXED_AMOUNT'
);

-- mapování starých hodnot na nové
ALTER TABLE discounts
    ALTER COLUMN discount_type TYPE discount_type_enum
    USING (
        CASE
            WHEN discount_type = 'percent' THEN 'PERCENTAGE'
            WHEN discount_type = 'fixed' THEN 'FIXED_AMOUNT'
        END
    )::discount_type_enum;

-- nový check constraint
ALTER TABLE discounts
ADD CONSTRAINT chk_discounts_discount_value CHECK (
    (
        discount_type = 'PERCENTAGE'::discount_type_enum
        AND discount_value >= 0
        AND discount_value <= 100
    )
    OR
    (
        discount_type = 'FIXED_AMOUNT'::discount_type_enum
        AND discount_value >= 0
    )
);

CREATE OR REPLACE VIEW public.vw_orders_with_items AS
SELECT
    o.order_id AS order_id,
    o.customer_id AS customer_id,
    o.order_date AS order_date,
    o.status_id AS order_status_id,
    o.currency_code AS currency_code,
    o.subtotal AS subtotal,
    o.tax_amount AS tax_amount,
    o.total_amount AS total_amount,
    o.delivery_address_id AS order_delivery_address_id,
    o.notes AS order_notes, 
    o.created_at AS order_created_at,
    o.updated_at AS order_updated_at,
    s.status_name AS order_status_name,
    s.status_type AS order_status_type,
    s.status_description AS order_status_description,
    s.created_at AS order_status_created_at,
    s.updated_at AS order_status_updated_at,
    c.currency_name AS currency_name,
    c.symbol AS currency_symbol,
    c.created_at AS currency_created_at,
    c.updated_at AS currency_updated_at,
    r.region_name AS order_region_name,
    r.created_at AS region_created_at,
    r.updated_at AS region_updated_at,
    a.state_name AS order_state_name,
    a.postal_code AS order_postal_code,
    a.country_name AS order_country_name,
    a.created_at AS order_delivery_address_created_at,
    a.updated_at AS order_delivery_address_updated_at,
    oi.order_item_id AS order_item_id,
    oi.quantity AS quantity,
    oi.line_total AS line_total,
    oi.notes AS item_notes,
    oi.unit_price AS unit_price
FROM orders o
LEFT JOIN order_items oi ON oi.order_id = o.order_id
LEFT JOIN order_statuses s ON s.status_id = o.status_id
JOIN currencies c ON c.currency_code = o.currency_code
JOIN addresses a ON a.address_id = o.delivery_address_id
LEFT JOIN regions r ON r.region_id = a.region_id;

CREATE OR REPLACE VIEW public.vw_customers_with_orders AS
SELECT
    c.customer_id AS customer_id,
    COALESCE(c.branch_name, CONCAT(c.first_name, ' ', c.last_name)) AS customer_name,
    c.birth_date AS birth_date,
    c.phone_number AS phone_number,
    c.address_id AS customer_address_id,
    c.user_id AS user_id,
    c.created_at AS customer_created_at,
    c.updated_at AS customer_updated_at,
    o.order_id AS order_id,
    o.customer_id AS order_customer_id,
    o.order_date AS order_date,
    o.status_id AS order_status_id,
    o.currency_code AS order_currency_code,
    o.subtotal AS order_subtotal,
    o.tax_amount AS order_tax_amount,
    o.total_amount AS order_total_amount,
    o.delivery_address_id AS order_delivery_address_id,
    o.notes AS order_notes,
    o.created_at AS order_created_at,
    o.updated_at AS order_updated_at,
    s.status_name AS order_status_name,
    s.status_type AS order_status_type,
    s.status_description AS order_status_description,
    s.created_at AS order_status_created_at,
    s.updated_at AS order_status_updated_at,
    cu.currency_name AS currency_name,
    cu.symbol AS currency_symbol,
    cu.created_at AS currency_created_at,
    cu.updated_at AS currency_updated_at,
    r.region_name AS order_region_name,
    r.created_at AS region_created_at,
    r.updated_at AS region_updated_at,
    a.state_name AS customer_state_name,
    a.postal_code AS customer_postal_code,
    a.country_name AS customer_country_name,
    a.created_at AS customer_address_created_at,
    a.updated_at AS customer_address_updated_at
FROM customers c
LEFT JOIN orders o ON o.customer_id = c.customer_id
LEFT JOIN order_statuses s ON s.status_id = o.status_id
LEFT JOIN currencies cu ON cu.currency_code = o.currency_code
JOIN addresses a ON a.address_id = c.address_id
LEFT JOIN regions r ON r.region_id = a.region_id;

CREATE OR REPLACE VIEW public.vw_invoices_with_items AS
SELECT 
    i.invoice_id AS invoice_id,
    i.invoice_code AS invoice_code,
    i.order_id AS order_id,
    i.issue_date AS issue_date,
    i.due_date AS due_date,
    i.payment_date AS payment_date,
	i.currency_code AS currency_code,
    i.billing_address_id AS billing_address_id,
	i.subtotal AS subtotal,
	i.tax_amount AS tax_amount,
	i.total_amount AS total_amount,
    i.notes AS invoice_notes, 
    i.payment_method_id AS payment_method_id,
    i.created_at AS invoice_created_at,
    i.updated_at AS invoice_updated_at,
    pm.method_name AS payment_method_name,
    pm.method_type AS payment_method_type,
    pm.method_description AS payment_method_description, 
    pm.created_at AS payment_method_created_at,
    pm.updated_at AS payment_method_updated_at,
    c.currency_name AS currency_name,
    c.symbol AS currency_symbol,
    c.created_at AS currency_created_at,
    c.updated_at AS currency_updated_at,
    r.region_name AS invoice_region_name,
    r.created_at AS region_created_at,
    r.updated_at AS region_updated_at,
    a.state_name AS billing_state_name,
    a.postal_code AS billing_postal_code,
    a.country_name AS billing_country_name,
    a.created_at AS billing_address_created_at,
    a.updated_at AS billing_address_updated_at,
    ii.invoice_item_id AS invoice_item_id,
    ii.line_total AS line_total,
    ii.quantity AS quantity,
    ii.notes AS item_notes,
    ii.unit_price AS unit_price
FROM invoices i
LEFT JOIN invoice_items ii ON ii.invoice_id = i.invoice_id 
JOIN payment_methods pm ON pm.payment_method_id = i.payment_method_id
JOIN currencies c ON c.currency_code = i.currency_code
JOIN addresses a ON a.address_id = i.billing_address_id
LEFT JOIN regions r ON r.region_id = a.region_id;

CREATE OR REPLACE FUNCTION public.insert_and_get_currency
(
    p_currency_code CHAR(3),
    p_currency_name VARCHAR(50),
    p_symbol VARCHAR(8),
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
        INSERT INTO currencies
        (
            currency_code,
            currency_name,
            symbol,
            created_at,
            created_by,
            updated_at,
            updated_by
        )
        VALUES
        (
            p_currency_code,
            p_currency_name,
            p_symbol,
            p_created_at,
            p_created_by,
            p_updated_at,
            p_updated_by
        )
        ON CONFLICT (currency_code) DO NOTHING
        RETURNING currency_code INTO curr_code;

        IF curr_code IS NULL THEN
            SELECT c.currency_code
            INTO curr_code
            FROM currencies c
            WHERE c.currency_code = p_currency_code;
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
    p_symbol VARCHAR(8)
)
RETURNS INT
LANGUAGE plpgsql
AS $$
    DECLARE
        reg_id INT;
        addr_id INT;
        curr_code CHAR(3);

    BEGIN
        curr_code := insert_and_get_currency(
            p_currency_code,
            p_currency_name,
            p_symbol,
            p_created_at,
            p_created_by,
            p_updated_at,
            p_updated_by
        );

        INSERT INTO regions
        (
            region_name,
            currency_code,
            created_at,
            created_by,
            updated_at,
            updated_by
        )
        VALUES
        (
            p_region_name,
            curr_code,
            p_created_at,
            p_created_by,
            p_updated_at,
            p_updated_by
        )
        ON CONFLICT (region_name) DO NOTHING
        RETURNING regions.region_id INTO reg_id;

        IF reg_id IS NULL THEN
            SELECT r.region_id
            INTO reg_id
            FROM regions r
            WHERE r.region_name = p_region_name
              AND r.currency_code = curr_code;
        END IF;

        INSERT INTO addresses
        (
            street_name,
            city_name,
            state_name,
            postal_code,
            country_name,
            region_id,
            created_at,
            created_by,
            updated_at,
            updated_by
        )
        VALUES
        (
            p_street_name,
            p_city_name,
            p_state_name,
            p_postal_code,
            p_country_name,
            reg_id,
            p_created_at,
            p_created_by,
            p_updated_at,
            p_updated_by
        )
        ON CONFLICT
        (
            country_name,
            state_name,
            city_name,
            street_name,
            postal_code
        ) DO NOTHING
        RETURNING address_id INTO addr_id;

        IF addr_id IS NULL THEN
            SELECT a.address_id
            INTO addr_id
            FROM addresses a
            WHERE a.country_name = p_country_name
              AND a.state_name = p_state_name
              AND a.city_name = p_city_name
              AND a.street_name = p_street_name
              AND a.postal_code = p_postal_code
              AND a.region_id = reg_id;
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
    p_symbol VARCHAR(8)
)
LANGUAGE plpgsql
AS $$
    DECLARE
        addr_id INT;

    BEGIN
        IF p_branch_name IS NULL
           AND (p_first_name IS NULL OR p_last_name IS NULL) THEN
            RAISE EXCEPTION
                'Either branch_name OR first_name + last_name must be provided';
        END IF;

        IF EXISTS
        (
            SELECT 1
            FROM customers
            WHERE email = p_email
               OR phone_number = p_phone_number
        ) THEN
            RAISE EXCEPTION
                'Customer with the same email or phone number already exists';
        END IF;

        addr_id := public.insert_and_get_address(
            p_street_name,
            p_city_name,
            p_state_name,
            p_postal_code,
            p_country_name,
            p_created_by,
            p_updated_by,
            p_created_at,
            p_updated_at,
            p_region_name,
            p_currency_code,
            p_currency_name,
            p_symbol
        );

        INSERT INTO customers
        (
            branch_name,
            first_name,
            last_name,
            birth_date,
            phone_number,
            email,
            address_id,
            created_at,
            updated_at,
            created_by,
            updated_by
        )
        VALUES
        (
            p_branch_name,
            p_first_name,
            p_last_name,
            p_birth_date,
            p_phone_number,
            p_email,
            addr_id,
            p_created_at,
            p_updated_at,
            p_created_by,
            p_updated_by
        );

        COMMIT;

    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE;
    END;
$$;
