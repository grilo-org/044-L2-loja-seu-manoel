-- Primeiro, insira o usuário admin
INSERT INTO app_user (username, password, name, email, active) VALUES
('admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'Administrator', 'admin@seumanoel.com', true);

-- Use uma consulta para obter o ID gerado e inserir as roles
INSERT INTO user_roles (user_id, roles)
SELECT id, 'ROLE_ADMIN' FROM app_user WHERE username = 'admin';

INSERT INTO user_roles (user_id, roles)
SELECT id, 'ROLE_USER' FROM app_user WHERE username = 'admin';
