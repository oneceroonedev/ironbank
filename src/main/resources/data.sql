-- ====================
-- CLEANUP
-- ====================
DELETE FROM credit_card;
DELETE FROM savings;
DELETE FROM student_checking;
DELETE FROM checking_account;
DELETE FROM account;
DELETE FROM third_party;
DELETE FROM account_holder;
DELETE FROM admin;
DELETE FROM user;

-- ====================
-- ADMINS
-- ====================
INSERT INTO user (id, name, username, password, birth_date, user_type)
VALUES
    (1, 'Admin1', 'admin1', 'admin123', '1985-01-01', 'Admin'),
    (2, 'Admin2', 'admin2', 'admin123', '1980-02-01', 'Admin');

INSERT INTO admin (id) VALUES (1), (2);

-- ====================
-- ACCOUNT HOLDERS
-- ====================
INSERT INTO user (id, name, username, password, birth_date, user_type)
VALUES
    (3, 'Mario Holder', 'mario', 'mario123', '2000-01-01', 'AccountHolder'),
    (4, 'Luigi Young', 'luigi', 'luigi123', '2010-03-15', 'AccountHolder');

INSERT INTO account_holder (
    id, primary_street, primary_city, primary_postal_code, primary_country,
    mailing_street, mailing_city, mailing_postal_code, mailing_country
)
VALUES
    (3, 'Calle Falsa 123', 'Madrid', '28080', 'España', 'Calle Alterna 1', 'Madrid', '28081', 'España'),
    (4, 'Av. Infantil 456', 'Valencia', '46000', 'España', 'Av. Alternativa 2', 'Valencia', '46001', 'España');

-- ====================
-- THIRDPARTY
-- ====================
INSERT INTO user (id, name, username, password, birth_date, user_type)
VALUES (5, 'Stripe System', 'stripe', 'stripe123', '2000-01-01', 'ThirdParty');

INSERT INTO third_party (id, hashed_key) VALUES (5, 'STRIPE_HASH_ABC123');

-- ====================
-- ACCOUNTS
-- ====================

-- 1. CHECKING Account for Mario (25 años)
INSERT INTO account (id, balance, secret_key, status, primary_owner_id, secondary_owner_id, account_type)
VALUES (1, 1800.00, 'sk1', 'ACTIVE', 3, NULL, 'CheckingAccount');

INSERT INTO checking_account (id, minimum_balance, monthly_maintenance_fee, penalty_fee)
VALUES (1, 250.00, 12.00, 40.00);

-- 2. STUDENT Account for Lucia (15 años)
INSERT INTO account (id, balance, secret_key, status, primary_owner_id, secondary_owner_id, account_type)
VALUES (2, 300.00, 'sk2', 'ACTIVE', 4, NULL, 'StudentChecking');

INSERT INTO student_checking (id) VALUES (2);

-- 3. SAVINGS Account for Mario
INSERT INTO account (id, balance, secret_key, status, primary_owner_id, secondary_owner_id, account_type)
VALUES (3, 5000.00, 'sk3', 'ACTIVE', 3, NULL, 'Savings');

INSERT INTO savings (id, interest_rate, minimum_balance, penalty_fee)
VALUES (3, 0.0025, 1000.00, 40.00);

-- 4. CREDIT CARD Account for Lucia
INSERT INTO account (id, balance, secret_key, status, primary_owner_id, secondary_owner_id, account_type)
VALUES (4, -200.00, 'sk4', 'ACTIVE', 4, NULL, 'CreditCard');

INSERT INTO credit_card (id, interest_rate, credit_limit)
VALUES (4, 0.12, 2000.00);