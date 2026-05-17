CREATE TYPE role_type_enum AS ENUM ('admin', 'staff', 'customer');

CREATE TYPE resource_type_enum AS ENUM (
    'app_users',
    'roles',
    'app_permissions',
    'addresses',
    'categories',
    'currencies',
    'customers',
    'discounts',
    'invoices',
    'order_statuses',
    'orders',
    'payment_methods',
    'products',
    'regions'
);

CREATE TYPE operation_type_enum AS ENUM (
    'create',
    'read',
    'update',
    'delete'
);

CREATE TABLE roles (
    role_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL, 
    role_type role_type_enum NOT NULL,  
    role_description TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by INT NOT NULL,
    updated_by INT NOT NULL
);