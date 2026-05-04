CREATE OR REPLACE FUNCTION public.fn_is_name_valid(name VARCHAR)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
BEGIN
    IF name IS NULL OR LENGTH(name) = 0 THEN
        RETURN FALSE;
    END IF;

    IF name !~ '^[[:alpha:]]+$' THEN
        RETURN FALSE;
    END IF;

    RETURN TRUE;
END;
$$;