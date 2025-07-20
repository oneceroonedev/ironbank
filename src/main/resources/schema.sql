-- ====================
-- USERS TABLE (ABSTRACT)
-- ====================
CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    birth_date DATE,
    user_type VARCHAR(31) NOT NULL
);

-- ====================
-- ADMIN TABLE
-- ====================
CREATE TABLE IF NOT EXISTS admin (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES user(id)
);

-- ====================
-- THIRDPARTY TABLE
-- ====================
CREATE TABLE IF NOT EXISTS third_party (
    id BIGINT PRIMARY KEY,
    hashed_key VARCHAR(255) NOT NULL UNIQUE,
    FOREIGN KEY (id) REFERENCES user(id)
);

-- ====================
-- ACCOUNT_HOLDER TABLE
-- ====================
CREATE TABLE IF NOT EXISTS account_holder (
    id BIGINT PRIMARY KEY,
    primary_street VARCHAR(255),
    primary_city VARCHAR(255),
    primary_postal_code VARCHAR(255),
    primary_country VARCHAR(255),
    mailing_street VARCHAR(255),
    mailing_city VARCHAR(255),
    mailing_postal_code VARCHAR(255),
    mailing_country VARCHAR(255),
    FOREIGN KEY (id) REFERENCES user(id)
);

-- ====================
-- ACCOUNTS TABLE
-- ====================
CREATE TABLE IF NOT EXISTS account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    balance DECIMAL(38, 2) NOT NULL,
    secret_key VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    primary_owner_id BIGINT NOT NULL,
    secondary_owner_id BIGINT,
    account_type VARCHAR(31) NOT NULL,
    FOREIGN KEY (primary_owner_id) REFERENCES account_holder(id),
    FOREIGN KEY (secondary_owner_id) REFERENCES account_holder(id)
);

-- ====================
-- CHECKING_ACCOUNT
-- ====================
CREATE TABLE IF NOT EXISTS checking_account (
    id BIGINT PRIMARY KEY,
    minimum_balance DECIMAL(38, 2),
    monthly_maintenance_fee DECIMAL(38, 2),
    penalty_fee DECIMAL(38, 2),
    FOREIGN KEY (id) REFERENCES account(id)
);

-- ====================
-- STUDENT_CHECKING_ACCOUNT
-- ====================
CREATE TABLE IF NOT EXISTS student_checking (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES account(id)
);

-- ====================
-- SAVINGS ACCOUNT
-- ====================
CREATE TABLE IF NOT EXISTS savings (
    id BIGINT PRIMARY KEY,
    interest_rate DECIMAL(5, 4),
    minimum_balance DECIMAL(38, 2),
    penalty_fee DECIMAL(38, 2),
    FOREIGN KEY (id) REFERENCES account(id)
);

-- ====================
-- CREDIT CARD ACCOUNT
-- ====================
CREATE TABLE IF NOT EXISTS credit_card (
    id BIGINT PRIMARY KEY,
    interest_rate DECIMAL(5, 4),
    credit_limit DECIMAL(38, 2),
    FOREIGN KEY (id) REFERENCES account(id)
);