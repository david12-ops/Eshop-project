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

CREATE OR REPLACE VIEW public.vw_customers_with_accounts AS
SELECT
    c.customer_id AS customer_id,
    COALESCE(c.branch_name, CONCAT(c.first_name, ' ', c.last_name)) AS customer_name,
    c.birth_date AS birth_date,
    c.phone_number AS phone_number,
    c.address_id AS customer_address_id,
    c.user_id AS customers_user_id,
    c.created_at AS customer_created_at,
    c.updated_at AS customer_updated_at,
    u.user_id AS users_user_id,
    u.username AS username,
    u.email AS email
FROM customers c
LEFT JOIN app_users u ON u.user_id = c.user_id;

CREATE OR REPLACE VIEW public.vw_products_with_categories AS
SELECT
    p.product_id AS product_id,
    p.product_name AS product_name,
    p.product_code AS product_code,
    p.product_description AS product_description,
    p.recommended_price AS recommended_price,
    p.unit_price AS unit_price,
    p.tax_rate AS tax_rate,
    p.category_id AS category_id,
    p.is_active AS product_is_active,
    p.created_at AS product_created_at,
    p.updated_at AS product_updated_at,
    c.category_name AS category_name,
    c.category_description AS category_description,
    c.is_active AS category_is_active,
    c.created_at AS category_created_at,
    c.updated_at AS category_updated_at
FROM products p
LEFT JOIN categories c ON c.category_id = p.category_id;

CREATE OR REPLACE VIEW public.vw_active_products_with_categories AS
SELECT *
FROM public.vw_products_with_categories
WHERE product_is_active IS TRUE AND category_is_active IS TRUE;

CREATE OR REPLACE VIEW public.vw_inactive_products_with_categories AS
SELECT *
FROM public.vw_products_with_categories
WHERE product_is_active IS NOT TRUE OR category_is_active IS NOT TRUE;


DROP FUNCTION IF EXISTS public.fn_is_currency_symbol_valid();