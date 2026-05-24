ALTER TABLE app_users
ADD CONSTRAINT uq_app_users_username
UNIQUE (username);