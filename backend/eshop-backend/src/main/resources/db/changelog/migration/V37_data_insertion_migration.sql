
ALTER TABLE addresses
DROP CONSTRAINT IF EXISTS chk_addresses_postal_code;

ALTER TABLE addresses
ADD CONSTRAINT chk_addresses_postal_code
CHECK (postal_code ~ '^[0-9A-Za-z -]{3,10}$');

ALTER TABLE customers
DROP CONSTRAINT IF EXISTS chk_customers_names;

ALTER TABLE customers
DROP CONSTRAINT IF EXISTS chk_customers_names_format;

ALTER TABLE customers
ADD CONSTRAINT chk_customers_names_and_format
CHECK (
    (
        branch_name IS NOT NULL
    )
    OR
    (
        first_name IS NOT NULL
        AND last_name IS NOT NULL
        AND public.fn_is_name_valid(first_name)
        AND public.fn_is_name_valid(last_name)
    )
);

ALTER TABLE discounts
DROP CONSTRAINT IF EXISTS chk_discounts_discount_name;

--zmena triggeru
CREATE OR REPLACE FUNCTION public.update_orders_totals()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE orders o
    SET subtotal = COALESCE(sub.sum_total, 0),
        total_amount = COALESCE(sub.sum_total, 0) + o.tax_amount
    FROM (
        SELECT
            oi.order_id,
            COALESCE(SUM(oi.line_total), 0) AS sum_total
        FROM order_items oi
        WHERE oi.order_id IN (
            SELECT order_id FROM new_table
        )
        GROUP BY oi.order_id
    ) sub
    WHERE o.order_id = sub.order_id;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION public.update_orders_totals_delete()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE orders o
    SET subtotal = COALESCE(sub.sum_total, 0),
        total_amount = COALESCE(sub.sum_total, 0) + o.tax_amount
    FROM (
        SELECT
            ord.order_id,
            COALESCE(SUM(oi.line_total), 0) AS sum_total
        FROM (
            SELECT order_id FROM old_table
        ) ord
        LEFT JOIN order_items oi
            ON oi.order_id = ord.order_id
        GROUP BY ord.order_id
    ) sub
    WHERE o.order_id = sub.order_id;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_update_order_totals_insert ON order_items;

DROP TRIGGER IF EXISTS trg_update_order_totals_update ON order_items;

DROP TRIGGER IF EXISTS trg_update_order_totals_delete ON order_items;

CREATE TRIGGER trg_update_order_totals_insert
AFTER INSERT ON order_items
REFERENCING NEW TABLE AS new_table
FOR EACH STATEMENT
EXECUTE FUNCTION public.update_orders_totals();

CREATE TRIGGER trg_update_order_totals_update
AFTER UPDATE ON order_items
REFERENCING NEW TABLE AS new_table
FOR EACH STATEMENT
EXECUTE FUNCTION public.update_orders_totals();

CREATE TRIGGER trg_update_order_totals_delete
AFTER DELETE ON order_items
REFERENCING OLD TABLE AS old_table
FOR EACH STATEMENT
EXECUTE FUNCTION public.update_orders_totals_delete();

CREATE OR REPLACE FUNCTION public.update_invoices_totals()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE invoices i
    SET subtotal = COALESCE(sub.sum_total, 0),
        total_amount = COALESCE(sub.sum_total, 0) + i.tax_amount
    FROM (
        SELECT
            ii.invoice_id,
            COALESCE(SUM(ii.line_total), 0) AS sum_total
        FROM invoice_items ii
        WHERE ii.invoice_id IN (
            SELECT invoice_id FROM new_table
        )
        GROUP BY ii.invoice_id
    ) sub
    WHERE i.invoice_id = sub.invoice_id;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION public.update_invoices_totals_delete()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE invoices i
    SET subtotal = COALESCE(sub.sum_total, 0),
        total_amount = COALESCE(sub.sum_total, 0) + i.tax_amount
    FROM (
        SELECT
            inv.invoice_id,
            COALESCE(SUM(ii.line_total), 0) AS sum_total
        FROM (
            SELECT invoice_id FROM old_table
        ) inv
        LEFT JOIN invoice_items ii
            ON ii.invoice_id = inv.invoice_id
        GROUP BY inv.invoice_id
    ) sub
    WHERE i.invoice_id = sub.invoice_id;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_update_invoice_totals_insert ON invoice_items;
DROP TRIGGER IF EXISTS trg_update_invoice_totals_update ON invoice_items;
DROP TRIGGER IF EXISTS trg_update_invoice_totals_delete ON invoice_items;

CREATE TRIGGER trg_update_invoice_totals_insert
AFTER INSERT ON invoice_items
REFERENCING NEW TABLE AS new_table
FOR EACH STATEMENT
EXECUTE FUNCTION public.update_invoices_totals();

CREATE TRIGGER trg_update_invoice_totals_update
AFTER UPDATE ON invoice_items
REFERENCING NEW TABLE AS new_table
FOR EACH STATEMENT
EXECUTE FUNCTION public.update_invoices_totals();

CREATE TRIGGER trg_update_invoice_totals_delete
AFTER DELETE ON invoice_items
REFERENCING OLD TABLE AS old_table
FOR EACH STATEMENT
EXECUTE FUNCTION public.update_invoices_totals_delete();


-- měny
INSERT INTO currencies (
    currency_code,
    currency_name,
    symbol,
    created_by,
    updated_by
) VALUES
('USD', 'US Dollar', '$', 1, 1),
('EUR', 'Euro', '€', 1, 1),
('CZK', 'Czech Koruna', 'Kč', 1, 1),
('GBP', 'British Pound', '£', 1, 1),
('JPY', 'Japanese Yen', '¥', 1, 1),
('PLN', 'Polish Zloty', 'zł', 1, 1);

-- platební metody
INSERT INTO payment_methods (
    method_name,
    method_type,
    method_description,
    created_by,
    updated_by
) VALUES
(
    'Cash Payment',
    'CASH',
    'Payment made using physical cash.',
    1,
    1
),
(
    'Credit Card Payment',
    'CREDIT_CARD',
    'Payment processed using a credit card.',
    1,
    1
),
(
    'Debit Card Payment',
    'DEBIT_CARD',
    'Payment processed using a debit card.',
    1,
    1
),
(
    'PayPal Payment',
    'PAYPAL',
    'Online payment processed through PayPal.',
    1,
    1
),
(
    'Bank Transfer Payment',
    'BANK_TRANSFER',
    'Payment transferred directly between bank accounts.',
    1,
    1
),
(
    'Apple Pay Payment',
    'APPLE_PAY',
    'Payment processed using Apple Pay.',
    1,
    1
),
(
    'Other Payment Method',
    'OTHER',
    'Other unspecified payment method.',
    1,
    1
);

-- stavy objednávek
INSERT INTO order_statuses (
    status_name,
    status_type,
    status_description,
    created_by,
    updated_by
) VALUES
(
    'Pending Order',
    'PENDING',
    'Order has been created and is awaiting processing.',
    1,
    1
),
(
    'Processing Order',
    'PROCESSING',
    'Order is currently being processed.',
    1,
    1
),
(
    'Shipped Order',
    'SHIPPED',
    'Order has been shipped to the customer.',
    1,
    1
),
(
    'Delivered Order',
    'DELIVERED',
    'Order has been successfully delivered.',
    1,
    1
),
(
    'Cancelled Order',
    'CANCELLED',
    'Order has been cancelled.',
    1,
    1
),
(
    'Returned Order',
    'RETURNED',
    'Order has been returned by the customer.',
    1,
    1
);

-- regiony
INSERT INTO regions (
    region_name,
    currency_code,
    created_by,
    updated_by
) VALUES
('North America', 'USD', 1, 1),
('Europe', 'EUR', 1, 1),
('Czech Republic', 'CZK', 1, 1),
('United Kingdom', 'GBP', 1, 1),
('Japan', 'JPY', 1, 1),
('Poland', 'PLN', 1, 1);

-- adresy
INSERT INTO addresses (
    street_name,
    city_name,
    state_name,
    postal_code,
    country_name,
    region_id,
    created_by,
    updated_by
) VALUES
(
    '5th Avenue 742',
    'New York',
    'New York',
    '10001',
    'United States',
    1,
    1,
    1
),
(
    'Champs-Élysées 10',
    'Paris',
    'Île-de-France',
    '75008',
    'France',
    2,
    1,
    1
),
(
    'Václavské náměstí 25',
    'Prague',
    'Prague',
    '11000',
    'Czech Republic',
    3,
    1,
    1
),
(
    'Oxford Street 221B',
    'London',
    'England',
    'W1D1BS',
    'United Kingdom',
    4,
    1,
    1
),
(
    'Shibuya Crossing 1',
    'Tokyo',
    'Tokyo',
    '1500002',
    'Japan',
    5,
    1,
    1
),
(
    'Marszałkowska 15',
    'Warsaw',
    'Masovian',
    '00-001',
    'Poland',
    6,
    1,
    1
);

-- zákazníci, tam bude jeden na existujícího customera
INSERT INTO customers (
    branch_name,
    first_name,
    last_name,
    birth_date,
    phone_number,
    address_id,
    user_id,
    created_by,
    updated_by
) VALUES (
    NULL,
    'David',
    'Novak',
    '1998-04-15',
    '+420777123456',
    3,
    3,
    1,
    1
);

INSERT INTO customers (
    branch_name,
    first_name,
    last_name,
    birth_date,
    phone_number,
    address_id,
    user_id,
    created_by,
    updated_by
) VALUES
(
    NULL,
    'Jan',
    'Svoboda',
    '1985-03-12',
    '+420601111111',
    3,
    NULL,
    1,
    1
),
(
    NULL,
    'Petra',
    'Novotna',
    '1992-07-25',
    '+420602222222',
    4,
    NULL,
    1,
    1
),
(
    'Tech Solutions Ltd.',
    NULL,
    NULL,
    '2000-01-01',
    '+420603333333',
    1,
    NULL,
    1,
    1
),
(
    NULL,
    'Martin',
    'Dvorak',
    '1978-11-08',
    '+420604444444',
    5,
    NULL,
    1,
    1
),
(
    'Global Trade s.r.o.',
    NULL,
    NULL,
    '2005-05-15',
    '+420605555555',
    2,
    NULL,
    1,
    1
),
(
    NULL,
    'Lucie',
    'Prochazkova',
    '1995-09-18',
    '+420606666666',
    6,
    NULL,
    1,
    1
);

-- objednávky
INSERT INTO orders (
    customer_id,
    order_date,
    status_id,
    currency_code,
    subtotal,
    tax_amount,
    total_amount,
    delivery_address_id,
    notes,
    created_by,
    updated_by
)
VALUES
(
    1,
    '2026-05-10',
    4,
    'CZK',
    108998.00,
    22889.58,
    131887.58,
    3,
    'Customer requested fast delivery.',
    1,
    1
),
(
    2,
    '2026-05-15',
    2,
    'EUR',
    42298.00,
    8882.58,
    51180.58,
    4,
    'Waiting for payment confirmation.',
    1,
    1
),
(
    3,
    '2026-05-18',
    1,
    'USD',
    54998.00,
    11549.58,
    66547.58,
    1,
    'Business customer order.',
    1,
    1
);

-- položky objednávek
INSERT INTO order_items (
    order_id,
    product_id,
    quantity,
    line_total,
    notes,
    unit_price
)
VALUES
(
    1,
    1,
    1,
    75999.00,
    'MacBook for office work.',
    75999.00
),
(
    1,
    7,
    2,
    5198.00,
    'Wireless mice bundle.',
    2599.00
),
(
    1,
    9,
    3,
    27897.00,
    'Headphones for team.',
    9299.00
),
(
    2,
    3,
    1,
    32999.00,
    'Customer selected blue color.',
    32999.00
),
(
    2,
    8,
    2,
    5998.00,
    'Mechanical keyboards.',
    2999.00
),
(
    2,
    10,
    1,
    3999.00,
    'Portable speaker.',
    3999.00
),
(
    3,
    2,
    1,
    52999.00,
    'Dell laptop.',
    52999.00
),
(
    3,
    11,
    1,
    2699.00,
    'WiFi router.',
    2699.00
);

-- faktury
INSERT INTO invoices (
    invoice_code,
    order_id,
    issue_date,
    due_date,
    payment_date,
    payment_method_id,
    billing_address_id,
    currency_code,
    subtotal,
    tax_amount,
    total_amount,
    notes
)
VALUES
(
    'INV-202605-0001',
    1,
    '2026-05-10',
    '2026-05-24',
    '2026-05-12',
    2,
    3,
    'CZK',
    108998.00,
    22889.58,
    131887.58,
    'Invoice paid successfully.'
),
(
    'INV-202605-0002',
    2,
    '2026-05-15',
    '2026-05-29',
    NULL,
    4,
    4,
    'EUR',
    42298.00,
    8882.58,
    51180.58,
    'Awaiting PayPal payment.'
),
(
    'INV-202605-0003',
    3,
    '2026-05-18',
    '2026-06-01',
    NULL,
    5,
    1,
    'USD',
    54998.00,
    11549.58,
    66547.58,
    'Corporate bank transfer invoice.'
);

-- položky faktur
INSERT INTO invoice_items (
    invoice_id,
    product_id,
    line_total,
    quantity,
    notes,
    unit_price
)
VALUES
(
    1,
    1,
    75999.00,
    1,
    'MacBook Pro invoice item.',
    75999.00
),
(
    1,
    7,
    5198.00,
    2,
    'Mouse bundle invoice item.',
    2599.00
),
(
    1,
    9,
    27897.00,
    3,
    'Headphones invoice item.',
    9299.00
),
(
    2,
    3,
    32999.00,
    1,
    'iPhone invoice item.',
    32999.00
),
(
    2,
    8,
    5998.00,
    2,
    'Keyboard invoice item.',
    2999.00
),
(
    2,
    10,
    3999.00,
    1,
    'Speaker invoice item.',
    3999.00
),
(
    3,
    2,
    52999.00,
    1,
    'Dell XPS invoice item.',
    52999.00
),
(
    3,
    11,
    2699.00,
    1,
    'Router invoice item.',
    2699.00
);

-- slevy
INSERT INTO discounts (
    discount_name,
    discount_code,
    discount_type,
    discount_value,
    discount_description,
    valid_from,
    valid_to,
    created_by,
    updated_by
)
VALUES
(
    'Spring Sale 10%',
    'DIS-202603-001',
    'PERCENTAGE',
    10.00,
    'Seasonal spring discount for selected products.',
    '2026-03-01',
    '2026-06-30',
    1,
    1
),
(
    'VIP Customer Discount',
    'DIS-202601-002',
    'FIXED_AMOUNT',
    500.00,
    'Fixed discount for VIP customers.',
    '2026-01-01',
    '2026-12-31',
    1,
    1
),
(
    'Black Friday 20%',
    'DIS-202611-003',
    'PERCENTAGE',
    20.00,
    'Black Friday promotional discount.',
    '2026-11-20',
    '2026-11-30',
    1,
    1
),
(
    'Free Shipping Bonus',
    'DIS-202601-004',
    'FIXED_AMOUNT',
    250.00,
    'Shipping compensation discount.',
    '2026-01-01',
    '2026-12-31',
    1,
    1
),
(
    'Loyalty Program',
    'DIS-202601-005',
    'PERCENTAGE',
    15.00,
    'Discount for loyal returning customers.',
    '2026-01-01',
    '2026-12-31',
    1,
    1
);

INSERT INTO order_discounts (
    order_id,
    discount_id
)
VALUES
(1, 1),
(1, 2),
(2, 4),
(3, 5);

INSERT INTO invoice_discounts (
    invoice_id,
    discount_id
)
VALUES
(1, 1),
(1, 2),
(2, 4),
(3, 5);