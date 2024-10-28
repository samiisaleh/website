CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    bio TEXT,
    display_name VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Insert a default admin user (password: admin123)
INSERT INTO users (username, password, role, display_name, created_at)
VALUES (
    'admin',
    '$2a$12$8uR0Y7rFomYK1mQZqQH3v.LX3Oj0T0nQsGkKXh4f0tXc7jKR5n0W6',
    'ADMIN',
    'Administrator',
    CURRENT_TIMESTAMP
);

-- Create index for username lookups
CREATE INDEX idx_users_username ON users(username);