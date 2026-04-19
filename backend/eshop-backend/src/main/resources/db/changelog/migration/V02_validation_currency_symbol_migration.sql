CREATE OR REPLACE FUNCTION public.fn_is_currency_symbol_valid(symbol VARCHAR)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
BEGIN
    IF symbol IS NULL OR LEN(symbol) = 0 THEN
        RETURN FALSE;
    END IF;

    IF LENGTH(symbol) > 5 THEN 
        RETURN FALSE;
    END IF;

    IF symbol ~ '[0-9!@#$%^&*()_+=\\{}|;:"'',.<>/?`~]' THEN
        RETURN FALSE;
    END IF;

    IF EXISTS (
	SELECT 1 
	FROM generate_series(1, LENGTH(symbol)) AS i
	WHERE unicode(substr(symbol, i, 1)) >= x'1F000'::int
    ) THEN
	RETURN FALSE;
    END IF;

    RETURN TRUE;
       
END;
$$;
