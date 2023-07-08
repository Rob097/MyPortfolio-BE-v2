-- Crea l'utente "myportfolio"
CREATE USER 'myportfolio'@'%' IDENTIFIED BY 'password';

-- Assegna tutti i privilegi all'utente "myportfolio"
GRANT ALL PRIVILEGES ON *.* TO 'myportfolio'@'%';

-- Crea i database "auth" e "core"
CREATE DATABASE IF NOT EXISTS auth;
CREATE DATABASE IF NOT EXISTS core;

-- Seleziona il database "auth"
USE auth;

-- Crea la tabella "permissions"
CREATE TABLE IF NOT EXISTS permissions (
                                           id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           description VARCHAR(255) NULL,
    name        VARCHAR(255) NULL
    );

-- Crea la tabella "roles"
CREATE TABLE IF NOT EXISTS roles (
                                     id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NULL
    );

-- Crea la tabella "role_permissions"
CREATE TABLE IF NOT EXISTS role_permissions (
                                                role_id       BIGINT NOT NULL,
                                                permission_id BIGINT NOT NULL,
                                                PRIMARY KEY (role_id, permission_id),
    CONSTRAINT FKegdk29eiy7mdtefy5c7eirr6e FOREIGN KEY (permission_id) REFERENCES permissions (id),
    CONSTRAINT FKn5fotdgk8d1xvo8nav9uv3muc FOREIGN KEY (role_id) REFERENCES roles (id)
    );

-- Popola la tabella "roles"
INSERT INTO roles (id, name)
VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_BASIC');

-- Popola la tabella "permissions"
INSERT INTO permissions (id, name, description)
VALUES
    (1, 'users_read', 'users_read'),
    (2, 'users_write', 'users_write');

-- Popola la tabella "role_permissions"
INSERT INTO role_permissions (role_id, permission_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 2);
