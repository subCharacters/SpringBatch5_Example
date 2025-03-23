DROP TABLE IF EXISTS product;
CREATE TABLE product
(
    id       BIGINT PRIMARY KEY,
    name     VARCHAR(255),
    price    INT,
    type     VARCHAR(2)
);