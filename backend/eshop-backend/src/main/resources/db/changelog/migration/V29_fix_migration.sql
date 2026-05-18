ALTER TABLE app_users
ALTER COLUMN password_hash TYPE VARCHAR(255)
USING convert_from(password_hash, 'UTF8');