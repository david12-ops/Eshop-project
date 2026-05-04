CREATE OR REPLACE FUNCTION public.insert_and_get_category
(
    p_category_name VARCHAR(50),
    p_category_description TEXT,
    p_is_active BOOLEAN,
    p_created_at TIMESTAMPTZ,
    p_updated_at TIMESTAMPTZ,
    p_created_by INT,
    p_updated_by INT
)
RETURNS INT
LANGUAGE plpgsql
AS $$
    DECLARE 
       catgr_id INT;

    BEGIN
        INSERT INTO categories(category_name, category_description, is_active, created_at, updated_at, created_by, updated_by)
        VALUES (p_category_name, p_category_description, p_is_active, p_created_at, p_updated_at, p_created_by, p_updated_by)
        ON CONFLICT (category_name) DO NOTHING
        RETURNING category_id INTO catgr_id;

        IF catgr_id IS NULL THEN
            SELECT c.category_id INTO catgr_id FROM categories c WHERE c.category_name = p_category_name;
        END IF;

        RETURN catgr_id;
    END;
$$;

CREATE OR REPLACE PROCEDURE public.product_registration
(
    p_product_name VARCHAR(50),
    p_product_code VARCHAR(25),
    p_product_description TEXT,
    p_product_image_url TEXT ,
    p_recommended_price NUMERIC(10,2),
    p_unit_price NUMERIC(10,2),
    p_tax_rate NUMERIC(5,2),
    p_is_active BOOLEAN,
    p_created_at TIMESTAMPTZ,
    p_updated_at TIMESTAMPTZ,
    p_created_by INT,
    p_updated_by INT,
    p_category_name VARCHAR(50),
    p_category_description TEXT,
    p_category_is_active BOOLEAN
)
LANGUAGE plpgsql
AS $$
    DECLARE 
        catgr_id INT;

    BEGIN
        catgr_id := insert_and_get_category(p_category_name, p_category_description, p_category_is_active, p_created_at, p_updated_at, p_created_by, p_updated_by);

        INSERT INTO products(product_name, product_code, product_description, product_image_url, recommended_price, unit_price, tax_rate, category_id, is_active, created_at, updated_at, created_by, updated_by)
        VALUES (p_product_name, p_product_code, p_product_description, p_product_image_url, p_recommended_price, p_unit_price, p_tax_rate, catgr_id, p_is_active, p_created_at, p_updated_at, p_created_by, p_updated_by);

        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE;
    END;
$$;