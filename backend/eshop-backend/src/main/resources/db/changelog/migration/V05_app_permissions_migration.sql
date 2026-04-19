CREATE TABLE app_permissions (
    role_id INT NOT NULL,
    operation_type operation_type_enum NOT NULL,
    resource_type resource_type_enum NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by INT NOT NULL,
    updated_by INT NOT NULL,
    PRIMARY KEY (role_id, resource_type, operation_type)
);