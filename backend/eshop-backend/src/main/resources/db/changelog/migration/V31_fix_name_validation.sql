CREATE OR REPLACE FUNCTION public.fn_is_name_valid(
    p_name VARCHAR
)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS
$$
BEGIN

    IF p_name IS NULL
        OR LENGTH(TRIM(p_name)) = 0 THEN

        RETURN FALSE;

    END IF;

    IF p_name !~ '^[[:alpha:][:digit:] _-]+$' THEN

        RETURN FALSE;

    END IF;

    RETURN TRUE;

END;
$$;