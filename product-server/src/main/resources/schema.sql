DROP TABLE IF EXISTS product;

CREATE TABLE product
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(120) NOT NULL,
    price               DOUBLE          NOT NULL
);

