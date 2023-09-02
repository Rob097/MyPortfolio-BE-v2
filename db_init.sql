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


##############################
##  DUMMY DATA (31/08/2023) ##
##############################
-- Insert mock data into the 'skills' table
INSERT INTO skills (name)
VALUES
    ('Java'),
    ('React'),
    ('Spring Boot'),
    ('NextJS'),
    ('Docker'),
    ('Kubernetes');

-- Insert mock data into the 'users' table
INSERT INTO users (email, first_name, last_name)
VALUES
    ('user1@example.com', 'John', 'Doe'),
    ('user2@example.com', 'Jane', 'Smith');

-- Insert mock data into the 'diaries' table
INSERT INTO diaries (user_id)
VALUES
    (1),
    (2);

-- Insert mock data into the 'educations' table
INSERT INTO educations (degree, description, school, start_date, user_id)
VALUES
    ('Bachelor of Science', 'Computer Science', 'University of XYZ', '2020-09-01', 1),
    ('Master of Engineering', 'Software Engineering', 'ABC University', '2018-09-01', 2);

-- Insert mock data into the 'experiences' table
INSERT INTO experiences (description, start_date, user_id)
VALUES
    ('Software Developer', '2022-01-15', 1),
    ('Frontend Developer', '2021-06-01', 2);

-- Insert mock data into the 'experience_skills' table
INSERT INTO experience_skills (experience_id, skill_id)
VALUES
    (1, 1),
    (1, 3),
    (2, 2),
    (2, 4);

-- Insert mock data into the 'projects' table
INSERT INTO projects (title, user_id, description)
VALUES
    ('E-commerce Platform', 1, 'Developed a scalable e-commerce platform'),
    ('Personal Portfolio', 2, 'Created a portfolio website showcasing frontend skills');

-- Insert mock data into the 'project_skills' table
INSERT INTO project_skills (project_id, skill_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (2, 4);

-- Insert mock data into the 'stories' table
INSERT INTO stories (description, title, diary_id)
VALUES
    ('Today I worked on a new feature.', 'Feature Development', 1),
    ('Completed the frontend redesign.', 'Frontend Redesign', 2);

-- Insert mock data into the 'story_educations' table
INSERT INTO story_educations (story_id, education_id)
VALUES
    (1, 1),
    (2, 2);

-- Insert mock data into the 'story_experiences' table
INSERT INTO story_experiences (story_id, experience_id)
VALUES
    (1, 1),
    (2, 2);

-- Insert mock data into the 'story_projects' table
INSERT INTO story_projects (story_id, project_id)
VALUES
    (1, 1),
    (2, 2);

-- Insert mock data into the 'story_skills' table
INSERT INTO story_skills (story_id, skill_id)
VALUES
    (1, 1),
    (1, 3),
    (2, 2),
    (2, 4);

-- Insert mock data into the 'user_skills' table
INSERT INTO user_skills (user_id, skill_id)
VALUES
    (1, 1),
    (1, 3),
    (2, 2),
    (2, 4);
