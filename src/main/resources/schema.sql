CREATE TABLE IF NOT EXISTS "user" (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id INT,
    roles VARCHAR(50),
    PRIMARY KEY (user_id, roles),
    FOREIGN KEY (user_id) REFERENCES "user" (id)
);
