CREATE TABLE tasks (
id SERIAL PRIMARY KEY,
name VARCHAR(255) NOT NULL,
duration BIGINT NOT NULL,
status VARCHAR(255) NOT NULL DEFAULT 'NEW',
result TEXT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);