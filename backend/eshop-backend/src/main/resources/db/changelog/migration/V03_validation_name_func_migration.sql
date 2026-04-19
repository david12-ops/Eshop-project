CREATE OR REPLACE FUNCTION public.fn_is_name_valid(text VARCHAR)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
BEGIN
    IF symbol IS NULL OR LEN(text) = 0 THEN
        RETURN FALSE;
    END IF;

    -- Reject punctuation or digits (blacklist)
    IF text ~ '[0-9!@#$%^&*()_=+{}[\]|;:",.<>/?`~]' THEN
        RETURN FALSE;
    END IF;

    -- Reject emojis (Unicode U+1F600 and above)
    IF EXISTS (
        SELECT 1
        FROM generate_series(1, LENGTH(text)) AS i
        WHERE unicode(substr(text, i, 1)) >= x'1F600'::int
    ) THEN
        RETURN FALSE;
    END IF;

    RETURN TRUE;
END;
$$;