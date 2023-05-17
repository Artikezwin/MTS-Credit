INSERT INTO USERS (firstname, lastname, email, password, role)
VALUES ('admin', 'ivanov', 'ivanov@mail.ru', '$2a$12$xeC2RIQo6GimUdbNstEoaObxagNGpLvy/UIiZbIjAmxxQzlRiC/fW', 'ROLE_ADMIN');

INSERT INTO TARIFF(TYPE, INTEREST_RATE)
VALUES ('CONSUMER', '11.9%');

INSERT INTO TARIFF(TYPE, INTEREST_RATE)
VALUES ('MORTGAGE', '5.9%%');

INSERT INTO TARIFF(TYPE, INTEREST_RATE)
VALUES ('SALE', '1.2%');

INSERT INTO loan_order (order_id, user_id, tariff_id, credit_rating, status, time_insert)
VALUES ('37f38d66-6c55-43b5-b981-0f9fd0b448d7', 1, 1, 0.43, 'IN_PROGRESS', '2000-01-01 00:00:37');

INSERT INTO loan_order (order_id, user_id, tariff_id, credit_rating, status, time_insert)
VALUES ('37f38d66-6c55-43b5-b981-0f9fd0b448d6', 1, 2, 0.31, 'APPROVED', '2001-01-01 00:00:01');

INSERT INTO loan_order (order_id, user_id, tariff_id, credit_rating, status, time_insert)
VALUES ('37f38d66-6c55-43b5-b981-0f9fd0b448d5', 1, 3, 0.99, 'REFUSED', '2002-01-01 00:00:45');