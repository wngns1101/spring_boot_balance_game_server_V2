CREATE TABLE auth
(
    auth_id    BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    auth_group VARCHAR(255) NOT NULL,
    created_at DATETIME(6)  NOT NULL,
    deleted_at DATETIME(6)  NULL,
    updated_at DATETIME(6)  NOT NULL
);