-- Brands
INSERT INTO brand (id, name) VALUES (1, 'ZARA');

-- Currencies
INSERT INTO currency (id, name) VALUES (1, 'EUR');
INSERT INTO currency (id, name) VALUES (2, 'USD');
INSERT INTO currency (id, name) VALUES (3, 'GBP');

-- Products
INSERT INTO product (id, name) VALUES (35455, 'MEN STRIPPED T-SHIRT SLIM FIT WHITE 202502 COLLECTION');

-- Rates
INSERT INTO rate (id, name) VALUES (1, 'Standard rate 1');
INSERT INTO rate (id, name) VALUES (2, 'Standard rate 2');
INSERT INTO rate (id, name) VALUES (3, 'Standard rate 3');
INSERT INTO rate (id, name) VALUES (4, 'Standard rate 4');

-- Prices
INSERT INTO price (id, brand_id, rate_id, currency_id, product_id, amount, start_date, end_date, priority)
VALUES (1, 1, 1, 1, 35455, 35.50, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 0);

INSERT INTO price (id, brand_id, rate_id, currency_id, product_id, amount, start_date, end_date, priority)
VALUES (2, 1, 2, 1, 35455, 25.45, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 1);

INSERT INTO price (id, brand_id, rate_id, currency_id, product_id, amount, start_date, end_date, priority)
VALUES (3, 1, 3, 1, 35455, 30.50, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 1);

INSERT INTO price (id, brand_id, rate_id, currency_id, product_id, amount, start_date, end_date, priority)
VALUES (4, 1, 4, 1, 35455, 38.95, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 1);