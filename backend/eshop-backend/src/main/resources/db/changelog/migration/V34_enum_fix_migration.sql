DROP VIEW public.vw_orders_with_items;
DROP VIEW public.vw_customers_with_orders;
DROP VIEW public.vw_invoices_with_items;

ALTER TYPE order_type_enum RENAME TO order_type_enum_old;

CREATE TYPE order_type_enum AS ENUM (
    'PENDING',
    'PROCESSING',
    'SHIPPED',
    'DELIVERED',
    'CANCELLED',
    'RETURNED'
);

ALTER TYPE method_type_enum RENAME TO method_type_enum_old;

CREATE TYPE method_type_enum AS ENUM (
    'CASH',
    'CREDIT_CARD',
    'DEBIT_CARD',
    'PAYPAL',
    'BANK_TRANSFER',
    'APPLE_PAY',
    'OTHER'
);


ALTER TABLE order_statuses
ALTER COLUMN status_type TYPE order_type_enum
USING UPPER(status_type::text)::order_type_enum;

DROP TYPE order_type_enum_old;

ALTER TABLE payment_methods
ALTER COLUMN method_type TYPE method_type_enum
USING UPPER(method_type::text)::method_type_enum;

DROP TYPE method_type_enum_old;