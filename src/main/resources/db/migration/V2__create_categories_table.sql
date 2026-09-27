CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);   

        INSERT INTO categories (name) VALUES ('バックエンド');
        INSERT INTO categories (name) VALUES ('フロントエンド');
        INSERT INTO categories (name) VALUES ('インフラ');