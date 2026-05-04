-- Roles
ALTER TABLE roles ADD CONSTRAINT chk_roles_role_name CHECK (public.fn_is_name_valid(role_name::varchar(50)));
ALTER TABLE roles ADD CONSTRAINT chk_roles_timestamps CHECK (created_at <= updated_at);

ALTER TABLE roles ADD CONSTRAINT fk_roles_created_by FOREIGN KEY (created_by) REFERENCES app_users(user_id); 
ALTER TABLE roles ADD CONSTRAINT fk_roles_updated_by FOREIGN KEY (updated_by) REFERENCES app_users(user_id);

CREATE INDEX idx_roles_role_type ON roles(role_type);
CREATE INDEX idx_roles_created_by ON roles(created_by);
CREATE INDEX idx_roles_updated_by ON roles(updated_by);
CREATE INDEX idx_roles_created_at ON roles (created_at);
CREATE INDEX idx_roles_updated_at ON roles (updated_at);

--------------------------------------------------------------------------------------------------------------------------

-- AppPermissions
ALTER TABLE app_permissions ADD CONSTRAINT chk_app_permissions_timestamps CHECK (created_at <= updated_at);

ALTER TABLE app_permissions ADD CONSTRAINT fk_app_permissions_role_id FOREIGN KEY (role_id) REFERENCES roles(role_id);
ALTER TABLE app_permissions ADD CONSTRAINT fk_app_permissions_created_by FOREIGN KEY (created_by) REFERENCES app_users(user_id); 
ALTER TABLE app_permissions ADD CONSTRAINT fk_app_permissions_updated_by FOREIGN KEY (updated_by) REFERENCES app_users(user_id);

CreATE INDEX idx_app_permissions_role_id ON app_permissions(role_id);
CREATE INDEX idx_app_permissions_resource_type_operation_type ON app_permissions(resource_type, operation_type);
CREATE INDEX idx_app_permissions_created_by ON app_permissions(created_by);
CREATE INDEX idx_app_permissions_updated_by ON app_permissions(updated_by);
CREATE INDEX idx_app_permissions_created_at ON app_permissions (created_at);
CREATE INDEX idx_app_permissions_updated_at ON app_permissions (updated_at);

---------------------------------------------------------------------------------------------------------------------------

-- AppUsers
ALTER TABLE app_users ADD CONSTRAINT chk_app_users_username CHECK (public.fn_is_name_valid(username::varchar(50)));
ALTER TABLE app_users ADD CONSTRAINT chk_app_users_email CHECK (email ~ '^[^@\s]+@[^@\s]+\.[^@\s]+$');
ALTER TABLE app_users ADD CONSTRAINT chk_app_users_timestamps CHECK (created_at <= updated_at);

ALTER TABLE app_users ADD CONSTRAINT fk_app_users_role_id FOREIGN KEY (role_id) REFERENCES roles(role_id);

CREATE INDEX idx_app_users_username ON app_users(username);
CREATE INDEX idx_app_users_role_id ON app_users(role_id);
CREATE INDEX idx_app_users_created_by ON app_users(created_by);
CREATE INDEX idx_app_users_updated_by ON app_users(updated_by);
CREATE INDEX idx_app_users_created_at ON app_users (created_at);
CREATE INDEX idx_app_users_updated_at ON app_users (updated_at);

---------------------------------------------------------------------------------------------------------------------------

-- Currencies
ALTER TABLE currencies ADD CONSTRAINT chk_currencies_currency_code CHECK (trim(currency_code) ~ '^[A-Z]{3}$');
ALTER TABLE currencies ADD CONSTRAINT chk_currencies_currency_name CHECK (public.fn_is_name_valid(currency_name::varchar(50)));
ALTER TABLE currencies ADD CONSTRAINT chk_currencies_timestamps CHECK (created_at <= updated_at);

ALTER TABLE currencies ADD CONSTRAINT fk_currencies_created_by FOREIGN KEY (created_by) REFERENCES app_users(user_id);
ALTER TABLE currencies ADD CONSTRAINT fk_currencies_updated_by FOREIGN KEY (updated_by) REFERENCES app_users(user_id);

CREATE INDEX idx_currencies_currency_name_symbol ON currencies(currency_name, symbol);
CREATE INDEX idx_currencies_created_by ON currencies(created_by);
CREATE INDEX idx_currencies_updated_by ON currencies(updated_by);
CREATE INDEX idx_currencies_created_at ON currencies (created_at);
CREATE INDEX idx_currencies_updated_at ON currencies (updated_at);

----------------------------------------------------------------------------------------------------------------------------

-- OrderStatuses
ALTER TABLE order_statuses ADD CONSTRAINT chk_order_statuses_timestamps CHECK (created_at <= updated_at);
ALTER TABLE order_statuses ADD CONSTRAINT chk_order_statuses_status_name CHECK (public.fn_is_name_valid(status_name::varchar(50)));

ALTER TABLE order_statuses ADD CONSTRAINT fk_order_statuses_created_by FOREIGN KEY (created_by) REFERENCES app_users(user_id); 
ALTER TABLE order_statuses ADD CONSTRAINT fk_order_statuses_updated_by FOREIGN KEY (updated_by) REFERENCES app_users(user_id);

CREATE INDEX idx_order_statuses_status_name ON order_statuses(status_name);
CREATE INDEX idx_order_statuses_created_by ON order_statuses(created_by);
CREATE INDEX idx_order_statuses_updated_by ON order_statuses(updated_by);
CREATE INDEX idx_order_statuses_created_at ON order_statuses (created_at);
CREATE INDEX idx_order_statuses_updated_at ON order_statuses (updated_at);


-----------------------------------------------------------------------------------------------------------------------------

-- PaymentMethods
ALTER TABLE payment_methods ADD CONSTRAINT chk_payment_methods_timestamps CHECK (created_at <= updated_at);
ALTER TABLE payment_methods ADD CONSTRAINT chk_payment_methods_method_name CHECK (public.fn_is_name_valid(method_name::varchar(50)));

ALTER TABLE payment_methods ADD CONSTRAINT fk_payment_methods_created_by FOREIGN KEY (created_by) REFERENCES app_users(user_id);
ALTER TABLE payment_methods ADD CONSTRAINT fk_payment_methods_updated_by FOREIGN KEY (updated_by) REFERENCES app_users(user_id);

CREATE INDEX idx_payment_methods_method_name ON payment_methods(method_name);
CREATE INDEX idx_payment_methods_created_by ON payment_methods(created_by);
CREATE INDEX idx_payment_methods_updated_by ON payment_methods(updated_by);
CREATE INDEX idx_payment_methods_created_at ON payment_methods (created_at);
CREATE INDEX idx_payment_methods_updated_at ON payment_methods (updated_at);

-----------------------------------------------------------------------------------------------------------------------------

-- Addresses
ALTER TABLE addresses ADD CONSTRAINT chk_addresses_timestamps CHECK (created_at <= updated_at);
ALTER TABLE addresses ADD CONSTRAINT chk_addresses_names CHECK (
	(public.fn_is_name_valid(street_name::varchar(50))) AND
	(public.fn_is_name_valid(city_name::varchar(50))) AND
	(public.fn_is_name_valid(state_name::varchar(50))) AND
	(public.fn_is_name_valid(country_name::varchar(50))));
ALTER TABLE addresses ADD CONSTRAINT chk_addresses_postal_code CHECK (postal_code ~ '^[0-9A-Za-z]{3,10}$');

ALTER TABLE addresses ADD CONSTRAINT fk_addresses_region_id FOREIGN KEY (region_id) REFERENCES regions(region_id); 
ALTER TABLE addresses ADD CONSTRAINT fk_addresses_created_by FOREIGN KEY (created_by) REFERENCES app_users(user_id); 
ALTER TABLE addresses ADD CONSTRAINT fk_addresses_updated_by FOREIGN KEY (updated_by) REFERENCES app_users(user_id); 

CREATE INDEX idx_addresses_created_by ON addresses(created_by);
CREATE INDEX idx_addresses_updated_by ON addresses(updated_by);
CREATE INDEX idx_addresses_created_at ON addresses (created_at);
CREATE INDEX idx_addresses_updated_at ON addresses (updated_at);
CREATE INDEX idx_addresses_region_id ON addresses(region_id);

-----------------------------------------------------------------------------------------------------------------------------

-- Categories
ALTER TABLE categories ADD CONSTRAINT chk_categories_timestamps CHECK (created_at <= updated_at);
ALTER TABLE categories ADD CONSTRAINT chk_categories_category_name CHECK (public.fn_is_name_valid(category_name::varchar(50)));

ALTER TABLE categories ADD CONSTRAINT fk_categories_created_by FOREIGN KEY (created_by) REFERENCES app_users(user_id); 
ALTER TABLE categories ADD CONSTRAINT fk_categories_updated_by FOREIGN KEY (updated_by) REFERENCES app_users(user_id); 

CREATE INDEX idx_categories_is_active_category_name ON categories(is_active, category_name);
CREATE INDEX idx_categories_created_by ON categories(created_by);
CREATE INDEX idx_categories_updated_by ON categories(updated_by);
CREATE INDEX idx_categories_created_at ON categories (created_at);
CREATE INDEX idx_categories_updated_at ON categories (updated_at);

------------------------------------------------------------------------------------------------------------------------------

-- Products
ALTER TABLE products ADD CONSTRAINT chk_products_timestamps CHECK (created_at <= updated_at);
ALTER TABLE products ADD CONSTRAINT chk_products_recommended_price CHECK (recommended_price >= 0);
ALTER TABLE products ADD CONSTRAINT chk_products_unit_price CHECK (unit_price >= 0);
ALTER TABLE products ADD CONSTRAINT chk_products_tax_rate CHECK (tax_rate >= 0);
ALTER TABLE products ADD CONSTRAINT chk_products_product_name CHECK (public.fn_is_name_valid(product_name::varchar(50)));
ALTER TABLE products ADD CONSTRAINT chk_products_product_code_name CHECK (product_code IS DISTINCT FROM product_name);
ALTER TABLE products ADD CONSTRAINT chk_products_product_code CHECK (product_code ~ '^PROD-[0-9]{6}-[0-9]{3,8}$');

ALTER TABLE products ADD CONSTRAINT fk_products_category_id FOREIGN KEY (category_id) REFERENCES categories(category_id);
ALTER TABLE products ADD CONSTRAINT fk_products_created_by FOREIGN KEY (created_by) REFERENCES app_users(user_id); 
ALTER TABLE products ADD CONSTRAINT fk_products_updated_by FOREIGN KEY (updated_by) REFERENCES app_users(user_id); 

CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_category_id_is_active ON products(category_id, is_active);
CREATE INDEX idx_products_is_active_product_name ON products(is_active, product_name);
CREATE INDEX idx_products_created_by ON products(created_by);
CREATE INDEX idx_products_updated_by ON products(updated_by);
CREATE INDEX idx_products_created_at ON products (created_at);
CREATE INDEX idx_products_updated_at ON products (updated_at);

------------------------------------------------------------------------------------------------------------------------------

-- Customers
ALTER TABLE customers ADD CONSTRAINT chk_customers_timestamps CHECK (created_at <= updated_at);
ALTER TABLE customers ADD CONSTRAINT chk_customers_names CHECK (branch_name IS NOT NULL OR (first_name IS NOT NULL AND last_name IS NOT NULL));
ALTER TABLE customers ADD CONSTRAINT chk_customers_phone_number CHECK (
    LENGTH(phone_number) BETWEEN 9 AND 16
    AND phone_number ~ '^\+?[0-9]+$'
);
ALTER TABLE customers ADD CONSTRAINT chk_customers_names_format CHECK (
	(public.fn_is_name_valid(branch_name::varchar(50))) AND
	(public.fn_is_name_valid(first_name::varchar(50))) AND
	(public.fn_is_name_valid(last_name::varchar(50))));

ALTER TABLE customers ADD CONSTRAINT fk_customers_address_id FOREIGN KEY (address_id) REFERENCES addresses(address_id);
ALTER TABLE customers ADD CONSTRAINT fk_customers_created_by FOREIGN KEY (created_by) REFERENCES app_users(user_id); 
ALTER TABLE customers ADD CONSTRAINT fk_customers_updated_by FOREIGN KEY (updated_by) REFERENCES app_users(user_id); 
ALTER TABLE customers ADD CONSTRAINT fk_customers_user_id FOREIGN KEY (user_id) REFERENCES app_users(user_id);

CREATE INDEX idx_customers_user_id ON customers(user_id);
CREATE INDEX idx_customers_address_id ON customers(address_id);
CREATE INDEX idx_customers_created_by ON customers(created_by);
CREATE INDEX idx_customers_updated_by ON customers(updated_by);
CREATE INDEX idx_customers_created_at ON customers (created_at);
CREATE INDEX idx_customers_updated_at ON customers (updated_at);

-------------------------------------------------------------------------------------------------------------------------------

-- Orders
ALTER TABLE orders ADD CONSTRAINT chk_orders_timestamps CHECK (created_at <= updated_at);
ALTER TABLE orders ADD CONSTRAINT chk_orders_currency_code CHECK (trim(currency_code) ~ '^[A-Z]{3}$');
ALTER TABLE orders ADD CONSTRAINT chk_orders_subtotal CHECK (subtotal >= 0);
ALTER TABLE orders ADD CONSTRAINT chk_orders_tax_amount CHECK (tax_amount >= 0);
ALTER TABLE orders ADD CONSTRAINT chk_orders_total_amount CHECK (total_amount >= 0 AND total_amount = subtotal + tax_amount);

ALTER TABLE orders ADD CONSTRAINT fk_orders_customer_id FOREIGN KEY (customer_id) REFERENCES customers(customer_id);
ALTER TABLE orders ADD CONSTRAINT fk_orders_status_id FOREIGN KEY (status_id) REFERENCES order_statuses(status_id);
ALTER TABLE orders ADD CONSTRAINT fk_orders_currency_code FOREIGN KEY (currency_code) REFERENCES currencies(currency_code);
ALTER TABLE orders ADD CONSTRAINT fk_orders_delivery_address_id FOREIGN KEY (delivery_address_id) REFERENCES addresses(address_id);
ALTER TABLE orders ADD CONSTRAINT fk_orders_created_by FOREIGN KEY (created_by) REFERENCES app_users(user_id);
ALTER TABLE orders ADD CONSTRAINT fk_orders_updated_by FOREIGN KEY (updated_by) REFERENCES app_users(user_id);

CREATE INDEX idx_orders_customer_id ON orders(customer_id);
CREATE INDEX idx_orders_delivery_address_id ON orders(delivery_address_id);
CREATE INDEX idx_orders_status_id ON orders(status_id);
CREATE INDEX idx_orders_currency_code ON orders(currency_code);
CREATE INDEX idx_orders_currency_code_order_date ON orders(currency_code, order_date);
CREATE INDEX idx_orders_created_by ON orders(created_by);
CREATE INDEX idx_orders_updated_by ON orders(updated_by);
CREATE INDEX idx_orders_created_at ON orders (created_at);
CREATE INDEX idx_orders_updated_at ON orders (updated_at);

-------------------------------------------------------------------------------------------------------------------------------

-- OrderItems
ALTER TABLE order_items ADD CONSTRAINT chk_order_items_line_total CHECK (line_total >= 0 AND line_total = unit_price * quantity);
ALTER TABLE order_items ADD CONSTRAINT chk_order_items_quantity CHECK (quantity >= 0);

ALTER TABLE order_items ADD CONSTRAINT fk_order_items_order_id FOREIGN KEY (order_id) REFERENCES orders(order_id);
ALTER TABLE order_items ADD CONSTRAINT fk_order_items_product_id FOREIGN KEY (product_id) REFERENCES products(product_id);

CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_product_id ON order_items(product_id);

-------------------------------------------------------------------------------------------------------------------------------

-- Invoices
ALTER TABLE invoices ADD CONSTRAINT chk_invoices_invoice_code CHECK (invoice_code ~ '^INV-[0-9]{6}-[0-9]{3,8}$');
ALTER TABLE invoices ADD CONSTRAINT chk_invoices_timestamps CHECK (created_at <= updated_at);
ALTER TABLE invoices ADD CONSTRAINT chk_invoices_subtotal CHECK (subtotal >= 0);
ALTER TABLE invoices ADD CONSTRAINT chk_invoices_tax CHECK (tax_amount >= 0);
ALTER TABLE invoices ADD CONSTRAINT chk_invoices_total_amount CHECK (total_amount >= 0 AND total_amount = subtotal + tax_amount);
ALTER TABLE invoices ADD CONSTRAINT chk_invoices_dates_nullable_payment CHECK 
(due_date >= issue_date AND (payment_date IS NULL OR payment_date >= issue_date));
ALTER TABLE invoices ADD CONSTRAINT chk_invoices_currency_code_format CHECK (trim(currency_code) ~ '^[A-Z]{3}$');

ALTER TABLE invoices ADD CONSTRAINT fk_invoices_order_id FOREIGN KEY (order_id) REFERENCES orders(order_id);
ALTER TABLE invoices ADD CONSTRAINT fk_invoices_payment_method_id FOREIGN KEY (payment_method_id) REFERENCES payment_methods(payment_method_id);
ALTER TABLE invoices ADD CONSTRAINT fk_invoices_currency_code FOREIGN KEY (currency_code) REFERENCES currencies(currency_code);
ALTER TABLE invoices ADD CONSTRAINT fk_invoices_billing_address_id FOREIGN KEY (billing_address_id) REFERENCES addresses(address_id);

CREATE INDEX idx_invoices_order_id         ON invoices(order_id);
CREATE INDEX idx_invoices_payment_method_id       ON invoices(payment_method_id);
CREATE INDEX idx_invoices_currency_code      ON invoices(currency_code);
CREATE INDEX idx_invoices_billing_address_id  ON invoices(billing_address_id);
CREATE INDEX idx_invoices_unpaid_due ON invoices(due_date) WHERE payment_date IS NULL;
CREATE INDEX idx_invoices_paid_date ON invoices(payment_date) WHERE payment_date IS NOT NULL;
CREATE INDEX idx_invoices_created_at ON invoices (created_at);
CREATE INDEX idx_invoices_updated_at ON invoices (updated_at);

--------------------------------------------------------------------------------------------------------------------------------

-- InvoiceItems
ALTER TABLE invoice_items ADD CONSTRAINT chk_invoice_items_line_total CHECK (line_total >= 0 AND line_total = unit_price * quantity);
ALTER TABLE invoice_items ADD CONSTRAINT chk_invoice_items_quantity CHECK (quantity >= 0);

ALTER TABLE invoice_items ADD CONSTRAINT fk_invoice_items_invoice_id FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id);
ALTER TABLE invoice_items ADD CONSTRAINT fk_invoice_items_product_id FOREIGN KEY (product_id) REFERENCES products(product_id);

CREATE INDEX idx_invoice_items_invoice_id ON invoice_items(invoice_id);
CREATE INDEX idx_invoice_items_product_id ON invoice_items(product_id);

---------------------------------------------------------------------------------------------------------------------------------

-- Discounts
ALTER TABLE discounts ADD CONSTRAINT chk_discounts_timestamps CHECK (created_at <= updated_at);
ALTER TABLE discounts ADD CONSTRAINT chk_discounts_discount_value CHECK (
	(discount_type = 'percent' AND discount_value >= 0 AND discount_value <= 100)
	OR
	(discount_type = 'fixed' AND discount_value >= 0)
);
ALTER TABLE discounts ADD CONSTRAINT chk_discounts_dates CHECK (
	(valid_from IS NULL AND valid_to IS NULL)
	OR
	(valid_from IS NOT NULL AND valid_to IS NOT NULL AND valid_from <= valid_to)
);
ALTER TABLE discounts ADD CONSTRAINT chk_discounts_discount_code_discount_name CHECK (discount_code IS DISTINCT FROM discount_name);
ALTER TABLE discounts ADD CONSTRAINT chk_discounts_discount_name CHECK (public.fn_is_name_valid(discount_name::varchar(50))); 
ALTER TABLE discounts ADD CONSTRAINT chk_discounts_discount_code CHECK (discount_code ~ '^DIS-[0-9]{6}-[0-9]{3,8}$');

ALTER TABLE discounts ADD CONSTRAINT uq_discounts_unique_discount_type_discount_value UNIQUE (discount_type, discount_value);

ALTER TABLE discounts ADD CONSTRAINT fk_discounts_created_by FOREIGN KEY (created_by) REFERENCES app_users(user_id); 
ALTER TABLE discounts ADD CONSTRAINT fk_discounts_updated_by FOREIGN KEY (updated_by) REFERENCES app_users(user_id); 

CREATE INDEX idx_order_discounts_discount_id ON order_discounts(discount_id);
CREATE INDEX idx_invoice_discounts_discount_id ON invoice_discounts(discount_id);

CREATE INDEX idx_discounts_valid_from_valid_to ON discounts(valid_from, valid_to);
CREATE INDEX idx_discounts_created_by ON discounts(created_by);
CREATE INDEX idx_discounts_updated_by ON discounts(updated_by);
CREATE INDEX idx_discounts_created_at ON discounts (created_at);
CREATE INDEX idx_discounts_updated_at ON discounts (updated_at);

----------------------------------------------------------------------------------------------------------------------------------

-- Regions
ALTER TABLE regions ADD CONSTRAINT chk_regions_currency_code CHECK (trim(currency_code) ~ '^[A-Z]{3}$');
ALTER TABLE regions ADD CONSTRAINT chk_regions_region_name CHECK (public.fn_is_name_valid(region_name::varchar(50)));
ALTER TABLE regions ADD CONSTRAINT chk_regions_timestamps CHECK (created_at <= updated_at);

ALTER TABLE regions ADD CONSTRAINT fk_regions_currency_code FOREIGN KEY (currency_code) REFERENCES currencies(currency_code);
ALTER TABLE regions ADD CONSTRAINT fk_regions_created_by FOREIGN KEY (created_by) REFERENCES app_users(user_id);
ALTER TABLE regions ADD CONSTRAINT fk_regions_updated_by FOREIGN KEY (updated_by) REFERENCES app_users(user_id);

CREATE INDEX idx_regions_currency_code ON regions(currency_code);
CREATE INDEX idx_regions_created_by ON regions(created_by);
CREATE INDEX idx_regions_updated_by ON regions(updated_by);
CREATE INDEX idx_regions_created_at ON regions (created_at);
CREATE INDEX idx_regions_updated_at ON regions (updated_at);