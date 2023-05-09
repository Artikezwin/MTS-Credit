INSERT INTO USERS (firstname, lastname, email, password, role)
SELECT 'admin', 'ivanov', 'ivanov@mail.ru', '$2a$12$xeC2RIQo6GimUdbNstEoaObxagNGpLvy/UIiZbIjAmxxQzlRiC/fW', 'ROLE_ADMIN'
WHERE NOT EXISTS(SELECT * FROM users WHERE ROLE = 'ROLE_ADMIN');