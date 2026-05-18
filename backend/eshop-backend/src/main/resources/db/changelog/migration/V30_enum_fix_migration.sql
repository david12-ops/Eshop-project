ALTER TYPE role_type_enum RENAME TO role_type_enum_old;

CREATE TYPE role_type_enum AS ENUM (
    'ADMIN',
    'STAFF',
    'CUSTOMER'
);

ALTER TABLE roles
ALTER COLUMN role_type TYPE role_type_enum
USING UPPER(role_type::text)::role_type_enum;

DROP TYPE role_type_enum_old;

ALTER TYPE operation_type_enum RENAME TO operation_type_enum_old;

CREATE TYPE operation_type_enum AS ENUM (
    'CREATE',
    'READ',
    'UPDATE',
    'DELETE'
);

ALTER TABLE app_permissions
ALTER COLUMN operation_type TYPE operation_type_enum
USING UPPER(operation_type::text)::operation_type_enum;

DROP TYPE operation_type_enum_old;

ALTER TYPE resource_type_enum RENAME TO resource_type_enum_old;

CREATE TYPE resource_type_enum AS ENUM (
    'APP_USERS',
    'ROLES',
    'APP_PERMISSIONS',
    'ADDRESSES',
    'CATEGORIES',
    'CURRENCIES',
    'CUSTOMERS',
    'DISCOUNTS',
    'INVOICES',
    'ORDER_STATUSES',
    'ORDERS',
    'PAYMENT_METHODS',
    'PRODUCTS',
    'REGIONS'
);

ALTER TABLE app_permissions
ALTER COLUMN resource_type TYPE resource_type_enum
USING UPPER(resource_type::text)::resource_type_enum;

DROP TYPE resource_type_enum_old;