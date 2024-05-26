CREATE TABLE user
(
    user_id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nickname        VARCHAR(255) NOT NULL,
    real_name       VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    birth           VARCHAR(255) NOT NULL,
    phone_number    VARCHAR(255) NOT NULL,
    invitation_code VARCHAR(255) NOT NULL,
    created_at      DATETIME(6)  NOT NULL,
    deleted_at      DATETIME(6)  NULL,
    updated_at      DATETIME(6)  NOT NULL
);