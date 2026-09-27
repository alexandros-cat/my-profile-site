CREATE TABLE learning_data (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),  -- 外部キーの設定
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id),  -- 外部キーの設定
    study_month VARCHAR(10) NULL,
    study_item VARCHAR(255) NOT NULL,
    study_time  INTEGER NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
); 