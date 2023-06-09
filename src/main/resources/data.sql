INSERT INTO USERS (firstname, lastname, email, password, role)
SELECT 'admin', 'ivanov', 'ivanov@mail.ru', '$2a$12$xeC2RIQo6GimUdbNstEoaObxagNGpLvy/UIiZbIjAmxxQzlRiC/fW', 'ROLE_ADMIN'
WHERE NOT EXISTS(SELECT * FROM users WHERE ROLE = 'ROLE_ADMIN');

INSERT INTO TARIFF(TYPE, INTEREST_RATE)
SELECT 'CONSUMER', '11.9%'
WHERE NOT EXISTS(SELECT * FROM tariff WHERE TYPE = 'CONSUMER');

INSERT INTO TARIFF(TYPE, INTEREST_RATE)
SELECT 'MORTGAGE', '5.9%%'
WHERE NOT EXISTS(SELECT * FROM tariff WHERE TYPE = 'MORTGAGE');

INSERT INTO TARIFF(TYPE, INTEREST_RATE)
SELECT 'SALE', '1.2%'
WHERE NOT EXISTS(SELECT * FROM tariff WHERE TYPE = 'SALE');